package com.finance.lululeleseven.perfil.infrastructure;

import com.finance.lululeleseven.perfil.domain.IPerfilRepository;
import com.finance.lululeleseven.perfil.domain.Perfil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PerfilJpaRepositoryImpl implements IPerfilRepository {

    private final IPerfilJpaRepository jpa;
    private final PerfilMapper mapper;

    @Override
    public Optional<Perfil> recuperaPerfilPorCodigo(Long codigoPerfil) {
        return jpa.findByCodPerfil(codigoPerfil).map(mapper::toDomain);
    }

    @Override
    public List<Perfil> recuperaPerfisPorCodigoUsuario(Long codigoUsuario) {
        return jpa.findAllByCodUsuario(codigoUsuario).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Perfil> listaTodosPerfis() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Perfil> recuperaPerfilPorNome(String nomePerfil) {
        return jpa.findByNomPerfil(nomePerfil).map(mapper::toDomain);
    }

    @Override
    public Perfil salvar(Perfil perfil) {
        return mapper.toDomain(jpa.save(mapper.toEntity(perfil)));
    }
}
