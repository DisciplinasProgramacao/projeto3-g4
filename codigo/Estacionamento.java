

public class Estacionamento {

	private String nome;
	private List<Cliente> clientes = new ArrayList<>();
	private List<Vaga> vagas = new ArrayList<>();
	private int quantFileiras;
	private int vagasPorFileira;

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		this.nome=nome;
		this.quantfileiras=fileiras;
		this.vagasPorFileira=vagasPorFila;
	}

	public void addVeiculo(Veiculo veiculo, String idCli) {
		Cliente buscando = new Cliente(idCli);
		for(Cliente x:clientes){
			if(x.equals(buscando))
				x.addVeiculo(veiculo);
			}
		}
		
	}

	public void addCliente(Cliente cliente) {
		clientes.add(cliente);


	}

	private void gerarVagas() {

		
		
	}

	public void estacionar(String placa) {
		
	}

	public double sair(String placa) {
		
	}

	public double totalArrecadado() {
		
	}

	public double arrecadacaoNoMes(int mes) {
		
	}

	public double valorMedioPorUso() {

		double usos=0;
		double valorArrecadado=0;

		for(Cliente x:clientes){
			usos+=x.totalDeUsos();
			valorArrecadado+=x.arrecadadoTotal();	
		}	
		double media = valorArrecadado/usos;
	}

	public String top5Clientes(int mes) {
		
		
	}

}
