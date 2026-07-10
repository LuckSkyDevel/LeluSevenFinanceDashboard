package com.finance.leluseven.finances.conexaoplaid.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

public record Instituicao(String valor) {
    public Instituicao {
        if (valor == null || valor.isEmpty())
            throw new DomainException("Instituição não é válida!");
    }

    public static Instituicao de(String valor) {
        return new Instituicao(valor);
    }
}
