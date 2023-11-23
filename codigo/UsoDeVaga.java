import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public abstract class UsoDeVaga {

    private static final double FRACAO_USO = 0.25;
    private static final double VALOR_FRACAO = 4.0;
    private static final double VALOR_MAXIMO = 50.0;


    protected Vaga vaga;
    protected LocalDateTime entrada;
    protected LocalDateTime saida;
    protected double valorPago;
    protected double valorServicos;
    protected Servico servico;
    protected int servicoMinPermanencia;

    public UsoDeVaga(Vaga vaga) {
        this.vaga = vaga;
        this.entrada = LocalDateTime.now();
    }

    public UsoDeVaga(Vaga vaga, Servico servico) {
        this.vaga = vaga;
        this.entrada = LocalDateTime.now();
        contratarServico(servico);
    }

    public double sair() {
        vaga.setDisponivel(true);
        this.saida = LocalDateTime.now();
        int tempoPermanenciaMinutos = (int) entrada.until(saida, ChronoUnit.MINUTES);
        if (servico != null) {
            if (tempoPermanenciaMinutos >= servico.getTempo()) {
                return valorPago() + servico.getValor();
            } else {
                return valorPago();
            }
        }
        return valorPago();
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
