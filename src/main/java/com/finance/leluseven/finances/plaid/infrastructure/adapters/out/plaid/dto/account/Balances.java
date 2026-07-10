package com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.account;

import java.math.BigDecimal;

public record Balances(
        BigDecimal available,
        BigDecimal current,
        String iso_currency_code
) {
}
