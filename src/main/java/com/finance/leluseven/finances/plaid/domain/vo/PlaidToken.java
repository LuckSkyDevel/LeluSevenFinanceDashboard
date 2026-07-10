package com.finance.leluseven.finances.plaid.domain.vo;

import com.finance.leluseven.shared.exception.DomainException;

public record PlaidToken(String accessToken, String itemId) {
    public PlaidToken {
        if (accessToken.isEmpty())
            throw new DomainException("Não é permitido valor nulo ou vazio para Access Token!");

        if (itemId.isEmpty())
            throw new DomainException("NÃo É permitido valor nulo ou vazio para Item ID!");
    }

    public static PlaidToken de(String accessToken, String itemId) {
        return  new PlaidToken(accessToken, itemId);
    }
}
