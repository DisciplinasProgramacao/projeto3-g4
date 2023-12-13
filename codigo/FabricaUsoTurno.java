import java.time.LocalDateTime;

public class FabricaUsoTurno implements IFactory<UsoDeVaga> {

    private TURNO turno;

    /**
     * Construtor da classe FabricaUsoTurno
     * @param turno
     */
    public FabricaUsoTurno(TURNO turno) {
        this.turno = turno;
    }

    /**
     * Cria e retorna uma instância de UsoTurno com base em uma Vaga, associando o turno.
     * @param vaga A vaga associada ao uso turno.
     */
    @Override
    public UsoDeVaga create(Vaga vaga) {
        UsoTurno uso = new UsoTurno(vaga);
        uso.setTurno(turno);
        return uso;
    }

    /**
     * Cria e retorna uma instância de UsoTurno com base em uma Vaga, um Serviço e associando o turno.
     * @param vaga A vaga associada ao uso turno.
     * @param servico O serviço associado ao uso turno.
     */
    @Override
    public UsoDeVaga create(Vaga vaga, Servico servico) {
        UsoTurno uso = new UsoTurno(vaga, servico);
        uso.setTurno(turno);
        return uso;
    }

    /**
     * Cria e retorna uma instância de UsoTurno com base em uma Vaga, datas de entrada e saída, valor pago, um Serviço
     * e associando o turno configurado.
     * @param vaga A vaga associada ao uso turno.
     * @param entrada A data e hora de entrada do veículo na vaga.
     * @param saida A data e hora de saída do veículo da vaga.
     * @param valorPago O valor pago pelo uso da vaga turno.
     * @param servico O serviço associado ao uso turno.
     */
    @Override
    public UsoDeVaga create(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        UsoTurno uso = new UsoTurno(vaga, entrada, saida, valorPago, servico);
        uso.setTurno(turno);
        return uso;
    }
}
