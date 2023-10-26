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


    /**
     * Metodo para adicionar/cadastrar um veiculo ao cliente.
     * @param veiculo	Recebe veiculo como parâmetro para vincular ao cliente.
     */
	public void addVeiculo(Veiculo veiculo) {
		for (int i = 0; i < veiculos.length; i++) {
			if (veiculos[i] == null) {
				veiculos[i] = veiculo;
				return; // Adicionou o veículo, saia do método.
			}
		}
	}

    /**
     * Metodo para verificar se o cliente possui um determiando veículo.
     * @param placa	Recebe placa como parâmetro para procurar, com o metodo equals, na lista de veiculos para ver se ele pertence ao cliente.
	 * @return retorna o veículo se o cliente possuir o carro, caso contrário retorna null.
     */
	public Veiculo possuiVeiculo(String placa) {
		for (Veiculo veiculo : veiculos) {
			if (veiculo.getPlaca().equals(placa)) {
				return veiculo;
			}
		}
		return null;
	}

	/**
     * Metodo para verificar o tatal de usos do cliente no estacionamento.
	 * @return retorna o total de usos de todos os veiculos do cliente.
     */
	public int totalDeUsos() {
		int total = 0;
		for (Veiculo v : veiculos) {
			total += v.totalDeUsos();
		}
		return total;
	}

	/**
     * Metodo para calcular o total arrecadado pelo veiculo do cliente, cujo a placa será dada como parametro.
	 * @param recebe a placa como parametro para decidir qual veiculo sera pesquisado do cliente.
	 * @return retorna o total arrecadado por veiculo (pesquisado pela placa) do cliente.
     */
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

	/**
     * Metodo para calcular o total arrecadado por todos os veiculos de um cliente.
	 * @return retorna o total arrecadado pelo cliente, de todos os veiculos.
     */
	public double arrecadadoTotal() {
		double totalArrecadado = 0d;

		for (Veiculo veiculo : veiculos) {
			totalArrecadado += veiculo.totalArrecadado();
		}

		return totalArrecadado;
	}

	/**
     * Metodo para calcular o total arrecadado no mes pelo cliente.
	 * @param recebe o mes desejado como parametro.
	 * @return retorna o total arrecadado pela data do cliente.
     */
	public double arrecadadoNoMes(int mes) {
		double arrecadadoVeiculoMes = 0d;

		for (Veiculo v : veiculos) {
			arrecadadoVeiculoMes += v.arrecadadoNoMes(mes);
		}

		return arrecadadoVeiculoMes;
	}

	/**
     * Metodo retornar o historico (todos os dados) do cliente pelo mes.
	 * @param mes recebe o mes desejado como parametro.
	 * @param placa recebe a placa para pesquisar qual veiculo do cliente sera referido o relatorio.
	 * @return retorna um string builder com o relatorio do cliente.
     */
	public String pesquisarHistorico(int mes, String placa) {
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
			relatorio.append("Total de Uso: ").append(totalDeUsos()).append("\n");
			relatorio.append("Arrecadado pelo veiculo: ").append(placa).append(" ").append(arrecadadoPorVeiculo(placa)).append("\n");
			relatorio.append("Arrecadado Total: ").append(arrecadadoTotal()).append("\n");
		}

		return relatorio.toString();
	}

	public Object getId() {
		return id;
	}

}