package com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.transactions;

import com.finance.leluseven.shared.exception.DomainException;

public record PlaidTransactionsRequest(String access_token, String cursor) {
    public PlaidTransactionsRequest {
        if (access_token == null || access_token.isEmpty())
            throw new DomainException("Access token is null or empty");
    }
}
