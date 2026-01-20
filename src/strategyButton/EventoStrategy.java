package strategyButton;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modelo.Evento;
import servico.EventoServico;
import persistencia.EventoDAO;
import interfaces.repositorioInterface.EventoRepositorio;

public class EventoStrategy implements AcaoTabelaStrategy {
    
    private EventoServico servico;

    public EventoStrategy(EventoServico servico) {
        this.servico = servico;
    }

    public EventoStrategy() {
    	EventoRepositorio repositorio = new EventoDAO();
        this.servico = new EventoServico(repositorio);
    }

    @Override
    public void editar(JTable table, int row) {
        try {
            
            Object idObj = table.getModel().getValueAt(row, 0);
            long id = Long.parseLong(idObj.toString());

            String tituloAtual = table.getModel().getValueAt(row, 1).toString();
            String descricaoAtual = table.getModel().getValueAt(row, 2).toString(); 
            String dataAtual = table.getModel().getValueAt(row, 3).toString();      

            JTextField campoTitulo = new JTextField(tituloAtual);
            JTextField campoDescricao = new JTextField(descricaoAtual);
            JTextField campoData = new JTextField(dataAtual);

            JPanel painel = new JPanel(new GridLayout(0, 1, 5, 5));
            painel.add(new JLabel("Título:"));
            painel.add(campoTitulo);
            painel.add(new JLabel("Descrição:"));
            painel.add(campoDescricao);
            painel.add(new JLabel("Data (DD-MM-YYYY):"));
            painel.add(campoData);

            int result = JOptionPane.showConfirmDialog(null, painel, 
                "Editar Evento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                Evento evento = new Evento();
                evento.setId(id);
                evento.setTitulo(campoTitulo.getText());
                evento.setDescricao(campoDescricao.getText());
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                evento.setData(LocalDate.parse(campoData.getText(), formatter));

                servico.atualizarEvento(evento);

                table.getModel().setValueAt(campoTitulo.getText(), row, 1);
                table.getModel().setValueAt(campoDescricao.getText(), row, 2);
                table.getModel().setValueAt(campoData.getText(), row, 3);

                JOptionPane.showMessageDialog(null, "Evento atualizado com sucesso!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao editar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void remover(JTable table, int row) {
         try {
            Object idObj = table.getModel().getValueAt(row, 0);
            long id = Long.parseLong(idObj.toString());
            
            servico.cancelarEvento(id); 
            
            if (table.getModel() instanceof javax.swing.table.DefaultTableModel) {
                ((javax.swing.table.DefaultTableModel) table.getModel()).removeRow(row);
            }
            JOptionPane.showMessageDialog(null, "Evento removido.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao remover: " + e.getMessage());
        }
    }
}