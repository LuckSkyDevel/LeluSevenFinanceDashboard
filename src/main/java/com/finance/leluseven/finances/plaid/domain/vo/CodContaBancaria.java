package com.finance.leluseven.finances.plaid.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

public record CodContaBancaria(Long codigo) {
    public CodContaBancaria {
        if (codigo == null || codigo <= 0) {
            throw new DomainException("Código de conta bancária inválido ou nulo!");
        }
    }

    public static CodContaBancaria de(Long codigo) {
        return new CodContaBancaria(codigo);
    }
}
