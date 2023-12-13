import java.time.LocalDateTime;

public class UsoMensalista extends UsoDeVaga {

    /**
     * Construtor classe UsoMensalista
     * @param vaga A vaga utilizada
     */
    public UsoMensalista(Vaga vaga) {
        super(vaga);
    }

     /**
     * Construtor classe UsoMensalista
     * @param vaga A vaga utilizada
     * @param servico O serviço contratado
     */
    public UsoMensalista(Vaga vaga, Servico servico) {
        super(vaga, servico);
    }

    /**
     * Construtor classe UsoMensalista
     * @param vaga A vaga utilizada
     * @param entrada Data e hora de entrada
     * @param saida Data e hora de saída
     * @param valorPago Valor pago pelo uso mensalista
     * @param servico O serviço contratado
     */
    public UsoMensalista(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        super(vaga, entrada, saida, valorPago, servico);
    }

     /**
     * Método que calcula o valor para um uso de turno. 
     * @return Valor a ser pago.
     */
    @Override
    public double valorPago() {
        return 0d;
    }
}
