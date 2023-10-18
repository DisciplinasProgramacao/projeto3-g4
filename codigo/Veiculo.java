import java.util.ArrayList;
import java.util.List;

/**
 * Classe para guardar atributos e realizar ações referentes ao Veículo
 */
public class Veiculo {

    private String placa;
    private List<UsoDeVaga> usos;

    /**
     * Construtor para criar um veículo a partir de sua placa
     * @param placa
     */
    public Veiculo(String placa) {
        this.placa = placa;
        usos = new ArrayList<>();
    }

    /**
     * Se a vaga passada por parâmetro estiver disponível é adicionada
     * um uso de vaga para a lista de usos de vaga.
     * @param vaga
     */
    public void estacionar(Vaga vaga) {
        if (vaga.disponivel()) {
            usos.add(new UsoDeVaga(vaga));
        }
    }

    /**
     * Chama o metodo de sair da classe UsoDeVaga e retorna o valor pago para esse uso.
     * @return
     */
    public double sair() {
        int ultimaPosicao = usos.size() - 1;
        UsoDeVaga uso = usos.get(ultimaPosicao);
        uso.sair();
        return uso.valorPago();
    }

    /**
     * Calcula e retorna o total de valores de todos os usos de vaga.
     * @return totalArrecadado nos usos
     */
    public double totalArrecadado() {
        double totalPago = 0;
        for (UsoDeVaga uso : usos) {
            totalPago += uso.valorPago();
        }
        return totalPago;
    }

    /**
     * Calcula o total arrecado em um determinado mes
     * @param mes
     * @return totalArrecadado no mes
     */
    public double arrecadadoNoMes(int mes) {
        double arrecadacao = 0d;
        for (UsoDeVaga uso : usos) {
            if (uso.ehDoMes(mes)) {
                arrecadacao += uso.valorPago();
            }
        }
        return arrecadacao;
    }

    /**
     * Retorna o total de usos de vagas
     * @return totalDeUsos
     */
    public int totalDeUsos() {
        return usos.size();
    }

    public String getPlaca() {
        return placa;
    }
}


