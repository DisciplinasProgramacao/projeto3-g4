public class UsoTurno extends UsoDeVaga {

    private CATEGORIA turno;
    private UsoHorista horista;

    public UsoTurno(Vaga vaga, Servico servico) {
        super(vaga, servico);
        this.horista = new UsoHorista(vaga, servico);
    }

    public UsoTurno(Vaga vaga, CATEGORIA turno) {
        super(vaga);
        this.turno = turno;
        this.horista = new UsoHorista(vaga, servico);
    }

    public UsoTurno(Vaga vaga) {
        super(vaga);
        this.horista = new UsoHorista(vaga, servico);
    }

    @Override
    public double valorPago() {
        if (ehDoTurno()) {
            return 0d;
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
