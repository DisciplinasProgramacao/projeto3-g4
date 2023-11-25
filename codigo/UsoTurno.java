import java.time.LocalDateTime;
import java.time.LocalTime;

public class UsoTurno extends UsoDeVaga {

    private TURNO turno;
    private UsoHorista horista;

    public UsoTurno(Vaga vaga, Servico servico, TURNO turno) {
        super(vaga, servico);
        this.horista = new UsoHorista(vaga, servico);
        this.turno = turno;
    }

    public UsoTurno(Vaga vaga, TURNO turno) {
        super(vaga);
        this.turno = turno;
        this.horista = new UsoHorista(vaga, servico);
    }

    public UsoTurno(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico, TURNO turno) {
        super(vaga, entrada, saida, valorPago, servico);
        this.turno = turno;
    }

    @Override
    public double valorPago() {
        LocalTime horaAtual = LocalTime.now();

        if (ehDoTurno(horaAtual)) {
            return 0d;
        } else {
            return horista.sair() - servico.getValor();
        }
    }

    public boolean ehDoTurno(LocalTime momento) {
        boolean dentroDoTurno = false;

        if (momento.isAfter(turno.getHoraInicial()) && momento.isBefore(turno.getHoraFinal())) {
            dentroDoTurno = true;
        }
        return dentroDoTurno;
    }

    public void setTurno(TURNO turno) {
        this.turno = turno;
    }
}
