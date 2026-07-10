package com.finance.leluseven.finances.transacao.infrastructure;

import com.finance.leluseven.usuario.infrastructure.UsuarioEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transacao", schema = "financeiro")
@Getter
@Setter
public class TransacaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cod_transacao_seq")
    @SequenceGenerator(name = "cod_transacao_seq",
            sequenceName = "financeiro.tb_transacao_cod_transacao_seq",
            allocationSize = 1)
    @Column(name = "cod_transacao")
    private Long codtransacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_usuario")
    private UsuarioEntity usuario;

    @Column(name = "str_plaid_transacao_id", unique = true)
    private String plaidTransacaoId;

    @Column(name = "des_descricao")
    private String descricao;

    @Column(name = "val_transacao", precision = 19, scale = 2)
    private BigDecimal valor;

    @Column(name = "des_categoria")
    private String categoria;

    @Column(name = "dat_transacao")
    private LocalDate datTransacao;

    @Column(name = "str_conta_id")
    private String contaId;

    @Column(name = "dat_criacao")
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "st_ativo")
    private Boolean isAtivo = true;
}
