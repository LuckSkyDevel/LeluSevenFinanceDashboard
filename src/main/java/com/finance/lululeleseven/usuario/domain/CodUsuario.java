package com.finance.lululeleseven.usuario.domain;

import com.finance.lululeleseven.shared.exception.DomainException;

public record CodUsuario(Long valor) {

    public CodUsuario {
        if (valor == null || valor <= 0)
            throw new DomainException("ID inválido"); // Criar DomainException
    }

    public static CodUsuario de(Long valor) {
        return new CodUsuario(valor);
    }
}
