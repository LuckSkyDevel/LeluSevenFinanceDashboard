package com.finance.leluseven.usuario.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByNomUsuario(String nomUsuario);

    Optional<UsuarioEntity> findByEmailUsuario(String emailUsuario);

    @Modifying
    @Query("""
              UPDATE UsuarioEntity u
              SET u.plaidAccessToken = :access_token,
                  u.plaidItemId = :item_id
              WHERE u.codUsuario = :cod_usuario
            """)
    void updatePlaidToken(@Param("cod_usuario") Long codUsuario, @Param("access_token") String accessToken, @Param("item_id") String itemId);

    @Modifying
    @Query("""
              UPDATE UsuarioEntity u
              SET u.plaidAccessToken = null,
                  u.plaidItemId = null
              WHERE u.codUsuario = :cod_usuario
            """)
    void removerPlaidToken(@Param("cod_usuario") Long codUsuario);
}
