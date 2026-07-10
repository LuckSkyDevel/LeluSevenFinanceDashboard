package com.finance.leluseven.finances.conexaoplaid.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

public record AccessTokenPlaid(String valor) {
    public AccessTokenPlaid {
        if (valor == null || valor.isEmpty())
            throw new DomainException("Access Token Plaid Inválido!");
    }

    public static AccessTokenPlaid de(String valor) {
        return new AccessTokenPlaid(valor);
    }
}
