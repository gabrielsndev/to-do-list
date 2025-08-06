package principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import modelo.Tarefa;
import persistencia.Persistencia;
import persistencia.TarefaDAO;
import relatorios.GeradorDeRelatorios;
import email.Mensageiro;

public class Main {
    public static void main(String[] args) {
    	
        Scanner input = new Scanner(System.in);
        String opcao = "";
        TarefaDAO taskDAO = new TarefaDAO();
        long id;
        List<Tarefa> todasTarefas;
        //CentralDeInformacoes todasTarefas;
        Persistencia xml = new Persistencia();
        //todasTarefas = xml.recuperarCentral("Tasks.xml"); 
        
        
        do {
            System.out.println("---> MENU: ");
            System.out.println("1 - Adicionar nova tarefa.");
            System.out.println("2 - Listar Todas as Tarefas.");
            System.out.println("3 - Receber detalhes de uma tarefa específica.");
            System.out.println("4 - Gerar PDF das tarefas de um dia.");
            System.out.println("5 - Receber email com as tarefas de hoje.");
            System.out.println("6 - Excluir uma tarefa.");
            System.out.println("7 - Listar tarefas de um dia específico.");
            System.out.println("8 - Editar informações de uma tarefa.");
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
                	int prioridade = 0;
                	System.out.println("Digite a prioridade de 1 a 5");
                	while (true) {
                		try {
                		prioridade = Integer.parseInt(input.nextLine());
                		} catch (Exception e) {
                			System.out.println("Valor informado inválido");
                		}
                		if(prioridade >= 1 && prioridade <=5) {
                			break;
                		}
                		System.out.println("Valor informado não é válido, o valor deve estar entre 1 e 5");
                	}
                	
                	Tarefa tarefa = new Tarefa(title, description, deadline, prioridade);
                	
                	//todasTarefas.adicionarTarefa(tarefa);
                	
                	try {
                		taskDAO.salvar(tarefa);
                		//xml.salvarCentral(todasTarefas, "Tasks.xml");
                		System.out.println("Tarefa Adicionada com Sucesso");
                	} catch(Exception e) {
                		System.out.println("Houve um erro ao salvar a tarefa: " + e.getMessage());
                	}
                    break;
                    
                    
                case "2":
                	// get todas as tarefas e imprime os toString delas
                	System.out.println("Lista de todas as tarefas: ");
                	todasTarefas = taskDAO.listar();
                	for(Tarefa t: todasTarefas) {
                		System.out.println(t+" ID: " + t.getId() + ". Titulo: " +  t.getTitulo());
                	}                
                	break;
                	
                	
                case "3":
                	// listando tarefa específica (pelo id)
                	while (true) {
                	    System.out.println("Digite o número do ID que deseja procurar: ");
                	    try {
                	        id = Long.parseLong(input.nextLine());
                	        break; 
                	    } catch (Exception e) {
                	        System.out.println("ID inválido. Tente novamente.");
                	    }
                	}
                	
                	
                	Tarefa encontrada = taskDAO.buscar(id);
                	
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
                	todasTarefas = taskDAO.listar();
                	System.out.println("Digite a data para o relatório (formato: aaaa-mm-dd):");
                    String entradaData = input.nextLine();
                    try {
                        LocalDate dataRelatorio = LocalDate.parse(entradaData);
                        // GeradorDeRelatorios.obterTarefasDeUmDia(dataRelatorio, todasTarefas);
                    } catch (Exception e) {
                        System.out.println("Data inválida! Use o formato correto: aaaa-mm-dd");
                    }
                    break;
                
                	
                case "5":
                	//email
                	String mensagem = "Segue em anexo o relatório de tarefas para hoje.";
            		String destinatario = " ";
            		String dataDeEntrada = " ";
                	do {
                		System.out.println("Digite o e-mail do destinatário:");
                		destinatario = input.nextLine();
                		if (!destinatario.contains("@") || !destinatario.contains(".")) {
                            System.out.println("Endereço de e-mail inválido. Tente novamente.");
                            continue;
                        	}
                		
                	 try {
                		 
                     // Primeiro, gerar o PDF do dia atual
            		 System.out.println("Digite a data para o relatório (formato: aaaa-mm-dd):");
            		 dataDeEntrada = input.nextLine();
            		 LocalDate dataDoRelatorio = LocalDate.parse(dataDeEntrada);
            		 todasTarefas = taskDAO.listar();
            		 // GeradorDeRelatorios.obterTarefasDeUmDia(dataDoRelatorio, todasTarefas);

                		
                	 //LocalDate hoje = LocalDate.now();
                     //GeradorDeRelatorios.obterTarefasDeUmDia(hoje, todasTarefas);
                	 
                     // Agora, enviar o e-mail
                     // Mensageiro.enviarEmail(destinatario, mensagem);
                     System.out.println("Mensagem enviada com sucesso!");
                     break;
                	 
                	 } catch(Exception e) {
                		 System.out.println("Digite um e-mail válido. "+ e.getMessage());
                	 	}
                	} while(true);	
                	break;
                	
                
                case "6":
                	try {
                		System.out.println("Digite o ID da tarefa a ser excluída");
                		id = Long.parseLong(input.nextLine());
                		taskDAO.remover(id);
                		System.out.println("Tarefa excluída com sucesso!");
                	} catch (NumberFormatException e) {
                        System.out.println("Entrada inválida");
                    } catch(Exception e) {
                		System.out.println("Nenhuma tarefa foi encontrada com esse ID");
                	}
                	break;
                
                	
                case "7":
                	dataValida = false;
                	deadline = null;
                	do {
                	    try {
                	        System.out.println("Digite a Data para Procurar as Tarefas (no formato: AAAA-MM-DD):");
                	        String date = input.nextLine();
                	        deadline = LocalDate.parse(date);
                	        dataValida = true;
                	    } catch (Exception e) {
                	        System.out.println("Data inválida! Tente novamente");
                	    }
                	    
                	} while (!dataValida);

                	List<Tarefa> tarefasEspecificas = taskDAO.buscarDeadLine(deadline);
                	if(tarefasEspecificas.isEmpty()) {
                		System.out.println("Nenhuma tarefa tem o dia especificado");
                	} else {
	                	for(Tarefa t: tarefasEspecificas) {
	                		System.out.println(t + " ID: " + t.getId() + ". Titulo: " +  t.getTitulo());
	                	}  
                	}
                	
                	
                	break;
                	
                case "8":
                	System.out.println("Digite o id da tarefa que você quer editar:");
                	long idEditar;
                	try {
                		idEditar = Long.parseLong(input.nextLine());
                	} catch (Exception e) {
                		System.out.println("Id digitado é inválido");
                		break;
                	}
                	System.out.println("Digite um Titulo para a Tarefa:");
                	String tituloNovo= input.nextLine();
                	
                	System.out.println("Digite uma Descrição para a Tarefa:");
                	String descricaoNova = input.nextLine();
                	dataValida = false;
                	LocalDate novadeadline = null;
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
                	int novaPrioridade = 0;
                	System.out.println("Digite a prioridade de 1 a 5");
                	while (true) {
                		try {
                		novaPrioridade = Integer.parseInt(input.nextLine());
                		} catch (Exception e) {
                			System.out.println("Valor informado inválido");
                		}
                		if(novaPrioridade >= 1 && novaPrioridade <=5) {
                			break;
                		}
                		System.out.println("Valor informado não é válido, o valor deve estar entre 1 e 5");
                	}
                	
                	Tarefa novaTarefa = new Tarefa(tituloNovo, descricaoNova, novadeadline, novaPrioridade);
                	try {
                		taskDAO.editarTarefa(idEditar, novaTarefa);
                	} catch(Exception e) {
                		System.out.println(e.getMessage());
                	}
                	
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