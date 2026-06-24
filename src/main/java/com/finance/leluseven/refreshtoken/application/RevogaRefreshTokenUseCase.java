package com.finance.leluseven.refreshtoken.application;

import com.finance.leluseven.refreshtoken.domain.IRefreshTokenRepositroy;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevogaRefreshTokenUseCase {
    private final IRefreshTokenRepositroy repo;

    public void execute(RefreshToken rToken) {
        var refresh = repo.recuperaRefreshTokenPorCodigo(rToken.getCodRefreshToken().valor());

        if (refresh.isEmpty())
            throw new DataNotFoundException("Refresh token não foi encontrado");

        refresh.get().revogar();

        repo.salvar(refresh.get());
    }
}
