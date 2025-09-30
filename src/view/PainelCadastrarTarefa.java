package view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import modelo.Tarefa;
import persistencia.TarefaDAO;

public class PainelCadastrarTarefa extends JPanel {
    private TarefaPrincipal framePrincipal;
    private JTextField textFieldTituloTarefa;
    private JFormattedTextField textFieldDataTarefa;
    private JTextField textFieldDescricaoTarefa;
    private JTextField textFieldPrioridadeTarefa;
    
    public PainelCadastrarTarefa(TarefaPrincipal framePrincipal) {
    	this.framePrincipal = framePrincipal;
        setLayout(null);
        
        JLabel lblDigiteOTtulo = new JLabel("Digite o titulo:");
        lblDigiteOTtulo.setBounds(51, 36, 150, 54);
        lblDigiteOTtulo.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblDigiteOTtulo);
        
        textFieldTituloTarefa = new JTextField();
        textFieldTituloTarefa.setBounds(188, 51, 384, 28);
        add(textFieldTituloTarefa);

        JLabel lblDigiteAData = new JLabel("Digite a data:");
        lblDigiteAData.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblDigiteAData.setBounds(51, 101, 150, 81);
        add(lblDigiteAData);

        MaskFormatter mascaraData = null;
        try {
            mascaraData = new MaskFormatter("##-##-####");
            mascaraData.setPlaceholderCharacter('_');
        } catch (java.text.ParseException e1) {
            e1.printStackTrace(); 
        }
        
        textFieldDataTarefa = new JFormattedTextField(mascaraData);
        textFieldDataTarefa.setBounds(188, 129, 197, 28);
        add(textFieldDataTarefa);

        // Label e TextField para Descrição
        JLabel lblDigiteADescrio = new JLabel("Digite a descrição da tarefa:");
        lblDigiteADescrio.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblDigiteADescrio.setBounds(51, 193, 285, 91);
        add(lblDigiteADescrio);
        
        textFieldDescricaoTarefa = new JTextField();
        textFieldDescricaoTarefa.setBounds(274, 226, 384, 28);
        add(textFieldDescricaoTarefa);

        // Label e TextField para Prioridade
        JLabel lblDigiteAPrioridade = new JLabel("Digite a prioridade da tarefa:");
        lblDigiteAPrioridade.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblDigiteAPrioridade.setBounds(51, 295, 275, 91);
        add(lblDigiteAPrioridade);
        
        textFieldPrioridadeTarefa = new JTextField();
        textFieldPrioridadeTarefa.setBounds(274, 328, 384, 28);
        add(textFieldPrioridadeTarefa);

        // Botão Salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(274, 431, 160, 40);
        add(btnSalvar);
        
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarTarefa(btnSalvar);
            }
        });
    }
    
    private void salvarTarefa(JButton btnSalvar) {
        String titulo = textFieldTituloTarefa.getText().trim();
        String data = textFieldDataTarefa.getText().trim();
        String prioridadeTexto = textFieldPrioridadeTarefa.getText().trim();
        String descricao = textFieldDescricaoTarefa.getText().trim();

        if (titulo.isEmpty() || data.isEmpty() || prioridadeTexto.isEmpty()) {
            JOptionPane.showMessageDialog(btnSalvar,
                "Preencha todos os campos obrigatórios!\nTítulo, Data e Prioridade",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate deadline;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            deadline = LocalDate.parse(data, formatter);

            if (deadline.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(btnSalvar,
                    "A data não pode estar no passado.",
                    "Data inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(btnSalvar,
                "A data precisa estar no formato DD-MM-YYYY",
                "Formato inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int prioridade;
        try {
            prioridade = Integer.parseInt(prioridadeTexto);
            if (prioridade < 0 || prioridade > 5) {
                JOptionPane.showMessageDialog(btnSalvar,
                    "A prioridade deve estar entre 0 e 5.",
                    "Valor inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(btnSalvar,
                "A prioridade deve ser um número inteiro.",
                "Erro de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Agora criamos e salvamos a tarefa
        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setTitulo(titulo);
        novaTarefa.setDescricao(descricao);
        novaTarefa.setDeadline(deadline);
        novaTarefa.setPrioridade(prioridade);

        try {
            TarefaDAO dao = new TarefaDAO();
            dao.salvar(novaTarefa);

            JOptionPane.showMessageDialog(btnSalvar,
                "Tarefa salva com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
            this.framePrincipal.atualizarTelasListagem();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(btnSalvar,
                "Erro ao salvar a tarefa no banco de dados.",
                "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    
    private void limparCampos() {
        textFieldTituloTarefa.setText("");
        textFieldDataTarefa.setText("");
        textFieldDescricaoTarefa.setText("");
        textFieldPrioridadeTarefa.setText("");
    }
}