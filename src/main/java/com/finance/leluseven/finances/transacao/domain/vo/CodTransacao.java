package com.finance.leluseven.finances.transacao.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

public record CodTransacao(Long valor) {
    public CodTransacao {
        if (valor == null || valor <= 0)
            throw new DomainException("Codigo de transaçÃo inválido!");
    }

    public static CodTransacao de(Long valor) {
        return new CodTransacao(valor);
    }
}
