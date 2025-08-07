package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class BaseTabelas extends JFrame {
    protected JPanel contentPane;
    protected JTable table;
    protected JScrollPane scrollPane;
    protected JButton btnVoltar;
    protected JLabel lblTitulo;

    // Construtor que recebe o título da janela
    public BaseTabelas(String tituloJanela) {
        setTitle(tituloJanela); // Define o título da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ao fechar, encerra a janela
        setSize(600, 350); // Tamanho padrão da janela
        setLocationRelativeTo(null); // Centraliza na tela
        

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10)); // Usa layout automático
        setContentPane(contentPane);

        // Painel do topo (título e botão voltar)
        JPanel topPanel = new JPanel(new BorderLayout());

        lblTitulo = new JLabel(tituloJanela);
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 22));
        topPanel.add(lblTitulo, BorderLayout.WEST); // Título fica à esquerda

        btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Times New Roman", Font.PLAIN, 11));
        btnVoltar.addActionListener(e -> voltar()); // Chama método abstrato
        topPanel.add(btnVoltar, BorderLayout.EAST); // Botão à direita

        contentPane.add(topPanel, BorderLayout.NORTH); // Topo da tela

        // Tabela
        table = new JTable();
        scrollPane = new JScrollPane(table);
        contentPane.add(scrollPane, BorderLayout.CENTER); // Área central da tela

        configurarTabela(); // Método abstrato que cada tela filha implementa
    }

    protected abstract void configurarTabela();

    protected abstract void voltar();
}
