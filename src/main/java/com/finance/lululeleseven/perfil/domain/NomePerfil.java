package com.finance.lululeleseven.perfil.domain;

import com.finance.lululeleseven.shared.exception.DomainException;
import org.apache.commons.lang3.StringUtils;

public record NomePerfil(String nome) {
    public NomePerfil {
        if (nome == null || StringUtils.isEmpty(nome)) {
            throw new DomainException("Nome de perfil invalido");
        }
    }

    public static NomePerfil de(String nome) {
        return new NomePerfil(nome);
    }

}
