package com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.link;

public record PlaidLinkTokenCreateResponse(String link_token, String expiration, String request_id) {
}
