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
            e.printStackTrace();
            throw new Exception("Erro ao salvar usuário no banco de dados");
        } finally {
            em.close();
        }
    }
    
    public User buscarPorUsername(String username) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :user", User.class)
                    .setParameter("user", username);
            
            List<User> users = query.getResultList();
            
            if (users.isEmpty()) {
                return null; 
            }
            
            return users.get(0); 
            
        } catch(Exception e ) {
            e.printStackTrace();
            throw new Exception("Erro na conexão com o Banco de Dados");
        } finally {
            em.close();
        }
    }
    
    
}