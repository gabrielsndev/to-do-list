package view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PainelCadastrarTarefa extends JPanel {
    private JTextField textFieldTituloTarefa;
    private JFormattedTextField textFieldDataTarefa;
    private JTextField textFieldDescricaoTarefa;
    private JTextField textFieldPrioridadeTarefa;
    
    public PainelCadastrarTarefa() {
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
        
        // Validação de campos obrigatórios
        if(titulo.isEmpty() || titulo.equalsIgnoreCase(" ") || 
           data.isEmpty() || data.equalsIgnoreCase(" ") ||
           prioridadeTexto.isEmpty() || prioridadeTexto.equalsIgnoreCase(" ")) {
            
            JOptionPane.showMessageDialog(btnSalvar,
                "Preencha todos os campos obrigatorios!\n Titulo, Data e Prioridade",
                "Avisos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(data, dataFormatada);
            
            if (date.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(btnSalvar, 
                    "Não é possível cadastrar uma tarefa com uma data no passado.",
                    "Data inválida", JOptionPane.WARNING_MESSAGE);
                
                return;
            }
        } catch(DateTimeParseException ex) {
            JOptionPane.showMessageDialog(btnSalvar, 
                "A data precisa esta no formato DD-MM-YYYY", 
                "Formato Invalido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int prioridade = Integer.parseInt(prioridadeTexto);
            
            if(prioridade < 0 || prioridade > 5) {
                JOptionPane.showMessageDialog(btnSalvar, 
                    "A Prioridade deve ser entre 0 e 5.", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            JOptionPane.showMessageDialog(btnSalvar,"Tarefa Salva.", "Concluido", JOptionPane.INFORMATION_MESSAGE);
            
            // Limpar campos após salvar
            limparCampos();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(btnSalvar,"A prioridade deve ser um número inteiro.","Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        textFieldTituloTarefa.setText("");
        textFieldDataTarefa.setText("");
        textFieldDescricaoTarefa.setText("");
        textFieldPrioridadeTarefa.setText("");
    }
}