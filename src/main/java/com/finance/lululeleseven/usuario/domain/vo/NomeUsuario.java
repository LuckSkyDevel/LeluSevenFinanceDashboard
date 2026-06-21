package com.finance.lululeleseven.usuario.domain.vo;

import com.finance.lululeleseven.shared.exception.DomainException;
import org.apache.commons.lang3.StringUtils;

public record NomeUsuario(String valor) {

    public NomeUsuario {
        if (valor == null || StringUtils.isEmpty(valor)) {
            throw new DomainException("Nome de usuário obrigatório");
        }

        if (valor.length() < 5) {
            throw new DomainException("Nome de usuário deve conter no minimo 5 caracteres");
        }
    }

    public static NomeUsuario de(String valor) {
        return new NomeUsuario(valor);
    }
}
