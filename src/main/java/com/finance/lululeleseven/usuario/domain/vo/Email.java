package com.finance.lululeleseven.usuario.domain.vo;

import com.finance.lululeleseven.shared.exception.DomainException;

public record Email(String valor) {
    public Email {
        if (valor == null || !valor.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))
            throw new DomainException("E-mail inválido: " + valor);
    }

    public static Email de(String valor) {
        return new Email(valor);
    }
}
