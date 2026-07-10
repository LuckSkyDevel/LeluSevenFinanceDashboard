package com.finance.leluseven.finances.plaid.domain.vo;

import com.finance.leluseven.finances.transacao.domain.Transacao;

import java.util.List;

public record SyncResult(
        List<Transacao> adicionadas,
        List<Transacao> modificadas,
        List<String> removidasIds,
        String novoCursor,
        boolean temMaisPaginas
) {
}
