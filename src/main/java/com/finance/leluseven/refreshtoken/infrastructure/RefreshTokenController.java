package com.finance.leluseven.refreshtoken.infrastructure;

import com.finance.leluseven.refreshtoken.application.RecuperaRefreshTokenPorCodUsuarioUseCase;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resfresh-token")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RecuperaRefreshTokenPorCodUsuarioUseCase recuperaRefreshTokenPorCodUsuarioUseCase;

    @GetMapping("/usuario/{codUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG','USER')")
    public ResponseEntity<RefreshToken> recuperaRefreshTokenPorCodUsuario(@Param("codUsuario") Long codUsuario) {
        try {
            return ResponseEntity.ok(recuperaRefreshTokenPorCodUsuarioUseCase.execute(codUsuario));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}
