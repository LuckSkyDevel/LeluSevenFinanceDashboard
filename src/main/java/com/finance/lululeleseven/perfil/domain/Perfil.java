package com.finance.lululeleseven.perfil.domain;

import com.finance.lululeleseven.perfil.domain.vo.CodPerfil;
import com.finance.lululeleseven.perfil.domain.vo.NomePerfil;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Perfil {
    private CodPerfil codigoPerfil;
    private NomePerfil nomePerfil;
    private String descricao;
    private Boolean ativo;
    private LocalDate dataCriacao;

    public static Perfil criar(String nomePerfil, String descricao) {
        var perfil = new Perfil();
        perfil.nomePerfil = NomePerfil.de(nomePerfil);
        perfil.descricao = descricao;
        return perfil;
    }

    // construtor para reconstituir do banco
    public static Perfil reconstituir(
            Long codPerfil, String nome, String descricao, Boolean ativo, LocalDate dataCriacao
    ) {
        var perfil = new Perfil();
        perfil.codigoPerfil = CodPerfil.de(codPerfil);
        perfil.nomePerfil = NomePerfil.de(nome);
        perfil.descricao = descricao;
        perfil.ativo = ativo;
        perfil.dataCriacao = dataCriacao;
        return perfil;
    }
}
