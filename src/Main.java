import java.util.Scanner;

import modelo.CentralDeInformacoes;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String opcao = "";
        CentralDeInformacoes tarefasSalvas = new CentralDeInformacoes();
        
        // Puxa as tarefas da persistencia: 
        
        //
        
        
        do {
            System.out.println("---- MENU ----");
            System.out.println("1 - Adicionar Nova Tarefa");
            System.out.println("2 - Listar Todas as Tarefas");
            System.out.println("3 - Receber Detalhes de Uma Tarefa");
            System.out.println("4 - Gerar PDF das Tarefas de Um Dia");
            System.out.println("5 - Receber Email Com as Tarefas de Hoje");
            System.out.println("S - Para Encerrar o Programa");

            switch (opcao) {
                case "1":
                	//Lê uma tarefa e adiciona no arraylis local e no arquivo de persistencia
                	
                    
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
                	System.out.println("Opção inválida!");
                    break;
            }


            
        } while (opcao.equals("S"));


        input.close();
    }
}
