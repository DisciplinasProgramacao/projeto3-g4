public class UsoTurno extends UsoDeVaga {

    private CATEGORIA turno;
    private UsoHorista horista;

    public UsoTurno(Vaga vaga, CATEGORIA turno) {
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
    public boolean ehDoTurno() {
//        LocalDateTime now = LocalDateTime.now();
//        int hora = now.getHour();
//        return turno.dentroDoTurno(hora);
        return false;
    }
}
