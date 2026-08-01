package com.finance.leluseven.finances.dashboard.infrastructure.pdf;

import com.finance.leluseven.finances.dashboard.domain.GeradorRelatorioPdfPort;
import com.finance.leluseven.finances.dashboard.domain.RelatorioFinanceiro;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class RelatorioPdfGenerator implements GeradorRelatorioPdfPort {

    @Override
    public byte[] gerar(RelatorioFinanceiro relatorio) throws IOException {
        try (PDDocument doc = new PDDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 14);
                content.newLineAtOffset(50, 750);
                content.showText("Resumo Financeiro");
                content.endText();

                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(50, 730);
                content.showText("Periodo: " + relatorio.getPeriodoInicio() + " a " + relatorio.getPeriodoFim());
                content.newLineAtOffset(0, -16);
                content.showText("Receitas: " + relatorio.getTotalReceitas());
                content.newLineAtOffset(0, -16);
                content.showText("Despesas: " + relatorio.getTotalDespesas());
                content.endText();

                // Para tabelas/linhas maiores, crie mais páginas ou use utilitários para tabela.
            }

            doc.save(baos);
            return baos.toByteArray();
        }
    }
}
