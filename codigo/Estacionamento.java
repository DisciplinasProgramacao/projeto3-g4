import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Estacionamento {

	private String nome;
	private List<Cliente> clientes = new ArrayList<>();
	private List<Vaga> vagas = new ArrayList<>();
	private int quantFileiras;
	private int vagasPorFileira;

	public Estacionamento(String nome, int fileiras, int vagasPorFila) {
		this.nome = nome;
		this.quantFileiras = fileiras;
		this.vagasPorFileira = vagasPorFila;

	}

    public boolean addVeiculo(Veiculo veiculo, String idCli) {
        boolean resposta = false;
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(idCli)) {
                cliente.addVeiculo(veiculo);
                resposta = true;
            }
        }
        return resposta;
    }

    public boolean addCliente(Cliente cliente) {
        boolean resposta = false;
        if (!clientes.contains(cliente)) {
            clientes.add(cliente);
            resposta = true;
        }
        return resposta;
    }

    private int gerarVagas() {
        int numVagas = 0;
        for (int i = 0; i < quantFileiras; i++) {
            for (int j = 0; j < vagasPorFileira; j++) {
                Vaga x = new Vaga(i, j);
                vagas.add(x);
                numVagas++;
            }
        }
        return numVagas;
    }

    public boolean estacionar(String placa) {
        boolean estacionado = false;
        Veiculo veiculo = procuraVeiculo(placa);
        Vaga vaga = procuraVaga();

        if (vaga != null && veiculo != null) {
            veiculo.estacionar(vaga);
            estacionado = true;
        }
		return estacionado;
    }

    public Vaga procuraVaga() {
        gerarVagas();
        for (Vaga vaga : vagas) {
            if (vaga.disponivel()) {
                return vaga;
            }
        }
        return null;
    }

	public Veiculo procuraVeiculo(String placa) {
		for (Cliente cliente : clientes) {
			Veiculo veiculo = cliente.possuiVeiculo(placa);
			if (veiculo != null) {
				return veiculo;
			}
		}
		return null;
	}


	public double sair(String placa) {
		Veiculo veiculo = procuraVeiculo(placa);
		return veiculo.sair();
	}

	public double totalArrecadado() {
		double total = 0;
		for (Cliente cliente : clientes) {
			if (cliente != null) {
				total += cliente.arrecadadoTotal();
			}
		}
		return total;
	}

	public double arrecadacaoNoMes(int mes) {
		double totalMes = 0;
		for (Cliente cliente : clientes) {
			if (cliente != null) {
				totalMes += cliente.arrecadadoNoMes(mes);
			}
		}
		return totalMes;
	}

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

	public String top5Clientes(int mes) {
		List<Cliente> clienteDoMes = new ArrayList<>();

		for (Cliente x : clientes) {
			if (x.arrecadadoNoMes(mes) > 0) {
				clienteDoMes.add(x);
			}
		}
		clienteDoMes.sort(new Comparator<Cliente>() {// utiliza o sort para ordernar a lista clienteDoMes; Utiliza a
														// interface Comparator para estabelecer uma regra de
														// comparação, realizando uma instanciação anonima;
			@Override // reescrevemos a regra compare da interface comparator
			public int compare(Cliente cliente1, Cliente cliente2) {// utiliza o metodo compare da interface comparator
																	// (no qual se der negativo cliente1 vem antes do 2
																	// , se der zero sao iguais e se der positivo
																	// cliente1 vem depois do 2)
				double arrecadacao1 = cliente1.arrecadadoNoMes(mes);
				double arrecadacao2 = cliente2.arrecadadoNoMes(mes);
				// Ordene em ordem decrescente
				return Double.compare(arrecadacao2, arrecadacao1);// ordenamos o compare dessa maneira para podermos ter
																	// a Lista decrescente. Ex.: se arrecadaçao cliente
																	// 2 > arrecadaçao cliente1 = +1 logo cliente1 vem
																	// depois do cliente2;
			}
		});

		int maxClientes = Math.min(5, clienteDoMes.size());// Math.min estabelece que o tamanho maximo da lista seja 5,

		StringBuilder top5 = new StringBuilder();

		top5.append("Top 5 clientes do mês " + mes + " :\n");

		for (int i = 0; i < maxClientes; i++) {
			Cliente cliente = clienteDoMes.get(i);
			top5.append(cliente + "\n");// utiliza o metodo toString() da classe cliente;
		}

		return top5.toString();

	}

	public int getNumVagas() {
		int numVagas = gerarVagas();
		return numVagas;
	}

	public int getVagasLivres() {
		int numVagas = 0;
		for (Vaga vaga: vagas){
			if (vaga.disponivel()){
				numVagas++;
			}
		}
		return numVagas;
	}

}
