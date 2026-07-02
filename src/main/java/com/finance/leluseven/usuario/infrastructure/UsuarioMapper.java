package com.finance.leluseven.usuario.infrastructure;

import com.finance.leluseven.perfil.infrastructure.PerfilEntity;
import com.finance.leluseven.usuario.domain.Usuario;

public class UsuarioMapper {
    // JPA entity → domain
    public Usuario toDomain(UsuarioEntity entity) {
        var perfis = entity.getPerfis().stream()
                .map(PerfilEntity::getNomPerfil)
                .toList();

        return Usuario.reconstituir(
                entity.getCodUsuario(),
                entity.getNomUsuario(),
                entity.getDesEmail(),
                entity.getSenhaHash(),
                perfis,
                entity.getDatCriacao()
        );
    }

    // domain → JPA entity
    public UsuarioEntity toEntity(Usuario domain) {
        var entity = new UsuarioEntity();
        entity.setNomUsuario(domain.getNome().valor());
        entity.setDesEmail(domain.getEmail().valor());
        entity.setSenhaHash(domain.getSenha().hash());
        entity.setDatCriacao(domain.getDataCriacao());
        return entity;
    }
}
