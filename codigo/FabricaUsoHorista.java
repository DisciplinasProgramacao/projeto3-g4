import java.time.LocalDateTime;

public class FabricaUsoHorista implements IFactory<UsoDeVaga> {
    
    /**
     * Cria e retorna uma instância de UsoHorista com base em uma Vaga.
     * @param vaga A vaga associada ao uso horista.
     */
    @Override
    public UsoDeVaga create(Vaga vaga) {
        return new UsoHorista(vaga);
    }

    /**
     * Cria e retorna uma instância de UsoHorista com base em uma Vaga e um Serviço.
     * @param vaga A vaga associada ao uso horista.
     * @param servico O serviço associado ao uso horista.
     */
    @Override
    public UsoDeVaga create(Vaga vaga, Servico servico) {
        return new UsoHorista(vaga, servico);
    }

    /**
     * Cria e retorna uma instância de UsoHorista com base em uma Vaga, datas de entrada e saída, valor pago e um Serviço.
     * @param vaga A vaga associada ao uso horista.
     * @param entrada A data e hora de entrada do veículo na vaga.
     * @param saida A data e hora de saída do veículo da vaga.
     * @param valorPago O valor pago pelo uso da vaga horista.
     * @param servico O serviço associado ao uso horista.
     */
    @Override
    public UsoDeVaga create(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        return new UsoHorista(vaga, entrada, saida, valorPago, servico);
    }
}
