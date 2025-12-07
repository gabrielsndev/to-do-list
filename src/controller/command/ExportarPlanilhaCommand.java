package controller.command;

import strategy.ExportarPlanilhaStrategy;
import strategy.IExportacaoStrategy;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import servico.RelatorioServico;
import servico.SessionManager;

public class ExportarPlanilhaCommand implements Command {

    private Component parentView;
    private JTextField mesField; // Campo com formato YYYY-MM
    private RelatorioServico relatorioServico;

    public ExportarPlanilhaCommand(Component parentView, JTextField mesField) throws Exception {
        this.parentView = parentView;
        this.mesField = mesField;
        this.relatorioServico = new RelatorioServico();
    }

    @Override
    public void execute() {
        String mesAno = mesField.getText().trim();

        if (mesAno.isEmpty()) {
            JOptionPane.showMessageDialog(parentView, "Preencha o campo Mês (YYYY-MM)!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String[] parts = mesAno.split("-");
            if (parts.length != 2) throw new IllegalArgumentException("Formato inválido. Use YYYY-MM");

            int ano = Integer.parseInt(parts[0]);
            int mes = Integer.parseInt(parts[1]);

            IExportacaoStrategy estrategia = new ExportarPlanilhaStrategy(ano, mes, relatorioServico.getTarefaServico() );
            
            relatorioServico.processarExportacao(estrategia);
            

            JOptionPane.showMessageDialog(parentView, "Planilha exportada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parentView, "Ano e Mês devem ser números!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parentView, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}