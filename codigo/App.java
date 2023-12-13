import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class App {
    static Estacionamento estacionamento = null;
    static int selecionado = 0;
    static List<Estacionamento> estacionamentosAletorios = new ArrayList<>();
    static Long idClientes = 1l;

    private static Map<String, Cliente> mapClientes = new HashMap<>();
    private static Map<String, Veiculo> mapVeiculos = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        GerarDados();

        int escolha;

        do {
            try {
                System.out.println("Selecione uma das opções:");
                if (validaEstacionamento()) {
                    System.out.println("1 - Cadastrar");
                } else {
                    System.out.println("1 - Cadastrar (selecione o estacionamento a ser analisado)");
                }
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

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
                scanner.next();
                escolha = 0;
            }
        } while (escolha != 4);

        scanner.close();
    }

    public static boolean validaEstacionamento() {
        return estacionamento != null;
    }

    public static StringBuilder retornaArrecadacaoTotalEstacionamentos() {
        StringBuilder retorno = new StringBuilder();
        int atual = 1;
        double valor = 0;
        estacionamentosAletorios.sort(Comparator.comparingDouble(Estacionamento::totalArrecadado).reversed());

        for (Estacionamento x : estacionamentosAletorios) {

            double valorDeCada = x.totalArrecadado();

            valor += valorDeCada;

            retorno.append(" O Estacionamento ").append(atual).append(" Arrecadou ").append(formatarMoeda(valorDeCada))
                    .append("\n");

            atual++;
        }

        retorno.append("E o total arrecadado pelos 3 foi de " + formatarMoeda(valor));

        return retorno;
    }

    ;

    public static int usoMensalistasMes(int mes) {
        return estacionamento.quantidadeUsosMes(mes, TipoCliente.MENSALISTA);
    }

    ;

    public static double arrecadacaoMediaHoristas(int mes) {
        return estacionamento.arrecadacaoNoMesPorTipo(mes, TipoCliente.HORISTA);
    }

    ;

    public static void cadastrarSubMenu(Scanner scanner) throws FileNotFoundException {
        int subEscolha;
        do {
            if (validaEstacionamento()) {
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
                    System.out.print("Em qual estacionamento você deseja realizar operações(1, 2 ou 3)? ");
                    int num = scanner.nextInt();

                    selecionado = num;

                    estacionamento = estacionamentosAletorios.get(num - 1);

                    lerDadosArquivoClientes();
                    lerDadosArquivoVeiculos(scanner);

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
                        TipoCliente tipoCliente = null;

                        try {
                            tipoCliente = TipoCliente.valueOf(scanner.nextLine().toUpperCase());

                        } catch (IllegalArgumentException e) {
                            System.out.println(
                                    "Tipo de cliente inválido. Escolha entre MENSALISTA, HORISTA ou DE_TURNO.");
                            continue;
                        }

                        Cliente cliente = new Cliente(nome, String.valueOf(idClientes), tipoCliente);

                        if (tipoCliente == TipoCliente.DE_TURNO) {
                            System.out.print("Qual turno você gostaria de escolher(manhã, tarde ou noite)? ");
                            TURNO turno = TURNO.valueOf(scanner.next().toUpperCase());
                            System.out.println("Turno " + turno + " escolhido!");
                            tipoCliente.setTurno(turno);
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
                        try {
                            Cliente cliente = mapClientes.get(id);
                            mapVeiculos.put(placa, veiculo);
                            estacionamento.addVeiculo(veiculo, cliente);

                            DAOCliente daoCliente = new DAOCliente("clientes.txt");

                            daoCliente.abrirEscrita();
                            daoCliente.add(cliente);
                            daoCliente.fechar();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        } catch (NullPointerException e) {
                            System.out.println("Não foi possível cadastrar veículo sem clientes cadastrados.");
                        }
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
            if (validaEstacionamento()) {
                System.out.println("Estacionamento selecionado -> " + selecionado);
            }
            System.out.println("Selecione a operação de veículo:");
            System.out.println("1 - Estacionar Veículo");
            System.out.println("2 - Sair da Vaga com Veículo");
            System.out.println("3 - Voltar ao menu principal");
            System.out.println("4 - Relatorio de usos de vaga do veiculo");
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
                            System.out.print("Qual serviço você gostaria de contratar(MANOBRISTA/LAVAGEM/POLIMENTO)? ");
                            String opcaoServico = scanner.nextLine().toUpperCase();

                            try {
                                Servico servico = Servico.valueOf(opcaoServico);
                                estacionamento.estacionar(placa, servico);
                                System.out.println("Serviço de " + servico.getNome() + " contratado!");
                                System.out.println("Veículo de placa: " + placa + " estacionado!");
                            } catch (IllegalStateException | PlacaNaoEncontradaException e) {
                                System.out.println(e.getMessage());
                            } catch (IllegalArgumentException e) {
                                System.out.println("Serviço inválido. Escolha entre MANOBRISTA, LAVAGEM ou POLIMENTO.");
                            }
                        } else if (opcao.trim().equalsIgnoreCase("n")) {
                            try {
                                estacionamento.estacionar(placa);
                                System.out.println("Veículo de placa: " + placa + " estacionado!");
                            } catch (IllegalStateException | PlacaNaoEncontradaException e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            System.out.println("Comando inválido. Digite s ou n.");
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

                        double valorAPagar = 0; // Inicializando com um valor padrão
                        try {
                            valorAPagar = estacionamento.sair(placa);
                            Veiculo veiculo = mapVeiculos.get(placa);
                            DAOVeiculo daoVeiculo = new DAOVeiculo("veiculos.txt");
                            try {
                                daoVeiculo.abrirEscrita();
                                daoVeiculo.add(veiculo);
                                daoVeiculo.fechar();
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            }
                            System.out.println("Cliente " + veiculo.getPlano().getDesc() + " - Valor a ser pago: "
                                    + formatarMoeda(valorAPagar));
                        } catch (IllegalStateException | PlacaNaoEncontradaException e) {
                            System.out.println("Ocorreu um erro ao sair do estacionamento: " + e.getMessage());
                        }

                    }
                }
                case 3 -> System.out.println("Voltando ao menu principal.");
                case 4 -> {
                    System.out.print("Qual a placa do veiculo? ");
                    String placa = scanner.next();
                    try {
                        Veiculo veiculo = mapVeiculos.get(placa);
                        System.out.println(veiculo.relatorioUsosDeVagaVeiculo());
                    } catch (NullPointerException e) {
                        System.out.println("Nenhum veículo com essa placa!");
                    }
                }
                default -> System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (subEscolha != 3);
    }

    public static void operacoesEstacionamentoSubMenu(Scanner scanner) {
        int subEscolha;
        do {
            if (validaEstacionamento()) {
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
                        int mesInt;
                        do {
                            System.out.print("Qual mês você deseja conferir (1 a 12): ");
                            String mes = scanner.nextLine();
                            try {
                                mesInt = Integer.parseInt(mes);
                                if (mesInt < 1 || mesInt > 12) {
                                    throw new NumberFormatException();
                                }
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Mês inválido. Insira um número de 1 a 12.");
                            }
                        } while (true);

                        double totalMes = estacionamento.arrecadacaoNoMes(mesInt);

                        System.out.println("O total arrecadado pelo estacionamento no mês " + mesInt);
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
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não selecionado.");
                    } else {
                        System.out.print("Qual mês você deseja conferir: ");
                        int mes = scanner.nextInt();

                        int usos = usoMensalistasMes(mes);

                        System.out.println("Em média, os mensalistas utilizaram o estacionamento " + usos
                                + " vezes no mês " + mes);
                    }
                }
                case 7 -> {
                    if (!validaEstacionamento()) {
                        System.out.println("Estacionamento não selecionado.");
                    } else {
                        System.out.print("Qual mês você deseja conferir: ");
                        int mes = scanner.nextInt();

                        double arrecadado = arrecadacaoMediaHoristas(mes);

                        System.out.println("Em média, a arrecadação média dos horistas no mês " + mes + " foi de "
                                + formatarMoeda(arrecadado));
                    }
                }
                case 8 -> System.out.println("Voltando ao menu principal.");
                default -> System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (subEscolha != 8);
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
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Nao foi possivel criar o arquivo. " + e.getMessage());
            }
        }
        Scanner fileReader = new Scanner(new FileReader("clientes.txt"));
        while (fileReader.hasNext()) {
            String[] fields = fileReader.nextLine().split(";");
            String id = fields[0];
            String nome = fields[1];
            String tipoCliente = fields[2];
            Cliente cliente = new Cliente(nome, id, TipoCliente.valueOf(tipoCliente));
            mapClientes.put(id, cliente);
            estacionamento.addCliente(cliente);
            for (int i = 3; i < fields.length; i++) {
                String placa = fields[i];
                Veiculo veiculo = new Veiculo(placa);
                veiculo.setPlano(TipoCliente.valueOf(tipoCliente));
                mapVeiculos.put(placa, veiculo);
                estacionamento.addVeiculo(veiculo, cliente);
            }
            idClientes++;
        }
    }

    private static void lerDadosArquivoVeiculos(Scanner scanner) throws FileNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        File file = new File("veiculos.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Nao foi possivel criar o arquivo. " + e.getMessage());
            }
        }
        Scanner fileReader = new Scanner(new FileReader("veiculos.txt"));

        while (fileReader.hasNext()) {
            String[] fields = fileReader.nextLine().split(";");
            String placa = fields[0];
            int totalDeUsos = Integer.parseInt(fields[1]);

            int iArq = 2;
            if (totalDeUsos > 1) {
                iArq += 5 * (totalDeUsos - 1);
            }

            String idVaga = fields[iArq];
            iArq++;
            LocalDateTime entrada = LocalDateTime.parse(fields[iArq], formatter);
            iArq++;
            LocalDateTime saida = LocalDateTime.parse(fields[iArq], formatter);
            iArq++;
            double valorPago = Double.parseDouble(fields[iArq]);
            iArq++;

            String servicoStr = fields[iArq];
            Servico servico = servicoStr.equals("null") ? null : Servico.valueOf(servicoStr.toUpperCase());
            iArq++;
            try {
                Veiculo veiculo = mapVeiculos.get(placa);

                Vaga vaga = new Vaga(idVaga);

                // if (veiculo == null){
                // System.out.println("Arquivo Vazio, Realize o Cadastro");
                // break;
                // }

                TipoCliente plano = veiculo.getPlano();
                plano.setTurno(TURNO.NOITE);

                UsoFactory usoFactory = new UsoFactory();
                String desc = "";
                switch (plano) {
                    case HORISTA -> desc = "horista";
                    case MENSALISTA -> desc = "mensalista";
                    case DE_TURNO -> desc = "turno" + plano.getTurno().name();
                }
                UsoDeVaga uso = usoFactory.get(desc, vaga, entrada, saida, valorPago, servico);
                veiculo.addUsoDeVaga(uso);

                mapVeiculos.put(placa, veiculo);
            } catch (NullPointerException e) {
                System.out.println("Nenhum veículo com essa placa!");
            }
        }
    }

    public static String formatarMoeda(Double valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }

}