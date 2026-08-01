package com.finance.leluseven.perfil.infrastructure;

import com.finance.leluseven.perfil.domain.Perfil;
import com.finance.leluseven.perfil.domain.vo.CodPerfil;
import com.finance.leluseven.perfil.domain.vo.NomePerfil;
import org.springframework.stereotype.Component;

@Component
public class PerfilMapper {
    // JPA entity → domain
    public Perfil toDomain(PerfilEntity entity) {
        return new Perfil.Builder()
                .codPerfil(CodPerfil.de(entity.getCodPerfil()))
                .nomePerfil(NomePerfil.de(entity.getNomPerfil()))
                .descricao(entity.getDesPerfil())
                .ativo(entity.getStAtivo())
                .dataCriacao(entity.getDatCriacao())
                .build();
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
