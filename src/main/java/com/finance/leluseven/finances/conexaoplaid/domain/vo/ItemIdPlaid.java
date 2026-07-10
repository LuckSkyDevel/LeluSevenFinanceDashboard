package com.finance.leluseven.finances.conexaoplaid.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

public record ItemIdPlaid(String valor) {
    public ItemIdPlaid {
        if (valor == null || valor.isEmpty())
            throw new DomainException("Item ID Plaid Inválido!");
    }

    public static ItemIdPlaid de(String valor) {
        return new ItemIdPlaid(valor);
    }
}
