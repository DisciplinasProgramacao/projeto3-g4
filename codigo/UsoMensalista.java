public class UsoMensalista extends UsoDeVaga {


    public UsoMensalista(Vaga vaga) {
        super(vaga);
    }

    public UsoMensalista(Vaga vaga, Servico servico) {
        super(vaga, servico);
    }

    @Override
    public double valorPago() {
        return 500.00;
    }
}
