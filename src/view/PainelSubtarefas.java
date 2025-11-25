package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import modelo.Subtarefa;
import modelo.Tarefa;
import persistencia.SubtarefaDAO;
import persistencia.TarefaDAO;
import model.ButtonRenderer;
import model.DataPrazoRender;
import model.TipoDAO;
import model.ButtonEditor;
import servico.SubtarefaServico;

import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;

public class PainelSubtarefas extends JPanel {

    private JComboBox<Tarefa> comboBoxTarefaMae;
    private JTextField textTitulo;
    private JTextField textDescricao;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private SubtarefaServico subtarefaServico = new SubtarefaServico();

    private List<Tarefa> tarefas;
    
    public PainelSubtarefas(List<Tarefa> tarefas) throws Exception {
        setLayout(new BorderLayout());

        JTabbedPane abasInternas = new JTabbedPane();

        JPanel painelListagem = criarPainelListagem();
        JPanel painelCadastro = criarPainelCadastro();

        abasInternas.addTab("Listar", painelListagem);
        abasInternas.addTab("Cadastrar", painelCadastro);

        add(abasInternas, BorderLayout.CENTER);
        
        carregarSubtarefasNaTabela();

        carregarTarefasNoComboBox(tarefas);
        carregarSubtarefasNaTabela();
    }

    private JPanel criarPainelListagem() {
        JPanel painelListagem = new JPanel(new BorderLayout());

        String[] colunas = {"ID", "Título", "Data", "Descrição", "Status", "Prioridade", "Editar", "Apagar"};
        modeloTabela = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // só colunas Editar, Apagar e Status podem ser editadas
                return column == 4 || column == 6 || column == 7;
            }
        };

        tabela = new JTable(modeloTabela);

        // Ocultar coluna ID
        tabela.removeColumn(tabela.getColumn("ID"));

        String[] statusOptions = {"Pendente", "Concluída", "Atrasada"};
        tabela.getColumn("Status").setCellEditor(new DefaultCellEditor(new JComboBox<>(statusOptions)));
        tabela.getColumn("Data").setCellRenderer(new DataPrazoRender());

        tabela.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabela.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Editar",TipoDAO.TAREFA) {
           
            protected void onClick(JTable table, int row) {
                JOptionPane.showMessageDialog(table, "Funcionalidade editar ainda não implementada");
            }
        });

        tabela.getColumn("Apagar").setCellRenderer(new ButtonRenderer("Apagar"));
        tabela.getColumn("Apagar").setCellEditor(new ButtonEditor(new JCheckBox(), tabela, "Apagar",TipoDAO.TAREFA) {
           
            protected void onClick(JTable table, int row) {
                int confirm = JOptionPane.showConfirmDialog(table,
                    "Tem certeza que deseja excluir esta subtarefa?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    int modelRow = table.convertRowIndexToModel(row);
                    Object idObj = table.getModel().getValueAt(modelRow, 0);

                    try {
                        String id = idObj.toString();
                        subtarefaServico.remover(id);
                        ((DefaultTableModel) table.getModel()).removeRow(modelRow);
                        JOptionPane.showMessageDialog(table, "Subtarefa excluída com sucesso.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(table, "Erro ao excluir subtarefa.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabela);
        painelListagem.add(scrollPane, BorderLayout.CENTER);

        return painelListagem;
    }

    private JPanel criarPainelCadastro() {
        JPanel painelCadastro = new JPanel(null);

        JLabel labelTarefaMae = new JLabel("Selecione a Tarefa: ");
        labelTarefaMae.setBounds(50, 50, 150, 25);
        painelCadastro.add(labelTarefaMae);

        comboBoxTarefaMae = new JComboBox<>();
        comboBoxTarefaMae.setBounds(200, 50, 300, 25);
        painelCadastro.add(comboBoxTarefaMae);

        JLabel labelTitulo = new JLabel("Título da Subtarefa:");
        labelTitulo.setBounds(50, 90, 150, 25);
        painelCadastro.add(labelTitulo);

        textTitulo = new JTextField();
        textTitulo.setBounds(200, 90, 300, 25);
        painelCadastro.add(textTitulo);

        JLabel labelDescricao = new JLabel("Descrição:");
        labelDescricao.setBounds(50, 130, 150, 25);
        painelCadastro.add(labelDescricao);

        textDescricao = new JTextField();
        textDescricao.setBounds(200, 130, 300, 25);
        painelCadastro.add(textDescricao);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(250, 180, 100, 30);
        painelCadastro.add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            try {
                salvarSubtarefa();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return painelCadastro;
    }

    public void carregarTarefasNoComboBox(List<Tarefa> tarefas) {
        comboBoxTarefaMae.removeAllItems();
        this.tarefas = tarefas;
        for (Tarefa t : this.tarefas) {
            comboBoxTarefaMae.addItem(t);
        }
        comboBoxTarefaMae.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Tarefa) {
                    setText(((Tarefa) value).getTitulo());
                }
                return this;
            }
        });
    }

    private void carregarSubtarefasNaTabela() {
        modeloTabela.setRowCount(0);
        List<Subtarefa> subtarefas = subtarefaServico.listarSubtarefas(tarefas);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Subtarefa s : subtarefas) {
            modeloTabela.addRow(new Object[] {
                s.getId(),
                s.getTitulo(),
                s.getDescricao(),
                "Pendente", // status fixo, pode expandir futuramente
                "0", // prioridade fixa aqui, pode adaptar se subtarefa tiver campo
                "Editar",
                "Apagar"
            });
        }
    }
    
    public void atualizarPainel(List<Tarefa> tarefas) {
        carregarTarefasNoComboBox(tarefas);
        carregarSubtarefasNaTabela();
    }

    private void salvarSubtarefa() throws Exception {
        String titulo = textTitulo.getText().trim();
        String descricao = textDescricao.getText().trim();
        Tarefa tarefaSelecionada = (Tarefa) comboBoxTarefaMae.getSelectedItem();

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "É preciso adicionar um título para a subtarefa", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (tarefaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione a tarefa mãe", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Subtarefa subtarefa = new Subtarefa();
        subtarefa.setTitulo(titulo);
        subtarefa.setDescricao(descricao);

        subtarefaServico.salvarNovaSubtarefa(subtarefa, tarefaSelecionada.getId());

        JOptionPane.showMessageDialog(this, "Subtarefa salva com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        carregarSubtarefasNaTabela();
        textTitulo.setText("");
        textDescricao.setText("");
    }
}
