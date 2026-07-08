package com.finance.leluseven.refreshtoken.infrastructure;

import com.finance.leluseven.perfil.domain.Perfil;
import com.finance.leluseven.perfil.infrastructure.PerfilEntity;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import com.finance.leluseven.usuario.domain.Usuario;
import com.finance.leluseven.usuario.infrastructure.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class RefreshTokenMapper {

    // JPA entity → domain
    public RefreshToken toDomain(RefreshTokenEntity entity) {
        var usuarioBanco = entity.getUsuario();

        var perfis = usuarioBanco.getPerfis().stream().map(p -> Perfil.reconstituir(
                p.getCodPerfil(),
                p.getNomPerfil(),
                p.getDesPerfil(),
                p.getStAtivo(),
                p.getDatCriacao()
        )).toList();

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

        if (domain.getCodRefreshToken() != null)
            entity.setCodRefreshToken(domain.getCodRefreshToken().valor());

        entity.setRefreshToken(domain.getRToken());
        entity.setDatExpiracao(domain.getDatExpiracao());
        entity.setDispositivo(domain.getDispositivo().valor());

        var usuarioDomain = domain.getUsuario();

        var usuario = new UsuarioEntity();
        usuario.setCodUsuario(usuarioDomain.getCodUsuario().valor());
        usuario.setNomUsuario(usuarioDomain.getNome().valor());
        usuario.setDesEmail(usuarioDomain.getEmail().valor());

        var perfis = new HashSet<PerfilEntity>();

        usuarioDomain.getPerfis().forEach(p -> {
            var perfil = new PerfilEntity();
            perfil.setCodPerfil(p.getCodigoPerfil().valor());
            perfil.setNomPerfil(p.getNomePerfil().nome());
            perfil.setDesPerfil(p.getDescricao());
            perfil.setDatCriacao(p.getDataCriacao());
            perfil.setStAtivo(p.getAtivo());

            perfis.add(perfil);
        });

        usuario.setPerfis(perfis);

        entity.setUsuario(usuario);
        entity.setRevogado(domain.getIsRevogado());

        return entity;
    }
}
