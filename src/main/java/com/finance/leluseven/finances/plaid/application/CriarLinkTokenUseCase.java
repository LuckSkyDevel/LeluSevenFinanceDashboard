package com.finance.leluseven.finances.plaid.application;

import com.finance.leluseven.finances.plaid.domain.ProvedorOpenBankingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarLinkTokenUseCase {
    private final ProvedorOpenBankingPort provedorOpenBanking;

    public String execute(String userId) {
        return provedorOpenBanking.criarLinkToken(userId);
    }
}
