package com.finance.leluseven.usuario.application;

import com.finance.leluseven.perfil.domain.Perfil;
import com.finance.leluseven.refreshtoken.application.CriaRefreshTokenUseCase;
import com.finance.leluseven.shared.infrastructure.security.TokenService;
import com.finance.leluseven.usuario.application.dto.LoginDto;
import com.finance.leluseven.usuario.application.dto.UsuarioDto;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final IUsuarioRepository repoUsuario;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final CriaRefreshTokenUseCase criaRefreshTokenUseCase;

    @Transactional
    public UsuarioDto execute(LoginDto login, String dispositivo) {
        // Fetch user by username (LoginDto.nomeUsuario maps to Usuario.nomUsuario)
        var usuarioBanco = repoUsuario.findByNomUsuario(NomeUsuario.de(login.nomeUsuario().valor()))
                .orElseThrow(() -> new UsernameNotFoundException("Nome de Usuário não encontrado!"));

        // Extrai perfis e valida
        if (usuarioBanco.getPerfis() == null || usuarioBanco.getPerfis().isEmpty()) {
            throw new RuntimeException("Usuário não possui perfis!");
        }

        if (!usuarioBanco.validarSenha(login.senha(), passwordEncoder)) {
            throw new AuthenticationCredentialsNotFoundException("Nome de Usuário ou Senha invalido!");
        }

        var accessToken = tokenService.generateAccessToken(login.nomeUsuario().valor(), usuarioBanco.getPerfis().stream().map(p -> p.getNomePerfil().nome()).toList());
        var refreshToken = tokenService.generateRefreshToken(login.nomeUsuario().valor());

        criaRefreshTokenUseCase.execute(refreshToken, dispositivo, usuarioBanco);

        return UsuarioDto.de(usuarioBanco, accessToken, refreshToken);
    }
}
