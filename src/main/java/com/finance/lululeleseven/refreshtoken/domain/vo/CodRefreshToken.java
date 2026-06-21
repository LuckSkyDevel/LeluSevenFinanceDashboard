package com.finance.lululeleseven.refreshtoken.domain.vo;

import com.finance.lululeleseven.shared.exception.DomainException;

public record CodRefreshToken(Long valor) {
    public CodRefreshToken {
        if (valor == null || valor <= 0) {
            throw new DomainException("Código de refresh token informado invalido");
        }
    }

    public static CodRefreshToken de(Long valor) {
        return new CodRefreshToken(valor);
    }
}
