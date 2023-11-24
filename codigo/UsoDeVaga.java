import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
}
