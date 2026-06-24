package com.finance.leluseven.refreshtoken.application;

import com.finance.leluseven.refreshtoken.domain.IRefreshTokenRepositroy;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.shared.infrastructure.security.TokenService;
import com.finance.leluseven.usuario.application.dto.UsuarioDto;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizaRefreshTokenUseCase {

    private final IRefreshTokenRepositroy repo;
    private final IUsuarioRepository repoUsuario;
    private final CriaRefreshTokenUseCase criaRefreshTokenUseCase;
    private final TokenService tokenService;

    public UsuarioDto execute(String token, String dispositivo) {
        var refresh = repo.recuperaRefreshTokenPorToken(token).orElseThrow(() -> new DataNotFoundException("Refresh token inválido!"));

        if (!refresh.isValido())
            throw new RuntimeException("Refresh Token expirado ou revogado!");

        refresh.revogar();
        repo.salvar(refresh);

        var usuario = repoUsuario.findByCodUsuario(refresh.getUsuario().getCodUsuario()).orElseThrow(() -> new DataNotFoundException("Usuário não encontrado!"));
        var nomeUsuario = usuario.getNome().valor();

        var newStrToken = tokenService.generateAccessToken(nomeUsuario, usuario.getPerfis());
        var newStrRefreshToken = tokenService.generateRefreshToken(nomeUsuario);

        var refreshToken = criaRefreshTokenUseCase.execute(newStrRefreshToken, dispositivo, usuario);

        return UsuarioDto.de(usuario, newStrToken, refreshToken.getRToken());
    }
}
