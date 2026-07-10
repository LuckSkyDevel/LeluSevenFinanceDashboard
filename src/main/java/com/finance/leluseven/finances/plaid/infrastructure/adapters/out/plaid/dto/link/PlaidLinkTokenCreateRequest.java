package com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.link;

import java.util.List;

public record PlaidLinkTokenCreateRequest(
        User user,
        String client_name,
        List<String> products,
        List<String> country_codes,
        String language
) {
}
