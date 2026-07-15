package com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid;

import com.finance.leluseven.finances.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.finances.plaid.domain.ContaBancaria;
import com.finance.leluseven.finances.plaid.domain.ProvedorOpenBankingPort;
import com.finance.leluseven.finances.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.finances.plaid.domain.vo.SyncResult;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.PlaidRequest;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.exchange.PlaidExchangeRequest;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.link.PlaidLinkTokenCreateRequest;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.link.User;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.transactions.PlaidTransactionsRequest;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.transactions.Transaction;
import com.finance.leluseven.finances.transacao.domain.Transacao;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.shared.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PlaidIntegrationAdapter implements ProvedorOpenBankingPort {

    private final PlaidFeignClient client;

    @Override
    public String criarLinkToken(String userId) {
        var user = new User(userId);
        var request = new PlaidLinkTokenCreateRequest(user, "LeLu Seven Finance", List.of("transactions"), List.of("US"), "pt");

        var response = client.criarLinkToken(request);

        if (response == null || response.link_token().isEmpty()) {
            throw new DataNotFoundException("Token vazio");
        }

        return response.link_token();
    }

    @Override
    public ConexaoPlaid trocarPublicToken(String publicToken) {
        var request = new PlaidExchangeRequest(publicToken);
        var response = client.trocarToken(request);

        var access_token = response.access_token();
        if (access_token == null)
            throw new DataNotFoundException("Token vazio!");

        var itemRequest = new PlaidRequest(access_token);
        var item = client.recuperarItem(itemRequest);

        if (item.item_id() == null)
            throw new DataNotFoundException("Não foi possível recuperar a instituição bancária!");

        return ConexaoPlaid.criar(access_token, item.item_id(), item.institution_name());
    }

    @Override
    public List<ContaBancaria> listarContas(PlaidToken plaidToken) {
        var request = new PlaidRequest(plaidToken.accessToken());

        var response = client.listarContas(request);

        if (response == null || response.isEmpty())
            throw new AssertionError("Não foi possível encontrar contas!");

        return response.stream().map(ContaBancaria::criar).toList();
    }

    @Override
    public SyncResult sincronizarTransacoes(PlaidToken plaidToken, String cursor) {
        var request = new PlaidTransactionsRequest(plaidToken.accessToken(), cursor);

        var response = client.sincronizarTransacoes(request);

        var adicionadas = response.added().stream().map(this::toTransacao).toList();

        var modificadas = response.modified().stream().map(this::toTransacao).toList();

        var removidasIds = response.removed().stream().map(Transaction::transaction_id).toList();

        return new SyncResult(
                adicionadas,
                modificadas,
                removidasIds,
                response.next_cursor(),
                response.has_more()
        );
    }

    private Transacao toTransacao(Transaction raw) {
        return Transacao.dePlaid(
                raw.transaction_id(),
                raw.name(),
                raw.amount(),
                raw.date(),
                raw.personal_finance_category().primary()
        );
    }
}
