import java.time.LocalDateTime;
import java.time.LocalTime;

public class UsoTurno extends UsoDeVaga {

    private TURNO turno;
    private UsoHorista horista;

    public UsoTurno(Vaga vaga, Servico servico) {
        super(vaga, servico);
        this.horista = new UsoHorista(vaga, servico);
    }

    public UsoTurno(Vaga vaga, TURNO turno) {
        super(vaga);
        this.turno = turno;
        this.horista = new UsoHorista(vaga, servico);
    }

    public UsoTurno(Vaga vaga) {
        super(vaga);
        this.horista = new UsoHorista(vaga, servico);
    }

    public UsoTurno(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        super(vaga, entrada, saida, valorPago, servico);
    }

    @Override
    public double valorPago() {
        LocalDateTime horaAtual = LocalDateTime.now();
    
        if (ehDoTurno(horaAtual)) {
            return 0d; // Está dentro do turno, não há cobrança extra.
        } else {
            return horista.valorPago(); // Fora do turno, usa a lógica de cobrança horista.
        }
    }
    

    public boolean ehDoTurno(LocalDateTime momento) {

        boolean dentroDoTurno = false;
        LocalTime horaMomento = momento.toLocalTime();

        if (horaMomento.isAfter(turno.getHoraInicial()) && horaMomento.isBefore(turno.getHoraFinal())){
            dentroDoTurno = true;
        }
        return dentroDoTurno;
    }

}
