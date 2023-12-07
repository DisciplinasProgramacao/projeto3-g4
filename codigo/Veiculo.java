import java.util.LinkedList;
import java.util.List;

/**
 * Classe para guardar atributos e realizar ações referentes ao Veículo
 */
public class Veiculo implements IDataToText {

    private String placa;
    private List<UsoDeVaga> usos;
    private TipoCliente tipoCliente;

    /**
     * Construtor para criar um veículo a partir de sua placa
     *
     * @param placa
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
        UsoFactory usoFactory = new UsoFactory();
        String desc = "";
        if (vaga.disponivel()) {
            vaga.setDisponivel(false);
            switch (tipoCliente) {
                case HORISTA -> desc = "horista";
                case MENSALISTA -> desc = "mensalista";
                case DE_TURNO -> desc = "turno" + tipoCliente.getTurno().name();
            }
            UsoDeVaga uso = usoFactory.get(desc, vaga);
            usos.add(uso);
        }
    }

    public void estacionar(Vaga vaga, Servico servico) {
        UsoFactory usoFactory = new UsoFactory();
        String desc = "";
        if (vaga.disponivel()) {
            vaga.setDisponivel(false);
            switch (tipoCliente) {
                case HORISTA -> desc = "horista";
                case MENSALISTA -> desc = "mensalista";
                case DE_TURNO -> desc = "turno" + tipoCliente.getTurno().name();
            }
            UsoDeVaga uso = usoFactory.get(desc, vaga, servico);
            usos.add(uso);
        }
    }

    /**
     * Chama o metodo de sair da classe UsoDeVaga e retorna o valor pago para esse uso.
     *
     * @return
     */
    public double sair() {
        int ultimaPosicao = usos.size() - 1;
        UsoDeVaga uso = usos.get(ultimaPosicao);
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

    public void setPlano(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public TipoCliente getPlano() {
        return tipoCliente;
    }

    public void addUsoDeVaga(UsoDeVaga uso) {
        usos.add(uso);
    }
}