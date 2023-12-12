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

    public UsoDeVaga(Vaga vaga) {
        this.vaga = vaga;
        this.entrada = LocalDateTime.now();
    }

    public UsoDeVaga(Vaga vaga, Servico servico) {
        this.vaga = vaga;
        this.entrada = LocalDateTime.now();
        contratarServico(servico);
    }

    public UsoDeVaga(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        this.vaga = vaga;
        this.entrada = entrada;
        this.saida = saida;
        this.valorPago = valorPago;
        this.servico = servico;
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

    public abstract double valorPago();

    public void contratarServico(Servico servico) {
        this.servico = servico;
    }

    @Override
    public String toString() {
        return vaga.getIdVaga() + ";" + entrada.format(formatter) + ";" + saida.format(formatter) + ";" + valorPago + ";" + servico + ";";
    }

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

    private static String formatarMoeda(Double valor) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valor);
    }

    public LocalDateTime getSaida() {
        return saida;
    }
}
