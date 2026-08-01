package com.finance.leluseven.finances.dashboard.infrastructure.pdf;

import com.finance.leluseven.finances.dashboard.domain.GeradorRelatorioPdfPort;
import com.finance.leluseven.finances.dashboard.domain.RelatorioFinanceiro;
import com.finance.leluseven.finances.dashboard.domain.vo.GastoCategoria;
import com.finance.leluseven.shared.exception.DomainException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class RelatorioPdfGenerator implements GeradorRelatorioPdfPort {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Locale LOCALE_PT_BR = new Locale("pt", "BR");

    private static final Color COR_RECEITA = new Color(0, 128, 0);
    private static final Color COR_DESPESA = new Color(178, 34, 34);
    private static final Color COR_CABECALHO = new Color(40, 53, 147);

    private static final Font FONTE_TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLACK);
    private static final Font FONTE_SUBTITULO = FontFactory.getFont(FontFactory.HELVETICA, 11, Color.DARK_GRAY);
    private static final Font FONTE_SECAO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13, Color.BLACK);
    private static final Font FONTE_ROTULO_CARD = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.DARK_GRAY);
    private static final Font FONTE_CABECALHO_TABELA = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
    private static final Font FONTE_CELULA = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.BLACK);

    @Override
    public byte[] gerar(RelatorioFinanceiro relatorio) {
        var out = new ByteArrayOutputStream();

        try {
            var document = new Document(PageSize.A4, 36, 36, 54, 36);
            PdfWriter.getInstance(document, out);
            document.open();

            adicionarCabecalho(document, relatorio);
            adicionarResumo(document, relatorio);
            adicionarGastosPorCategoria(document, relatorio);
            adicionarTransacoes(document, relatorio);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new DomainException("Erro ao gerar relatório em PDF: " + e.getMessage(), e);
        }
    }

    private void adicionarCabecalho(Document document, RelatorioFinanceiro relatorio) {
        var titulo = new Paragraph("LeLu Seven Finance — Relatório Financeiro", FONTE_TITULO);
        titulo.setSpacingAfter(4);
        document.add(titulo);

        var periodo = "Período: %s a %s".formatted(
                relatorio.getPeriodoInicio().format(FORMATO_DATA),
                relatorio.getPeriodoFim().format(FORMATO_DATA));

        var subtitulo = new Paragraph(periodo, FONTE_SUBTITULO);
        subtitulo.setSpacingAfter(16);
        document.add(subtitulo);
    }

    private void adicionarResumo(Document document, RelatorioFinanceiro relatorio) {
        var tabela = new PdfPTable(3);
        tabela.setWidthPercentage(100);
        tabela.setSpacingAfter(20);

        var corSaldo = relatorio.getSaldo().compareTo(BigDecimal.ZERO) >= 0 ? COR_RECEITA : COR_DESPESA;

        tabela.addCell(celulaResumo("Receitas", relatorio.getTotalReceitas(), COR_RECEITA));
        tabela.addCell(celulaResumo("Despesas", relatorio.getTotalDespesas(), COR_DESPESA));
        tabela.addCell(celulaResumo("Saldo", relatorio.getSaldo(), corSaldo));

        document.add(tabela);
    }

    private PdfPCell celulaResumo(String rotulo, BigDecimal valor, Color cor) {
        var container = new PdfPCell();
        container.setPadding(10);
        container.setBorderColor(Color.LIGHT_GRAY);

        var fonteValor = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, cor);

        var rotuloParagrafo = new Paragraph(rotulo, FONTE_ROTULO_CARD);
        var valorParagrafo = new Paragraph(formatarMoeda(valor), fonteValor);
        valorParagrafo.setSpacingBefore(4);

        container.addElement(rotuloParagrafo);
        container.addElement(valorParagrafo);

        return container;
    }

    private void adicionarGastosPorCategoria(Document document, RelatorioFinanceiro relatorio) {
        if (relatorio.getGastosPorCategoria().isEmpty())
            return;

        document.add(new Paragraph("Gastos por categoria", FONTE_SECAO));
        document.add(new Paragraph(" "));

        var tabela = new PdfPTable(new float[]{3, 2, 1, 1.5f});
        tabela.setWidthPercentage(100);
        tabela.setSpacingAfter(20);

        adicionarCabecalhoTabela(tabela, "Categoria", "Total", "Qtd.", "% do total");

        for (GastoCategoria gasto : relatorio.getGastosPorCategoria()) {
            tabela.addCell(celula(gasto.categoria()));
            tabela.addCell(celula(formatarMoeda(gasto.total())));
            tabela.addCell(celula(String.valueOf(gasto.quantidade())));
            tabela.addCell(celula(gasto.percentual() + "%"));
        }

        document.add(tabela);
    }

    private void adicionarTransacoes(Document document, RelatorioFinanceiro relatorio) {
        if (relatorio.getTransacoes().isEmpty())
            return;

        document.add(new Paragraph("Transações do período", FONTE_SECAO));
        document.add(new Paragraph(" "));

        var tabela = new PdfPTable(new float[]{1.2f, 3, 2, 1.5f});
        tabela.setWidthPercentage(100);

        adicionarCabecalhoTabela(tabela, "Data", "Descrição", "Categoria", "Valor");

        relatorio.getTransacoes().stream()
                .sorted((a, b) -> b.getDataTransacao().compareTo(a.getDataTransacao()))
                .forEach(t -> {
                    tabela.addCell(celula(t.getDataTransacao().format(FORMATO_DATA)));
                    tabela.addCell(celula(t.getDescricao() == null ? "-" : t.getDescricao()));
                    tabela.addCell(celula(t.getCategoria() == null ? "-" : t.getCategoria()));

                    var sinal = t.isDebito() ? "-" : "+";
                    var valorCelula = celula(sinal + formatarMoeda(t.getValor().valor().abs()));
                    valorCelula.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tabela.addCell(valorCelula);
                });

        document.add(tabela);
    }

    private void adicionarCabecalhoTabela(PdfPTable tabela, String... colunas) {
        for (String coluna : colunas) {
            var cabecalho = new PdfPCell(new Phrase(coluna, FONTE_CABECALHO_TABELA));
            cabecalho.setBackgroundColor(COR_CABECALHO);
            cabecalho.setPadding(6);
            tabela.addCell(cabecalho);
        }
    }

    private PdfPCell celula(String texto) {
        var cell = new PdfPCell(new Phrase(texto, FONTE_CELULA));
        cell.setPadding(6);
        return cell;
    }

    private String formatarMoeda(BigDecimal valor) {
        return String.format(LOCALE_PT_BR, "R$ %,.2f", valor);
    }
}
