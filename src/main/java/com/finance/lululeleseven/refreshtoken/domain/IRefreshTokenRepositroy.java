package com.finance.lululeleseven.refreshtoken.domain;

import com.finance.lululeleseven.usuario.domain.Usuario;
import com.finance.lululeleseven.usuario.domain.vo.CodUsuario;

import java.util.Optional;

public interface IRefreshTokenRepositroy {
    Optional<RefreshToken> recuperaRefreshTokenPorToken(String token);

    Optional<RefreshToken> recuperaRefreshTokenPorCodigo(Long codRefreshToken);

    Optional<RefreshToken> recuperaRefreshTokenPorCodUsuario(Long codUsuario);

    RefreshToken salvar(RefreshToken refreshToken);

    void updateRefreshToken(CodUsuario codUsuario, String refreshToken);
}
