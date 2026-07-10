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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<ApiResponse<Usuario>> registraUsuario(@Valid @RequestBody RegistroDto registroDto, UriComponentsBuilder uriBuilder) {
        var location = uriBuilder.path("/api/auth/login").build().toUri();
        var response = ApiResponse.created(criarUsuarioUseCase.execute(registroDto), "Usuário criado com sucesso!");

        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UsuarioDto>> login(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request) {
        // Valida credenciais do usuário
        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.nomeUsuario().valor(), loginDto.senha()));

        //Recupera ip do dispositivo utilizado!
        var dispositivo = request.getRemoteHost();

        var response = ApiResponse.success(loginUseCase.execute(loginDto, dispositivo));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<UsuarioDto>> refresh(@RequestBody String refreshToken, HttpServletRequest request) {
        //Recupera ip do dispositivo utilizado!
        var dispositivo = request.getRemoteHost();

        var response = ApiResponse.success(atualizaRefreshTokenUseCase.execute(refreshToken, dispositivo));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody String refreshToken) {
        logoutUseCase.execute(refreshToken);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
