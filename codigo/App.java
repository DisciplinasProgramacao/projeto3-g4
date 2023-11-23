import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

public class App {
    static Estacionamento estacionamento = null;
    static int selecionado = 0;
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
        return estacionamento != null;
    }

    public static StringBuilder retornaArrecadacaoTotalEstacionamentos(){
        StringBuilder retorno = new StringBuilder();
        int atual = 1;
        double valor = 0;
        Collections.sort(estacionamentosAletorios, Comparator.comparingDouble(Estacionamento::totalArrecadado).reversed());

        for (Estacionamento x : estacionamentosAletorios) {

            double valorDeCada = x.totalArrecadado();

            valor += valorDeCada;

            retorno.append(" O Estacionamento ").append(atual).append(" Arrecadou R$").append(valorDeCada);

            atual ++;
        }

        retorno.append("E o total arrecadado pelos 3 foi de " + valor);

        return retorno;
    };

    public static int usoMensalistasMes(String mes){
        // método no estacionamento pegando o método totalDeUsos dos clientes mensalistas

        return 0;
    };

    public static double arrecadacaoMediaHoristas(String mes){
        // método parecido com o abaixo porém considerando apenas horistas
        return estacionamento.valorMedioPorUso();
    };

    public static void cadastrarSubMenu(Scanner scanner) {
        int subEscolha;
        do {
            if(validaEstacionamento()){
                System.out.println("Estacionamento selecionado -> " + selecionado);
            }
            System.out.println("Selecione o que deseja cadastrar:");
            System.out.println("1 - Selecionar Estacionamento");
            System.out.println("2 - Cadastrar Cliente");
            System.out.println("3 - Cadastrar Veículo");
            System.out.println("4 - Voltar ao menu principal");
            System.out.print("Digite o número da opção desejada: ");
            subEscolha = scanner.nextInt();

            switch (subEscolha) {
                case 1:
                    System.out.println("Em qual estacionamento você deseja realizar operações? (1, 2 ou 3).");
                    int num = scanner.nextInt();

                    selecionado = num;

                    estacionamento = estacionamentosAletorios.get(num - 1);
                    break;
                case 2:
                    System.out.println("Opção Cadastrar Cliente selecionada.");
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não selecionado.");
                        break;
                    } else {
                        scanner.nextLine();
                        System.out.print("Digite o nome do cliente: ");
                        String nome = scanner.nextLine();

                        System.out.print("Cliente será de qual tipo(MENSALISTA/HORISTA/DE_TURNO)? ");
                        TipoCliente tipoCliente = TipoCliente.valueOf(scanner.nextLine());

                        Cliente cliente = new Cliente(nome, String.valueOf(idClientes), tipoCliente);

                        DAOCliente daoCliente = new DAOCliente("clientes.txt");
                        try {
                            daoCliente.abrirEscrita();
                            daoCliente.add(cliente);
                            daoCliente.fechar();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }

                        estacionamento.addCliente(cliente);
                        mapClientes.put(String.valueOf(idClientes), cliente);

                        System.out.print("Cliente cadastrado, seu id é: " + idClientes + "\n");

                        idClientes++;
                    }
                    break;
                case 3:
                    System.out.println("Opção Cadastrar Veículo selecionada.");
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não selecionado.");
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
                        Cliente cliente = mapClientes.get(id);
                        estacionamento.addVeiculo(veiculo, cliente);
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
            if(validaEstacionamento()){
                System.out.println("Estacionamento selecionado -> " + selecionado);
            }
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
                        System.out.println("Estacionamento não selecionado.");
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
                        System.out.println("Estacionamento não selecionado.");
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
            if(validaEstacionamento()){
                System.out.println("Estacionamento selecionado -> " + selecionado);
            }
            System.out.println("Selecione a operação de cliente:");
            System.out.println("1 - Total arrecadado");
            System.out.println("2 - Arrecadação no mês");
            System.out.println("3 - Valor Médio por uso");
            System.out.println("4 - Top 5 Clientes");
            System.out.println("5 - Total arrecadado por todos estacionamentos");
            System.out.println("6 - Média de vezes que os mensalistas usaram o estacionamento em um mês");
            System.out.println("7 - Média de arrecadação gerada por horistas em um mês");
            System.out.println("8 - Voltar ao menu principal");
            System.out.print("Digite o número da opção desejada: ");
            subEscolha = scanner.nextInt();

            switch (subEscolha) {
                case 1 -> {
                    System.out.println("Opção retorno do total arrecadado.");
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não selecionado.");
                    } else {
                        double total = estacionamento.totalArrecadado();

                        System.out.println("O total arrecadado pelo estacionamento foi de " + formatarMoeda(total));
                    }
                }
                case 2 -> {
                    System.out.println("Opção retorno do total arrecadado no mês.");
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não selecionado.");
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
                        System.out.println("Estacionamento não selecionado.");
                    } else {
                        double valorMedio = estacionamento.valorMedioPorUso();

                        System.out.println("O valor medio por uso foi de: " + formatarMoeda(valorMedio));
                    }
                }
                case 4 -> {
                    System.out.println("Opção top 5 clientes.");
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não selecionado.");
                    } else {
                        scanner.nextLine();
                        System.out.print("Qual mês você deseja conferir: ");
                        String mes = scanner.nextLine();

                        String clientes = estacionamento.top5Clientes(Integer.parseInt(mes));

                        System.out.println("Os top 5 clientes são." + clientes);
                    }
                }
                case 5 -> {
                    System.out.println(retornaArrecadacaoTotalEstacionamentos());
                }
                case 6 -> {
                    System.out.print("Qual mês você deseja conferir: ");
                    String mes = scanner.nextLine();

                    int usos = usoMensalistasMes(mes);

                    System.out.println("Em média, os mensalistas utilizaram o estacionamento " + usos + " vezes em " + mes);
                }
                case 7 -> {
                    System.out.print("Qual mês você deseja conferir: ");
                    String mes = scanner.nextLine();

                    double arrecadado = arrecadacaoMediaHoristas(mes);

                    System.out.println("Em média, a arrecadação média dos horistas em " + mes + " foi de R$" + arrecadado);
                }
                case 8 -> System.out.println("Voltando ao menu principal.");
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
            estacionamento.addVeiculo(veiculo, cliente);
        }
    }
    public static String formatarMoeda(Double valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }

}