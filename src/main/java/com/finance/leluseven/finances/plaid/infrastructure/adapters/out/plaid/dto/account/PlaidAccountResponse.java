package com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.account;

public record PlaidAccountResponse(
        String account_id,
        String name,
        String official_name,
        Balances balances,
        String type
) {
}
