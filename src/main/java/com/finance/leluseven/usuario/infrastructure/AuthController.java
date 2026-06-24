package com.finance.leluseven.usuario.infrastructure;

import com.finance.leluseven.refreshtoken.application.AtualizaRefreshTokenUseCase;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Usuario> registraUsuario(@Valid @RequestBody RegistroDto registroDto) {
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(criarUsuarioUseCase.execute(registroDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDto> login(@Valid @RequestBody LoginDto loginDto,
                                            HttpServletRequest request) {
        // Valida credenciais do usuário
        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.nomeUsuario().valor(), loginDto.senha()));

        //Recupera ip do dispositivo utilizado!
        var dispositivo = request.getRemoteHost();

        return ResponseEntity.ok(loginUseCase.execute(loginDto, dispositivo));
    }

    @PostMapping("/refresh")
    public ResponseEntity<UsuarioDto> refresh(@RequestBody String refreshToken, HttpServletRequest request) {
        //Recupera ip do dispositivo utilizado!
        var dispositivo = request.getRemoteHost();

        return ResponseEntity.ok(atualizaRefreshTokenUseCase.execute(refreshToken, dispositivo));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody String refreshToken) {
        logoutUseCase.execute(refreshToken);
        return ResponseEntity.ok().build();
    }
}
