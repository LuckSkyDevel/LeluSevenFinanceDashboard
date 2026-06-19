package com.finance.lululeleseven.usuario.domain;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository {
    Optional<Usuario> findByCodUsuario(CodUsuario codUsuario);

    Optional<Usuario> findByNomUsuario(NomeUsuario nomUsuario);

    Optional<Usuario> findByEmailUsuario(String emailUsuario);

    List<Usuario> listaUsuarios();

    Usuario save(Usuario usuario);

    void updateRefreshToken(CodUsuario codUsuario, String refreshToken);
}
