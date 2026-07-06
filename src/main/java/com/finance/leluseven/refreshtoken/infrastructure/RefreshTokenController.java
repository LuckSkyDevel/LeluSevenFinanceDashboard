package com.finance.leluseven.refreshtoken.infrastructure;

import com.finance.leluseven.refreshtoken.application.AtualizaRefreshTokenUseCase;
import com.finance.leluseven.refreshtoken.application.RecuperaRefreshTokenPorCodUsuarioUseCase;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import com.finance.leluseven.usuario.application.dto.UsuarioDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resfres-token")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final AtualizaRefreshTokenUseCase atualizaRefreshTokenUseCase;
    private final RecuperaRefreshTokenPorCodUsuarioUseCase recuperaRefreshTokenPorCodUsuarioUseCase;

    @PutMapping("/usuario/{codUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG')")
    public ResponseEntity<UsuarioDto> atualizaRefreshTokenPorCodUsuario(@RequestBody @NotNull String refreshToken, HttpServletRequest request) {
        var dispositivo = request.getRemoteHost();

        return ResponseEntity.ok(atualizaRefreshTokenUseCase.execute(refreshToken, dispositivo));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAG','USER')")
    public ResponseEntity<RefreshToken> recuperaRefreshTokenPorCodUsuario(Long codUsuario) {
        return ResponseEntity.ok(recuperaRefreshTokenPorCodUsuarioUseCase.execute(codUsuario));
    }
}
