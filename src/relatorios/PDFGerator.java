package relatorios;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import interfaces.reportGerator.IGeradorRelatorioDiario;
import modelo.Tarefa;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

public class PDFGerator implements IGeradorRelatorioDiario {

    @Override
    public void gerarRelatorioDiario(List<Tarefa> tarefas, LocalDate dia, String nomeArquivo) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(nomeArquivo));
            document.open();

            document.add(new Paragraph("Relatório de tarefas do dia: " + dia));
            document.add(new Paragraph(" "));  // Linha em branco

            boolean encontrou = false;

            for (Tarefa tarefa : tarefas) {
                if (tarefa.getDeadline().equals(dia)) {
                    document.add(new Paragraph(tarefa.toString()));
                    document.add(new Paragraph(" "));
                    encontrou = true;
                }
            }

            if (!encontrou) {
                document.add(new Paragraph("Nenhuma tarefa encontrada para essa data."));
            }

            System.out.println("PDF gerado com sucesso: " + nomeArquivo);

        } catch (FileNotFoundException | DocumentException e) {
            System.out.println("Erro ao gerar o PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }
}
