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

    public static Transacao dePlaid(String plaidId, String descricao, BigDecimal valor, LocalDate dataTransacao,
                                    String categoria) {
        var transacao = new Transacao();
        transacao.plaidTransacaoId = plaidId;
        transacao.descricao = descricao;
        transacao.valor = Valor.de(valor);
        transacao.dataTransacao = dataTransacao;
        transacao.categoria = categoria;
        return transacao;
    }

    public static Transacao de(String plaidId, String descricao, BigDecimal valor, LocalDate dataTransacao,
                                    String categoria, CodUsuario codUsuario) {
        var transacao = new Transacao();
        transacao.plaidTransacaoId = plaidId;
        transacao.descricao = descricao;
        transacao.valor = Valor.de(valor);
        transacao.dataTransacao = dataTransacao;
        transacao.categoria = categoria;
        transacao.codUsuario = codUsuario;
        return transacao;
    }

    public static Transacao reconstruir(Long codTransacao, String plaidId, String descricao, BigDecimal valor,
                                        LocalDate data, String categoria, CodUsuario codUsuario) {
        var transacao = new Transacao();
        transacao.codigoTransacao = CodTransacao.de(codTransacao);
        transacao.plaidTransacaoId = plaidId;
        transacao.descricao = descricao;
        transacao.valor = Valor.de(valor);
        transacao.dataTransacao = data;
        transacao.categoria = categoria;
        transacao.codUsuario = codUsuario;
        return transacao;
    }

    public void atualizar(String descricao, BigDecimal valor, String categora) {
        this.descricao = descricao;
        this.valor = Valor.de(valor);
        this.categoria = categora;
    }

    public boolean isDebito() {
        return valor.valor().compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isCredito() {
        return valor.valor().compareTo(BigDecimal.ZERO) < 0;
    }

}
