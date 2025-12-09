package controller.command;

import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;

import modelo.Evento;
import servico.EventoServico;
import persistencia.EventoDAO; // Precisamos do DAO para injetar no Serviço

public class SalvarEventoCommand implements Command {

    private Component parentView;
    private String titulo;
    private String descricao;
    private String dataTexto;
    
    private EventoServico eventoServico;
    private boolean sucesso = false;

    public SalvarEventoCommand(Component parentView, String titulo, String descricao, String dataTexto) {
        this.parentView = parentView;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataTexto = dataTexto;
        
        this.eventoServico = new EventoServico(new EventoDAO());
    }

    @Override
    public void execute() {
        this.sucesso = false;

        if (titulo.isEmpty() || descricao.isEmpty() || dataTexto.contains("_")) {
            JOptionPane.showMessageDialog(parentView, "Preencha todos os campos obrigatórios!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
 
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate dataFinal;
            try {
                dataFinal = LocalDate.parse(dataTexto, formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Data inválida. Use o formato DD-MM-YYYY.");
            }
            
            // 3. Validação de Regra de Data Passada (Opcional aqui, mas bom para UX)
            if (dataFinal.isBefore(LocalDate.now())) {
                 throw new IllegalArgumentException("A data do evento não pode ser no passado.");
            }

            Evento evento = new Evento();
            evento.setTitulo(titulo);
            evento.setDescricao(descricao);
            evento.setData(dataFinal);

            // chamada ao Serviço
            // O serviço vai verificar disponibilidade e salvar
            eventoServico.criarEvento(evento);

            JOptionPane.showMessageDialog(parentView, "Evento cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            this.sucesso = true;

        } catch (IllegalArgumentException e) {
            // erro de validação de dados
            JOptionPane.showMessageDialog(parentView, e.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
            
        } catch (Exception e) {
            // erro do serviço (ex: "Já existe evento nesta data") ou Banco
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentView, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSucesso() {
        return sucesso;
    }
}