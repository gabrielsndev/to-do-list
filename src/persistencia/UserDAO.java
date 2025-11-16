package persistencia;

import interfaces.repositorioInterface.IUserRepositorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import modelo.User;

import java.util.List;

public class UserDAO implements IUserRepositorio {

    private final EntityManagerFactory emf;

    public UserDAO() {
        this.emf = Persistence.createEntityManagerFactory("todo-pu");
    }


    public void cadastrar(User user) throws Exception {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            throw new Exception("Erro ao salvar usuário no banco de dados");
        } finally {
            em.close();
        }
    }

    public boolean encontrar(String username, String password) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            // Query JPQL que busca por username E password
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :user AND u.password = :pass", User.class)
            .setParameter("user", username)
            .setParameter("pass", password);

            List<User> resultado = query.getResultList();

            return !resultado.isEmpty();
        } catch (Exception e) {
            throw new Exception("Erro ao buscar no Banco de dados");
        }
        finally {
            em.close();
        }
    }

    public List<User> buscarUsuario(String username, String password) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :user AND u.password = :pass", User.class)
                    .setParameter("user", username)
                    .setParameter("pass", password)
                    .getResultList();
        } catch(Exception e ) {
            throw new Exception("Erro na conexão com o Banco de Dados");
        }
        finally {
            em.close();
        }
    }

}
