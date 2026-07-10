package com.finance.leluseven.finances.conexaoplaid.infrastructure;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_conexao_plaid", schema = "financeiro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConexaoPlaidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cod_conexao_plaid_seq")
    @SequenceGenerator(name = "cod_conexao_plaid_seq",
            sequenceName = "financeiro.tb_conexao_plaid_cod_conexao_plaid_seq",
            allocationSize = 1)
    @Column(name = "cod_conexao_plaid")
    private Long codConexcaoPlaid;

    @Column(name = "cod_usuario")
    private Long codUsuario;

    @Column(name = "str_item_id")
    private String itemId;       // identifica a conexão

    @Column(name = "str_access_token")
    private String accessToken;  // autentica chamadas

    @Column(name = "str_cursor")
    private String cursor;       // progresso da sincronização

    @Column(name = "nom_instituicao")
    private String instituicao;  // "Nubank", "Itaú"...

    @Column(name = "sta_ativo")
    private boolean ativo = true;
}
