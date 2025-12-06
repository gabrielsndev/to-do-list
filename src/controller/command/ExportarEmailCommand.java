package controller.command;

import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import servico.RelatorioServico;
import servico.SessionManager;


import strategy.IExportacaoStrategy;
import strategy.ExportarEmailPdfStrategy;

public class ExportarEmailCommand implements Command {

    private Component parentView;
    private JTextField emailField;
    private JTextField dataField;
    private RelatorioServico relatorioServico;

    public ExportarEmailCommand(Component parentView, JTextField emailField, JTextField dataField) throws Exception {
        SessionManager session = SessionManager.getInstance();
        this.parentView = parentView;
        this.emailField = emailField;
        this.dataField = dataField;
        
        this.relatorioServico = new RelatorioServico(session);
    }

    @Override
    public void execute() {
        String destinatario = emailField.getText().trim();
        String dataStr = dataField.getText().trim();

        if (destinatario.isEmpty() || dataStr.isEmpty()) {
            JOptionPane.showMessageDialog(parentView, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            LocalDate data;
            try {
                data = LocalDate.parse(dataStr);
            } catch (Exception e) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                data = LocalDate.parse(dataStr, dtf);
            }

            
            // cria a estratégia específica, passando os dados que ela precisa (email e data)
            IExportacaoStrategy estrategia = new ExportarEmailPdfStrategy(destinatario, data);
            
            // O serviço vai buscar as tarefas do banco e entregar para a estratégia.
            relatorioServico.processarExportacao(estrategia);



            JOptionPane.showMessageDialog(parentView, "E-mail enviado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parentView, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}