import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class UsoDeVaga {

    protected static final double FRACAO_USO = 0.25;
    protected static final double VALOR_FRACAO = 4.0;
    protected static final double VALOR_MAXIMO = 50.0;

    protected Vaga vaga;
    protected LocalDateTime entrada;
    protected LocalDateTime saida;
    protected double valorPago;
    protected double valorServicos;
    protected Servico servico;
    protected int servicoMinPermanencia;

    public UsoDeVaga(Vaga vaga, Servico servico) {
      
    }

    public double sair() {
        return servicoMinPermanencia;
        
    }

    public boolean ehDoMes(int mes) {
        return entrada.getMonthValue() == mes;
    }

    public double valorPago() {
        int calcTempo = (int) entrada.until(saida, ChronoUnit.MINUTES);
        if (calcTempo == 0)
            calcTempo = 1;
        int quantidadeFracoesTempo = (int) Math.ceil(calcTempo / 15.0);
        valorPago = quantidadeFracoesTempo * VALOR_FRACAO;

        if (valorPago > VALOR_MAXIMO)
            valorPago = VALOR_MAXIMO;

        return valorPago;
    }

    public void contratarServico(Servico servico) {
        this.servico = servico;
    }
}
