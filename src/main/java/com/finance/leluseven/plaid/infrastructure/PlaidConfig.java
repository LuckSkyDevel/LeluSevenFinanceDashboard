package com.finance.leluseven.plaid.infrastructure;

import com.plaid.client.ApiClient;
import com.plaid.client.request.PlaidApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class PlaidConfig {
    @Value("${plaid.client-id}")
    private String clientId;

    @Value("${plaid.secret}")
    private String secret;

    @Value("${plaid.env}")
    private String env;

    @Bean
    public PlaidApi plaidApi() {
        HashMap<String, String> apiKeys = new HashMap<>();
        apiKeys.put("clientId", clientId);
        apiKeys.put("secret", secret);

        var apiClient = new ApiClient(apiKeys);

        // define o ambiente
        apiClient.setPlaidAdapter(env.equalsIgnoreCase("sandbox") ? ApiClient.Sandbox : ApiClient.Production);

        return apiClient.createService(PlaidApi.class);
    }
}

