public class UsoHorista extends UsoDeVaga{

    public UsoHorista(Vaga vaga) {
        super(vaga);
    }

    public UsoHorista(Vaga vaga, Servico servico) {
        super(vaga, servico);
        contratarServico(servico);
    }
}
