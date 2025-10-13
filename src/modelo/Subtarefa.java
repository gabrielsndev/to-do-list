package modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subtarefa")
public class Subtarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String titulo;  
    
    @Column(length = 250)
    private String descricao;
    
    private Double percentualConcluido; 
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "tarefa_id", nullable = false)
    private Tarefa tarefa;

    public Subtarefa() {}

    public Subtarefa(String titulo, String descricao, double percentualConcluido, LocalDate deadline, Tarefa tarefa) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.percentualConcluido = percentualConcluido;
        this.deadline = deadline;
        this.tarefa = tarefa;
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

    public double getPercentualConcluido() {
        return percentualConcluido;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public Tarefa getTarefa() {
        return tarefa;
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

    public void setPercentualConcluido(double percentualConcluido) {
        this.percentualConcluido = percentualConcluido;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    @Override
    public String toString() {
        return "Subtarefa: " + titulo + " (" + percentualConcluido + "%)";
    }
}
