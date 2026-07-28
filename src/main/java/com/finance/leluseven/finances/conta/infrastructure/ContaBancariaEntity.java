package com.finance.leluseven.finances.conta.infrastructure;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_conta_bancaria", schema = "financeiro")
@Getter
@Setter
public class ContaBancariaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cod_conta_bancaria_seq")
    @SequenceGenerator(name = "cod_conta_bancaria_seq",
            sequenceName = "financeiro.tb_conta_bancaria_cod_conta_bancaria_seq",
            allocationSize = 1)
    @Column(name = "cod_conta_bancaria")
    Long codContaBancaria;

    @Column(name = "nom_conta_bancaria")
    String nomeContaBancaria;

    @Column(name = "val_saldo")
    BigDecimal saldo;

    @Column(name = "dat_criacao")
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "st_ativo")
    private Boolean isAtivo = true;
}
