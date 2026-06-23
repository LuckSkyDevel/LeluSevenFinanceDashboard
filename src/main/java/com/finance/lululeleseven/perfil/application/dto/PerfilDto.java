package com.finance.lululeleseven.perfil.application.dto;

import com.finance.lululeleseven.shared.exception.DomainException;

public record PerfilDto(String nome, String descricao) {
    public PerfilDto {
        if (nome == null || nome.isEmpty()) {
            throw new DomainException("Nome do perfil é obrigatório!");
        }

        if (nome.length() < 4 || nome.length() > 5) {
            throw new DomainException("Nome do perfil deve ter no mínimo 4 e no máximo 5 caracteres!");
        }
    }

    public static PerfilDto de(String nome, String descricao) {
        return new PerfilDto(nome, descricao);
    }

    public static PerfilDto deNome(String nome) {
        return new  PerfilDto(nome, "");
    }
}
