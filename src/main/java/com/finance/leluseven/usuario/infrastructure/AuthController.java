package com.finance.leluseven.usuario.infrastructure;

import com.finance.leluseven.refreshtoken.application.AtualizaRefreshTokenUseCase;
import com.finance.leluseven.shared.infrastructure.payload.ApiResponse;
import com.finance.leluseven.usuario.application.CriarUsuarioUseCase;
import com.finance.leluseven.usuario.application.LoginUseCase;
import com.finance.leluseven.usuario.application.LogoutUseCase;
import com.finance.leluseven.usuario.application.dto.LoginDto;
import com.finance.leluseven.usuario.application.dto.RegistroDto;
import com.finance.leluseven.usuario.application.dto.UsuarioDto;
import com.finance.leluseven.usuario.domain.Usuario;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetAddress;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final CriarUsuarioUseCase criarUsuarioUseCase;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final AtualizaRefreshTokenUseCase atualizaRefreshTokenUseCase;

    @PostMapping("/registra-usuario")
    public ApiResponse<Usuario> registraUsuario(@Valid @RequestBody RegistroDto registroDto, UriComponentsBuilder uriBuilder) {
        var location = uriBuilder.path("/api/auth/login").build().toUri();

        try {
            return ApiResponse.created(criarUsuarioUseCase.execute(registroDto), "Usuário criado com sucesso!", location);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ApiResponse<UsuarioDto> login(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request) {
        // Valida credenciais do usuário
        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.nomeUsuario().valor(), loginDto.senha()));

        //Recupera ip do dispositivo utilizado!
        var dispositivo = request.getRemoteHost();

        try {
            return ApiResponse.success(loginUseCase.execute(loginDto, dispositivo));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ApiResponse<UsuarioDto> refresh(@RequestBody String refreshToken, HttpServletRequest request) {
        //Recupera ip do dispositivo utilizado!
        var dispositivo = request.getRemoteHost();

        try {
            return ApiResponse.success(atualizaRefreshTokenUseCase.execute(refreshToken, dispositivo));
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody String refreshToken) {
        try {
            logoutUseCase.execute(refreshToken);

            return ApiResponse.success(null);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
}
