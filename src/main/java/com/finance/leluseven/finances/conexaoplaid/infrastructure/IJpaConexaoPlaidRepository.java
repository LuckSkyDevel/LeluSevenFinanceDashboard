package com.finance.leluseven.finances.conexaoplaid.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IJpaConexaoPlaidRepository extends JpaRepository<ConexaoPlaidEntity, Long> {

    List<ConexaoPlaidEntity> findByCodUsuario(Long codUsuario);

    Optional<ConexaoPlaidEntity> findByAccessToken(String accessToken);

    @Modifying
    @Query("UPDATE ConexaoPlaidEntity c SET c.ativo = :ativo WHERE c.codConexcaoPlaid = :codConexcaoPlaid")
    void deleteConexaoPlaidByCodConexaoPlaid(@Param("codConexcaoPlaid") Long codConexaoPlaid, @Param("ativo") Boolean ativo);

    @Modifying
    @Query("UPDATE ConexaoPlaidEntity c SET c.cursor = :cursor WHERE c.codConexcaoPlaid = :codConexcaoPlaid")
    void updatePlaidCursor(@Param("codConexcaoPlaid") Long codConexaoPlaid, @Param("cursor") String cursor);
}
