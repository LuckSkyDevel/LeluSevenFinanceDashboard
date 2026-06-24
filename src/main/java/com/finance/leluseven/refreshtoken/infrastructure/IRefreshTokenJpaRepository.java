package com.finance.leluseven.refreshtoken.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<RefreshTokenEntity> findByCodRefreshToken(Long codRefreshToken);

    Optional<RefreshTokenEntity> findByCodUsuario(Long codUsuario);
}
