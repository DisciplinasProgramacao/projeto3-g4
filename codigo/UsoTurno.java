import java.time.LocalDateTime;

public class UsoTurno extends UsoDeVaga {

    private Turno turno;
    private UsoHorista horista;

    public UsoTurno(Vaga vaga, Turno turno) {
        super(vaga);
        this.turno = turno;
    }

    @Override
    public double valorPago() {
        if (ehDoTurno()) {
            return 0; 
        } else {
            return horista.valorPago(); 
        }
    }

    @Override
    public boolean ehDoTurno() {
        LocalDateTime now = LocalDateTime.now();
        int hora = now.getHour();
        return turno.dentroDoTurno(hora);
    }
}
