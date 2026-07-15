package com.finance.leluseven.finances.transacao.application;

import com.finance.leluseven.finances.transacao.domain.ITransacaoRepository;
import com.finance.leluseven.finances.transacao.domain.Transacao;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultaTransacaoPorPlaidTransacaoIdUseCase {
    private final ITransacaoRepository repo;

    @Transactional
    public Transacao execute(String plaidTransacaoId) {
        return repo.findByPlaidTransacaoId(plaidTransacaoId).orElseThrow(() -> new DataNotFoundException("Transação/Plaid ID não foi encontrado!"));
    }
}
