package persistencia;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import modelo.Subtarefa;
import com.mongodb.client.MongoCollection;
import modelo.Tarefa;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void remover(String idSubtask) throws Exception {
        try {
            Bson filtro = Filters.eq("itens.id", idSubtask);
            Bson update = Updates.pull("itens", new Document("id", idSubtask));
            this.collection.updateOne(filtro, update);

        } catch(Exception e) {
            throw new Exception("Erro ao remover Subtarefa pelo ID", e);
        }
    }


    public List<Subtarefa> listarSubtarefas(List<Tarefa> tasks) {
        List<Subtarefa> resultado = new ArrayList<>();

        List<Long> ids = tasks.stream()
                .map(Tarefa::getId)
                .collect(Collectors.toList());

        var documentos = this.collection.find(Filters.in("task_id", ids));

        for (Document doc: documentos) {
            List<Document> itens = doc.getList("itens", Document.class);

            if(itens != null) {
                for (Document item: itens) {
                    Subtarefa s = new Subtarefa();
                    s.setId(item.getString("id"));
                    s.setTitulo(item.getString("titulo"));
                    s.setDescricao(item.getString("descricao"));
                    s.setConcluida(item.getBoolean("concluida", false));

                    resultado.add(s);
                }
            }
        }

        return resultado;
    }


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
