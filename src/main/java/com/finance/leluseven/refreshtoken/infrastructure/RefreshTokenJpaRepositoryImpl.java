package com.finance.leluseven.refreshtoken.infrastructure;

import com.finance.leluseven.refreshtoken.domain.IRefreshTokenRepositroy;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenJpaRepositoryImpl implements IRefreshTokenRepositroy {

    private final IRefreshTokenJpaRepository jpa;
    private final RefreshTokenMapper mapper;

    @Override
    public Optional<RefreshToken> recuperaRefreshTokenPorToken(String token) {
        return jpa.findByToken(token).map(mapper::toDomain);
    }

    @Override
    public Optional<RefreshToken> recuperaRefreshTokenPorCodigo(Long codRefreshToken) {
        return jpa.findByCodRefreshToken(codRefreshToken).map(mapper::toDomain);
    }

    @Override
    public Optional<RefreshToken> recuperaRefreshTokenPorCodUsuario(Long codUsuario) {
        return jpa.findByCodUsuario(codUsuario).map(mapper::toDomain);
    }

    @Override
    public RefreshToken salvar(RefreshToken refreshToken) {
        return mapper.toDomain(jpa.save(mapper.toEntity(refreshToken)));
    }

}
