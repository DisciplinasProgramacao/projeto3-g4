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

		for (Veiculo v: veiculos){
			if (v.equals(buscando))
			return v.totalArrecadado();
		}
	}

	public double arrecadadoTotal() {
		Veiculo buscando = new Veiculo(placa);
		double totalArrecadado = 0d;

		for (Veiculo v: veiculos){
			totalArrecadado += veiculos.totalArrecadado();
		}

		retrun totalArrecadado;
	}

	public double arrecadadoNoMes(int mes) {
		double arrecadadoVeiculoMes = 0d;

		for (Veiculo v: veiculos){
				arrecadadoNoMes += v.arrecadadoNoMes(mes);
			}

		retrun arrecadadoVeiculoMes;	
		}

}