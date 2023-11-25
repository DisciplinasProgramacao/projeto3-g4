public enum TipoCliente{
    HORISTA("horista", 0),
    DE_TURNO("De Turno", 200),
    MENSALISTA("Mensalista", 500);

    private final String desc;
    private final double mensalidade;
    private TURNO turno;

    TipoCliente(String desc, double mensalidade){
        this.desc = desc;
        this.mensalidade = mensalidade;
    }

    public double getMensalidade(){
        return mensalidade;
    }

    public String getDesc(){
        return desc;
    }

    public void setTurno(TURNO turno) {
        this.turno = turno;
   }

    public TURNO getTurno() {
        return turno;
    }
}