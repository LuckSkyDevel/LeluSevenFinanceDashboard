package com.finance.leluseven.usuario.infrastructure;

import com.finance.leluseven.perfil.domain.IPerfilRepository;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;
import com.finance.leluseven.usuario.domain.Usuario;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioJpaRepositoryImpl implements IUsuarioRepository {

    private final IUsuarioJpaRepository jpa;
    private final IPerfilRepository repoPerfil;
    private final UsuarioMapper mapper;

    @Override
    public Optional<Usuario> findByCodUsuario(CodUsuario codUsuario) {
        return jpa.findById(codUsuario.valor()).map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByNomUsuario(NomeUsuario nomUsuario) {
        return jpa.findByNomUsuario(nomUsuario.valor()).map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByEmailUsuario(String emailUsuario) {
        return jpa.findByDesEmail(emailUsuario).map(mapper::toDomain);
    }

    @Override
    public List<Usuario> listaUsuarios() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Usuario salvaUsuario(Usuario usuario) {
        var usuarioBanco = jpa.save(mapper.toEntity(usuario));
        return mapper.toDomain(usuarioBanco);
    }

}
