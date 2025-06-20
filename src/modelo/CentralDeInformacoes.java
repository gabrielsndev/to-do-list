package modelo;

import java.util.ArrayList;

public class CentralDeInformacoes {
    private ArrayList<Tarefa>todasAsTarefas = new ArrayList<Tarefa>();

    public boolean adicionarTarefa(Tarefa t){
        for(Tarefa tarefa: todasAsTarefas)
            if(t.getId() == tarefa.getId()){
                return false;
        }
        todasAsTarefas.add(t);
        return true;  
    }

    public ArrayList<Tarefa> getTodasAsTarefas(){
        return todasAsTarefas;
    }
    
    public void setTodasAsTarefas(ArrayList<Tarefa>todasAsTarefas){
        this.todasAsTarefas = todasAsTarefas;
    }

    public Tarefa recuperarTarefaPorId(long id) {
        for (Tarefa tarefa : todasAsTarefas) {
            if (id == tarefa.getId()) {
                return tarefa;
            }
        }
        return null;
    } 
 
    }


