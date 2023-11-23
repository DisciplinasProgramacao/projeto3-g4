import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Cliente implements IDataToText {

    private String nome;
    private String id;
    private TipoCliente tipoCliente;
    private Map<String, Veiculo> veiculos;

    public Cliente(String nome, String id, TipoCliente tipoCliente) {
        this.nome = nome;
        this.id = id;
        this.tipoCliente = tipoCliente;
        this.veiculos = new HashMap<>(10);
    }

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
        veiculo.setPlano(this.tipoCliente);
        veiculos.put(veiculo.getPlaca(), veiculo);
    }


    /**
     * Metodo para verificar se o cliente possui um determiando veículo.
     *
     * @param placa Recebe placa como parâmetro para procurar, com o metodo equals, na lista de veiculos para ver se ele pertence ao cliente.
     * @return retorna o veículo se o cliente possuir o carro, caso contrário retorna null.
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
     * Metodo para calcular o total arrecadado pelo veiculo do cliente, cujo a placa será dada como parametro.
     *
     * @param placa recebe a placa como parametro para decidir qual veiculo sera pesquisado do cliente.
     * @return retorna o total arrecadado por veiculo (pesquisado pela placa) do cliente.
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
        valor += veiculos.values().stream()
                .filter(x -> x.getPlano() != TipoCliente.HORISTA)
                .mapToDouble(x -> x.getPlano().getMensalidade())
                .sum();
        return valor;
    }

    /**
     * Metodo retornar o historico (todos os dados) do cliente pelo mes.
     *
     * @param mes   recebe o mes desejado como parametro.
     * @param placa recebe a placa para pesquisar qual veiculo do cliente sera referido o relatorio.
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
            relatorio.append("Arrecadado pelo veiculo: ").append(placa).append(" ").append(arrecadadoPorVeiculo(placa)).append("\n");
            relatorio.append("Arrecadado Total: ").append(arrecadadoTotal()).append("\n");
        }

        return relatorio.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Cliente cliente = (Cliente) object;
        return Objects.equals(id, cliente.id);
    }

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

    @Override
    public String dataToText() {
        return id + ";" + nome + ";" + tipoCliente + ";";
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }
}