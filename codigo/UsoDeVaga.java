import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class UsoDeVaga {

	private static final double FRACAO_USO = 0.25;
	private static final double VALOR_FRACAO = 4.0;
	private static final double VALOR_MAXIMO = 50.0;
	private Vaga vaga;
	private LocalDateTime entrada;
	private LocalDateTime saida;
	private double valorPago;

	public UsoDeVaga(Vaga vaga) {
		this.vaga = vaga;
		this.entrada = LocalDateTime.now();
	}

	public double sair() {
		this.saida = LocalDateTime.now();
		return valorPago();
	}

	public boolean ehDoMes(int mes) {
		return entrada.getMonthValue() == mes;
	}

	public double valorPago() {
		int calcTempo = (int) entrada.until(saida, ChronoUnit.MINUTES);
		double valorTotal = (calcTempo / 15.0) * VALOR_FRACAO;

		if (valorTotal > VALOR_MAXIMO) {
			valorPago = VALOR_MAXIMO;
		} else {
			valorPago = valorTotal;
		}

		return valorPago;
	}
}
