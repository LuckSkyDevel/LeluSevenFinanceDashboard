package com.finance.leluseven.perfil.infrastructure;

import com.finance.leluseven.perfil.domain.Perfil;
import org.springframework.stereotype.Component;

@Component
public class PerfilMapper {
    // JPA entity → domain
    public Perfil toDomain(PerfilEntity entity) {
        return Perfil.reconstituir(
                entity.getCodPerfil(),
                entity.getNomPerfil(),
                entity.getDesPerfil(),
                entity.getStAtivo(),
                entity.getDatCriacao()
        );
    }

    // domain → JPA entity
    public PerfilEntity toEntity(Perfil domain) {
        var entity = new PerfilEntity();
        entity.setNomPerfil(domain.getNomePerfil().nome());
        entity.setDesPerfil(domain.getDescricao());
        entity.setStAtivo(domain.getAtivo());
        entity.setDatCriacao(domain.getDataCriacao());
        return entity;
    }

}
