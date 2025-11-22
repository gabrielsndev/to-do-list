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
        this.collection = MongoDBConnection.getDatabase().getCollection("subtarefas_listas");
    }

    public void salvar(Subtarefa subtarefa, Long idTask) throws Exception{
        try {
            this.collection.updateOne(
                    Filters.eq("id_task", idTask),
                    Updates.push("itens", subtarefa));
        } catch(Exception e) {
            throw new Exception("Erro na conexão com o MongoDB");
        }
    }

    public void editar(Subtarefa subtarefa) {

    }

    // Remover subtarefa pelo ID
    public void remover(long idTask, String idSubtask) throws Exception {
        try {
            this.collection.updateOne(
                    Filters.eq("id_task", idTask),
                    Updates.pull("id", idSubtask)
            );
        } catch(Exception e) {
            throw new Exception("Erro ao remover Subtarefa");
        }
    }

//    // Tem que implementar recebendo uma lista de tarefas e iterando sobre elas pra evitar filtrar por usuário

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
