import java.time.LocalDateTime;

public class UsoMensalista extends UsoDeVaga {

    public UsoMensalista(Vaga vaga) {
        super(vaga);
    }

    public UsoMensalista(Vaga vaga, Servico servico) {
        super(vaga, servico);
    }

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
