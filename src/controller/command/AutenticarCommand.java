package controller.command;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

import modelo.User;
import servico.SessionManager;
import servico.UserService;
import view.creators.HomeCreator; // Precisamos do Creator da Home
import view.factory.IViewCreator;

public class AutenticarCommand implements Command {

    private JFrame loginView; 
    private String username;
    private String password;
    private UserService userService;

    public AutenticarCommand(JFrame loginView, String username, String password) {
        this.loginView = loginView;
        this.username = username;
        this.password = password;
       
        this.userService = new UserService();
    }


    public void execute() { 
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "Preencha usuário e senha!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            SessionManager.getInstance().logarUsuario(userService.retornarUsuario(username, password));
            JOptionPane.showMessageDialog(loginView, "Login realizado com sucesso!", "Bem-vindo", JOptionPane.INFORMATION_MESSAGE);

            IViewCreator homeCreator = new HomeCreator();
            Command irParaHome = new NavegarCommand(loginView, homeCreator);
            irParaHome.execute();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loginView, "Erro: " + e.getMessage(), "Falha no Login", JOptionPane.ERROR_MESSAGE);
        }
    }
}