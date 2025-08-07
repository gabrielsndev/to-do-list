package view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TarefaPrincipal extends JFrame {
    
    public TarefaPrincipal() {
        setTitle("Sistema de Tarefas com Abas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        
        // Botão Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(690, 5, 80, 20);
        getContentPane().add(btnVoltar);
        
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                home home = new home();
                home.setVisible(true);
                home.setLocationRelativeTo(null);
            }
        });
        
        // Criando o painel de abas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, 784, 561);

        // Adicionando as abas com as telas separadas
        tabbedPane.addTab("Listar Tarefas", new PainelListarTarefas());
        tabbedPane.addTab("Cadastrar Tarefas", new PainelCadastrarTarefa());
        tabbedPane.addTab("Subtarefas", new PainelSubtarefas());
        tabbedPane.addTab("Listar Tarefa Por Dia", new PainelListarPorDia());
        tabbedPane.addTab("Listar Tarefas Criticas", new PainelListarCriticas());

        getContentPane().add(tabbedPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TarefaPrincipal::new);
    }
}