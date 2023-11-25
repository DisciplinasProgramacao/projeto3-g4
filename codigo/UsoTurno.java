import java.time.LocalDateTime;
import java.time.LocalTime;

public class UsoTurno extends UsoDeVaga {

    private TURNO turno;
    private UsoHorista horista;

    public UsoTurno(Vaga vaga, Servico servico) {
        super(vaga, servico);
        this.horista = new UsoHorista(vaga, servico);
        this.turno = TURNO.MANHA;
    }

    public UsoTurno(Vaga vaga, TURNO turno) {
        super(vaga);
        this.turno = turno;
        this.horista = new UsoHorista(vaga, servico);
    }

    public UsoTurno(Vaga vaga) {
        super(vaga);
        this.horista = new UsoHorista(vaga, servico);
        this.turno = TURNO.NOITE;
    }

    public UsoTurno(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        super(vaga, entrada, saida, valorPago, servico);
        this.turno = TURNO.NOITE;
    }

    @Override
    public double valorPago() {
        LocalTime horaAtual = LocalTime.now();

        if (ehDoTurno(horaAtual)) {
            return 0d; // Está dentro do turno, não há cobrança extra.
        } else {
            return horista.sair(); // Fora do turno, usa a lógica de cobrança horista.
        }
    }

    public boolean ehDoTurno(LocalTime momento) {
        boolean dentroDoTurno = false;

        if (momento.isAfter(turno.getHoraInicial()) && momento.isBefore(turno.getHoraFinal())) {
            dentroDoTurno = true;
        }
        return dentroDoTurno;
    }
}
