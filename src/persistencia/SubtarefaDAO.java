package persistencia;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import modelo.Subtarefa;
import modelo.Tarefa;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class SubtarefaDAO {

    private final MongoCollection<Document> collection;

    public SubtarefaDAO() throws Exception {
        // Pega o banco de dados da nossa classe de conexão e escolhe a coleção
        this.collection = MongoDBConnection.getDatabase().getCollection("subtarefas_listas");
    }


    
    public void limparSubtarefasOrfas() {

    }

    // Salvar nova subtarefa 
    public void salvar(Subtarefa subtarefa) {

    }

    public void editar(Subtarefa subtarefa) {

    }

    // Remover subtarefa pelo ID
    public void remover(long id) throws Exception {

    }

    // Buscar uma subtarefa pelo ID

//    public Subtarefa buscar(long id) {
//
//    }
//
//    public List<Subtarefa> listar() {
//
//    }
//
//    // Listar subtarefas de uma tarefa específica
//    public List<Subtarefa> listarPorTarefa(Tarefa tarefa) {
//
//    }

    public void iniciarListaSubtarefas(Long idTask) throws Exception {
        try {
            Document novoDocumento = new Document()
                    .append("task_id", idTask)
                    .append("itens", new ArrayList<Subtarefa>());

            this.collection.insertOne(novoDocumento);
        } catch(Exception e) {
            throw new Exception("Erro ao iniciar lista de tarefas no MongoDB");
        }
    }

    public void limparOrfas(Long id) throws Exception {
        try {
            this.collection.deleteOne(Filters.eq("task_id", id));
        } catch (Exception e) {
            throw new Exception("Erro ao apagar tarefas orfãs da tarefa: " + id);
        }
    }


}
