import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class UsoHorista extends UsoDeVaga {
    private static final double FRACAO_USO = 0.25;
    private static final double VALOR_FRACAO = 4.0;
    private static final double VALOR_MAXIMO = 50.0;

    public UsoHorista(Vaga vaga) {
        super(vaga);
    }

    public UsoHorista(Vaga vaga, Servico servico) {
        super(vaga, servico);
        contratarServico(servico);
    }

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
