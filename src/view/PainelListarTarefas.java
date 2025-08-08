package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import modelo.Tarefa;
import persistencia.TarefaDAO;
import model.ButtonRenderer;
import model.DataPrazoRender;
import model.TipoDAO;
import model.ButtonEditor;

public class PainelListarTarefas extends JPanel {

    public PainelListarTarefas() {
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Titulo", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};

        TarefaDAO dao = new TarefaDAO();
        List<Tarefa> tarefas = dao.listar();

        Object[][] dados = new Object[tarefas.size()][8];
        for (int i = 0; i < tarefas.size(); i++) {
            Tarefa t = tarefas.get(i);
            dados[i][0] = t.getId();
            dados[i][1] = t.getTitulo();
            
            if (t.getDeadline() != null) {
                dados[i][2] = t.getDeadline().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } else {
                dados[i][2] = "";
            }
            
            dados[i][3] = t.getDescricao();
            dados[i][4] = "Pendente"; // Status fixo
            dados[i][5] = t.getPrioridade();
            dados[i][6] = "Editar";
            dados[i][7] = "Apagar";
        }

        JTable tabela = new JTable(dados, colunas);

        tabela.removeColumn(tabela.getColumn("ID"));

        String[] statusOptions = {"Pendente", "Concluída", "Atrasada"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(statusComboBox));

        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());

        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar",TipoDAO.TAREFA));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar",TipoDAO.TAREFA));

        add(new JScrollPane(tabela), BorderLayout.CENTER);
    }
}
