package com.finance.leluseven.finances.plaid.domain;

import com.finance.leluseven.finances.plaid.domain.vo.CodContaBancaria;
import com.finance.leluseven.finances.plaid.infrastructure.adapters.out.plaid.dto.account.PlaidAccountResponse;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ContaBancaria {
    private CodContaBancaria codContaBancaria;
    private String nome;
    private String nomeOficial;
    private BigDecimal saldo;
    private String tipoConta;

    public static ContaBancaria criar(PlaidAccountResponse raw) {
        var contaBancaria = new ContaBancaria();
        contaBancaria.codContaBancaria = CodContaBancaria.de(raw.account_id());
        contaBancaria.nome = raw.name();
        contaBancaria.nomeOficial = raw.official_name();
        contaBancaria.saldo = raw.balances().current();
        contaBancaria.tipoConta = raw.type();
        return contaBancaria;
    }
}
