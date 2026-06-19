package com.finance.lululeleseven.perfil.domain;

import com.finance.lululeleseven.shared.exception.DomainException;

public record CodPerfil(Long valor) {
    public CodPerfil {
        if(valor == null || valor <= 0) {
            throw new DomainException("Código Perfil Invalido");
        }
    }

    public static CodPerfil de(Long valor) {
        return new CodPerfil(valor);
    }
}
