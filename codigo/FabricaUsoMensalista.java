import java.time.LocalDateTime;

public class FabricaUsoMensalista implements IFactory<UsoDeVaga> {

    /**
     * Cria e retorna uma instância de UsoMensalista com base em uma Vaga.
     * @param vaga A vaga associada ao uso mensalista.
     */
    @Override
    public UsoDeVaga create(Vaga vaga) {
        return new UsoMensalista(vaga);
    }

    /**
     * Cria e retorna uma instância de UsoMensalista com base em uma Vaga e um Serviço.
     * @param vaga A vaga associada ao uso mensalista.
     * @param servico O serviço associado ao uso mensalista.
     */
    @Override
    public UsoDeVaga create(Vaga vaga, Servico servico) {
        return new UsoMensalista(vaga, servico);
    }

    /**
     * Cria e retorna uma instância de UsoMensalista com base em uma Vaga, datas de entrada e saída, valor pago e um Serviço.
     * @param vaga A vaga associada ao uso mensalista.
     * @param entrada A data e hora de entrada do veículo na vaga.
     * @param saida A data e hora de saída do veículo da vaga.
     * @param valorPago O valor pago pelo uso da vaga mensalista.
     * @param servico O serviço associado ao uso mensalista.
     */
    @Override
    public UsoDeVaga create(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        return new UsoMensalista(vaga, entrada, saida, valorPago, servico);
    }
}
