package com.finance.leluseven.usuario.domain;

import com.finance.leluseven.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.shared.exception.DomainException;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import com.finance.leluseven.usuario.domain.vo.Email;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;
import com.finance.leluseven.usuario.domain.vo.Senha;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Usuario {
    private CodUsuario codUsuario;
    private NomeUsuario nome;
    private Email email;
    private Senha senha;
    private List<String> perfis;
    private ConexaoPlaid conexaoPlaid;
    private LocalDate dataCriacao;

    // construtor para novo usuário
    public static Usuario criar(String nome, String email, String senha, PasswordEncoder encoder) {
        var usuario = new Usuario();
        usuario.nome = NomeUsuario.de(nome);
        usuario.email = Email.de(email);
        usuario.senha = Senha.criar(senha, encoder);
        usuario.dataCriacao = LocalDate.now();
        usuario.perfis = List.of("USUARIO");
        return usuario;
    }

    // construtor para reconstituir do banco
    public static Usuario reconstituir(Long codUsuario, String nome, String email, String senhaHash, List<String> perfis,
                                       LocalDate dataCriacao
    ) {
        var usuario = new Usuario();
        usuario.codUsuario = CodUsuario.de(codUsuario);
        usuario.nome = NomeUsuario.de(nome);
        usuario.email = Email.de(email);
        usuario.senha = Senha.doBanco(senhaHash);
        usuario.perfis = perfis;
        usuario.dataCriacao = dataCriacao;
        return usuario;
    }

    // regras de negócio
    public boolean validarSenha(String senhaPura, PasswordEncoder encoder) {
        return this.senha.confere(senhaPura, encoder);
    }

    public void adicionarPerfil(String perfil) {
        if (!this.perfis.contains(perfil))
            this.perfis.add(perfil);
    }

}
