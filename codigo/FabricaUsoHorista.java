import java.time.LocalDateTime;

public class FabricaUsoHorista implements IFactory<UsoDeVaga> {
    @Override
    public UsoDeVaga create(Vaga vaga) {
        return new UsoHorista(vaga);
    }

    @Override
    public UsoDeVaga create(Vaga vaga, Servico servico) {
        return new UsoHorista(vaga, servico);
    }

    @Override
    public UsoDeVaga create(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        return new UsoHorista(vaga, entrada, saida, valorPago, servico);
    }
}
