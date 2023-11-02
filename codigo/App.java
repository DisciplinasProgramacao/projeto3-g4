import java.io.IOException;
import java.util.Scanner;  // Import the Scanner class

public class App {
    static Estacionamento estacionamento = new Estacionamento("estacionamento", 1, 1);
    static String idClientes = "1";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int escolha;

        do {
            System.out.println("Selecione uma das opções:");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Operações de Veículo");
            System.out.println("3 - Operações de Estacionamento");
            System.out.println("4 - Sair");
            System.out.print("Digite o número da opção desejada: ");

            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    cadastrarSubMenu(scanner);
                    break;
                case 2:
                    operacoesVeiculoSubMenu(scanner);
                    break;
                case 3:
                    operacoesEstacionamentoSubMenu(scanner);
                    break;
                case 4:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                    break;
            }
        } while (escolha != 5);

        scanner.close();
    }

    public static boolean validaEstacionamento(){
        if(estacionamento == null){
            return false;
        } return true;
    };

    public static void cadastrarSubMenu(Scanner scanner) {
        int subEscolha;
        do {
            System.out.println("Selecione o que deseja cadastrar:");
            System.out.println("1 - Cadastrar Estacionamento");
            System.out.println("2 - Cadastrar Cliente");
            System.out.println("3 - Cadastrar Veículo");
            System.out.println("4 - Voltar ao menu principal");
            System.out.print("Digite o número da opção desejada: ");
            subEscolha = scanner.nextInt();

            switch (subEscolha) {
                case 1:
                    System.out.println("Opção Cadastrar Estacionamento selecionada.");
                    if (!validaEstacionamento()){
                        System.out.println("Estacionamento já cadastrado.");
                        break;
                    } else {
                        System.out.print("Digite o nome do estacionamento: ");
                        String nome = scanner.nextLine();

                        System.out.print("Digite o número de fileiras desejadas: ");
                        int numFileiras = scanner.nextInt();

                        System.out.print("Digite o número de vagas por fila: ");
                        int numVagasPorFila = scanner.nextInt();

                        estacionamento = new Estacionamento(nome, numFileiras, numVagasPorFila);
                    }
                    break;
                case 2:
                    System.out.println("Opção Cadastrar Cliente selecionada.");
                    if (validaEstacionamento()){
                        System.out.println("Estacionamento não cadastrado.");
                        break;
                    } else{
                        System.out.print("Digite o nome do cliente: ");
                        String nome = scanner.nextLine();

                        Cliente cliente = new Cliente(nome, idClientes);

                        estacionamento.addCliente(cliente);

                        System.out.print("Cliente cadastrado, seu id é: " + idClientes);

                        var idAtual = Integer.parseInt(idClientes);
                        idAtual++;
                        idClientes = String.valueOf(idAtual);
                    }
                    break;
                case 3:
                    System.out.println("Opção Cadastrar Veículo selecionada.");
                    if (!validaEstacionamento()){
                        System.out.println("Estacionamento não cadastrado.");
                        break;
                    } else{
                        scanner.nextLine();
                        System.out.print("Id do cliente: ");
                        String id = scanner.nextLine();

                        System.out.print("Digite a placa do carro: ");
                        String placa = scanner.nextLine();

                        Veiculo veiculo = new Veiculo(placa);

                        DAOVeiculo daoVeiculo = new DAOVeiculo("veiculos.txt");
                        try {
                            daoVeiculo.abrirEscrita();
                            daoVeiculo.add(veiculo);
                            daoVeiculo.fechar();
                        }catch (IOException e){
                            System.out.println(e.getMessage());
                        }
                        estacionamento.addVeiculo(veiculo, id);
                    }
                    break;
                case 4:
                    System.out.println("Voltando ao menu principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                    break;
            }
        } while (subEscolha != 4);
    }

    public static void operacoesVeiculoSubMenu(Scanner scanner) {
        int subEscolha;

        do {
            System.out.println("Selecione a operação de veículo:");
            System.out.println("1 - Estacionar Veículo");
            System.out.println("2 - Sair da Vaga com Veículo");
            System.out.println("3 - Voltar ao menu principal");
            System.out.print("Digite o número da opção desejada: ");
            subEscolha = scanner.nextInt();

            switch (subEscolha) {
                case 1:
                    System.out.println("Opção Estacionar Veículo selecionada.");
                    if (validaEstacionamento()){
                        System.out.println("Estacionamento não cadastrado.");
                        break;
                    } else{
                        System.out.print("Digite a placa do carro: ");
                        String placa = scanner.nextLine();

                        estacionamento.estacionar(placa);
                    }
                    break;
                case 2:
                    System.out.println("Opção Sair da Vaga com Veículo selecionada.");
                    if (validaEstacionamento()){
                        System.out.println("Estacionamento não cadastrado.");
                        break;
                    } else{
                        System.out.print("Digite a placa do carro: ");
                        String placa = scanner.nextLine();

                        estacionamento.sair(placa);
                    }
                    break;
                case 3:
                    System.out.println("Voltando ao menu principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                    break;
            }
        } while (subEscolha != 6);
    }

    public static void operacoesEstacionamentoSubMenu(Scanner scanner) {
        int subEscolha;
        do {
            System.out.println("Selecione a operação de cliente:");
            System.out.println("1 - Total arrecadado");
            System.out.println("2 - Arrecadação no mês");
            System.out.println("3 - Valor Médio por uso");
            System.out.println("4 - Top 5 Clientes");
            System.out.println("5 - Voltar ao menu principal");
            System.out.print("Digite o número da opção desejada: ");
            subEscolha = scanner.nextInt();

            switch (subEscolha) {
                case 1:
                    System.out.println("Opção retorno do total arrecadado.");
                    if (validaEstacionamento()){
                        System.out.println("Estacionamento não cadastrado.");
                        break;
                    } else {
                        double total = estacionamento.totalArrecadado();

                        System.out.println("O total arrecadado pelo estacionamento foi de." + total);
                    }
                    break;
                case 2:
                    System.out.println("Opção retorno do total arrecadado no mês.");
                    if (validaEstacionamento()){
                        System.out.println("Estacionamento não cadastrado.");
                        break;
                    } else {
                        System.out.print("Qual mês você deseja conferir: ");
                        String mes = scanner.nextLine();

                        double totalMes = estacionamento.arrecadacaoNoMes(Integer.parseInt(mes));

                        System.out.println("O total arrecadado pelo estacionamento no mês" + mes);
                        System.out.println("foi de" + totalMes);
                    }
                    break;
                case 3:
                    System.out.println("Opção retorno valor médio por uso.");
                    if (validaEstacionamento()){
                        System.out.println("Estacionamento não cadastrado.");
                        break;
                    } else {
                        double valorMedio = estacionamento.valorMedioPorUso();

                        System.out.println("O total arrecadado pelo estacionamento no mês" + valorMedio);
                    }
                    break;
                case 4:
                    System.out.println("Opção top 5 clientes.");
                    if (validaEstacionamento()){
                        System.out.println("Estacionamento não cadastrado.");
                        break;
                    } else {
                        System.out.print("Qual mês você deseja conferir: ");
                        String mes = scanner.nextLine();

                        String clientes = estacionamento.top5Clientes(Integer.parseInt(mes));

                        System.out.println("Os top 5 clientes são." + clientes);
                    }
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal.");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                    break;
            }
        } while (subEscolha != 5);
    }
}