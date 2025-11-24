package servico;

import org.mindrot.jbcrypt.BCrypt;

public class Auth {

    public static String hashearSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    public static boolean compararSenhas(String senha, String hash) {
        return BCrypt.checkpw(senha, hash);
    }

}
