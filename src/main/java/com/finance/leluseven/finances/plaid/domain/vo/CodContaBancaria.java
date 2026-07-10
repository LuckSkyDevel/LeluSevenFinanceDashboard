package com.finance.leluseven.finances.plaid.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

public record CodContaBancaria(String codigo) {
    public CodContaBancaria {
        if (codigo == null || codigo.isEmpty()) {
            throw new DomainException("Código de conta bancária inválido ou nulo!");
        }
    }

    public static CodContaBancaria de(String codigo) {
        return new CodContaBancaria(codigo);
    }
}
