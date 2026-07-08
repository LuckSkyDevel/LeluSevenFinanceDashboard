package com.finance.leluseven.refreshtoken.infrastructure;

import com.finance.leluseven.refreshtoken.application.RecuperaRefreshTokenPorCodUsuarioUseCase;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import com.finance.leluseven.shared.infrastructure.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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
    public ApiResponse<RefreshToken> recuperaRefreshTokenPorCodUsuario(@Param("codUsuario") Long codUsuario) {
        try {
            return ApiResponse.success(recuperaRefreshTokenPorCodUsuarioUseCase.execute(codUsuario));
        } catch (Exception e) {
            return ApiResponse.error(null);
        }
    }
}
