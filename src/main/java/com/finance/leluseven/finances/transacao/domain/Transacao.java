package com.finance.leluseven.finances.transacao.domain;

import com.finance.leluseven.finances.transacao.domain.vo.CodTransacao;
import com.finance.leluseven.finances.transacao.domain.vo.Valor;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class Transacao {
    private CodTransacao codigoTransacao;
    private CodUsuario codUsuario;
    private String plaidTransacaoId;
    private String descricao;
    private Valor valor;
    private String categoria;
    private LocalDate dataTransacao;
    private String contaId;
    private LocalDateTime dataCriacao;

    private Transacao(Builder builder) {
        this.codigoTransacao = builder.codigoTransacao;
        this.codUsuario = builder.codUsuario;
        this.plaidTransacaoId = builder.plaidTransacaoId;
        this.descricao = builder.descricao;
        this.valor = builder.valor;
        this.categoria = builder.categoria;
        this.dataTransacao = builder.dataTransacao;
        this.contaId = builder.contaId;
        this.dataCriacao = builder.dataCriacao;
    }

    public void atualizar(String descricao, BigDecimal valor, String categoria) {
        this.descricao = descricao;
        this.valor = Valor.de(valor);
        this.categoria = categoria;
    }

    public boolean isDebito() {
        return valor.valor().compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isCredito() {
        return valor.valor().compareTo(BigDecimal.ZERO) < 0;
    }

    public static class Builder {
        private CodTransacao codigoTransacao;
        private CodUsuario codUsuario;
        private String plaidTransacaoId;
        private String descricao;
        private Valor valor;
        private String categoria;
        private LocalDate dataTransacao;
        private String contaId;
        private LocalDateTime dataCriacao;

        public Builder codigoTransacao(CodTransacao codigoTransacao) {
            this.codigoTransacao = codigoTransacao;
            return this;
        }

        public Builder codUsuario(CodUsuario codUsuario) {
            this.codUsuario = codUsuario;
            return this;
        }

        public Builder plaidTransacaoId(String plaidTransacaoId) {
            this.plaidTransacaoId = plaidTransacaoId;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder valor(Valor valor) {
            this.valor = valor;
            return this;
        }

        public Builder categoria(String categoria) {
            this.categoria = categoria;
            return this;
        }

        public Builder dataTransacao(LocalDate dataTransacao) {
            this.dataTransacao = dataTransacao;
            return this;
        }

        public Builder contaId(String contaId) {
            this.contaId = contaId;
            return this;
        }

        public Builder dataCriacao(LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        public Transacao build() {
            return new Transacao(this);
        }
    }
}
