import java.util.List;

public class Cliente {

	private String nome;
	private String id;
	private Veiculo[] veiculos;

	public Cliente(String nome, String id) {
		this.nome = nome;
		this.id = id;
		this.veiculos = new Veiculo[10];
	}

	public void addVeiculo(Veiculo veiculo) {
		for (int i = 0; i < veiculos.length; i++) {
			if (veiculos[i] == null) {
				veiculos[i] = veiculo;
				return; // Adicionou o veículo, saia do método.
			}
		}
	}

	public Veiculo possuiVeiculo(String placa) {
		for (Veiculo veiculo : veiculos) {
			if (veiculo.getPlaca().equals(placa)) {
				return veiculo;
			}
		}
		return null;
	}

	public int totalDeUsos() {
		int total = 0;
		for (Veiculo v : veiculos) {
			total += v.totalDeUsos();
		}
		return total;
	}

	public double arrecadadoPorVeiculo(String placa) {
		Veiculo buscando = new Veiculo(placa);
		double arrecadado = 0d;
		for (Veiculo v : veiculos) {
			if (v.equals(buscando)) {
				arrecadado = v.totalArrecadado();
				break;
			}
		}
		return arrecadado;

	}

	public double arrecadadoTotal() {
		double totalArrecadado = 0d;

		for (Veiculo veiculo : veiculos) {
			totalArrecadado += veiculo.totalArrecadado();
		}

		return totalArrecadado;
	}

	public double arrecadadoNoMes(int mes) {
		double arrecadadoVeiculoMes = 0d;

		for (Veiculo v : veiculos) {
			arrecadadoVeiculoMes += v.arrecadadoNoMes(mes);
		}

		return arrecadadoVeiculoMes;
	}

	public String pesquisarHistorico(int mes, String placa) {
		int idasNoMes = 0;
		StringBuilder relatorio = new StringBuilder();

		Veiculo buscando = possuiVeiculo(placa);

		if (buscando != null) {
			List<UsoDeVaga> usos = buscando.getUsos();
			for (UsoDeVaga uso : usos) {
				if (uso.ehDoMes(mes)) {
					relatorio.append("Placa: ").append(placa).append("\n");
					relatorio.append("Data: ").append(mes).append("\n");
					relatorio.append("Valor Pago: ").append(uso.valorPago()).append("\n");
					relatorio.append("--------------").append("\n");
				}
			}
			relatorio.append("Total de Dias: ").append(idasNoMes).append("\n");
		}

		return relatorio.toString();
	}

	public Object getId() {
		return id;
	}

}