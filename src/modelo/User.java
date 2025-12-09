package modelo;

import jakarta.persistence.*;
import servico.Auth;

import java.util.List;

@Entity @Table(name = "User")
public class User {

    public User(String username, String password, String email) {
        this.username = username;
        this.password = Auth.hashearSenha(password);
        this.email = email;
    }

    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Column(nullable = false, length = 60)
    private String username;

    @Column(nullable = false, length = 150)
    private String password;

    @Column(nullable = false, length = 150)
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column(name = "Tarefas")
    private List<Tarefa> tarefasUsuario;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Column(name = "Eventos")
    private List<Evento> eventosUsuario;

    public boolean equals(User user) {
        return this.username.equals(user.username) && this.password.equals(user.password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Tarefa> getTarefasUsuario() {
        return tarefasUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setTarefasUsuario(List<Tarefa> tarefasUsuario) {
        this.tarefasUsuario = tarefasUsuario;
    }

    public List<Evento> getEventosUsuario() {
        return eventosUsuario;
    }

    public void setEventosUsuario(List<Evento> eventosUsuario) {
        this.eventosUsuario = eventosUsuario;
    }
    
    public String toString() {
    	return this.username;
    }
}
