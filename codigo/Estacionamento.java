import javax.print.attribute.standard.Severity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Estacionamento {

	private String nome;
	private List<Cliente> clientes = new ArrayList<>();
	private PriorityQueue<Vaga> vagas = new PriorityQueue<>((Comparator.comparing(Vaga::disponivel).reversed()));
	private int quantFileiras;
	private int vagasPorFileira;

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		this.nome = nome;
		this.quantFileiras = fileiras;
		this.vagasPorFileira = vagasPorFila;
		gerarVagas();
	}
	/**
	 * Cadastra um veículo à um cliente previamente cadastrado no sitema do estacionamento;
	 * @param veiculo veículo a ser adicionado;
	 * @param cliente string que identifica o cliente;
	 */
	public void addVeiculo(Veiculo veiculo, Cliente cliente) {
		for (Cliente x : clientes) {
			if (x.equals(cliente))
				x.addVeiculo(veiculo);
		}
	}
	/**
	 * Cadastra um cliente novo no estacionamento;
	 * @param cliente cliente novo a ser cadastrado.
	 */
	public void addCliente(Cliente cliente) {
		clientes.add(cliente);

	}
	/**
	 * Método que utiliza do número de fileiras e da quantidade de vagas por fileira para criar
	 * automaticamente vagas com seus respectivos identificadores. Ex.: "A1";
	 */
	private void gerarVagas() {
		
		for (int i = 0; i < quantFileiras; i++) {
			for (int j = 1; j <= vagasPorFileira; j++) {

				
				Vaga x = new Vaga(i,j);
				vagas.add(x);
			}
		}
	}
	/**
	 * Esse método recebe um veiculo que deseja estecionar e caso tenha vagas disponíveis estaciona o veiculo na vaga em questão;
	 * @param placa String da placa do veículo que deseja estacionar em questão;
	 */
	public void estacionar(String placa) {
		Veiculo veiculo = procuraVeiculo(placa);
		Vaga vaga = procuraVaga();

		if (vaga != null && veiculo != null) {
			veiculo.estacionar(vaga);
		}
	}
	public void estacionar(String placa, Servico servico) {
		Veiculo veiculo = procuraVeiculo(placa);
		Vaga vaga = procuraVaga();

		if (vaga != null && veiculo != null) {
			veiculo.estacionar(vaga, servico);
		}
	}
	/**
	 * Esse método procura alguma vaga disponível no estacionamento.
	 * @return a vaga caso ela esteja disponível, caso não tenha nenhuma vaga disponível retorna null
	 */
	private Vaga procuraVaga() {
		return vagas.peek();
	}
	/**
	 * Esse método tem como objetivo verificar se o veículo em questão já está cadastrado no cliente
	 * @param placa String da placa do veículo a ser verificado
	 * @return  retorna o veículo se o cliente já possuir o veículo cadastrado em questão, caso contrario retorna null.
	 */
	private Veiculo procuraVeiculo(String placa) {
		for (Cliente cliente : clientes) {
			Veiculo veiculo = cliente.possuiVeiculo(placa);
			if(veiculo!= null)
				return veiculo;
		}
		return null;
	}
	/**
	 * Esse método tem como objetivo pegar um veículo que esta estacionado retirá-lo da vaga e mostrar o tanto a ser cobrado;
	 * @param placa string da placa do veiculo que está saindo da vaga;
	 * @return tira o veiculo do estacionamento e retorna o valor a ser pago;
	 */
	public double sair(String placa) {
		Veiculo veiculo = procuraVeiculo(placa);
		if(veiculo != null)
			return veiculo.sair();
		return 0d;
	}
	/**
	 * Esse método tem como objetivo ver o total arrecado pelo estacionamento;
	 * @return valor do total arrecadado pelo estacionamento;
	 */
	public double totalArrecadado() {
		double total = 0;
		for (Cliente cliente : clientes) {
			if (cliente != null) {
				total += cliente.arrecadadoTotal();
			}
		}
		return total;
	}
	/**
	 * Esse método recebe um mês como parametro para ser analisado e calcula o total de arrecadação no respectivo mês.
	 * @param mes , mes em questão a ser analisado;
	 * @return total arrecadado no mês de interesse;
	 */

	public double arrecadacaoNoMes(int mes) {
		double totalMes = 0;
		for (Cliente cliente : clientes) {
			if (cliente != null) {
				totalMes += cliente.arrecadadoNoMes(mes);
			}
		}
		return totalMes;
	}
	/**
	 * Esse método tem como objetivo pegar o total de usos dos clientes do estacionamente e o total de arrecadação dos clientes e 
	 * retornar o valor medio por uso.
	 * @return o valor medio por uso no estacionamento
	 */
	public double valorMedioPorUso() {

		double usos = 0;
		double valorArrecadado = 0;

		for (Cliente x : clientes) {
			usos += x.totalDeUsos();
			valorArrecadado += x.arrecadadoTotal();
		}
		double media = valorArrecadado / usos;
		return media;
	}
	/**
	 * Esse método recebe um mes como parametro e verifica quais clientes da Lista clientes utilizou o estacionamento no mes em 
	 * questão, cria uma nova lista organizada de maneira decrescete levando em consideração a arrecadação dos clientes no mes.
	 * @param mes , mes de interesse a ser analisado
	 * @return retorna uma string com os 5 clientes que mais gastaram no estacionamento
	 */

	public String top5Clientes(int mes) {
		List<Cliente> clienteDoMes = new ArrayList<>();

		for (Cliente x : clientes) {
			if (x.arrecadadoNoMes(mes) > 0) {
				clienteDoMes.add(x);
			}
		}
		clienteDoMes.sort(new Comparator<Cliente>() {
			public int compare(Cliente cliente1, Cliente cliente2) {	
				double arrecadacao1 = cliente1.arrecadadoNoMes(mes);
				double arrecadacao2 = cliente2.arrecadadoNoMes(mes);
				
				return Double.compare(arrecadacao2, arrecadacao1);
			}
		});

		int maxClientes = Math.min(5, clienteDoMes.size());

		StringBuilder top5 = new StringBuilder();

		top5.append("Top 5 clientes do mês " + mes + " :\n");

		for (int i = 0; i < maxClientes; i++) {
			Cliente cliente = clienteDoMes.get(i);
			top5.append(cliente + "\n");
		}

		return top5.toString();

	}

}
