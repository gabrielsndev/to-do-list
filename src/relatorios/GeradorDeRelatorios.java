package relatorios;
import modelo.CentralDeInformacoes;
import modelo.Tarefa;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class GeradorDeRelatorios {
	public static void gerarRelatorioPDFDoDia(LocalDate dia, List<Tarefa> central,String nomeArquivo) {
		Document document = new Document();
		

        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorio.pdf"));
            document.open();
            
            document.add(new Paragraph("Relatório de tarefas do dia: " + dia));
            document.add(new Paragraph(" "));  // Linha em branco

            boolean encontrou = false;

            for (Tarefa tarefa : central) {
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
	 public static void gerarPlanilhaMensal(List<Tarefa> tarefas, int ano, int mes, String nomeArquivoExcel) {
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Relatório Mensal");

	        // Cabeçalho
	        Row header = sheet.createRow(0);
	        String[] colunas = {"Título", "Descrição", "Deadline", "Prioridade", "Progresso (%)", "Status"};

	        for (int i = 0; i < colunas.length; i++) {
	            Cell cell = header.createCell(i);
	            cell.setCellValue(colunas[i]);
	        }

	        int linha = 1;
	        for (Tarefa t : tarefas) {
	            if (t.getDeadline().getYear() == ano && t.getDeadline().getMonthValue() == mes) {
	                Row row = sheet.createRow(linha++);

	                double progresso = t.getPercentualConcluido();
	                boolean sucesso = progresso == 100.0 && !t.getDeadline().isBefore(LocalDate.now());

	                row.createCell(0).setCellValue(t.getTitulo());
	                row.createCell(1).setCellValue(t.getDescricao());
	                row.createCell(2).setCellValue(t.getDeadline().toString());
	                row.createCell(3).setCellValue(t.getPrioridade());
	                row.createCell(4).setCellValue(progresso);
	                row.createCell(5).setCellValue(sucesso ? "Sucesso" : "Fracasso");
	            }
	        }

	        for (int i = 0; i < colunas.length; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        try (FileOutputStream out = new FileOutputStream(nomeArquivoExcel)) {
	            workbook.write(out);
	            System.out.println("Planilha gerada com sucesso: " + nomeArquivoExcel);
	        } catch (IOException e) {
	            System.out.println("Erro ao gerar a planilha: " + e.getMessage());
	        }
	    }
	

}


