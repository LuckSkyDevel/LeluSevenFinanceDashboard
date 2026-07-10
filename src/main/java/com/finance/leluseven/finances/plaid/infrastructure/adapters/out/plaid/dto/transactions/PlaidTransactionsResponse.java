package com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.transactions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PlaidTransactionsResponse(List<Transaction> added, List<Transaction> modified, List<Transaction> removed, String next_cursor, Boolean has_more) {
}
