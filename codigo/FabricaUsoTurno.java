import java.time.LocalDateTime;

public class FabricaUsoTurno implements IFactory<UsoDeVaga> {

    @Override
    public UsoDeVaga create(Vaga vaga) {
        return new UsoTurno(vaga);
    }

    @Override
    public UsoDeVaga create(Vaga vaga, Servico servico) {
        UsoTurno uso = new UsoTurno(vaga, servico);
        return uso;
    }

    @Override
    public UsoDeVaga create(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        return new UsoTurno(vaga, entrada, saida, valorPago, servico);
    }
}
