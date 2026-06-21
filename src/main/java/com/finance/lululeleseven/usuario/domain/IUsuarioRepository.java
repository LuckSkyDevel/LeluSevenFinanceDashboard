package com.finance.lululeleseven.usuario.domain;

import com.finance.lululeleseven.usuario.domain.vo.CodUsuario;
import com.finance.lululeleseven.usuario.domain.vo.NomeUsuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository {
    Optional<Usuario> findByCodUsuario(CodUsuario codUsuario);

    Optional<Usuario> findByNomUsuario(NomeUsuario nomUsuario);

    Optional<Usuario> findByEmailUsuario(String emailUsuario);

    List<Usuario> listaUsuarios();

    Usuario save(Usuario usuario);

}
