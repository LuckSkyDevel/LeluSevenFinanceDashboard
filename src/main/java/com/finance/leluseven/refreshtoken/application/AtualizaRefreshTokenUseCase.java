package com.finance.leluseven.refreshtoken.application;

import com.finance.leluseven.refreshtoken.domain.IRefreshTokenRepositroy;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.shared.infrastructure.security.TokenService;
import com.finance.leluseven.usuario.application.dto.UsuarioDto;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizaRefreshTokenUseCase {

    private final IRefreshTokenRepositroy repo;
    private final IUsuarioRepository repoUsuario;
    private final CriaRefreshTokenUseCase criaRefreshTokenUseCase;
    private final TokenService tokenService;

    @Transactional
    public UsuarioDto execute(String token, String dispositivo) {
        var refresh = repo.recuperaRefreshTokenPorToken(token).orElseThrow(() -> new DataNotFoundException("Refresh token inválido!"));

        if (!refresh.isValido())
            throw new RuntimeException("Refresh Token expirado ou revogado!");

        refresh.revogar();
        repo.salvar(refresh);

        var usuario = repoUsuario.recuperarUsuarioPorCodigoUsuario(refresh.getUsuario().getCodUsuario()).orElseThrow(() -> new DataNotFoundException("Usuário não encontrado!"));
        var nomeUsuario = usuario.getNome().valor();

        var newStrToken = tokenService.generateAccessToken(nomeUsuario, usuario.getPerfis().stream().map(p -> p.getNomePerfil().nome()).toList());
        var newStrRefreshToken = tokenService.generateRefreshToken(nomeUsuario);

        var refreshToken = criaRefreshTokenUseCase.execute(newStrRefreshToken, dispositivo, usuario);

        return UsuarioDto.de(usuario, newStrToken, refreshToken.getRToken());
    }
}
