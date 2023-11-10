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
	private double valorServicos;
	private Servico servico;
    private int servicoMinPermanencia;

	public UsoDeVaga(Vaga vaga) {
		this.vaga = vaga;
		this.entrada = LocalDateTime.now();
	}

	public double sair() {
		contratarServico(Servico.LAVAGEM);
		this.saida = LocalDateTime.now();
		int tempoPermanenciaMinutos = (int) entrada.until(saida, ChronoUnit.MINUTES);
		
		if (tempoPermanenciaMinutos >= servico.getTempo()) {
			return valorPago();
		}else {
			return valorPago();
		}
		
	}
	

	public boolean ehDoMes(int mes) {
		return entrada.getMonthValue() == mes;
	}

	public double valorPago() {
		int calcTempo = (int) entrada.until(saida, ChronoUnit.MINUTES);
		if(calcTempo == 0)
			calcTempo = 1;
		int quantidadeFracoesTempo = (int) Math.ceil(calcTempo / 15.0);
		valorPago = quantidadeFracoesTempo * VALOR_FRACAO;
		
		if(valorPago > VALOR_MAXIMO) valorPago = VALOR_MAXIMO;

		return valorPago;
	}

	public void contratarServico(Servico servico){

		this.servico = servico;

	}
}
