package com.finance.leluseven.conexaoplaid.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

public record CodConexaoPlaid(Long valor) {
    public CodConexaoPlaid {
        if (valor == null || valor <= 0)
            throw new DomainException("Código de Conexao Plaid inválido!");
    }

    public static CodConexaoPlaid de(Long valor) {
        return new CodConexaoPlaid(valor);
    }
}
