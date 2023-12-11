import java.util.LinkedList;
import java.util.List;

/**
 * Classe para guardar atributos e realizar ações referentes ao Veículo
 */
public class Veiculo implements IDataToText {

    private String placa;
    private List<UsoDeVaga> usos;
    private Planos tipoCliente;

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
        if (vaga.disponivel()) {
            vaga.setDisponivel(false);
            switch (tipoCliente) {
                case HORISTA -> usos.add(new UsoHorista(vaga));
                case MENSALISTA -> usos.add(new UsoMensalista(vaga));
                case DE_TURNO -> usos.add(new UsoTurno(vaga, tipoCliente.getTurno()));
            }
        }
    }

    public void estacionar(Vaga vaga, Servico servico) {
        if (vaga.disponivel()) {
            vaga.setDisponivel(false);
            switch (tipoCliente) {
                case HORISTA -> usos.add(new UsoHorista(vaga, servico));
                case MENSALISTA -> usos.add(new UsoMensalista(vaga, servico));
                case DE_TURNO -> usos.add(new UsoTurno(vaga, servico, tipoCliente.getTurno()));
            }
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
    public int totalDeUsosMes(int mes){
        int qtdUsos = 0;

        for (UsoDeVaga x : usos) {
            if(x.ehDoMes(mes)){
                qtdUsos++;
            }
        }

        return qtdUsos;
    };

    public String getPlaca() {
        return placa;
    }

    public List<UsoDeVaga> getUsos() {
        return usos;
    }

    @Override
    public String dataToText() {
        return placa + ";" + totalDeUsos() + ";"  + formatUsos();
    }

    private String formatUsos() {
        StringBuilder builder = new StringBuilder();
        usos.forEach(uso -> builder.append(uso));
        return builder.toString();
    }

    public void setPlano(Planos tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Planos getPlano() {
        return tipoCliente;
    }

    public void addUsoDeVaga(UsoDeVaga uso) {
        usos.add(uso);
    }
}
