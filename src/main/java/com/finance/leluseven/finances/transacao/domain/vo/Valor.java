package com.finance.leluseven.finances.transacao.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

import java.math.BigDecimal;

public record Valor(BigDecimal valor) {
    public Valor {
        if (valor == null)
            throw new DomainException("Valor não pode ser nulo!");
    }

    public static Valor de (BigDecimal valor) {
        return new Valor(valor);
    }
}
