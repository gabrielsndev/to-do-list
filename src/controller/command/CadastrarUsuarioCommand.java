package controller.command;

import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.User;
import servico.UserService;
import view.creators.TelaLoginCreator;
import view.factory.IViewCreator;

public class CadastrarUsuarioCommand implements Command {

    private JFrame Cadastroview;
    private String nome;
    private String email;
    private String senha;
    private UserService userService;

    public CadastrarUsuarioCommand(JFrame view, String nome, String email, String senha) {
        this.Cadastroview = view;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.userService = new UserService();
    }

    public void execute() {
        
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(Cadastroview, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
           
            User novoUsuario = new User();
            novoUsuario.setUsername(nome);
            novoUsuario.setPassword(senha);
            novoUsuario.setEmail(email);
            
            userService.cadastrarUsuario(novoUsuario);

            JOptionPane.showMessageDialog(Cadastroview, "Usuário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            IViewCreator loginCreator = new TelaLoginCreator();
            Command irParaLogin = new NavegarCommand(Cadastroview, loginCreator);
            irParaLogin.execute();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(Cadastroview, "Erro ao cadastrar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}