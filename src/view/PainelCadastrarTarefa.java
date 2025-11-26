package view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import interfaces.AtualizarPaineis; 
import controller.command.SalvarTarefaCommand; 

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;

public class PainelCadastrarTarefa extends JPanel {
    
    private static final long serialVersionUID = 1L;

    private AtualizarPaineis atualizarPaineis;
   
    private JTextField textFieldTituloTarefa;
    private JFormattedTextField textFieldDataTarefa;
    private JTextField textFieldDescricaoTarefa;
    private JTextField textFieldPrioridadeTarefa;
    
    public PainelCadastrarTarefa(AtualizarPaineis atualizarPaineis) {
        this.atualizarPaineis = atualizarPaineis;
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
        } catch (ParseException e1) {
            e1.printStackTrace(); 
        }
        
        textFieldDataTarefa = new JFormattedTextField(mascaraData);
        textFieldDataTarefa.setBounds(188, 129, 197, 28);
        add(textFieldDataTarefa);

        JLabel lblDigiteADescrio = new JLabel("Digite a descrição da tarefa:");
        lblDigiteADescrio.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblDigiteADescrio.setBounds(51, 193, 285, 91);
        add(lblDigiteADescrio);
        
        textFieldDescricaoTarefa = new JTextField();
        textFieldDescricaoTarefa.setBounds(274, 226, 384, 28);
        add(textFieldDescricaoTarefa);

        JLabel lblDigiteAPrioridade = new JLabel("Digite a prioridade da tarefa:");
        lblDigiteAPrioridade.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblDigiteAPrioridade.setBounds(51, 295, 275, 91);
        add(lblDigiteAPrioridade);
        
        textFieldPrioridadeTarefa = new JTextField();
        textFieldPrioridadeTarefa.setBounds(274, 328, 384, 28);
        add(textFieldPrioridadeTarefa);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(274, 431, 160, 40);
        add(btnSalvar);
        
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String titulo = textFieldTituloTarefa.getText();
                String data = textFieldDataTarefa.getText();
                String prioridade = textFieldPrioridadeTarefa.getText();
                String descricao = textFieldDescricaoTarefa.getText();

                SalvarTarefaCommand command = null;
                try {
                    command = new SalvarTarefaCommand(PainelCadastrarTarefa.this, titulo,data,prioridade,descricao);
                } catch (Exception ex) {
                    throw new RuntimeException("Erro na execução");
                }

                command.execute();

                if (command.isSucesso()) {
                    limparCampos();

                    atualizarPaineis.atualizar();
                }
            }
        });
    }
    
    private void limparCampos() {
        textFieldTituloTarefa.setText("");
        textFieldDataTarefa.setText("");
        textFieldDataTarefa.setValue(null);
        textFieldDescricaoTarefa.setText("");
        textFieldPrioridadeTarefa.setText("");
    }
}