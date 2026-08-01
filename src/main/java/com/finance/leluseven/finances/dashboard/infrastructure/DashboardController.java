package com.finance.leluseven.finances.dashboard.infrastructure;

import com.finance.leluseven.finances.dashboard.application.GerarGraficoGastosUseCase;
import com.finance.leluseven.finances.dashboard.application.GerarResumoUseCase;
import com.finance.leluseven.finances.dashboard.application.dto.GastoCategoriaDto;
import com.finance.leluseven.finances.dashboard.application.dto.ResumoFinanceiroDto;
import com.finance.leluseven.finances.dashboard.domain.GeradorRelatorioPdfPort;
import com.finance.leluseven.shared.infrastructure.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final GerarResumoUseCase gerarResumoUseCase;
    private final GerarGraficoGastosUseCase gerarGraficoGastosUseCase;
    private final GeradorRelatorioPdfPort geradorRelatorioPdfPort;

    /**
     * Resumo financeiro (receitas, despesas, saldo e categorias) em JSON.
     * Se "inicio"/"fim" não forem informados, assume o mês corrente.
     */
    @GetMapping("/resumo")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG','USER')")
    public ResponseEntity<ApiResponse<ResumoFinanceiroDto>> resumo(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        var periodo = resolverPeriodo(inicio, fim);
        var relatorio = gerarResumoUseCase.execute(user.getUsername(), periodo[0], periodo[1]);

        return ResponseEntity.ok(ApiResponse.success(ResumoFinanceiroDto.de(relatorio)));
    }

    /**
     * Somente o agrupamento de gastos por categoria, útil para alimentar um
     * gráfico no frontend sem trafegar a lista completa de transações.
     */
    @GetMapping("/gastos-por-categoria")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG','USER')")
    public ResponseEntity<ApiResponse<List<GastoCategoriaDto>>> gastosPorCategoria(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        var periodo = resolverPeriodo(inicio, fim);
        var gastos = gerarGraficoGastosUseCase.execute(user.getUsername(), periodo[0], periodo[1]).stream()
                .map(GastoCategoriaDto::de)
                .toList();

        return ResponseEntity.ok(ApiResponse.success(gastos));
    }

    /**
     * Mesmo relatório, exportado como arquivo PDF para download.
     */
    @GetMapping(value = "/resumo/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','MANAG','USER')")
    public ResponseEntity<byte[]> resumoPdf(
            @AuthenticationPrincipal UserDetails user,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        var periodo = resolverPeriodo(inicio, fim);
        var relatorio = gerarResumoUseCase.execute(user.getUsername(), periodo[0], periodo[1]);
        var pdf = geradorRelatorioPdfPort.gerar(relatorio);

        var nomeArquivo = "relatorio-financeiro-%s-a-%s.pdf".formatted(periodo[0], periodo[1]);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                .body(pdf);
    }

    private LocalDate[] resolverPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null)
            return new LocalDate[]{inicio, fim};

        var hoje = LocalDate.now();
        var inicioPadrao = hoje.with(TemporalAdjusters.firstDayOfMonth());
        var fimPadrao = hoje.with(TemporalAdjusters.lastDayOfMonth());

        return new LocalDate[]{inicioPadrao, fimPadrao};
    }
}
