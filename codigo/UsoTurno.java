import java.time.LocalDateTime;

public class UsoTurno extends UsoDeVaga {

    private Turno turno;
    private Horista horista;

    public UsoTurno(Vaga vaga, Turno turno, Horista horista) {
        super(vaga);
        this.turno = turno;
        this.horista = horista;
    }

    @Override
    public double valorPago() {
        if (ehDoTurno()) {
            return 0; 
        } else {
            return horista.calcularValor(); 
        }
    }

    @Override
    public boolean ehDoTurno() {
        LocalDateTime now = LocalDateTime.now();
        int hora = now.getHour();
        return turno.dentroDoTurno(hora);
    }
}
