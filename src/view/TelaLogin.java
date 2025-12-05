package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.command.AutenticarCommand;
import controller.command.Command;
import controller.command.NavegarCommand;
import view.creators.CadastroUsuarioCreator; 
import view.factory.IViewCreator;

public class TelaLogin extends JFrame {

    private static final long serialVersionUID = 1L;
   
    private JTextField txtNome;
    private JPasswordField txtSenha;
    private JButton btnEnviar;
    
    public TelaLogin() {
    	
        setTitle("Login");
        setSize(400, 350); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

       
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 11)); 
        btnCadastrar.setBounds(280, 10, 90, 25); 
        contentPane.add(btnCadastrar);

        
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
                IViewCreator cadastroCreator = new CadastroUsuarioCreator();
                Command navegar = new NavegarCommand(TelaLogin.this, cadastroCreator);
                try {
					navegar.execute();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        });
        
        JLabel lblTitulo = new JLabel("Entrar");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitulo.setBounds(0, 20, 384, 40); 
        contentPane.add(lblTitulo);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNome.setBounds(50, 80, 100, 20);
        contentPane.add(lblNome);

        txtNome = new JTextField();
        txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNome.setBounds(50, 105, 284, 30); 
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblSenha.setBounds(50, 150, 100, 20);
        contentPane.add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtSenha.setBounds(50, 175, 284, 30);
        contentPane.add(txtSenha);

        btnEnviar = new JButton("Enviar");
        btnEnviar.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnEnviar.setBounds(140, 240, 100, 35);
        contentPane.add(btnEnviar);

        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Responsabilidade da View é apenas pegar os dados
                String nome = txtNome.getText();
                String senha = new String(txtSenha.getPassword());
                
                Command autenticar = new AutenticarCommand(TelaLogin.this, nome, senha);
                
               try {
					autenticar.execute();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        });
    }

    public JTextField getTxtNome() {
        return txtNome;
    }

    public JPasswordField getTxtSenha() {
        return txtSenha;
    }
}