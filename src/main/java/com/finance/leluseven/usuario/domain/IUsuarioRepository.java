package com.finance.leluseven.usuario.domain;

import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository {
    Optional<Usuario> recuperarUsuarioPorCodigoUsuario(CodUsuario codUsuario);

    Optional<Usuario> recuperarUsuarioPorNomeUsuario(NomeUsuario nomUsuario);

    Optional<Usuario> recuperarUsuarioPorEmailUsuario(String emailUsuario);

    List<Usuario> listarUsuarios();

    Usuario salvarUsuario(Usuario usuario);

}
