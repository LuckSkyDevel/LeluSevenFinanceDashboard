package com.finance.leluseven.finances.transacao.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITransacaoJpaRepository extends JpaRepository<TransacaoEntity, Long> {
    List<TransacaoEntity> findByUsuarioCodUsuario(Long usuarioId);

    Optional<TransacaoEntity> findByPlaidTransacaoId(String plaidTransacaoId);

    @Query("""
    SELECT t FROM TransacaoEntity t
    WHERE t.usuario.codUsuario = :usuarioId
    AND t.datTransacao BETWEEN :inicio AND :fim
    ORDER BY t.datTransacao DESC
    """)
    List<TransacaoEntity> findByUsuarioIdAndPeriodo(
            @Param("usuarioId") Long codUsuario,
            @Param("inicio") LocalDate inicio,
            @Param("fim")       LocalDate fim
    );

    boolean existsByPlaidTransacaoId(String plaidTransacaoId);

    @Query("UPDATE TransacaoEntity t SET t.isAtivo = false WHERE t.plaidTransacaoId = :plaidTransacaoId")
    void inativaTransacaoByPlaidTrasacaoId(@Param("plaidTransacaoId") String plaidTrasacaoId);
}
