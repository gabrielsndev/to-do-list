package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

import modelo.Tarefa;
import persistencia.TarefaDAO;
import model.ButtonRenderer;
import model.DataPrazoRender;
import model.TipoDAO;
import model.ButtonEditor;

public class PainelListarTarefas extends JPanel {

    private JTable tabela;
    private String[] colunas = {"ID", "Titulo", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};

    public PainelListarTarefas() {
        setLayout(new BorderLayout());

        tabela = new JTable(new DefaultTableModel(null, colunas));
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        atualizarTabela();
    }


    public void atualizarTabela() {
        TarefaDAO dao = new TarefaDAO();
        List<Tarefa> tarefas = dao.listar();

        Object[][] dados = new Object[tarefas.size()][8];
        for (int i = 0; i < tarefas.size(); i++) {
            Tarefa t = tarefas.get(i);
            dados[i][0] = t.getId();
            dados[i][1] = t.getTitulo();
            dados[i][2] = t.getDeadline().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            dados[i][3] = t.getDescricao();
            dados[i][4] = "Pendente"; // Status fixo
            dados[i][5] = t.getPrioridade();
            dados[i][6] = "Editar";
            dados[i][7] = "Apagar";
        }

        tabela.setModel(new DefaultTableModel(dados, colunas));

        configurarColunasEspeciais();
    }

    private void configurarColunasEspeciais() {
        tabela.removeColumn(tabela.getColumn("ID"));

        String[] statusOptions = {"Pendente", "Concluída", "Atrasada"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(statusComboBox));

        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());

        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar", TipoDAO.TAREFA));

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar", TipoDAO.TAREFA));
    }

}
