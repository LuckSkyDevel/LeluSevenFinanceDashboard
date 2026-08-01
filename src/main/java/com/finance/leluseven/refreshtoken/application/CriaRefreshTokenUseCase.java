package com.finance.leluseven.refreshtoken.application;

import com.finance.leluseven.refreshtoken.domain.IRefreshTokenRepositroy;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import com.finance.leluseven.refreshtoken.domain.vo.Dispositivo;
import com.finance.leluseven.shared.infrastructure.security.TokenService;
import com.finance.leluseven.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CriaRefreshTokenUseCase {

    private final IRefreshTokenRepositroy repoRefreshToken;
    private final TokenService tokenService;

    @Transactional
    public RefreshToken execute(String rToken, String dispositivo, Usuario usuario) {
        var refreshToken = new RefreshToken.Builder()
                .rToken(rToken)
                .datExpiracao(LocalDateTime.now().plusSeconds(tokenService.getRefreshExpiration() / 1000))
                .dispositivo(Dispositivo.de(dispositivo))
                .usuario(usuario)
                .build();

        return repoRefreshToken.salvar(refreshToken);
    }
}
