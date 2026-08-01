package com.finance.leluseven.perfil.domain;

import com.finance.leluseven.perfil.domain.vo.CodPerfil;
import com.finance.leluseven.perfil.domain.vo.NomePerfil;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Perfil {
    private CodPerfil codigoPerfil;
    private NomePerfil nomePerfil;
    private String descricao;
    private Boolean ativo;
    private LocalDate dataCriacao;

    private Perfil(Builder builder) {
        this.codigoPerfil = builder.codPerfil;
        this.nomePerfil = builder.nomePerfil;
        this.descricao = builder.descricao;
        this.ativo = builder.ativo;
        this.dataCriacao = builder.dataCriacao;
    }

    public static class Builder {
        private CodPerfil codPerfil;
        private NomePerfil nomePerfil;
        private String descricao;
        private Boolean ativo;
        private LocalDate dataCriacao;

        public Builder codPerfil(CodPerfil codPerfil) {
            this.codPerfil = codPerfil;
            return this;
        }

        public Builder nomePerfil(NomePerfil nomePerfil) {
            this.nomePerfil = nomePerfil;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder ativo(Boolean ativo) {
            this.ativo = ativo;
            return this;
        }

        public Builder dataCriacao(LocalDate dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        public Perfil build() {
            return new Perfil(this);
        }
    }
}
