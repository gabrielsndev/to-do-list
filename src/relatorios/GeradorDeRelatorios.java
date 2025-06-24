package relatorios;
import modelo.CentralDeInformacoes;
import modelo.Tarefa;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;

public class GeradorDeRelatorios {
	public static void obterTarefasDeUmDia(LocalDate dia, CentralDeInformacoes central) {
		Document document = new Document();
		

        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorio.pdf"));
            document.open();
            
            document.add(new Paragraph("Relatório de tarefas do dia: " + dia));
            document.add(new Paragraph(" "));  // Linha em branco

            boolean encontrou = false;

            for (Tarefa tarefa : central.getTodasAsTarefas()) {
                if (tarefa.getDeadline().equals(dia)) {
                    document.add(new Paragraph(tarefa.toString()));
                    document.add(new Paragraph(" "));
                    encontrou = true;
                }
            }

            if (!encontrou) {
                document.add(new Paragraph("Nenhuma tarefa encontrada para essa data."));
            }

            System.out.println("PDF gerado com sucesso: relatorio.pdf");

        } catch (FileNotFoundException | DocumentException e) {
            System.out.println("Erro ao gerar o PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    
	}
	

}


