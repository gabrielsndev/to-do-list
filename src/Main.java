import java.time.LocalDate;
import java.util.Scanner;

import modelo.CentralDeInformacoes;
import modelo.Tarefa;
import persistencia.Persistencia;


public class Main {
    public static void main(String[] args) {
    	
        Scanner input = new Scanner(System.in);
        String opcao = "";
        
        CentralDeInformacoes todasTarefas;
        Persistencia xml = new Persistencia();
        todasTarefas = xml.recuperarCentral("Tasks.xml"); 
        
        do {
            System.out.println("---- MENU ----");
            System.out.println("1 - Adicionar Nova Tarefa");
            System.out.println("2 - Listar Todas as Tarefas");
            System.out.println("3 - Receber Detalhes de Uma Tarefa");
            System.out.println("4 - Gerar PDF das Tarefas de Um Dia");
            System.out.println("5 - Receber Email Com as Tarefas de Hoje");
            System.out.println("S - Para Encerrar o Programa");
            
            opcao = input.nextLine();

            switch (opcao) {
            
                case "1":
                	System.out.println("Digite um Titulo para a Tarefa:");
                	String title = input.nextLine();
                	
                	System.out.println("Digite uma Descrição para a Tarefa:");
                	String description = input.nextLine();
                	boolean dataValida = false;
                	LocalDate deadline = null;
                	do {
                	    try {
                	        System.out.println("Digite a Data da Tarefa (no formato: AAAA-MM-DD):");
                	        String date = input.nextLine();
                	        deadline = LocalDate.parse(date);
                	        dataValida = true;
                	    } catch (Exception e) {
                	        System.out.println("Data inválida! Tente novamente");
                	    }
                	    
                	} while (!dataValida);
                	
                	Tarefa tarefa = new Tarefa(title, description, deadline);
                	
                	todasTarefas.adicionarTarefa(tarefa);
                	
                	try {
                		xml.salvarCentral(todasTarefas, "Tasks.xml");
                		System.out.println("Tarefa Adicionada com Sucesso");
                	} catch(Exception e) {
                		System.out.println("Houve um erro ao salvar a tarefa: " + e.getMessage());
                	}
                	
                    break;
                    
                case "2":
                	// get toda as tarefas e imprime os toString delas
                	
                	
                	break;
                	
                case "3":
                	// sei nem, pra onde vai
                	
                	
                	break;
                	
                case "4":
                	//gerar pdf
                	
                	
                	break;
                	
                case "5":
                	//email
                	
                	
                	break;
                	
                default:
                	System.out.println("Opção inválida");
                    break;
            }
            

            
        } while (!opcao.equalsIgnoreCase("S"));


        input.close();
    }
}
