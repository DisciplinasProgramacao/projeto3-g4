import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Cliente implements IDataToText {

    private String nome;
    private String id;
    private Planos tipoCliente;
    private Map<String, Veiculo> veiculos;

    /**
     * Construtor classe Cliente
     *
     * @param nome        nome do cliente
     * @param id          id do cliente
     * @param tipoCliente tipo do cliente
     */
    public Cliente(String nome, String id, Planos tipoCliente) {
        this.nome = nome;
        this.id = id;
        this.tipoCliente = tipoCliente;
        this.veiculos = new HashMap<>(10);
    }

    /**
     * Constutor classe Cliente
     *
     * @param nome nome do cliente
     * @param id   id do cliente
     */
    public Cliente(String nome, String id) {
        this.nome = nome;
        this.id = id;
        this.tipoCliente = TipoCliente.HORISTA;
        this.veiculos = new HashMap<>(10);
    }


    /**
     * Metodo para adicionar/cadastrar um veiculo ao cliente.
     *
     * @param veiculo Recebe veiculo como parâmetro para vincular ao cliente.
     */
    public void addVeiculo(Veiculo veiculo) {
        try {
            if (possuiVeiculo(veiculo.getPlaca()) == null) {
                veiculo.setPlano(this.tipoCliente);
                veiculos.put(veiculo.getPlaca(), veiculo);
            } else {
                throw new IllegalArgumentException("Veículo já cadastrado para este cliente");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo para verificar se o cliente possui um determiando veículo.
     *
     * @param placa Recebe placa como parâmetro para procurar, com o metodo equals,
     *              na lista de veiculos para ver se ele pertence ao cliente.
     * @return retorna o veículo se o cliente possuir o carro, caso contrário
     * retorna null.
     */
    public Veiculo possuiVeiculo(String placa) {
        return veiculos.get(placa);
    }

    /**
     * Metodo para verificar o tatal de usos do cliente no estacionamento.
     *
     * @return retorna o total de usos de todos os veiculos do cliente.
     */
    public int totalDeUsos() {
        return veiculos.values().stream()
                .mapToInt(Veiculo::totalDeUsos)
                .sum();
    }

    /**
     * Metodo para verificar o tatal de usos do cliente no estacionamento em um mês
     * específico.
     *
     * @return retorna o total de usos de todos os veiculos do cliente em um mês
     * específico.
     */
    public int totalDeUsosMes(int mes, TipoCliente tipo) {
        int qtdUsos = 0;
        if (tipoCliente == tipo) {
            qtdUsos += veiculos.entrySet().stream()
                    .mapToInt(entry -> entry.getValue().totalDeUsosMes(mes))
                    .sum();
        }
        return qtdUsos;
    }

    ;

    /**
     * Metodo para calcular o total arrecadado pelo veiculo do cliente, cujo a placa
     * será dada como parametro.
     *
     * @param placa recebe a placa como parametro para decidir qual veiculo sera
     *              pesquisado do cliente.
     * @return retorna o total arrecadado por veiculo (pesquisado pela placa) do
     * cliente.
     */
    public double arrecadadoPorVeiculo(String placa) {
        Veiculo veiculo = veiculos.get(placa);
        return veiculo.totalArrecadado();
    }

    /**
     * Metodo para calcular o total arrecadado por todos os veiculos de um cliente.
     *
     * @return retorna o total arrecadado pelo cliente, de todos os veiculos.
     */
    public double arrecadadoTotal() {
        double valor = veiculos.values().stream()
                .mapToDouble(Veiculo::totalArrecadado)
                .sum();
        valor += tipoCliente.getMensalidade();
        return valor;
    }

    /**
     * Metodo para calcular o total arrecadado por todos os veiculos de um cliente
     * considerando apenas usos de vaga.
     *
     * @return retorna o total arrecadado pelo cliente, de todos os veiculos em usos de vagas.
     */
    public double arrecadadoTotalDeUsos() {
        return veiculos.values().stream()
                .mapToDouble(Veiculo::totalArrecadado)
                .sum();
    }

    /**
     * Metodo para calcular o total arrecadado no mes pelo cliente.
     *
     * @param mes recebe o mes desejado como parametro.
     * @return retorna o total arrecadado pela data do cliente.
     */
    public double gastoNoMes(int mes) {
        double valor = veiculos.values().stream()
                .mapToDouble(v -> v.arrecadadoNoMes(mes))
                .sum();
        valor += tipoCliente.getMensalidade();
        return valor;
    }

    /**
     * Metodo retornar o historico (todos os dados) do cliente pelo mes.
     *
     * @param mes   recebe o mes desejado como parametro.
     * @param placa recebe a placa para pesquisar qual veiculo do cliente sera
     *              referido o relatorio.
     * @return retorna um string builder com o relatorio do cliente.
     */
    public String pesquisarHistorico(int mes, String placa) {
        StringBuilder relatorio = new StringBuilder();

        Veiculo buscando = veiculos.get(placa);

        if (buscando != null) {
            List<UsoDeVaga> usos = buscando.getUsos();
            for (UsoDeVaga uso : usos) {
                if (uso.ehDoMes(mes)) {
                    relatorio.append("Placa: ").append(placa).append("\n");
                    relatorio.append("Data: ").append(mes).append("\n");
                    relatorio.append("Valor Pago: ").append(uso.valorPago()).append("\n");
                    relatorio.append("--------------").append("\n");
                }
            }
            relatorio.append("Total de Uso: ").append(totalDeUsos()).append("\n");
            relatorio.append("Arrecadado pelo veiculo: ").append(placa).append(" ").append(arrecadadoPorVeiculo(placa))
                    .append("\n");
            relatorio.append("Arrecadado Total: ").append(arrecadadoTotal()).append("\n");
        }

        return relatorio.toString();
    }

    /**
     * Método equals que analisa se um cliente é iogual a outro
     *
     * @param object cliente
     * @return true se o cliente é igual ao outro, false se o cliente for diferente.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Cliente cliente = (Cliente) object;
        return Objects.equals(id, cliente.id);
    }

    /**
     * Retorna um valor hash baseado no id do cliente.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + " - Id: " + id;
    }

    /**
     * Retorna o gasto total no mes do cliente formatado com seu id e nome.
     *
     * @param mes
     * @return String
     */
    public String gastoNoMesDoClienteFormatado(int mes) {
        return "Id: " + id + " - Nome: " + nome + " - Valor gasto no mês " + mes + " : R$ " + gastoNoMes(mes);
    }

    /**
     * Metodo responsavel por converter os dados em uma String formatada
     * incluindo id, nome, tipoCliente e veiculos formatados
     */
    @Override
    public String dataToText() {
        return id + ";" + nome + ";" + tipoCliente + ";" + formatVeiculos();
    }

    /**
     * Formata as informacoes do veiculo e seus usos para salvar no arquivo
     *
     * @return
     */
    private String formatVeiculos() {
        StringBuilder builder = new StringBuilder();
        veiculos.values().forEach(v -> builder.append(v.getPlaca()).append(";"));
        return builder.toString();
    }

    /**
     * Metodo responsavel por obter o tipo do cliente associado a instancia da classe Planos
     *
     * @return o tipo do cliente
     */
    public Planos getTipoCliente() {
        return tipoCliente;
    }

    /**
     * Método que troca o tipo do cliente
     *
     * @param tipoCliente
     */
    public void TrocarPlano(Planos tipoCliente) {
        if (this.tipoCliente == tipoCliente)
            throw new IllegalArgumentException("O cliente " + nome + " já utiliza o plano  " + tipoCliente.getDesc());
        this.tipoCliente = tipoCliente;
        veiculos.values().forEach(v -> v.setPlano(tipoCliente));
    }
}