package com.finance.leluseven.finances.transacao.application;

import com.finance.leluseven.finances.transacao.domain.ITransacaoRepository;
import com.finance.leluseven.finances.transacao.domain.Transacao;
import com.finance.leluseven.finances.transacao.domain.vo.CodTransacao;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConsultaTransacaoPorCodUseCase {
    private final ITransacaoRepository repo;

    @Transactional
    public Transacao execute(Long codTransacao) {
        return repo.findById(CodTransacao.de(codTransacao))
                .orElseThrow(() -> new DataNotFoundException("Transação não foi encontrada para o codigo informado!"));
    }
}
