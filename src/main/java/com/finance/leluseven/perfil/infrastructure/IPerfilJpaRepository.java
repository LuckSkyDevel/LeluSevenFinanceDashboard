package com.finance.leluseven.perfil.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IPerfilJpaRepository extends JpaRepository<PerfilEntity, Long> {
    Optional<PerfilEntity> findByNomPerfil(String nomePerfil);

    Optional<PerfilEntity> findByCodPerfil(Long codigoPerfil);
}
