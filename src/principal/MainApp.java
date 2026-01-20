package principal;

import controller.command.Command;
import controller.command.NavegarCommand;
import modelo.User;
import persistencia.RedisUser;
import servico.SessionManager;
import view.TelaLogin;
import view.creators.TarefaPrincipalCreator;
import view.factory.IViewCreator;
import view.creators.CadastroUsuarioCreator;
import view.creators.HomeCreator;
import view.creators.TelaLoginCreator;

import javax.swing.JFrame;
import java.awt.EventQueue;

public class MainApp {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RedisUser redisUser = new RedisUser();
                    User u = redisUser.getUsario();

                    IViewCreator telaInicial;
                    if (u != null) {
                        try {
                            telaInicial = new HomeCreator();
                            SessionManager.getInstance().logarUsuario(u);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        telaInicial = new TelaLoginCreator();
                    }
                    JFrame frameInicial = telaInicial.createView();
                    frameInicial.setLocationRelativeTo(null);
                    frameInicial.setVisible(true);

                } catch (RuntimeException e) {
                    throw new RuntimeException(e);


            } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
