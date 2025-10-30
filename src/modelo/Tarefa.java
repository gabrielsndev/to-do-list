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
@Table(name = "Tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String titulo;
    
    @Column( length = 250)
    private String descricao;
    
    private LocalDate deadline;
  
    @Column(nullable = false)
    private Integer prioridade;
    
    private Boolean critica;

    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subtarefa> subtarefas = new ArrayList<>();

	public Tarefa() {}

    
    public Tarefa(String titulo, String descricao, LocalDate deadline, int prioridade, boolean critica) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.deadline = deadline;
        this.prioridade = prioridade;
        this.critica = critica;
    }



    public void adicionarSubtarefa(Subtarefa subtarefa) {
        subtarefa.setTarefa(this); // estabelece a referência para a tarefa pai
        this.subtarefas.add(subtarefa);
    }

    // Retorna a lista de subtarefas associadas a esta tarefa
    public List<Subtarefa> getSubtarefas() {

        return subtarefas;
    }
 
    // Define ou substitui a lista de subtarefas
    public void setSubtarefas(List<Subtarefa> subtarefas) {

        this.subtarefas = subtarefas;
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

    public boolean isCritica() {
        return critica;
    }

    public void setCritica(boolean critica) {
        this.critica = critica;
    }

    @Override
    public String toString() {
        return "\nTítulo: " + titulo + "\nDescrição: " + descricao;
    }
}
