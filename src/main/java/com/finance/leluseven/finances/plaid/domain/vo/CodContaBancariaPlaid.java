package com.finance.leluseven.finances.plaid.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

public record CodContaBancariaPlaid(String valor) {
    public CodContaBancariaPlaid {
        if (valor == null || valor.isEmpty())
            throw new DomainException("Codigo de Conta Bancaria da Integração com API externa Inválido!");
    }

    public static CodContaBancariaPlaid de(String valor) {
        return new CodContaBancariaPlaid(valor);
    }
}
