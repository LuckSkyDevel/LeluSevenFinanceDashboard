package com.finance.leluseven.finances.dashboard.application.dto;

import com.finance.leluseven.finances.dashboard.domain.vo.GastoCategoria;

import java.math.BigDecimal;

public record GastoCategoriaDto(String categoria, BigDecimal total, long quantidade, BigDecimal percentual) {

    public static GastoCategoriaDto de(GastoCategoria gasto) {
        return new GastoCategoriaDto(gasto.categoria(), gasto.total(), gasto.quantidade(), gasto.percentual());
    }
}
