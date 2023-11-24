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

    @Override
    public double valorPago() {
        return 0d;
    }
}
