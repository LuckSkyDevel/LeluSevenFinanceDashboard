package com.finance.leluseven.finances.plaid.domain;

import com.finance.leluseven.finances.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.finances.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.finances.plaid.domain.vo.SyncResult;
import com.finance.leluseven.finances.transacao.domain.Transacao;

import java.time.LocalDate;
import java.util.List;

public interface ProvedorOpenBankingPort {
    String criarLinkToken(String userId);
    ConexaoPlaid trocarPublicToken(String publicToken);
    List<ContaBancaria> listarContas(PlaidToken accessToken);
    SyncResult sincronizarTransacoes(PlaidToken accessToken, String cursor);
}
