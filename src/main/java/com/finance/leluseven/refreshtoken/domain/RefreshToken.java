package com.finance.leluseven.refreshtoken.domain;

import com.finance.leluseven.refreshtoken.domain.vo.CodRefreshToken;
import com.finance.leluseven.refreshtoken.domain.vo.Dispositivo;
import com.finance.leluseven.usuario.domain.Usuario;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RefreshToken {
    private CodRefreshToken codRefreshToken;
    private String rToken;
    private LocalDateTime datExpiracao;
    private Dispositivo dispositivo;
    private Boolean isRevogado;
    private Usuario usuario;
    private LocalDateTime datCriacao;

    private RefreshToken(Builder builder) {
        this.codRefreshToken = builder.codRefreshToken;
        this.rToken = builder.rToken;
        this.datExpiracao = builder.datExpiracao;
        this.dispositivo = builder.dispositivo;
        this.isRevogado = builder.isRevogado;
        this.usuario = builder.usuario;
        this.datCriacao = builder.datCriacao;
    }

    public boolean isValido() {
        return !isRevogado && LocalDateTime.now().isBefore(datExpiracao);
    }

    public void revogar() {
        this.datExpiracao = LocalDateTime.now();
        this.isRevogado = true;
    }

    public static class Builder {
        private CodRefreshToken codRefreshToken;
        private String rToken;
        private LocalDateTime datExpiracao;
        private Dispositivo dispositivo;
        private Boolean isRevogado;
        private Usuario usuario;
        private LocalDateTime datCriacao;

        public Builder codRefreshToken(CodRefreshToken codRefreshToken) {
            this.codRefreshToken = codRefreshToken;
            return this;
        }

        public Builder rToken(String rToken) {
            this.rToken = rToken;
            return this;
        }

        public Builder datExpiracao(LocalDateTime datExpiracao) {
            this.datExpiracao = datExpiracao;
            return this;
        }

        public Builder dispositivo(Dispositivo dispositivo) {
            this.dispositivo = dispositivo;
            return this;
        }

        public Builder isRevogado(Boolean isRevogado) {
            this.isRevogado = isRevogado;
            return this;
        }

        public Builder usuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder datCriacao(LocalDateTime datCriacao) {
            this.datCriacao = datCriacao;
            return this;
        }

        public RefreshToken build() {
            return new RefreshToken(this);
        }
    }
}
