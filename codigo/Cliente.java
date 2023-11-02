public class Cliente implements IDataToText{

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
            if (veiculos[i] == null && !veiculos[i].equals(veiculo)) {
                veiculos[i] = veiculo;
            }
        }
	}

	public Veiculo possuiVeiculo(String placa) {
		Veiculo buscando = new Veiculo(placa);
		for (Veiculo v: veiculos){
			if (v.equals(buscando)){
				return v;
			}
			
		}
		return null;
	}

	public int totalDeUsos() {
		int total = 0;
		for (Veiculo v: veiculos){
			total += v.totalDeUsos();
		}
		return total;
	}

	public double arrecadadoPorVeiculo(String placa) {
		Veiculo buscando = new Veiculo(placa);
		double arrecadado = 0d;
		for (Veiculo v: veiculos){
			if (v.equals(buscando)){
				arrecadado = v.totalArrecadado();
				break;
			}
		}
		return arrecadado;
	
	}

	public double arrecadadoTotal() {
		Veiculo buscando = new Veiculo("asdfg4");
		double totalArrecadado = 0d;

		for (Veiculo v: veiculos){
			totalArrecadado += v.totalArrecadado();
		}

		return totalArrecadado;
	}

	public double arrecadadoNoMes(int mes) {
		double arrecadadoVeiculoMes = 0d;

		for (Veiculo v: veiculos){
				arrecadadoVeiculoMes += v.arrecadadoNoMes(mes);
			}

		return arrecadadoVeiculoMes;	
		}

	@Override
	public String dataToText() {
		return id + nome;
	}

}