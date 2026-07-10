package com.finance.leluseven.finances.plaid.infrastructure;

import com.finance.leluseven.finances.plaid.application.CriarLinkTokenUseCase;
import com.finance.leluseven.finances.plaid.application.ListarContasUseCase;
import com.finance.leluseven.finances.plaid.application.ListarTransacoesPlaidUseCase;
import com.finance.leluseven.finances.plaid.application.TrocarPublicTokenUseCase;
import com.finance.leluseven.finances.plaid.domain.ContaBancaria;
import com.finance.leluseven.finances.transacao.domain.Transacao;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final ListarTransacoesPlaidUseCase listarTransacoesUseCase;

    @GetMapping("/link-token")
    public Map<String, String> criarLinkToken(@AuthenticationPrincipal String userId) {
        return Map.of("linkToken", criarLinkTokenUseCase.execute(userId));
    }

    @PostMapping("/exchange-token")
    public ResponseEntity<Void> trocarToken(@RequestBody Map<String, String> body,
                                            @AuthenticationPrincipal String userId) {
        trocarPublicTokenUseCase.execute(body.get("publicToken"), CodUsuario.de(Long.valueOf(userId)));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contas")
    public List<ContaBancaria> listarContas(@AuthenticationPrincipal Long userId) {
        return listarContasUseCase.execute(CodUsuario.de(userId));
    }

    @GetMapping("/transacoes")
    public List<Transacao> listarTransacoes(@AuthenticationPrincipal Long codUsuario, @RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        return listarTransacoesUseCase.execute(CodUsuario.de(codUsuario), inicio, fim);
    }
}
