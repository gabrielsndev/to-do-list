package modelo;

import jakarta.persistence.*;
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
    private Integer diasCriticos = 3;

    @Transient
    private List<Subtarefa> subtarefas = new ArrayList<>();

    //Tem que ajeitar pra adicionar o id do usuário
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

	public Tarefa() {}

    
    public Tarefa(String titulo, String descricao, LocalDate deadline, int prioridade) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.deadline = deadline;
        this.prioridade = prioridade;
        this.critica = false;
    }


    public void adicionarSubtarefa(Subtarefa subtarefa) {
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

    public int getPrioridade() {
        return prioridade;
    }

    public Integer getDiasCriticos() { return diasCriticos; }

    public User getUser() {return user; }


    public void setId(Long id) { this.id = id; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setPrioridade(int prioridade) { this.prioridade = prioridade; }

    public boolean isCritica() { return critica; }

    public void setCritica(boolean critica) { this.critica = critica; }

    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "\nTítulo: " + titulo + "\nDescrição: " + descricao;
    }
}
