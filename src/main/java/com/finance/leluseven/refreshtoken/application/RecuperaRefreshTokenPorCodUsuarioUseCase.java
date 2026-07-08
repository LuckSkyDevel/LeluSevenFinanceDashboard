package com.finance.leluseven.refreshtoken.application;

import com.finance.leluseven.refreshtoken.domain.IRefreshTokenRepositroy;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecuperaRefreshTokenPorCodUsuarioUseCase {

    private final IRefreshTokenRepositroy repo;

    @Transactional
    public RefreshToken execute(Long codUsuario){
        var refreshToken = repo.recuperaRefreshTokenPorCodUsuario(codUsuario);

        if (refreshToken.isEmpty()){
            throw new RuntimeException("Não foi possível recuperar o RefreshToken cadastrado para o usuário informado!");
        }

        return refreshToken.get();
    }
}
