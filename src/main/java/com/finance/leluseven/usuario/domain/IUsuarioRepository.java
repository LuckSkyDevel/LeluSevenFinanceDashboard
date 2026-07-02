package com.finance.leluseven.usuario.domain;

import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository {
    Optional<Usuario> findByCodUsuario(CodUsuario codUsuario);

    Optional<Usuario> findByNomUsuario(NomeUsuario nomUsuario);

    Optional<Usuario> findByEmailUsuario(String emailUsuario);

    List<Usuario> listaUsuarios();

    Usuario save(Usuario usuario);

    void updatePlaidToken(CodUsuario codigo, String accessToken, String itemId);

    void removerPlaidToken(CodUsuario codigo);
}
