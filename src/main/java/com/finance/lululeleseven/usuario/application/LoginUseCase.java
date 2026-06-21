package com.finance.lululeleseven.usuario.application;

import com.finance.lululeleseven.shared.infrastructure.security.TokenService;
import com.finance.lululeleseven.usuario.application.dto.LoginDto;
import com.finance.lululeleseven.usuario.application.dto.UsuarioDto;
import com.finance.lululeleseven.usuario.domain.IUsuarioRepository;
import com.finance.lululeleseven.usuario.domain.vo.NomeUsuario;
import com.finance.lululeleseven.usuario.infrastructure.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final AuthenticationManager atuhManager;
    private final IUsuarioRepository repoUsuario;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioDto execute(LoginDto login) {
        // Authenticate user credentials
        atuhManager.authenticate(new UsernamePasswordAuthenticationToken(login.getNomeUsuario(), login.getSenha()));

        // Fetch user by username (LoginDto.nomeUsuario maps to Usuario.nomUsuario)
        var usuarioBanco = repoUsuario.findByNomUsuario(NomeUsuario.de(login.getNomeUsuario()))
                .orElseThrow(() -> new UsernameNotFoundException("Nome de Usuário não encontrado!"));

        // Extrai perfis e valida
        if (usuarioBanco.getPerfis() == null || usuarioBanco.getPerfis().isEmpty()) {
            throw new RuntimeException("Usuário não possui perfis!");
        }

        if (!usuarioBanco.validarSenha(login.getSenha(), passwordEncoder)) {
            throw new AuthenticationCredentialsNotFoundException("Nome de Usuário ou Senha invalido!");
        }

        var accessToken = tokenService.generateAccessToken(login.getNomeUsuario(), usuarioBanco.getPerfis());
        var refreshToken = tokenService.generateRefreshToken(login.getNomeUsuario());

        var usuarioDto = usuarioMapper.toDto(usuarioBanco);
        usuarioDto.setRefreshToken(refreshToken);
        usuarioDto.setAccessToken(accessToken);

        return usuarioDto;
    }
}
