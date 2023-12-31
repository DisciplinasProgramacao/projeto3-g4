import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe para guardar atributos e realizar ações referentes ao Veículo
 */
public class Veiculo implements IDataToText {

    private String placa;
    private List<UsoDeVaga> usos;
    private Planos tipoCliente;
    private UsoFactory usoFactory = new UsoFactory();

    /**
     * Construtor para criar um veículo a partir de sua placa
     *
     * @param placa do veiculo
     */
    public Veiculo(String placa) {
        this.placa = placa;
        usos = new LinkedList<>();
    }

    /**
     * Se a vaga passada por parâmetro estiver disponível é adicionada
     * um uso de vaga para a lista de usos de vaga.
     *
     * @param vaga
     */
    public void estacionar(Vaga vaga) {
        String desc = "";
        if (vaga.disponivel()) {
            vaga.setDisponivel(false);
            desc = tipoCliente.getDesc();
            switch ((TipoCliente) tipoCliente) {
                case HORISTA -> desc = "horista";
                case MENSALISTA -> desc = "mensalista";
                case DE_TURNO -> desc = "turno" + tipoCliente.getTurno().name();
            }
            UsoDeVaga uso = usoFactory.get(desc, vaga);
            usos.add(uso);
        } else {
            throw new IllegalStateException("Você não pode estacionar duas vezes na mesma vaga!");
        }
    }

    public void estacionar(Vaga vaga, Servico servico) {
        String desc = "";
        if (vaga.disponivel()) {
            vaga.setDisponivel(false);
            switch ((TipoCliente) tipoCliente) {
                case HORISTA -> desc = "horista";
                case MENSALISTA -> desc = "mensalista";
                case DE_TURNO -> desc = "turno" + tipoCliente.getTurno().name();
            }
            UsoDeVaga uso = usoFactory.get(desc, vaga, servico);
            usos.add(uso);
        } else {
            throw new IllegalStateException("Você não pode estacionar duas vezes na mesma vaga!");
        }
    }

    /**
     * Chama o metodo de sair da classe UsoDeVaga e retorna o valor pago para esse
     * uso.
     *
     * @return
     */
    public double sair() {
        int ultimaPosicao = usos.size() - 1;
        if (ultimaPosicao < 0) {
            throw new IllegalStateException("Você tem que estacionar o veículo antes de sair!");
        }
        UsoDeVaga uso = usos.get(ultimaPosicao);
        if (uso.getSaida() != null) {
            throw new IllegalStateException("Não e possivel sair duas vezes");
        }
        return uso.sair();
    }

    /**
     * Calcula e retorna o total de valores de todos os usos de vaga.
     *
     * @return totalArrecadado nos usos
     */
    public double totalArrecadado() {
        return usos.stream()
                .mapToDouble(UsoDeVaga::valorPago)
                .sum();
    }

    /**
     * Calcula o total arrecado em um determinado mes
     *
     * @param mes
     * @return totalArrecadado no mes
     */
    public double arrecadadoNoMes(int mes) {
        return usos.stream()
                .filter(u -> u.ehDoMes(mes))
                .mapToDouble(UsoDeVaga::valorPago)
                .sum();
    }

    /**
     * Retorna o total de usos de vagas
     *
     * @return totalDeUsos
     */
    public int totalDeUsos() {
        return usos.size();
    }

    /**
     * Retorna o total de usos de vagas por mensalistas em um mês específico
     *
     * @return qtdUsos
     */
    public int totalDeUsosMes(int mes) {
        int qtdUsos = 0;

        for (UsoDeVaga x : usos) {
            if (x.ehDoMes(mes)) {
                qtdUsos++;
            }
        }

        return qtdUsos;
    }

    /**
    * Método que imprime um relatório sobre usos de vaga de um veículo
    * @return relaótrio de usos
    */
    public String relatorioUsosDeVagaVeiculo() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("\n         RELATORIO DE USOS DE VAGA\n");
        relatorio.append("-------------------------------------------");
        relatorio.append("\nVeiculo de placa: " + placa + "\n");
        relatorio.append("Plano Atual do Cliente: " + tipoCliente.getDesc() + "\n");
        relatorio.append("-------------------------------------------\n");
        usos.stream()
                .sorted(Comparator.comparingDouble(u -> u.valorPago()))
                .forEach(u -> relatorio.append(u.relatorioDeUsoParaVeiculo()).append("\n").append("-------------------------------------------\n"));
        return relatorio.toString();
    }

    public String getPlaca() {
        return placa;
    }

    public List<UsoDeVaga> getUsos() {
        return usos;
    }

    @Override
    public String dataToText() {
        return placa + ";" + totalDeUsos() + ";" + formatUsos();
    }

    private String formatUsos() {
        StringBuilder builder = new StringBuilder();
        usos.forEach(builder::append);
        return builder.toString();
    }

    public void setPlano(Planos tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Planos getPlano() {
        if (tipoCliente == null) {
            throw new IllegalStateException("Veículo vazio: plano não definido.");
        }
        return tipoCliente;
    }

  /**
   * Método que adiciona um uso de vaga a um veículo
   * @param uso
   */
    public void addUsoDeVaga(UsoDeVaga uso) {
        usos.add(uso);
    }
}