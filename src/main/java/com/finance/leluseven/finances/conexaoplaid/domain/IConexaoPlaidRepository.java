package com.finance.leluseven.finances.conexaoplaid.domain;

import com.finance.leluseven.finances.conexaoplaid.domain.vo.CodConexaoPlaid;
import com.finance.leluseven.finances.conexaoplaid.domain.vo.CursorPlaid;
import com.finance.leluseven.finances.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;

import java.util.List;
import java.util.Optional;

public interface IConexaoPlaidRepository {

    ConexaoPlaid salvaConexaoPlaid(ConexaoPlaid conexaoPlaid);

    List<ConexaoPlaid> listaConexoesPlaidPorCodUsuario(CodUsuario codUsuario);

    Optional<ConexaoPlaid> recuperaConexaoPlaidPorAccessToken(PlaidToken plaidToken);

    Optional<ConexaoPlaid> recuperaConexaoPlaidPorCodConexaoPlaid(CodConexaoPlaid codConexaoPlaid);

    void inativaConexaoPlaid(CodConexaoPlaid codConexaoPlaid);

    void atualizaPlaidCursor(CodConexaoPlaid codConexaoPlaid, CursorPlaid cursor);
}
