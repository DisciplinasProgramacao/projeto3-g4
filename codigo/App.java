import java.util.Scanner;  // Import the Scanner class

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int option = 0;
        while(option != 5){
            System.out.println("O que você deseja fazer?");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Operações de Veículo");
            System.out.println("3 - Operações de Cliente");
            System.out.println("4 - Operações de Vaga");
            System.out.println("5 - Operações de Vaga");
    
            option = sc.nextInt();

            switch(option){
                case 1:

                break;

                default:

                break;
            }
        }
    }
}