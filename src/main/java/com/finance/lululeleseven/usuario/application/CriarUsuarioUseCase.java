package com.finance.lululeleseven.usuario.application;

import com.finance.lululeleseven.usuario.application.dto.RegistroDto;
import com.finance.lululeleseven.usuario.application.dto.UsuarioDto;
import com.finance.lululeleseven.usuario.domain.IUsuarioRepository;
import com.finance.lululeleseven.usuario.domain.vo.NomeUsuario;
import com.finance.lululeleseven.usuario.domain.Usuario;
import com.finance.lululeleseven.usuario.infrastructure.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarUsuarioUseCase {

    private final IUsuarioRepository repoUsuario;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public UsuarioDto execute(RegistroDto dto) {
        var usuarioBanco = repoUsuario.findByNomUsuario(NomeUsuario.de(dto.getNome()));
        if (usuarioBanco.isPresent()) {
            throw new RuntimeException("Não é possível registrar o usuário, pois o nome de usuário já está em uso!");
        }

        var usuario = Usuario.criar(dto.getNome(), dto.getEmail(), dto.getSenha(), passwordEncoder);
        var usuarioSalvo = repoUsuario.save(usuario);

        return usuarioMapper.toDto(usuarioSalvo);
    }
}
