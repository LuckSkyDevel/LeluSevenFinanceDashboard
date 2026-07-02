package com.finance.leluseven.conexaoplaid.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

public record CursorPlaid(String valor) {
    public CursorPlaid {
        if (valor == null || valor.isEmpty())
            throw new DomainException("Cursor Plaid inválido!");
    }

    public static CursorPlaid de(String valor) {
        return new CursorPlaid(valor);
    }
}
