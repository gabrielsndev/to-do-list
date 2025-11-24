package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 

public class CadastroUsuario extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnCadastrar;
    private JButton btnVoltar; 

    public CadastroUsuario() {
        setTitle("Novo Cadastro");
        
        setSize(400, 450); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // --- 1. Título "Cadastro" ---
        JLabel lblTitulo = new JLabel("Cadastro");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitulo.setBounds(0, 20, 384, 40);
        contentPane.add(lblTitulo);

        // --- 2. Campo Nome ---
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNome.setBounds(50, 70, 100, 20);
        contentPane.add(lblNome);

        txtNome = new JTextField();
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNome.setBounds(50, 95, 284, 30);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        // --- 3. Campo Email ---
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblEmail.setBounds(50, 140, 100, 20);
        contentPane.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtEmail.setBounds(50, 165, 284, 30);
        contentPane.add(txtEmail);

        // --- 4. Campo Senha ---
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblSenha.setBounds(50, 210, 100, 20);
        contentPane.add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtSenha.setBounds(50, 235, 284, 30);
        contentPane.add(txtSenha);

        // --- 5. Botão Cadastrar ---
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnCadastrar.setBounds(130, 300, 120, 35);
        contentPane.add(btnCadastrar);

        // --- 6. Botão Voltar (Opcional, mas útil para voltar ao Login) ---
        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(10, 10, 80, 20); // Canto superior esquerdo
        contentPane.add(btnVoltar);

        // --- Ações ---
        
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aqui entrará o seu CadastrarUsuarioCommand
                String nome = txtNome.getText();
                String email = txtEmail.getText();
                String senha = new String(txtSenha.getPassword());

                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(CadastroUsuario.this, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Lógica temporária
                    JOptionPane.showMessageDialog(CadastroUsuario.this, "Cadastro enviado (simulação)!");
                }
            }
        });

        // Configurar o botão voltar para usar o NavegarCommand depois
        btnVoltar.addActionListener(e -> {
            // Futuro: NavegarCommand(this, new TelaLoginCreator()).execute();
            dispose();
            // new TelaLogin().setVisible(true); // Exemplo sem Factory
        });
    }

    // Getters para o Command ler os dados
    public JTextField getTxtNome() { return txtNome; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JPasswordField getTxtSenha() { return txtSenha; }
}