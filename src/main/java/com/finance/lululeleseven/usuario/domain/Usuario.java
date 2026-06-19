package com.finance.lululeleseven.usuario.domain;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Usuario {
    private CodUsuario codUsuario;
    private String nome;
    private Email email;
    private Senha senha;
    private String refreshToken;
    private List<String> perfis;
    private LocalDate dataCriacao;

    // construtor para novo usuário
    public static Usuario criar(String nome, String email, String senha, PasswordEncoder encoder) {
        var usuario = new Usuario();
        usuario.nome = nome;
        usuario.email = Email.de(email);
        usuario.senha = Senha.criar(senha, encoder);
        usuario.dataCriacao = LocalDate.now();
        usuario.perfis = List.of("USUARIO");
        return usuario;
    }

    // construtor para reconstituir do banco
    public static Usuario reconstituir(
            Long codUsuario, String nome, String email,
            String senhaHash, String refreshToken,
            List<String> perfis, LocalDate dataCriacao
    ) {
        var usuario = new Usuario();
        usuario.codUsuario = CodUsuario.de(codUsuario);
        usuario.nome = nome;
        usuario.email = Email.de(email);
        usuario.senha = Senha.doBanco(senhaHash);
        usuario.refreshToken = refreshToken;
        usuario.perfis = perfis;
        usuario.dataCriacao = dataCriacao;
        return usuario;
    }

    // regras de negócio
    public boolean validarSenha(String senhaPura, PasswordEncoder encoder) {
        return this.senha.confere(senhaPura, encoder);
    }

    public void atualizarRefreshToken(String token) {
        this.refreshToken = token;
    }

    public void adicionarPerfil(String perfil) {
        if (!this.perfis.contains(perfil))
            this.perfis.add(perfil);
    }
}
