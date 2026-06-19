package com.finance.lululeleseven.usuario.infrastructure;

import com.finance.lululeleseven.perfil.infrastructure.PerfilEntity;
import com.finance.lululeleseven.usuario.application.dto.UsuarioDto;
import com.finance.lululeleseven.usuario.domain.Usuario;

public class UsuarioMapper {
    // JPA entity → domain
    public Usuario toDomain(UsuarioEntity entity) {
        var perfis = entity.getPerfis().stream()
                .map(PerfilEntity::getNomPerfil)
                .toList();

        return Usuario.reconstituir(
                entity.getCodUsuario(),
                entity.getNomUsuario(),
                entity.getStrEmail(),
                entity.getStrSenhaHash(),
                entity.getRefreshToken(),
                perfis,
                entity.getDatCriacao()
        );
    }

    // domain → JPA entity
    public UsuarioEntity toEntity(Usuario domain) {
        var entity = new UsuarioEntity();
        entity.setNomUsuario(domain.getNome());
        entity.setStrEmail(domain.getEmail().valor());
        entity.setStrSenhaHash(domain.getSenha().hash());
        entity.setDatCriacao(domain.getDataCriacao());
        return entity;
    }

    public UsuarioDto toDto(Usuario usuario) {
        var usuarioDto = new UsuarioDto();
        usuarioDto.setCodUsuario(usuario.getCodUsuario().valor());
        usuarioDto.setNome(usuario.getNome());
        usuarioDto.setEmail(usuario.getEmail().valor());
        return usuarioDto;
    }
}
