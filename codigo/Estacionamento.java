import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Estacionamento {

    private String nome;
    private Map<String, Cliente> clientes = new HashMap<>(50);
    private PriorityQueue<Vaga> vagas = new PriorityQueue<>((Comparator.comparing(Vaga::disponivel).reversed()));
    private int quantFileiras;
    private int vagasPorFileira;

    /**
     * Construtor classe estacionamento
     * @param nome nome do estacionamento
     * @param fileiras quantidade de fileiras
     * @param vagasPorFila quantas vagaspor fila
     */
    public Estacionamento(String nome, int fileiras, int vagasPorFila) {
        this.nome = nome;
        this.quantFileiras = fileiras;
        this.vagasPorFileira = vagasPorFila;
        gerarVagas();
    }

    /**
     * Cadastra um veículo à um cliente previamente cadastrado no sitema do estacionamento;
     *
     * @param veiculo veículo a ser adicionado;
     * @param cliente string que identifica o cliente;
     */
    public void addVeiculo(Veiculo veiculo, Cliente cliente) {
        Cliente c = clientes.get(cliente.getId());
        c.addVeiculo(veiculo);
    }

    /**
     * Cadastra um cliente novo no estacionamento;
     *
     * @param cliente cliente novo a ser cadastrado.
     */
    public void addCliente(Cliente cliente) {
        clientes.put(cliente.getId(), cliente);
    }

    /**
     * Método que adiciona um cliente de turno ao estacionamento
     * @param cliente
     * @param turno
     */
    public void addCliente(Cliente cliente, TURNO turno) {
        clientes.put(cliente.getId(), cliente);
    }

    /**
     * Método que utiliza do número de fileiras e da quantidade de vagas por fileira para criar
     * automaticamente vagas com seus respectivos identificadores. Ex.: "A1";
     */
    private void gerarVagas() {
        for (int i = 0; i < quantFileiras; i++) {
            for (int j = 1; j <= vagasPorFileira; j++) {
                Vaga x = new Vaga(i, j);
                vagas.add(x);
            }
        }
    }

    /**
     * Esse método recebe um veiculo que deseja estecionar e caso tenha vagas disponíveis estaciona o veiculo na vaga em questão;
     *
     * @param placa String da placa do veículo que deseja estacionar em questão;
     */
    public boolean estacionar(String placa) {
        Veiculo veiculo = procuraVeiculo(placa);
        Vaga vaga = procuraVaga();

        if (vaga != null && veiculo != null) {
            veiculo.estacionar(vaga);
            return true;
        }
        return false;
    }

    /**
     * Esse método recebe um veiculo que deseja estecionar e um servico contratado
     * e caso tenha vagas disponíveis estaciona o veiculo na vaga em questão;
     *
     * @param placa String da placa do veículo que deseja estacionar em questão;
     */
    public boolean estacionar(String placa, Servico servico) {
        Veiculo veiculo = procuraVeiculo(placa);
        Vaga vaga = procuraVaga();

        if (vaga != null && veiculo != null) {
            veiculo.estacionar(vaga, servico);
            return true;
        }
        return false;
    }

    /**
     * Esse método procura alguma vaga disponível no estacionamento.
     *
     * @return a vaga caso ela esteja disponível, caso não tenha nenhuma vaga disponível retorna null
     */
    private Vaga procuraVaga() {
        return vagas.peek();
    }

    /**
     * Esse método tem como objetivo verificar se o veículo em questão já está cadastrado no cliente
     *
     * @param placa String da placa do veículo a ser verificado
     * @return retorna o veículo se o cliente já possuir o veículo cadastrado em questão, caso contrario retorna null.
     */
    private Veiculo procuraVeiculo(String placa) {
        return clientes.values()
                .stream()
                .map(cliente -> cliente.possuiVeiculo(placa))
                .filter(veiculo -> veiculo != null)
                .findFirst()
                .orElseThrow(() -> new PlacaNaoEncontradaException("Não existe veículo com essa placa!"));
    }

    /**
     * Esse método tem como objetivo pegar um veículo que esta estacionado retirá-lo da vaga e mostrar o tanto a ser cobrado;
     *
     * @param placa string da placa do veiculo que está saindo da vaga;
     * @return tira o veiculo do estacionamento e retorna o valor a ser pago;
     */
    public double sair(String placa) {
        Veiculo veiculo = procuraVeiculo(placa);
        if (veiculo != null)
            return veiculo.sair();
        return 0d;
    }

    /**
     * Esse método tem como objetivo ver o total arrecado pelo estacionamento;
     *
     * @return valor do total arrecadado pelo estacionamento;
     */
    public double totalArrecadado() {
        return clientes.values().stream()
                .mapToDouble(Cliente::arrecadadoTotal)
                .sum();
    }

    /**
     * Esse método recebe um mês como parametro para ser analisado e calcula o total de arrecadação no respectivo mês.
     *
     * @param mes , mes em questão a ser analisado;
     * @return total arrecadado no mês de interesse;
     */

    public double arrecadacaoNoMes(int mes) {
        return clientes.values().stream()
                .mapToDouble(c -> c.gastoNoMes(mes))
                .sum();
    }

    /**
     * Calcula a arrecadação total do estacionamento em um determinado mês para um tipo específico de cliente.
     * Este método itera sobre a lista de clientes, verifica se o cliente pertence ao tipo especificado
     * e, em caso afirmativo, adiciona o valor gasto por esse cliente no mês à arrecadação total.
     *
     * @param mes  Mês de interesse a ser analisado.
     * @param tipo Tipo de cliente para o qual a arrecadação será calculada.
     * @return O valor total arrecadado no estacionamento para o tipo de cliente especificado no mês.
     */
    public double arrecadacaoNoMesPorTipo(int mes, TipoCliente tipo) {
        return  clientes.values().stream()
                .filter(c -> c.getTipoCliente() == tipo)
                .mapToDouble(x -> x.gastoNoMes(mes))
                .sum();
    }

    /**
     * Esse método tem como objetivo pegar o total de usos dos clientes do estacionamente e o total de arrecadação dos clientes e
     * retornar o valor medio por uso.
     *
     * @return o valor medio por uso no estacionamento
     */
    public double valorMedioPorUso() {
        double sum = clientes.values().stream()
                .mapToDouble(Cliente::arrecadadoTotalDeUsos)
                .sum();
        double usos = clientes.values().stream()
                .mapToInt(Cliente::totalDeUsos)
                .sum();
        return sum / usos;
    }

    /**
     * Esse método recebe um mes como parametro e verifica quais clientes da Lista clientes utilizou o estacionamento no mes em
     * questão, cria uma nova lista organizada de maneira decrescete levando em consideração a arrecadação dos clientes no mes.
     *
     * @param mes , mes de interesse a ser analisado
     * @return retorna uma string com os 5 clientes que mais gastaram no estacionamento
     */

    public String top5Clientes(int mes) {
        List<Cliente> clienteDoMes = new ArrayList<>();

        for (Cliente x : clientes.values()) {
            if (x.gastoNoMes(mes) > 0) {
                clienteDoMes.add(x);
            }
        }
        clienteDoMes.sort(new Comparator<Cliente>() {
            public int compare(Cliente cliente1, Cliente cliente2) {
                double arrecadacao1 = cliente1.gastoNoMes(mes);
                double arrecadacao2 = cliente2.gastoNoMes(mes);

                return Double.compare(arrecadacao2, arrecadacao1);
            }
        });
        int maxClientes = Math.min(5, clienteDoMes.size());

        StringBuilder top5 = new StringBuilder();

        top5.append("Top 5 clientes do mês " + mes + " :\n");

        for (int i = 0; i < maxClientes; i++) {
            Cliente cliente = clienteDoMes.get(i);
            top5.append(cliente.gastoNoMesDoClienteFormatado(mes) + "\n");
        }
        return top5.toString();
    }

    /**
     * Calcula a quantidade total de usos do estacionamento em um determinado mês para um tipo específico de cliente.
     * Este método itera sobre a lista de clientes e chama o método totalDeUsosMes para cada cliente,
     * somando a quantidade de usos para o tipo de cliente especificado.
     *
     * @param mes  Mês de interesse a ser analisado.
     * @param tipo Tipo de cliente para o qual a quantidade de usos será calculada.
     * @return Um int contendo a quantidade total de usos do estacionamento para o tipo de cliente especificado no mês.
     */
    public int quantidadeUsosMes(int mes, TipoCliente tipo) {
        int qtdUsos = 0;
        for (Cliente x : clientes.values()) {
            qtdUsos += x.totalDeUsosMes(mes, tipo);
        }

        return qtdUsos;
    }

    public Map<String, Cliente> getClientes() {
        return Collections.unmodifiableMap(clientes);
    }
}