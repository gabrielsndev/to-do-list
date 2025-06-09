import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String opcao = "";
        
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
                    
                    break;
            
                default:
                    break;
            }


            
        } while (opcao.equals("S"));


        input.close();
    }
}
