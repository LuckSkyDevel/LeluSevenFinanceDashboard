package com.finance.leluseven.usuario.domain;

import com.finance.leluseven.finances.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.perfil.domain.Perfil;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import com.finance.leluseven.usuario.domain.vo.Email;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;
import com.finance.leluseven.usuario.domain.vo.Senha;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Usuario {
    private CodUsuario codUsuario;
    private NomeUsuario nome;
    private Email email;
    private Senha senha;
    private List<Perfil> perfis;
    private ConexaoPlaid conexaoPlaid;
    private LocalDate dataCriacao;

    private Usuario(Builder builder) {
        this.codUsuario = builder.codUsuario;
        this.nome = builder.nome;
        this.email = builder.email;
        this.senha = builder.senha;
        this.perfis = builder.perfis;
        this.conexaoPlaid = builder.conexaoPlaid;
        this.dataCriacao = builder.dataCriacao;
    }

    // regras de negócio
    public boolean validarSenha(String senhaPura, PasswordEncoder encoder) {
        return this.senha.confere(senhaPura, encoder);
    }

    public void adicionarPerfil(Perfil perfil) {
        if (this.perfis == null) {
            this.perfis = new ArrayList<>();
        }

        if (!this.perfis.contains(perfil))
            this.perfis.add(perfil);
    }

    public static class Builder {
        private CodUsuario codUsuario;
        private NomeUsuario nome;
        private Email email;
        private Senha senha;
        private List<Perfil> perfis;
        private ConexaoPlaid conexaoPlaid;
        private LocalDate dataCriacao;

        public Builder codUsuario(CodUsuario codUsuario) {
            this.codUsuario = codUsuario;
            return this;
        }

        public Builder nome(NomeUsuario nome) {
            this.nome = nome;
            return this;
        }

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder senha(Senha senha) {
            this.senha = senha;
            return this;
        }

        public Builder perfis(List<Perfil> perfis) {
            this.perfis = perfis;
            return this;
        }

        public Builder conexaoPlaid(ConexaoPlaid conexaoPlaid) {
            this.conexaoPlaid = conexaoPlaid;
            return this;
        }

        public Builder dataCriacao(LocalDate dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }

}
