package com.finance.leluseven.plaid.domain;

import com.finance.leluseven.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.plaid.domain.vo.SyncResult;
import com.finance.leluseven.transacao.domain.Transacao;

import java.time.LocalDate;
import java.util.List;

public interface IPlaidRepository {
    String criarLinkToken(String userId);
    ConexaoPlaid trocarPublicToken(String publicToken);
    List<ContaBancaria> listarContas(PlaidToken accessToken);
    List<Transacao> listarTransacoes(PlaidToken accessToken, LocalDate inicio, LocalDate fim);
    SyncResult sincronizarTransacoes(PlaidToken accessToken, String cursor);
}
