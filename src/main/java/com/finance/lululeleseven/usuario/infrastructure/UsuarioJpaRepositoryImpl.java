package com.finance.lululeleseven.usuario.infrastructure;

import com.finance.lululeleseven.usuario.domain.CodUsuario;
import com.finance.lululeleseven.usuario.domain.NomeUsuario;
import com.finance.lululeleseven.usuario.domain.Usuario;
import com.finance.lululeleseven.usuario.domain.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioJpaRepositoryImpl implements IUsuarioRepository {

    private final IUsuarioJpaRepository jpa;
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
        return jpa.findByEmailUsuario(emailUsuario).map(mapper::toDomain);
    }

    @Override
    public List<Usuario> listaUsuarios() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Usuario save(Usuario usuario) {
        return mapper.toDomain(jpa.save(mapper.toEntity(usuario)));
    }

    @Override
    public void updateRefreshToken(CodUsuario codUsuario, String refreshToken) {
        jpa.updateRefreshToken(codUsuario.valor(), refreshToken);
    }
}
