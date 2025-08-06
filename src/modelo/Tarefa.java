package modelo;

import jakarta.persistence.*;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate deadline;
    private int prioridade;
    
 // Uma tarefa pode ter várias subtarefas.
 // "mappedBy = 'tarefa'" significa que a chave estrangeira está na classe Subtarefa (campo 'tarefa').
 // "cascade = CascadeType.ALL" garante que salvar/excluir uma tarefa também salva/exclui suas subtarefas.
 // "orphanRemoval = true" remove subtarefas que forem removidas da lista.
    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subtarefa> subtarefas = new ArrayList<>(); // Conectando Subtarefa a Tarefa

	public Tarefa() {}

    
    public Tarefa(String titulo, String descricao, LocalDate deadline, int prioridade) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.deadline = deadline;
        this.prioridade = prioridade; 
    }

    // Retorna a lista de subtarefas associadas a esta tarefa
    public List<Subtarefa> getSubtarefas() {
        return subtarefas;
    }
 
    // Define ou substitui a lista de subtarefas
    public void setSubtarefas(List<Subtarefa> subtarefas) {
        this.subtarefas = subtarefas;
    }
    
 // Calcula o progresso total da tarefa com base na média do progresso das subtarefas
    public double getPercentualConcluido() {
        
    	// Se a lista estiver vazia ou for nula, retorna 0.0% de progresso
    	if (subtarefas == null || subtarefas.isEmpty()) {
            return 0.0;
        }
    	// Faz a média dos percentuais de cada subtarefa (usando programação funcional com stream)
        return subtarefas.stream()
                .mapToDouble(Subtarefa::getPercentualConcluido)// pega os valores de execução de cada subtarefa
                .average() // calcula a média
                .orElse(0.0);   // se der erro ou estiver vazia, retorna 0.0
    }
        
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getPrioridade() {
    	return prioridade;
    }
    
    
    public void setPrioridade(int prioridade) {
    	this.prioridade = prioridade;
    }
    @Override
    public String toString() {
        return "\nTítulo: " + titulo + "\nDescrição: " + descricao;
    }
}
