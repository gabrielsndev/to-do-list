package strategyButton;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modelo.Tarefa;
import servico.TarefaServico;

public class TarefaStrategy implements AcaoTabelaStrategy {
	
    public void editar(JTable table, int row) {
        try {
           
            Object idObj = table.getModel().getValueAt(row, 0);
            long id = Long.parseLong(idObj.toString());

            String tituloAtual = table.getModel().getValueAt(row, 1).toString();
            String dataAtual = table.getModel().getValueAt(row, 2).toString();
            String descricaoAtual = table.getModel().getValueAt(row, 3).toString();
            String prioridadeAtual = table.getModel().getValueAt(row, 5).toString();

            JTextField campoTitulo = new JTextField(tituloAtual);
            JTextField campoData = new JTextField(dataAtual);
            JTextField campoDescricao = new JTextField(descricaoAtual);
            JTextField campoPrioridade = new JTextField(prioridadeAtual);

            JPanel painel = new JPanel(new GridLayout(0, 1, 5, 5));
            painel.add(new JLabel("Título:"));
            painel.add(campoTitulo);
            painel.add(new JLabel("Data (DD-MM-YYYY):"));
            painel.add(campoData);
            painel.add(new JLabel("Descrição:"));
            painel.add(campoDescricao);
            painel.add(new JLabel("Prioridade:"));
            painel.add(campoPrioridade);

            int result = JOptionPane.showConfirmDialog(null, painel, 
                "Editar Tarefa", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(id);
                tarefa.setTitulo(campoTitulo.getText());
                tarefa.setDescricao(campoDescricao.getText());
                tarefa.setPrioridade(Integer.parseInt(campoPrioridade.getText()));
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                tarefa.setDeadline(LocalDate.parse(campoData.getText(), formatter));
                
               
                new TarefaServico().atualizarTarefa(tarefa); 
                

                table.getModel().setValueAt(campoTitulo.getText(), row, 1);
                table.getModel().setValueAt(campoData.getText(), row, 2);
                table.getModel().setValueAt(campoDescricao.getText(), row, 3);
                table.getModel().setValueAt(campoPrioridade.getText(), row, 5);

                JOptionPane.showMessageDialog(null, "Tarefa atualizada com sucesso!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao editar tarefa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

   
    public void remover(JTable table, int row) {
         try {
            Object idObj = table.getModel().getValueAt(row, 0);
            long id = Long.parseLong(idObj.toString());
            
            new TarefaServico().excluir(id); 
            
            if (table.getModel() instanceof javax.swing.table.DefaultTableModel) {
                ((javax.swing.table.DefaultTableModel) table.getModel()).removeRow(row);
            }
            JOptionPane.showMessageDialog(null, "Tarefa removida.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  

}