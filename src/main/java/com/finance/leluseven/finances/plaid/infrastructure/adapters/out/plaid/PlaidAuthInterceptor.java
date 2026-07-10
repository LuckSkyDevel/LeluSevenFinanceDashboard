package com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlaidAuthInterceptor implements RequestInterceptor {
    private final String clientId;
    private final String secret;

    // Puxando os valores seguros do seu application.properties
    public PlaidAuthInterceptor(
            @Value("${plaid.client-id}") String clientId,
            @Value("${plaid.secret}") String secret) {
        this.clientId = clientId;
        this.secret = secret;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.header("PLAID-CLIENT-ID", this.clientId);
        template.header("PLAID-SECRET", this.secret);
        template.header("Content-Type", "application/json");
    }
}
