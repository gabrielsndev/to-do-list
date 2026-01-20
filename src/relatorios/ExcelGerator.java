package relatorios;

import interfaces.ICalculadorProgresso;
import interfaces.reportGerator.IGeradorRelatorioMensal;
import modelo.Tarefa;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ExcelGerator implements IGeradorRelatorioMensal {

    private final ICalculadorProgresso calculador;

    public ExcelGerator(ICalculadorProgresso calculador) {
        this.calculador = calculador;
    }

    @Override
    public void gerarRelatorioMensal(List<Tarefa> tarefas, int ano, int mes, String nomeArquivo) {
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

                double progresso = calculador.calcularPercentualConcluido(t);
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

        try (FileOutputStream out = new FileOutputStream(nomeArquivo)) {
            workbook.write(out);
            System.out.println("Planilha gerada com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao gerar a planilha: " + e.getMessage());
        }
    }
}
