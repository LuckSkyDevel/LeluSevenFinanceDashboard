package com.finance.leluseven.finances.plaid.infrastructure;

import com.finance.leluseven.finances.plaid.application.CriarLinkTokenUseCase;
import com.finance.leluseven.finances.plaid.application.ListarContasUseCase;
import com.finance.leluseven.finances.plaid.application.TrocarPublicTokenUseCase;
import com.finance.leluseven.finances.plaid.domain.ContaBancaria;
import com.finance.leluseven.finances.transacao.application.SincronizarTransacoesUseCase;
import com.finance.leluseven.finances.transacao.domain.Transacao;
import com.finance.leluseven.shared.infrastructure.payload.ApiResponse;
import com.finance.leluseven.usuario.domain.Usuario;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plaid")
@RequiredArgsConstructor
public class PlaidController {
    private final CriarLinkTokenUseCase criarLinkTokenUseCase;
    private final TrocarPublicTokenUseCase trocarPublicTokenUseCase;
    private final ListarContasUseCase listarContasUseCase;
    private final SincronizarTransacoesUseCase sincronizarTransacoesUseCase;

    @GetMapping("/link-token")
    public Map<String, String> criarLinkToken(@AuthenticationPrincipal UserDetails user) {
        var username = user.getUsername();
        return Map.of("linkToken", criarLinkTokenUseCase.execute(username));
    }

    @PostMapping("/exchange-token")
    public ResponseEntity<Void> trocarToken(@RequestBody Map<String, String> body,
                                            @AuthenticationPrincipal UserDetails user) {
        trocarPublicTokenUseCase.execute(body.get("publicToken"), user.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contas")
    public ResponseEntity<ApiResponse<List<ContaBancaria>>> listarContas(@AuthenticationPrincipal UserDetails user) {
        var response = listarContasUseCase.execute(user.getUsername());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/sicronizar-transacoes")
    public ResponseEntity<ApiResponse<String>> sicronizarTransacoes(@AuthenticationPrincipal UserDetails user) {
        sincronizarTransacoesUseCase.execute(user.getUsername());

        return ResponseEntity.ok(ApiResponse.success("Transações sincronizadas com sucesso!"));
    }
}
