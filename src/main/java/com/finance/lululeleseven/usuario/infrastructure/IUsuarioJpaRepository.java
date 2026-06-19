package com.finance.lululeleseven.usuario.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByNomUsuario(String nomUsuario);

    Optional<UsuarioEntity> findByEmailUsuario(String emailUsuario);

    @Modifying
    @Query("UPDATE UsuarioEntity u SET u.refreshToken = :token WHERE u.codUsuario = :codUsuario")
    void updateRefreshToken(@Param("codUsuario") Long codUsuario, @Param("token") String token);
}
