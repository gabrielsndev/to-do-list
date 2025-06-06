package modelo;
import java.time.LocalDate;

public class Tarefa {

    private long id;
    private String titulo;
    private String descricao;
    private LocalDate deadline;

    public Tarefa(String titulo, String descricao, LocalDate deadline) {

        this.id = System.currentTimeMillis();
        this.titulo = titulo;
        this.descricao = descricao;
        this.deadline = deadline;
    }
    
    public long getId() {
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

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String toString() {
        return "Título: " + titulo + " - Prazo: " + deadline;
    }
}