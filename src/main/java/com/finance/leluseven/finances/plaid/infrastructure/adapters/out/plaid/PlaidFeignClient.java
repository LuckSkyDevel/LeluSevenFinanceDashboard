package com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid;

import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.PlaidRequest;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.account.PlaidAccountResponse;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.exchange.PlaidExchangeRequest;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.exchange.PlaidExchangeResponse;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.item.PlaidItemResponse;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.link.PlaidLinkTokenCreateRequest;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.link.PlaidLinkTokenCreateResponse;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.transactions.PlaidTransactionsRequest;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.transactions.PlaidTransactionsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "plaidClient",
        url = "${plaid.url}", // Ex: https://sandbox.plaid.com
        configuration = PlaidAuthInterceptor.class // Diz ao Feign para usar o nosso injetor de credenciais
)
public interface PlaidFeignClient {

    @PostMapping("/item/public_token/exchange")
    PlaidExchangeResponse trocarToken(@RequestBody PlaidExchangeRequest request);

    @PostMapping("/item/get")
    PlaidItemResponse recuperarItem(@RequestBody PlaidRequest request);

    @PostMapping("/link/token/create")
    PlaidLinkTokenCreateResponse criarLinkToken(@RequestBody PlaidLinkTokenCreateRequest request);

    @PostMapping("/accounts/get")
    List<PlaidAccountResponse> listarContas(@RequestBody PlaidRequest request);

    @PostMapping("/transactions/sync")
    PlaidTransactionsResponse sincronizarTransacoes(@RequestBody PlaidTransactionsRequest request);
}
