import java.time.LocalTime;

public enum TURNO {

    MANHA(LocalTime.of(8, 0), LocalTime.of(12, 0)),
    TARDE(LocalTime.of(12, 1), LocalTime.of(18, 0)),
    NOITE(LocalTime.of(18, 1), LocalTime.of(23, 59));

    private LocalTime horaInicial;
    private LocalTime horaFinal;

    TURNO(LocalTime horaInicial, LocalTime horaFinal) {
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
    }

    public LocalTime getHoraInicial() {
        return horaInicial;
    }

    public LocalTime getHoraFinal() {
        return horaFinal;
    }
}