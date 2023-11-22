import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

public class App {
    static Estacionamento estacionamento = new Estacionamento("estacionamento", 1, 1);
    static List<Estacionamento> estacionamentosAletorios = new ArrayList<>();
    static Long idClientes = 1l;

    private static Map<String, Cliente> mapClientes = new HashMap<>();
    private static Map<String, Veiculo> mapVeiculos = new HashMap<>();


    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        lerDadosArquivoClientes();
        lerDadosArquivoVeiculos(scanner);
        GerarDados();

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
                case 1 -> cadastrarSubMenu(scanner);
                case 2 -> operacoesVeiculoSubMenu(scanner);
                case 3 -> operacoesEstacionamentoSubMenu(scanner);
                case 4 -> System.out.println("Saindo do programa.");
                default -> System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (escolha != 4);

        scanner.close();
    }
    public static boolean validaEstacionamento() {
        if (estacionamento == null) {
            return false;
        }
        return true;
    }

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
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento já cadastrado.");
                        break;
                    } else {
                        scanner.nextLine();
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
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não cadastrado.");
                        break;
                    } else {
                        scanner.nextLine();
                        System.out.print("Digite o nome do cliente: ");
                        String nome = scanner.nextLine();

                        Cliente cliente = new Cliente(nome, String.valueOf(idClientes));

                        DAOCliente daoCliente = new DAOCliente("clientes.txt");
                        try {
                            daoCliente.abrirEscrita();
                            daoCliente.add(cliente);
                            daoCliente.fechar();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }

                        estacionamento.addCliente(cliente);

                        System.out.print("Cliente cadastrado, seu id é: " + idClientes + "\n");

                        idClientes++;
                    }
                    break;
                case 3:
                    System.out.println("Opção Cadastrar Veículo selecionada.");
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não cadastrado.");
                        break;
                    } else {
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
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        estacionamento.addVeiculo(veiculo, mapClientes.get(id));
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
                case 1 -> {
                    System.out.println("Opção Estacionar Veículo selecionada.");
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não cadastrado.");
                    } else {
                        scanner.nextLine();
                        System.out.print("Digite a placa do carro: ");
                        String placa = scanner.nextLine();

                        System.out.print("Gostaria de contratar algum serviço adicional(s/n)? ");
                        String opcao = scanner.nextLine();

                        if (opcao.trim().equalsIgnoreCase("s")) {
                            System.out.println("Qual serviço você gostaria de contratar(MANOBRISTA/LAVAGEM/POLIMENTO)? ");
                            String opcaoServico = scanner.nextLine();
                            Servico servico = Servico.valueOf(opcaoServico.toUpperCase());
                            System.out.println("Servico de " + servico.getNome() + " contratado!");
                            estacionamento.estacionar(placa, servico);
                        }else {
                            estacionamento.estacionar(placa);
                        }
                    }
                }
                case 2 -> {
                    System.out.println("Opção Sair da Vaga com Veículo selecionada.");
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não cadastrado.");
                    } else {
                        scanner.nextLine();
                        System.out.print("Digite a placa do carro: ");
                        String placa = scanner.nextLine();

                        double valorAPagar = estacionamento.sair(placa);
                        System.out.println("Valor a ser pago: " + formatarMoeda(valorAPagar));
                    }
                }
                case 3 -> System.out.println("Voltando ao menu principal.");
                default -> System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (subEscolha != 3);
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
                case 1 -> {
                    System.out.println("Opção retorno do total arrecadado.");
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não cadastrado.");
                    } else {
                        double total = estacionamento.totalArrecadado();

                        System.out.println("O total arrecadado pelo estacionamento foi de " + formatarMoeda(total));
                    }
                }
                case 2 -> {
                    System.out.println("Opção retorno do total arrecadado no mês.");
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não cadastrado.");
                    } else {
                        scanner.nextLine();
                        System.out.print("Qual mês você deseja conferir: ");
                        String mes = scanner.nextLine();

                        double totalMes = estacionamento.arrecadacaoNoMes(Integer.parseInt(mes));

                        System.out.println("O total arrecadado pelo estacionamento no mês " + mes);
                        System.out.println("foi de: " + formatarMoeda(totalMes));
                    }
                }
                case 3 -> {
                    System.out.println("Opção retorno valor médio por uso.");
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não cadastrado.");
                    } else {
                        double valorMedio = estacionamento.valorMedioPorUso();

                        System.out.println("O valor medio por uso foi de: " + formatarMoeda(valorMedio));
                    }
                }
                case 4 -> {
                    System.out.println("Opção top 5 clientes.");
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não cadastrado.");
                    } else {
                        scanner.nextLine();
                        System.out.print("Qual mês você deseja conferir: ");
                        String mes = scanner.nextLine();

                        String clientes = estacionamento.top5Clientes(Integer.parseInt(mes));

                        System.out.println("Os top 5 clientes são." + clientes);
                    }
                }
                case 5 -> System.out.println("Voltando ao menu principal.");
                default -> System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (subEscolha != 5);
    }

    static class GerarID {
        private static int lastID = 0;

        public static int ID() {
            lastID++;
            return lastID;
        }
    }

    private static void GerarDados() {

        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            String nome = "Estacionamento " + rand.nextInt(100);
            int fileiras = rand.nextInt(10) + 1; // Mínimo de 1 fileira
            int vagasPorFila = rand.nextInt(20) + 1; // Mínimo de 1 vaga por fileira
            Estacionamento n = new Estacionamento(nome, fileiras, vagasPorFila);

            estacionamentosAletorios.add(n);
        }
        for (Estacionamento x : estacionamentosAletorios) {
            for (int i = 0; i < 9; i++) {

                String placa = Integer.toString(GerarID.ID());
                Veiculo veiculo = new Veiculo(placa);

                String nome = "Cliente " + rand.nextInt(1000);
                String id = Integer.toString(GerarID.ID());
                Cliente n = new Cliente(nome, id);

                x.addCliente(n);
                x.addVeiculo(veiculo, n);
                for (int j = 0; j < 2; j++) {
                    x.estacionar(placa);
                    x.sair(placa);
                }
            }
        }
    }

    private static void lerDadosArquivoClientes() throws FileNotFoundException {
        File file = new File("clientes.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Nao foi possivel criar o arquivo. " + e.getMessage());
            }
        }
        Scanner fileReader = new Scanner(new FileReader("clientes.txt"));
        while(fileReader.hasNext()){
            String[] fields = fileReader.nextLine().split(";");
            String id = fields[0];
            String nome = fields[1];
            Cliente cliente = new Cliente(nome, id);
            mapClientes.put(id, cliente);
            estacionamento.addCliente(cliente);
            idClientes++;
        }
    }

    private static void lerDadosArquivoVeiculos(Scanner scanner) throws FileNotFoundException {
        File file = new File("veiculos.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Nao foi possivel criar o arquivo. " + e.getMessage());
            }
        }
        Scanner fileReader = new Scanner(new FileReader("veiculos.txt"));
        while(fileReader.hasNext()){
            String[] fields = fileReader.nextLine().split(";");
            String placa = fields[0];
            String totalDeUsos = fields[1];
            Veiculo veiculo = new Veiculo(placa);
            mapVeiculos.put(placa, veiculo);
            System.out.println("LENDO DADOS DO ARQUIVO VEICULOS");
            System.out.print("Qual o id do dono do veiculo de placa: '" + placa + "'? ");
            String idCliente = scanner.nextLine();
            Cliente cliente = mapClientes.get(idCliente);
            cliente.addVeiculo(veiculo);
            estacionamento.addVeiculo(veiculo, cliente);
        }
    }
    public static String formatarMoeda(Double valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }

}