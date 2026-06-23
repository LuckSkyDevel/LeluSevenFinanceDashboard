package com.finance.lululeleseven.perfil.infrastructure;

import com.finance.lululeleseven.perfil.domain.Perfil;

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
