package principal;
import java.time.LocalDate;
import java.util.Scanner;

import modelo.CentralDeInformacoes;
import modelo.Tarefa;
import persistencia.Persistencia;
import relatorios.GeradorDeRelatorios;
import email.Mensageiro;

public class Main {
    public static void main(String[] args) {
    	
        Scanner input = new Scanner(System.in);
        String opcao = "";
        
        CentralDeInformacoes todasTarefas;
        Persistencia xml = new Persistencia();
        todasTarefas = xml.recuperarCentral("Tasks.xml"); 
        
        do {
            System.out.println("---> MENU: ");
            System.out.println("1 - Adicionar nova tarefa.");
            System.out.println("2 - Listar Todas as Tarefas.");
            System.out.println("3 - Receber detalhes de uma tarefa específica.");
            System.out.println("4 - Gerar PDF das tarefas de um dia.");
            System.out.println("5 - Receber email com as tarefas de hoje.");
            System.out.println("S - Para encerrar o programa.");
            
            System.out.print("Escolha uma opção: ");
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
                	// get todas as tarefas e imprime os toString delas
                	System.out.println("Lista de todas as tarefas: ");
                	for(Tarefa t: todasTarefas.getTodasAsTarefas()) {
                		System.out.println(t+" ID: "+ t.getId());
                	}                
                	break;
                	
                case "3":
                	// listando tarefa específica (pelo id)
                	System.out.println("Digite o número do ID que deseja procurar: ");
                	long id = Long.parseLong(input.nextLine());
                	Tarefa encontrada = todasTarefas.recuperarTarefaPorId(id);
                	
                	if(encontrada != null) {
                		System.out.println("Tarefa encontrada.");
                		System.out.println("ID: "+ encontrada.getId()+ " \nDescrição: "+ encontrada.getDescricao());
                		System.out.println("Data: "+ encontrada.getDeadline());	
                	}
                	else {
                		System.out.println("Tarefa não encontrada.");
                		
                	}
                	break;
                	
                case "4":
                	//gerar pdf dia específico
                	System.out.println("Digite a data para o relatório (formato: aaaa-mm-dd):");
                    String entradaData = input.nextLine();
                    try {
                        LocalDate dataRelatorio = LocalDate.parse(entradaData);
                        GeradorDeRelatorios.obterTarefasDeUmDia(dataRelatorio, todasTarefas);
                    } catch (Exception e) {
                        System.out.println("Data inválida! Use o formato correto: aaaa-mm-dd");
                    }
                    break;
                
                	
                case "5":
                	//email
                	String mensagem = "Segue em anexo o relatório de tarefas para hoje.";
            		String destinatario = " ";
                    
                	do {
                		System.out.println("Digite o e-mail do destinatário:");
                		destinatario = input.nextLine();
                		if (!destinatario.contains("@") || !destinatario.contains(".")) {
                            System.out.println("Endereço de e-mail inválido. Tente novamente.");
                            continue;
                        }
                		
                	 try {
                		 
                     // Primeiro, gerar o PDF do dia atual
                     
                	 LocalDate hoje = LocalDate.now();
                     GeradorDeRelatorios.obterTarefasDeUmDia(hoje, todasTarefas);
                	 
                     // Agora, enviar o e-mail
                     Mensageiro.enviarEmail(destinatario, mensagem);
                     System.out.println("Mensagem enviada com sucesso!");
                     break;
                	 
                	 }catch(Exception e) {
                		 System.out.println("Digite um e-mail válido. "+ e.getMessage());
                	 }
                	}while(true);
                	break;
                	
                case "S":
                case "s":
                    System.out.println("Saindo...");
                    break;
                	
                default:
                	System.out.println("Opção inválida");
                    break;
            }            
            
        } while (!opcao.equalsIgnoreCase("S"));


        input.close();
    }
}