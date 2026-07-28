package com.finance.leluseven.finances.plaid.domain;

import com.finance.leluseven.finances.plaid.domain.vo.CodContaBancaria;
import com.finance.leluseven.finances.plaid.domain.vo.CodContaBancariaPlaid;
import com.finance.leluseven.shared.exception.DomainException;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ContaBancaria {
    private CodContaBancaria codContaBancaria;
    private CodContaBancariaPlaid codContaBancariaPlaid;
    private String nome;
    private String nomeOficial;
    private BigDecimal saldo;
    private String tipoConta;

    private ContaBancaria(Builder builder) {
        this.codContaBancaria = builder.codContaBancaria;
        this.codContaBancariaPlaid = builder.codContaBancariaPlaid;
        this.nome = builder.nome;
        this.nomeOficial = builder.nomeOficial;
        this.saldo = builder.saldo;
        this.tipoConta = builder.tipoConta;
    }

    public static class Builder {
        private CodContaBancaria codContaBancaria;
        private CodContaBancariaPlaid codContaBancariaPlaid;
        private String nome;
        private String nomeOficial;
        private BigDecimal saldo;
        private String tipoConta;

        public Builder codContaBancaria(CodContaBancaria cod) {
            this.codContaBancaria = cod;
            return this;
        }

        public Builder codContaBancariaPlaid(CodContaBancariaPlaid codPlaid) {
            this.codContaBancariaPlaid = codPlaid;
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder nomeOficial(String nomeOficial) {
            this.nomeOficial = nomeOficial;
            return this;
        }

        public Builder saldo(BigDecimal saldo) {
            this.saldo = saldo;
            return this;
        }

        public Builder tipoConta(String tipoConta) {
            this.tipoConta = tipoConta;
            return this;
        }

        public ContaBancaria build() {
            // Regras de negócio de domínio (Invariantes)
            if (this.codContaBancariaPlaid == null ||  this.codContaBancariaPlaid.valor().isEmpty()) {
                throw new DomainException("Código da conta da integração com o API externa é obrigatório");
            }
            if (this.saldo == null) {
                this.saldo = BigDecimal.ZERO; // Define um padrão seguro
            }

            return new ContaBancaria(this);
        }
    }
}
