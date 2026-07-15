package com.finance.leluseven.finances.transacao.infrastructure;

import com.finance.leluseven.finances.transacao.application.ConsultaTransacaoPorCodUseCase;
import com.finance.leluseven.finances.transacao.application.ConsultaTransacaoPorPlaidTransacaoIdUseCase;
import com.finance.leluseven.finances.transacao.application.ListarTransacoesPorUsuarioUseCase;
import com.finance.leluseven.finances.transacao.domain.Transacao;
import com.finance.leluseven.shared.infrastructure.payload.ApiResponse;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transacao")
@RequiredArgsConstructor
public class TransacaoController {
    private final ListarTransacoesPorUsuarioUseCase listarTransacoesPorUsuario;
    private final ConsultaTransacaoPorPlaidTransacaoIdUseCase consultaTranscaoPorPlaidId;
    private final ConsultaTransacaoPorCodUseCase consultaTransacaoPorCod;

    @GetMapping("/usuario/{codUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG','USER')")
    public ResponseEntity<ApiResponse<List<Transacao>>> listarTodasTransacoesPorUsuario(@Param("codUsuario") Long codUsuario) {
        var transacoes = listarTransacoesPorUsuario.execute(CodUsuario.de(codUsuario));

        return ResponseEntity.ok(ApiResponse.success(transacoes));
    }

    @GetMapping("/usuario/{codUsuario}/data-inicio/{dataInicio}/data-fim/{dataFim}")
    @PreAuthorize("hasAnyRole('MANAG','USER')")
    public ResponseEntity<ApiResponse<List<Transacao>>> listarTransacoesPorUsuarioEPeriodo(
            @Param("codUsuario") Long codUsuario, @Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim) {
        var transacoes = listarTransacoesPorUsuario.executeForPeriod(CodUsuario.de(codUsuario), dataInicio, dataFim);

        return ResponseEntity.ok(ApiResponse.success(transacoes));
    }

    @GetMapping("/plaid/{plaidTransacaoId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG','USER')")
    public ResponseEntity<ApiResponse<Transacao>> consultaTransacaoPorCodigoPlaid(@Param("plaidTransacaoId") String plaidTransacaoId) {
        return ResponseEntity.ok(ApiResponse.success(consultaTranscaoPorPlaidId.execute(plaidTransacaoId)));
    }

    @GetMapping("/{codTransacao}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG','USER')")
    public ResponseEntity<ApiResponse<Transacao>> consultaTransacaoPorCodigoPlaid(@Param("codTransacao") Long codTransacao) {
        return ResponseEntity.ok(ApiResponse.success(consultaTransacaoPorCod.execute(codTransacao)));
    }
}
