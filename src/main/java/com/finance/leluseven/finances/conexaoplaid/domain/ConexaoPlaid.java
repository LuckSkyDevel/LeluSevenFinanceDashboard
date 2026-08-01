package com.finance.leluseven.finances.conexaoplaid.domain;

import com.finance.leluseven.finances.conexaoplaid.domain.vo.*;
import com.finance.leluseven.finances.conexaoplaid.domain.vo.*;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.Getter;

@Getter
public class ConexaoPlaid {
    private final CodConexaoPlaid codConexaoPlaid;
    private final CodUsuario codUsuario;
    private final AccessTokenPlaid accessToken;  // autentica chamadas
    private final ItemIdPlaid itemId;       // identifica a conexão
    private CursorPlaid plaidCursor;       // progresso da sincronização
    private final Instituicao instituicao;  // "Nubank", "Itaú"...
    private final boolean ativo;

    private ConexaoPlaid(Builder builder){
        this.codConexaoPlaid = builder.codConexaoPlaid;
        this.codUsuario = builder.codUsuario;
        this.accessToken = builder.accessTokenPlaid;
        this.itemId = builder.itemIdPlaid;
        this.plaidCursor = builder.plaidCursor;
        this.instituicao = builder.instituicao;
        this.ativo = builder.ativo;
    }

    public void atualizarPlaidCursor(String cursor) {
        this.plaidCursor = CursorPlaid.de(cursor);
    }

    public static class Builder {
        private CodConexaoPlaid codConexaoPlaid;
        private CodUsuario codUsuario;
        private AccessTokenPlaid accessTokenPlaid;
        private ItemIdPlaid itemIdPlaid;
        private CursorPlaid plaidCursor;
        private Instituicao instituicao;
        private boolean ativo;

        public Builder codConexaoPlaid(CodConexaoPlaid codConexaoPlaid) {
            this.codConexaoPlaid = codConexaoPlaid;
            return this;
        }

        public Builder codUsuario(CodUsuario codUsuario) {
            this.codUsuario = codUsuario;
            return this;
        }

        public Builder accessTokenPlaid(AccessTokenPlaid accessTokenPlaid) {
            this.accessTokenPlaid = accessTokenPlaid;
            return this;
        }

        public Builder itemIdPlaid(ItemIdPlaid itemIdPlaid) {
            this.itemIdPlaid = itemIdPlaid;
            return this;
        }

        public Builder plaidCursor(CursorPlaid plaidCursor) {
            this.plaidCursor = plaidCursor;
            return this;
        }

        public Builder instituicao(Instituicao instituicao) {
            this.instituicao = instituicao;
            return this;
        }

        public Builder ativo(boolean ativo) {
            this.ativo = ativo;
            return this;
        }

        public  ConexaoPlaid build() {
            return new ConexaoPlaid(this);
        }
    }
}
