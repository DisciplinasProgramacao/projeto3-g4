import java.time.LocalTime;

public enum TURNO {

  MANHA(8.01),
  TARDE(12.01),
  NOITE(18.01);

  private double horaInicial;

  private TURNO(double horaInicial) {
    this.horaInicial = horaInicial;
  }

  public double getHora() {
    return horaInicial;
  }

  public LocalTime getHoraInicial() {
    return null;
  }

public LocalTime getHoraFinal() {
    return null;
}
}