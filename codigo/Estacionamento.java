import java.util.ArrayList;
import java.util.List;

public class Estacionamento {

	private String nome;
	private List<Cliente> clientes = new ArrayList<>();
	private List<Vaga> vagas = new ArrayList<>();
	private int quantFileiras;
	private int vagasPorFileira;
	
	

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		this.nome=nome;
		this.quantFileiras=fileiras;
		this.vagasPorFileira=vagasPorFila;
		
	}

	public void addVeiculo(Veiculo veiculo, String idCli) {
		Cliente buscando = new Cliente(idCli);
		for(Cliente x:clientes){
			if(x.equals(buscando))
				x.addVeiculo(veiculo);
			}
		}
		
	

	public void addCliente(Cliente cliente) {
		clientes.add(cliente);


	}
	
	private void gerarVagas() {
	

		String[] letras = {
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
		};
		int[]numeros = new int[vagasPorFileira] ;

		for (int j = 0; j < vagasPorFileira; j++) {
			numeros[j]=j+1;
		}
		String id;
    for (int i = 0; i < quantFileiras; i++) {
      for (int j = 0; j < vagasPorFileira; j++){ 
						
            id = letras[i] + String.valueOf(numeros[j]);
						Vaga x = new Vaga(i,j,id);
						vagas.add(x);
            
					
        }
    }
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
		return media;
	}

	public String top5Clientes(int mes) {
		
		
	}

}
