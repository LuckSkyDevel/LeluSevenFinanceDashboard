package com.finance.leluseven.usuario.application;

import com.finance.leluseven.refreshtoken.application.RevogaRefreshTokenUseCase;
import com.finance.leluseven.refreshtoken.domain.IRefreshTokenRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {
    private final IRefreshTokenRepositroy repo;
    private final RevogaRefreshTokenUseCase revogaRefreshTokenUseCase;

    public void execute(String rToken) {
        repo.recuperaRefreshTokenPorToken(rToken).ifPresent(revogaRefreshTokenUseCase::execute);
    }
}
