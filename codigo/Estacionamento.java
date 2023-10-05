

public class Estacionamento {

	private String nome;
	private Cliente[] id;
	private Vaga[] vagas;
	private int quantFileiras;
	private int vagasPorFileira;

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		
	}

	public void addVeiculo(Veiculo veiculo, String idCli) {
		
	}

	public void addCliente(Cliente cliente) {
		
	}

	private void gerarVagas() {
		
	}

	public void estacionar(String placa) {
		Veiculo veiculo = procuraVeiculo(placa);
		Vaga vaga = procuraVaga();

		if(vaga != null && veiculo != null){
			veiculo.estacionar(vaga);
		}
	}
	private Vaga procuraVaga(){
		for(Vaga vaga : vagas){
			if(vaga.disponivel()){
				return vaga;
			}
		}
		return null;
	}
	private Veiculo procuraVeiculo(String placa){
		for(Cliente cliente: id){
			Veiculo veiculo = cliente.possuiVeiculo(placa);
			return veiculo;
		}
		return null;
	}
	public double sair(String placa) {
		Veiculo veiculo = procuraVeiculo(placa);
		return veiculo.sair();
	}

	public double totalArrecadado() {
		double total = 0;
		for(Cliente cliente: id){
			if(cliente != null){
				total += cliente.arrecadadoTotal();
			}
		}
		return total;
	}

	public double arrecadacaoNoMes(int mes) {
		double totalMes = 0;
		for(Cliente cliente: id){
			if(cliente != null){
				totalMes += cliente.arrecadadoNoMes(mes);
			}
		}
		return totalMes;
	}

	public double valorMedioPorUso() {
		
	}

	public String top5Clientes(int mes) {
		
	}

}
