package com.finance.lululeleseven.usuario.application;

import com.finance.lululeleseven.shared.infrastructure.security.TokenService;
import com.finance.lululeleseven.usuario.application.dto.UsuarioDto;
import com.finance.lululeleseven.usuario.domain.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCase {

    private final IUsuarioRepository repoUsuario;
    private final TokenService tokenService;

    public UsuarioDto execute(String token) {
        return null;
    }
}
