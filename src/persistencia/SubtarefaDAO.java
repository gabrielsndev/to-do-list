package persistencia;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import modelo.Subtarefa;
import com.mongodb.client.MongoCollection;
import modelo.Tarefa;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SubtarefaDAO {

    private final MongoCollection<Document> collection;

    public SubtarefaDAO() throws Exception {
        this.collection = MongoDBConnection.getDatabase().getCollection("subtarefas_listas");
    }

    public void salvar(Subtarefa subtarefa, Long idTask) throws Exception{

        String UUId = UUID.randomUUID().toString();
        subtarefa.setId(UUId);
        Document docSalvar = convertToDoc(subtarefa);

        try {
            this.collection.updateOne(
                    Filters.eq("task_id", idTask),
                    Updates.push("itens", docSalvar));
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

        if (tasks == null || tasks.isEmpty()) {
            return resultado;
        }

        List<Long> ids = tasks.stream()
                .map(Tarefa::getId)
                .collect(Collectors.toList());

        var documentos = this.collection.find(Filters.in("task_id", ids));

        for (Document doc: documentos) {
            List<Document> itens = doc.getList("itens", Document.class);

            if(itens != null) {
                for (Document item: itens) {
                    Subtarefa s = convertToSubtask(item);
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


    public Document convertToDoc(Subtarefa sub) {
        Document doc = new Document()
                .append("id", sub.getId())
                .append("titulo", sub.getTitulo())
                .append("descricao", sub.getDescricao());

        return doc;
    }

    public Subtarefa convertToSubtask(Document doc) {
        Subtarefa sub = new Subtarefa();

        sub.setId(doc.getString("id"));
        sub.setTitulo(doc.getString("titulo"));
        sub.setDescricao(doc.getString("descricao"));

        return sub;
    }


}
