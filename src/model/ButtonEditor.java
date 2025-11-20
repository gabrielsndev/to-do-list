package model;

import persistencia.EventoDAO;
import persistencia.TarefaDAO;
import view.TarefaPrincipal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EventObject;

import modelo.Tarefa;

public class ButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private final String label;
    private int row;
    private final JTable table;
    private final TipoDAO tipo;
    // private TarefaPrincipal tarefaPrincipal; // COMENTADO: Não precisamos dessa referência aqui agora

    public ButtonEditor(JCheckBox checkBox, JTable table, String label, TipoDAO tipo) {
        super(checkBox);
        this.button = new JButton(label);
        this.label = label;
        this.table = table;
        this.tipo = tipo;

        button.addActionListener(e -> {
            fireEditingStopped();

            if (label.equals("Editar")) {
                try {
                    Object idObj = table.getModel().getValueAt(row, 0);
                    long id = Long.parseLong(idObj.toString());

                    String tituloAtual = table.getModel().getValueAt(row, 1).toString();
                    String descricaoAtual = table.getModel().getValueAt(row, 3).toString();
                    String prioridadeAtual = table.getModel().getValueAt(row, 5).toString();
                    String data = table.getModel().getValueAt(row, 2).toString();

                    // Campos para edição
                    JTextField campoTitulo = new JTextField(tituloAtual);
                    JTextField campoDescricao = new JTextField(descricaoAtual);
                    JTextField campoPrioridade = new JTextField(prioridadeAtual);
                    JTextField campoData = new JTextField(data);

                    JPanel painel = new JPanel(new GridLayout(0, 1, 5, 5));
                    painel.add(new JLabel("Título:"));
                    painel.add(campoTitulo);
                    painel.add(new JLabel("Descrição:"));
                    painel.add(campoDescricao);
                    painel.add(new JLabel("Prioridade:"));
                    painel.add(campoPrioridade);
                    painel.add(new JLabel("Data:"));
                    painel.add(campoData);

                    int result = JOptionPane.showConfirmDialog(button, painel, 
                        "Editar Tarefa", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        Tarefa tarefa = new Tarefa();
                        tarefa.setId(id);
                        tarefa.setTitulo(campoTitulo.getText());
                        tarefa.setDescricao(campoDescricao.getText());
                        tarefa.setPrioridade(Integer.parseInt(campoPrioridade.getText()));
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        tarefa.setDeadline(LocalDate.parse(campoData.getText(), formatter));


                        if (tipo == TipoDAO.TAREFA) {
                            new TarefaDAO().editarTarefa(tarefa);
                        }

                        // Atualiza a tabela
                        table.getModel().setValueAt(campoTitulo.getText(), row, 1);
                        table.getModel().setValueAt(campoDescricao.getText(), row, 3);
                        table.getModel().setValueAt(campoPrioridade.getText(), row, 5);
                        table.getModel().setValueAt(campoData.getText(), row, 2);

                        JOptionPane.showMessageDialog(button, "Tarefa atualizada com sucesso!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(button, "Erro ao editar a tarefa.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } else if (label.equals("Apagar")) {
                try {
                    int confirm = JOptionPane.showConfirmDialog(button,
                        "Deseja realmente apagar este item?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        Object idObj = table.getModel().getValueAt(row, 0);
                        long id = Long.parseLong(idObj.toString());

                        if (tipo == TipoDAO.TAREFA) {
                            // --- PALIATIVO START ---
                            // Removemos a criação da TarefaPrincipal. 
                            // Isso evita o erro de compilação e o erro de lógica.
                            // tarefaPrincipal = new TarefaPrincipal(); 
                            new TarefaDAO().remover(id);
                            // tarefaPrincipal.atualizarTelasListagem();
                            // --- PALIATIVO END ---
                            
                        } else if (tipo == TipoDAO.EVENTO) {
                            new EventoDAO().remover(id);
                        }

                        // Remove da tabela visualmente
                        if (table.getModel() instanceof DefaultTableModel) {
                            ((DefaultTableModel) table.getModel()).removeRow(row);
                        }

                        JOptionPane.showMessageDialog(button, "Removido com sucesso.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(button, "Erro ao remover.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.row = row;
        button.setText(label);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject e) {
        return true;
    }
}