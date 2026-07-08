package com.finance.leluseven.refreshtoken.application;

import com.finance.leluseven.refreshtoken.domain.IRefreshTokenRepositroy;
import com.finance.leluseven.refreshtoken.domain.RefreshToken;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RevogaRefreshTokenUseCase {
    private final IRefreshTokenRepositroy repo;

    @Transactional
    public void execute(RefreshToken rToken) {
        var refresh = repo.recuperaRefreshTokenPorCodigo(rToken.getCodRefreshToken().valor());

        if (refresh.isEmpty())
            throw new DataNotFoundException("Refresh token não foi encontrado");

        refresh.get().revogar();

        repo.salvar(refresh.get());
    }
}
