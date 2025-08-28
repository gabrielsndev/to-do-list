package principal;
import java.time.LocalDate;

import java.util.List;
import java.util.Scanner;

import modelo.EventoServico;
import modelo.Evento;
import modelo.Subtarefa;
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
        EventoServico eventoServico = new EventoServico();
        
        do {
            System.out.println("---> MENU: ");
            System.out.println("1 - Adicionar nova tarefa.");
            System.out.println("2  - Listar todas as tarefas.");
            System.out.println("3  - Receber detalhes de uma tarefa específica.");
            System.out.println("4  - Gerar PDF das tarefas de um dia.");
            System.out.println("5  - Receber email com as tarefas de hoje.");
            System.out.println("6  - Excluir uma tarefa.");
            System.out.println("7  - Listar tarefas de um dia específico.");
            System.out.println("8  - Editar informações de uma tarefa.");
            System.out.println("9  - Exportar relatório mensal (.xlsx).");                     
            System.out.println("10 - Listar tarefas críticas.");                              
            System.out.println("11 - Cadastrar subtarefas.");                                 
            System.out.println("12 - Listar subtarefas.");                                     
            System.out.println("13 - Editar subtarefa.");                                     
            System.out.println("14 - Excluir subtarefa.");                                     
            System.out.println("15 - Cadastrar eventos (sem choque).");                       
            System.out.println("16 - Editar evento.");                                         
            System.out.println("17 - Excluir evento.");                                        
            System.out.println("18 - Listar eventos + dias restantes.");                       
            System.out.println("19 - Listar eventos de um dia.");                              
            System.out.println("20 - Listar eventos de um mês.");                              
            System.out.println("S  - Para encerrar o programa.");
            
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
            		 GeradorDeRelatorios.gerarRelatorioPDFDoDia(dataDoRelatorio, todasTarefas, "aaaa!");

                		
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
                
                case "9":
                    todasTarefas = taskDAO.listar();
                    System.out.print("Digite o ano do relatório (ex: 2025): ");
                    int ano = Integer.parseInt(input.nextLine());

                    System.out.print("Digite o mês do relatório (1 a 12): ");
                    int mes = Integer.parseInt(input.nextLine());

                    String nomeArquivoExcel = "relatorio-mensal-" + mes + "-" + ano + ".xlsx";
                    GeradorDeRelatorios.gerarPlanilhaMensal(todasTarefas, ano, mes, nomeArquivoExcel);
                    break;
                
                case "10":
                    todasTarefas = taskDAO.listar();
                    LocalDate hoje = LocalDate.now();
                    System.out.println("Tarefas críticas:");
                    
                    for (Tarefa t : todasTarefas) {
                        long diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(hoje, t.getDeadline());
                        if (diasRestantes - t.getPrioridade() < 0) {
                            System.out.println("ID: " + t.getId() + " | Título: " + t.getTitulo() + " | Deadline: " + t.getDeadline() + " | Prioridade: " + t.getPrioridade());
                        }
                    }
                    break;
                
                case "11":
                    System.out.println("Digite o ID da tarefa principal:");
                    long idTarefaPrincipal;
                    try {
                        idTarefaPrincipal = Long.parseLong(input.nextLine());
                    } catch (Exception e) {
                        System.out.println("ID inválido.");
                        break;
                    }

                    Tarefa tarefaPai = taskDAO.buscar(idTarefaPrincipal);
                    if (tarefaPai == null) {
                        System.out.println("Tarefa não encontrada.");
                        break;
                    }

                    System.out.println("Digite a descrição da subtarefa:");
                    String descricaoSub = input.nextLine();

                    System.out.println("Digite a data da subtarefa (formato: AAAA-MM-DD):");
                    LocalDate dataSub;
                    try {
                        dataSub = LocalDate.parse(input.nextLine());
                    } catch (Exception e) {
                        System.out.println("Data inválida.");
                        break;
                    }

                    System.out.println("Digite o percentual concluído (0 a 100):");
                    double percentual;
                    try {
                        percentual = Double.parseDouble(input.nextLine());
                    } catch (Exception e) {
                        System.out.println("Percentual inválido.");
                        break;
                    }

                    // Cria a subtarefa e associa à tarefa principal
                    //Subtarefa sub = new Subtarefa(descricaoSub, percentual, dataSub, tarefaPai);
                    //tarefaPai.adicionarSubtarefa(sub);

                    try {
                        taskDAO.editarTarefa(tarefaPai.getId(), tarefaPai);
                        System.out.println("Subtarefa adicionada com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao salvar subtarefa: " + e.getMessage());
                    }
                    break;
                
                case "12":
                    System.out.println("Digite o ID da tarefa que deseja ver as subtarefas:");
                    long idTarefa;
                    try {
                        idTarefa = Long.parseLong(input.nextLine());
                    } catch (Exception e) {
                        System.out.println("ID inválido.");
                        break;
                    }

                    Tarefa tarefaComSubs = taskDAO.buscar(idTarefa);
                    if (tarefaComSubs == null) {
                        System.out.println("Tarefa não encontrada.");
                        break;
                    }

                    List<Subtarefa> subtarefas = tarefaComSubs.getSubtarefas();
                    if (subtarefas.isEmpty()) {
                        System.out.println("Essa tarefa não tem subtarefas.");
                    } else {
                        for (Subtarefa subT : subtarefas) {
                            System.out.println("▶ Subtarefa: " + subT.getDescricao() + " - Progresso: " + subT.getPercentualConcluido() + "%");
                        }
                    }
                    break;
                case "13":
                    System.out.println("Digite o ID da tarefa principal:");
                    long idPrincipal;
                    try {
                        idPrincipal = Long.parseLong(input.nextLine());
                    } catch (Exception e) {
                        System.out.println("ID inválido.");
                        break;
                    }

                    Tarefa tarefaBase = taskDAO.buscar(idPrincipal);
                    if (tarefaBase == null || tarefaBase.getSubtarefas().isEmpty()) {
                        System.out.println("Tarefa não encontrada ou sem subtarefas.");
                        break;
                    }

                    System.out.println("Digite o título da subtarefa a ser editada:");
                    String tituloSubAntigo = input.nextLine();

                    Subtarefa subtarefaEditar = tarefaBase.getSubtarefas().stream()
                        .filter(s -> s.getDescricao().equalsIgnoreCase(tituloSubAntigo))
                        .findFirst()
                        .orElse(null);

                    if (subtarefaEditar == null) {
                        System.out.println("Subtarefa não encontrada.");
                        break;
                    }

                    System.out.println("Digite a nova descrição:");
                    String novaDescricao = input.nextLine();
                  
                    System.out.println("Digite a nova data (AAAA-MM-DD):");
                    LocalDate novaData = LocalDate.parse(input.nextLine());
                   
                    System.out.println("Digite o novo percentual concluído:");
                    double novoPercentual = Double.parseDouble(input.nextLine());
                    
                    subtarefaEditar.setDescricao(novaDescricao);
                    subtarefaEditar.setDeadline(novaData);
                    subtarefaEditar.setPercentualConcluido(novoPercentual);

                    try {
                        taskDAO.editarTarefa(idPrincipal, tarefaBase);
                        System.out.println("Subtarefa editada com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao editar subtarefa: " + e.getMessage());
                    }
                    break;

                case "14":
                    System.out.println("Digite o ID da tarefa principal:");
                    long idTarefaSub;
                    try {
                        idTarefaSub = Long.parseLong(input.nextLine());
                    } catch (Exception e) {
                        System.out.println("ID inválido.");
                        break;
                    }

                    Tarefa tarefaSub = taskDAO.buscar(idTarefaSub);
                    if (tarefaSub == null || tarefaSub.getSubtarefas().isEmpty()) {
                        System.out.println("Tarefa não encontrada ou sem subtarefas.");
                        break;
                    }

                    System.out.println("Digite o título da subtarefa que deseja excluir:");
                    String tituloExcluir = input.nextLine();

                    boolean removido = tarefaSub.getSubtarefas().removeIf(s -> s.getDescricao().equalsIgnoreCase(tituloExcluir));

                    if (!removido) {
                        System.out.println("Subtarefa não encontrada.");
                        break;
                    }

                    try {
                        taskDAO.editarTarefa(idTarefaSub, tarefaSub);
                        System.out.println("Subtarefa excluída com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao excluir subtarefa: " + e.getMessage());
                    }
                    break;
                case "15":
                    try {
                        System.out.println("Digite o título do evento:");
                        String tituloEvento = input.nextLine();
                        System.out.println("Digite a descrição do evento:");
                        String descricaoEvento = input.nextLine();
                        System.out.println("Digite a data do evento (AAAA-MM-DD):");
                        LocalDate dataEvento = LocalDate.parse(input.nextLine());

                        Evento novoEvento = new Evento(tituloEvento, descricaoEvento, dataEvento);
                       
                        eventoServico.criarEvento(novoEvento);
                        System.out.println("Evento criado com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao cadastrar evento: " + e.getMessage());
                    }
                    break;

                case "16":
                    try {
  

                    	System.out.println("Digite o ID do evento a ser editado:");
                        
                    	long idEvento = Long.parseLong(input.nextLine());
                        Evento evento = eventoServico.buscar(idEvento);
                        
                        if (evento == null) {
                            System.out.println("Evento não encontrado.");
                            break;
                        }
                        System.out.println("Digite o novo título:");
                        String novoTitulo = input.nextLine();
                        
                        System.out.println("Digite a nova descrição:");
                        String descNova = input.nextLine();
                        
                        System.out.println("Digite a nova data (AAAA-MM-DD):");
                        LocalDate dataNova = LocalDate.parse(input.nextLine());

                        evento.setTitulo(novoTitulo);
                        evento.setDescricao(descNova);
                        evento.setData(dataNova);
                        eventoServico.editarEvento(evento);
                        System.out.println("Evento editado com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao editar evento: " + e.getMessage());
                    }
                    break;

                case "17":
                    try {
                        System.out.println("Digite o ID do evento a ser excluído:");
                        long idEvento = Long.parseLong(input.nextLine());
                        eventoServico.excluirEvento(idEvento);
                        System.out.println("Evento excluído com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao excluir evento: " + e.getMessage());
                    }
                    break;

                case "18":
                    List<Evento> eventos = eventoServico.listarTodos();
                    if (eventos.isEmpty()) {
                        System.out.println("Nenhum evento cadastrado.");
                    } else {
                        for (Evento ev : eventos) {
                            long diasRestantes = LocalDate.now().until(ev.getData()).getDays();
                            System.out.println(ev + " (Faltam " + diasRestantes + " dias)");
                        }
                    }
                    break;

                case "19":
                    try {
                        System.out.println("Digite a data do evento para listar (AAAA-MM-DD):");
                        LocalDate dataBusca = LocalDate.parse(input.nextLine());
                        List<Evento> eventosDoDia = eventoServico.listarPorDia(dataBusca);
                        if (eventosDoDia.isEmpty()) {
                            System.out.println("Nenhum evento encontrado nesta data.");
                        } else {
                            for (Evento ev : eventosDoDia) {
                                System.out.println(ev);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Data inválida: " + e.getMessage());
                    }
                    break;

                case "20":
                    try {
                        System.out.println("Digite o ano:");
                        int anoEvento = Integer.parseInt(input.nextLine());
                        System.out.println("Digite o mês (1 a 12):");
                        int mesEvento = Integer.parseInt(input.nextLine());
                        List<Evento> eventosDoMes = eventoServico.listarPorMes(mesEvento);
                        if (eventosDoMes.isEmpty()) {
                            System.out.println("Nenhum evento encontrado nesse mês.");
                        } else {
                            for (Evento ev : eventosDoMes) {
                                System.out.println(ev);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao buscar eventos: " + e.getMessage());
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