package com.finance.leluseven.finances.conexaoplaid.domain;

import com.finance.leluseven.finances.conexaoplaid.domain.vo.*;
import com.finance.leluseven.finances.conexaoplaid.domain.vo.*;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.Getter;

@Getter
public class ConexaoPlaid {
    private CodConexaoPlaid codConexaoPlaid;
    private CodUsuario codUsuario;
    private AccessTokenPlaid accessToken;  // autentica chamadas
    private ItemIdPlaid itemId;       // identifica a conexão
    private CursorPlaid plaidCursor;       // progresso da sincronização
    private Instituicao instituicao;  // "Nubank", "Itaú"...
    private boolean ativo;

    public static ConexaoPlaid criar(String accessToken, String itemId, String instituicao) {
        var conexao = new ConexaoPlaid();
        conexao.accessToken = AccessTokenPlaid.de(accessToken);
        conexao.itemId      = ItemIdPlaid.de(itemId);
        conexao.instituicao = Instituicao.de(instituicao);
        return conexao;
    }

    public static ConexaoPlaid vincularUsuario(Long codUsuario, String accessToken, String itemId, String instituicao) {
        var conexao = new ConexaoPlaid();
        conexao.codUsuario = CodUsuario.de(codUsuario);
        conexao.accessToken = AccessTokenPlaid.de(accessToken);
        conexao.itemId      = ItemIdPlaid.de(itemId);
        conexao.instituicao = Instituicao.de(instituicao);
        return conexao;
    }

    public static ConexaoPlaid reconstituir(Long codConexao, Long codUsuario, String accessToken, String itemId, String instituicao) {
        var conexao = new ConexaoPlaid();
        conexao.codConexaoPlaid = CodConexaoPlaid.de(codConexao);
        conexao.codUsuario = CodUsuario.de(codUsuario);
        conexao.accessToken = AccessTokenPlaid.de(accessToken);
        conexao.itemId      = ItemIdPlaid.de(itemId);
        conexao.instituicao = Instituicao.de(instituicao);
        return conexao;
    }

    public void atualizarPlaidCursor(String cursor) {
        this.plaidCursor = CursorPlaid.de(cursor);
    }
}
