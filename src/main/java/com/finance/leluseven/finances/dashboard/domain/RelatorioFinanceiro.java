package com.finance.leluseven.finances.dashboard.domain;

import com.finance.leluseven.finances.dashboard.domain.vo.GastoCategoria;
import com.finance.leluseven.finances.transacao.domain.Transacao;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class RelatorioFinanceiro {
    private final CodUsuario codUsuario;
    private final LocalDate periodoInicio;
    private final LocalDate periodoFim;
    private final BigDecimal totalReceitas;
    private final BigDecimal totalDespesas;
    private final BigDecimal saldo;
    private final List<GastoCategoria> gastosPorCategoria;
    private final List<Transacao> transacoes;

    private RelatorioFinanceiro(CodUsuario codUsuario, LocalDate periodoInicio, LocalDate periodoFim,
                                BigDecimal totalReceitas, BigDecimal totalDespesas, BigDecimal saldo,
                                List<GastoCategoria> gastosPorCategoria, List<Transacao> transacoes) {
        this.codUsuario = codUsuario;
        this.periodoInicio = periodoInicio;
        this.periodoFim = periodoFim;
        this.totalReceitas = totalReceitas;
        this.totalDespesas = totalDespesas;
        this.saldo = saldo;
        this.gastosPorCategoria = gastosPorCategoria;
        this.transacoes = transacoes;
    }

    /**
     * Fábrica que aplica a regra de negócio de cálculo do relatório a partir
     * das transações já filtradas pelo período (ver ITransacaoRepository#findByUsuarioIdAndPeriodo).
     */
    public static RelatorioFinanceiro gerar(CodUsuario codUsuario, LocalDate periodoInicio, LocalDate periodoFim,
                                            List<Transacao> transacoes) {
        var totalDespesas = transacoes.stream()
                .filter(Transacao::isDebito)
                .map(t -> t.getValor().valor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var totalReceitas = transacoes.stream()
                .filter(Transacao::isCredito)
                .map(t -> t.getValor().valor().abs())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var saldo = totalReceitas.subtract(totalDespesas);
        var gastosPorCategoria = agruparPorCategoria(transacoes, totalDespesas);

        return new RelatorioFinanceiro(codUsuario, periodoInicio, periodoFim,
                totalReceitas, totalDespesas, saldo, gastosPorCategoria, transacoes);
    }

    private static List<GastoCategoria> agruparPorCategoria(List<Transacao> transacoes, BigDecimal totalDespesas) {
        var porCategoria = transacoes.stream()
                .filter(Transacao::isDebito)
                .collect(Collectors.groupingBy(
                        t -> t.getCategoria() == null || t.getCategoria().isBlank() ? "Sem categoria" : t.getCategoria(),
                        Collectors.toList()
                ));

        return porCategoria.entrySet().stream()
                .map(entry -> {
                    var total = entry.getValue().stream()
                            .map(t -> t.getValor().valor())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    var percentual = totalDespesas.compareTo(BigDecimal.ZERO) == 0
                            ? BigDecimal.ZERO
                            : total.multiply(BigDecimal.valueOf(100)).divide(totalDespesas, 2, RoundingMode.HALF_UP);

                    return GastoCategoria.de(entry.getKey(), total, entry.getValue().size(), percentual);
                })
                .sorted(Comparator.comparing(GastoCategoria::total).reversed())
                .toList();
    }
}
