import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public abstract class UsoDeVaga {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    protected Vaga vaga;
    protected LocalDateTime entrada;
    protected LocalDateTime saida;

    protected double valorPago;

    protected Servico servico;

    /**
     * Construtor classe UseDeVaga
     * @param vaga A vaga utilizada
     */
    public UsoDeVaga(Vaga vaga) {
        this.vaga = vaga;
        this.entrada = LocalDateTime.now();
    }

    /**
     * Construtor classe UsoDeVaga
     * @param vaga A vaga utilizada
     * @param servico O serviço contratado
     */
    public UsoDeVaga(Vaga vaga, Servico servico) {
        this.vaga = vaga;
        this.entrada = LocalDateTime.now();
        contratarServico(servico);
    }

    /**
     * Construtor para classe UsoDeVaga
     * @param vaga A vaga utilizada
     * @param entrada Data e hora de entrada
     * @param saida Data e hora de saída
     * @param valorPago Valor pago pelo uso da vaga
     * @param servico O serviço contratado
     */
    public UsoDeVaga(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        this.vaga = vaga;
        this.entrada = entrada;
        this.saida = saida;
        this.valorPago = valorPago;
        this.servico = servico;
    }

    /**
     * Método para que um uso de vaga seja acabado e o cliente saia
     * @return valor a ser pago pelo uso.
     */
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

    /**
     * Verifica se o uso da vaga ocorreu no mes especificado.
     * @param mes desejado
     * @return true se o uso foi no mes especificado, false caso contrário.
     */
    public boolean ehDoMes(int mes) {
        return entrada.getMonthValue() == mes;
    }

    /**
     * Metodo abstrato para calcular o valor a ser pago pelo uso da vaga.
     * @return valor a ser pago
     */
    public abstract double valorPago();

    /**
     * Contrata um serviço para o uso da vaga.
     * @param servico a ser contratado.
     */
    public void contratarServico(Servico servico) {
        this.servico = servico;
    }

    @Override
    public String toString() {
        return vaga.getIdVaga() + ";" + entrada.format(formatter) + ";" + saida.format(formatter) + ";" + valorPago + ";" + servico + ";";
    }

    /**
     * Gera um relatório detalhado do uso da vaga para um veículo.
     * @return Uma string contendo o relatório.
     */
    public String relatorioDeUsoParaVeiculo() {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append(vaga);
        relatorio.append("\nHorário de entrada: " + formatter.format(entrada));
        relatorio.append("\nHorário de saida: " + formatter.format(saida));
        if (servico != null) {
            relatorio.append("\nServiço de " + servico.getNome() + " contratado: " + formatarMoeda(servico.getValor()));
            relatorio.append("\nValor total: " + formatarMoeda(valorPago() + servico.getValor()));
        } else {
            relatorio.append("\nValor total: " + formatarMoeda(valorPago()));
        }
        return relatorio.toString();
    }

    /**
     * Metodo responsavel por formatar um valor em real
     * @param valor a ser formatado
     * @return String com o valor formatado em real
     */
    private static String formatarMoeda(Double valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }

    public LocalDateTime getSaida() {
        return saida;
    }
}
