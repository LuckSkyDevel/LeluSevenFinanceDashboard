package com.finance.leluseven.finances.dashboard.application.dto;

import com.finance.leluseven.finances.dashboard.domain.RelatorioFinanceiro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ResumoFinanceiroDto(
        Long codUsuario,
        LocalDate periodoInicio,
        LocalDate periodoFim,
        BigDecimal totalReceitas,
        BigDecimal totalDespesas,
        BigDecimal saldo,
        int quantidadeTransacoes,
        List<GastoCategoriaDto> gastosPorCategoria
) {

    public static ResumoFinanceiroDto de(RelatorioFinanceiro relatorio) {
        var categorias = relatorio.getGastosPorCategoria().stream()
                .map(GastoCategoriaDto::de)
                .toList();

        return new ResumoFinanceiroDto(
                relatorio.getCodUsuario().valor(),
                relatorio.getPeriodoInicio(),
                relatorio.getPeriodoFim(),
                relatorio.getTotalReceitas(),
                relatorio.getTotalDespesas(),
                relatorio.getSaldo(),
                relatorio.getTransacoes().size(),
                categorias
        );
    }
}
