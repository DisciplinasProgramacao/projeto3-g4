import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class UsoHorista extends UsoDeVaga {
    private static final double FRACAO_USO = 0.25;
    private static final double VALOR_FRACAO = 4.0;
    private static final double VALOR_MAXIMO = 50.0;

    /**
     * Construtor classe usoHorista
     * @param vaga A vaga utilizada
     */
    public UsoHorista(Vaga vaga) {
        super(vaga);
    }

    /**
     * Construtor classe usoHorista
     * @param vaga A vaga utilizada
     * @param servico O serviço contratado
     */
    public UsoHorista(Vaga vaga, Servico servico) {
        super(vaga, servico);
        contratarServico(servico);
    }

    /**
     * Construtor classe UsoHorista
     * @param vaga A vaga utilizada
     * @param entrada Data e hora de entrada
     * @param saida Data e hora de saída
     * @param valorPago Valor pago pelo uso horista
     * @param servico O serviço contratado
     */
    public UsoHorista(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        super(vaga, entrada, saida, valorPago, servico);
    }

      /**
     * Método que calcula o valor para um uso de turno. 
     * @return Valor a ser pago.
     */
    @Override
    public double valorPago() {
        int calcTempo = (int) entrada.until(saida, ChronoUnit.MINUTES);
        if (calcTempo == 0)
            calcTempo = 1;
        int quantidadeFracoesTempo = (int) Math.ceil(calcTempo / 15.0);
        valorPago = quantidadeFracoesTempo * VALOR_FRACAO;

        if (valorPago > VALOR_MAXIMO)
            valorPago = VALOR_MAXIMO;

        return valorPago;
    }
}
