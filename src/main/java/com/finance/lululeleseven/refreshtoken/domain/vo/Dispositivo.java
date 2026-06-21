package com.finance.lululeleseven.refreshtoken.domain.vo;

import com.finance.lululeleseven.shared.exception.DomainException;
import org.apache.commons.lang3.StringUtils;

public record Dispositivo(String valor) {
    public Dispositivo {
        if (valor == null || StringUtils.isEmpty(valor)) {
            throw new DomainException("Dispositivo inválido!");
        }
    }

    public static Dispositivo de(String valor) {
        return new Dispositivo(valor);
    }
}
