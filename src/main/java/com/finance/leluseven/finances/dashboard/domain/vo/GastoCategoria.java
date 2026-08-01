package com.finance.leluseven.finances.dashboard.domain.vo;

import java.math.BigDecimal;

public record GastoCategoria(String categoria, BigDecimal total, long quantidade, BigDecimal percentual) {

    public static GastoCategoria de(String categoria, BigDecimal total, long quantidade, BigDecimal percentual) {
        return new GastoCategoria(categoria, total, quantidade, percentual);
    }
}
