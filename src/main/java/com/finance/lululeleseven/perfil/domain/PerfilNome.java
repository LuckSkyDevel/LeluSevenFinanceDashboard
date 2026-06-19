package com.finance.lululeleseven.perfil.domain;

import com.finance.lululeleseven.shared.domain.ValueObject;
import com.finance.lululeleseven.shared.exception.DomainException;

import java.util.Set;

public class PerfilNome extends ValueObject<String> {

    private static final Set<String> VALORES_VALIDOS =
            Set.of("ADMIN", "USER", "MANAGER");

    private final String valor;

  private PerfilNome(String valor) {
        if (!VALORES_VALIDOS.contains(valor))
            throw new DomainException(
                    "Perfil inválido. Valores aceitos: " + VALORES_VALIDOS
            );
        this.valor = valor;
    }

    public static PerfilNome de(String valor) {
        return new PerfilNome(valor.toUpperCase());
    }

    @Override
    protected String valor() {
        return this.valor;
    }
}
