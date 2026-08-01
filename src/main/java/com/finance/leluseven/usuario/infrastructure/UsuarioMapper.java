package com.finance.leluseven.usuario.infrastructure;

import com.finance.leluseven.perfil.domain.Perfil;
import com.finance.leluseven.perfil.domain.vo.CodPerfil;
import com.finance.leluseven.perfil.domain.vo.NomePerfil;
import com.finance.leluseven.perfil.infrastructure.PerfilEntity;
import com.finance.leluseven.usuario.domain.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class UsuarioMapper {
    // JPA entity → domain
    public Usuario toDomain(UsuarioEntity entity) {
        List<Perfil> perfis = new ArrayList<>();

        entity.getPerfis().forEach(perfilEntity -> perfis.add(new Perfil.Builder()
                .codPerfil(CodPerfil.de(perfilEntity.getCodPerfil()))
                .nomePerfil(NomePerfil.de(perfilEntity.getNomPerfil()))
                .descricao(perfilEntity.getDesPerfil())
                .ativo(perfilEntity.getStAtivo())
                .dataCriacao(perfilEntity.getDatCriacao())
                .build()
        ));

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

        var perfis = new HashSet<PerfilEntity>();

        domain.getPerfis().forEach(p -> {
            var perfil = new PerfilEntity();
            perfil.setCodPerfil(p.getCodigoPerfil().valor());
            perfil.setNomPerfil(p.getNomePerfil().nome());
            perfil.setDesPerfil(p.getDescricao());
            perfil.setDatCriacao(p.getDataCriacao());
            perfil.setStAtivo(p.getAtivo());

            perfis.add(perfil);
        });

        entity.setPerfis(perfis);

        return entity;
    }
}
