package view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import controller.command.Command;
import controller.command.SalvarEventoCommand;

public class PainelCadastroEvento extends JPanel {

    private JTextField textFieldTitulo;
    private JTextField textFieldDescricao;
    private JTextField textFieldDataInicial;
    private JFormattedTextField textFieldDataFinal;

    public PainelCadastroEvento() {
        setLayout(null);
        
        Date dataHoje = new Date();
        SimpleDateFormat formatacaoData = new SimpleDateFormat("dd-MM-yyyy");
        String dataFormatada = formatacaoData.format(dataHoje);
        
        JLabel labelTitulo = new JLabel("Digite o Titulo do Evento:");
        labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelTitulo.setBounds(22, 73, 176, 31);
        add(labelTitulo);
        
        textFieldTitulo = new JTextField();
        textFieldTitulo.setBounds(208, 80, 176, 20);
        add(textFieldTitulo);
        
        JLabel labelDescricao = new JLabel("Digite a Descrição do Evento:");
        labelDescricao.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelDescricao.setBounds(22, 134, 200, 31);
        add(labelDescricao);
        
        textFieldDescricao = new JTextField();
        textFieldDescricao.setBounds(232, 141, 233, 20);
        add(textFieldDescricao);
        
        JLabel labelDataInicial = new JLabel("Data Do Cadastro do Evento:");
        labelDataInicial.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelDataInicial.setBounds(22, 201, 219, 31);
        add(labelDataInicial);
        
        textFieldDataInicial = new JTextField();
        textFieldDataInicial.setText(dataFormatada);
        textFieldDataInicial.setEditable(false);
        textFieldDataInicial.setBounds(232, 208, 100, 20);
        add(textFieldDataInicial);
        
        JLabel labelDataConclusao = new JLabel("Digite a data que acontecerá o Evento:");
        labelDataConclusao.setFont(new Font("Tahoma", Font.BOLD, 14));
        labelDataConclusao.setBounds(22, 256, 281, 31);
        add(labelDataConclusao);
        
        try {
            MaskFormatter mascaraData = new MaskFormatter("##-##-####");
            mascaraData.setPlaceholder("_");
            textFieldDataFinal = new JFormattedTextField(mascaraData);
            textFieldDataFinal.setBounds(300, 263, 100, 20);
            add(textFieldDataFinal);
        } catch(java.text.ParseException e) {
            e.printStackTrace();
        }

        JButton btnSalvar = new JButton("Cadastrar");
        btnSalvar.setBounds(263, 331, 105, 31);
        add(btnSalvar);
        
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SalvarEventoCommand command = new SalvarEventoCommand(
                    PainelCadastroEvento.this, 
                    textFieldTitulo.getText(), 
                    textFieldDescricao.getText(), 
                    textFieldDataFinal.getText()
                );
                
                command.execute();
                
                if (command.isSucesso()) {
                    limparCampos();
                }
            }
        });
    }
    
    private void limparCampos() {
        textFieldTitulo.setText("");
        textFieldDescricao.setText("");
        textFieldDataFinal.setValue(null);
        textFieldDataFinal.setText("");
    }
}