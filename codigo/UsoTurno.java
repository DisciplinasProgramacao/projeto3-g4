import java.time.LocalDateTime;

public class UsoTurno extends UsoDeVaga {

    private Turno turno;

    public UsoTurno(Vaga vaga, Turno turno) {
        super(vaga);
        this.turno = turno;
    }

    @Override
    public double valorPago() {
        if (ehDoTurno()) {
            return 0; // Não paga pelo estacionamento no turno escolhido
        } else {
            return calcularValorHorista();
        }
    }

    @Override
    public boolean ehDoTurno() {
        LocalDateTime now = LocalDateTime.now();
        int hora = now.getHour();
        return turno.dentroDoTurno(hora);
    }

    private double calcularValorHorista() {
        // Implemente a lógica para calcular o valor quando o cliente de turno usar fora do seu turno
    }
}
