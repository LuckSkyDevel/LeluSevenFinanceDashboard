package com.finance.leluseven.refreshtoken.application;

import com.finance.leluseven.refreshtoken.domain.IRefreshTokenRepositroy;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import com.finance.leluseven.shared.infrastructure.security.TokenService;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriaRefreshTokenUseCase {

    private final IUsuarioRepository repoUsuario;
    private final IRefreshTokenRepositroy repoRefreshToken;
    private final TokenService tokenService;

    @Transactional
    public RefreshToken execute(String rToken, String dispositivo, Usuario usuario) {
        var refreshToken = RefreshToken.criar(rToken, tokenService.getRefreshExpiration(), dispositivo, usuario);

        return repoRefreshToken.salvar(refreshToken);
    }
}
