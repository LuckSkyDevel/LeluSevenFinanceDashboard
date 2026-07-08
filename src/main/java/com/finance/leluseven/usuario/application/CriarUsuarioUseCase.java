package com.finance.leluseven.usuario.application;

import com.finance.leluseven.perfil.domain.IPerfilRepository;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.usuario.application.dto.RegistroDto;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.Usuario;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarUsuarioUseCase {

    private final IUsuarioRepository repoUsuario;
    private final IPerfilRepository repoPerfil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario execute(RegistroDto dto) {
        var usuarioBanco = repoUsuario.findByNomUsuario(NomeUsuario.de(dto.nome().valor()));

        if (usuarioBanco.isPresent()) {
            throw new RuntimeException("Não é possível registrar o usuário, pois o nome de usuário já está em uso!");
        }

        var perfil = repoPerfil.recuperaPerfilPorNome("USER")
                .orElseThrow(() -> new DataNotFoundException("Não foi possível registrar o usuário, perfil não encontrado"));

        var usuario = Usuario.criar(dto.nome().valor(), dto.email().valor(), dto.senha(), passwordEncoder);

        usuario.adicionarPerfil(perfil);

        return repoUsuario.salvaUsuario(usuario);
    }
}
