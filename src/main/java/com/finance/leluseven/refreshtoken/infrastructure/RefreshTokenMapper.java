package com.finance.leluseven.refreshtoken.infrastructure;

import com.finance.leluseven.perfil.infrastructure.PerfilEntity;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import com.finance.leluseven.usuario.domain.Usuario;
import com.finance.leluseven.usuario.infrastructure.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {

    // JPA entity → domain
    public RefreshToken toDomain(RefreshTokenEntity entity) {
        var usuarioBanco = entity.getUsuario();

        var perfis = usuarioBanco.getPerfis().stream().map(PerfilEntity::getNomPerfil).toList();

        var usuario = Usuario.reconstituir(
                usuarioBanco.getCodUsuario(),
                usuarioBanco.getDesEmail(),
                usuarioBanco.getDesEmail(),
                usuarioBanco.getSenhaHash(),
                perfis,
                usuarioBanco.getDatCriacao()
        );

        return RefreshToken.recriar(
                entity.getCodRefreshToken(),
                entity.getRefreshToken(),
                entity.getDatExpiracao(),
                entity.getDispositivo(),
                entity.getRevogado(),
                usuario
        );
    }

    // domain → JPA entity
    public RefreshTokenEntity toEntity(RefreshToken domain) {
        var entity = new RefreshTokenEntity();
        entity.setRefreshToken(domain.getRToken());
        entity.setDatExpiracao(domain.getDatExpiracao());
        entity.setDispositivo(domain.getDispositivo().valor());

        var usuario = new UsuarioEntity();
        usuario.setCodUsuario(domain.getUsuario().getCodUsuario().valor());
        entity.setUsuario(usuario);

        return entity;
    }
}
