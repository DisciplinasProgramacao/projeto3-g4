import java.time.LocalDateTime;

public class FabricaUsoTurno implements IFactory<UsoDeVaga> {

    private TURNO turno;

    public FabricaUsoTurno(TURNO turno) {
        this.turno = turno;
    }

    @Override
    public UsoDeVaga create(Vaga vaga) {
        UsoTurno uso = new UsoTurno(vaga);
        uso.setTurno(turno);
        return uso;
    }

    @Override
    public UsoDeVaga create(Vaga vaga, Servico servico) {
        UsoTurno uso = new UsoTurno(vaga, servico);
        uso.setTurno(turno);
        return uso;
    }

    @Override
    public UsoDeVaga create(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        UsoTurno uso = new UsoTurno(vaga, entrada, saida, valorPago, servico);
        uso.setTurno(turno);
        return uso;
    }
}
