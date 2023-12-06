import java.time.LocalDateTime;

public class FabricaUsoMensalista implements IFactory<UsoDeVaga> {
    @Override
    public UsoDeVaga create(Vaga vaga) {
        return new UsoMensalista(vaga);
    }

    @Override
    public UsoDeVaga create(Vaga vaga, Servico servico) {
        return new UsoMensalista(vaga, servico);
    }

    @Override
    public UsoDeVaga create(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        return new UsoMensalista(vaga, entrada, saida, valorPago, servico);
    }
}
