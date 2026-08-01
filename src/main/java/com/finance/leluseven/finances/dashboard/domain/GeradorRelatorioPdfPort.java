package com.finance.leluseven.finances.dashboard.domain;

public interface GeradorRelatorioPdfPort {

    /**
     * Gera o PDF do relatório e retorna os bytes prontos para escrita na resposta HTTP.
     */
    byte[] gerar(RelatorioFinanceiro relatorio);
}
