package com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.transactions;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaction(String account_id, String transaction_id, String name, String merchant_name, BigDecimal amount,
                          LocalDate date, String payment_channel, PersonalFinanceCategory personal_finance_category) {
}
