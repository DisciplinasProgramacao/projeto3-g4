public enum TipoCliente implements Planos {
    HORISTA("Horista", 0),
    DE_TURNO("De Turno", 200),
    MENSALISTA("Mensalista", 500);


    private final String desc;
    private final double mensalidade;
    private TURNO turno;

    TipoCliente(String desc, double mensalidade) {
        this.desc = desc;
        this.mensalidade = mensalidade;
    }

    @Override
    public void setTurno(TURNO turno) {
        this.turno = turno;
    }

    @Override
    public TURNO getTurno() {
        return turno;
    }

    @Override
    public double getMensalidade() {
        return mensalidade;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}