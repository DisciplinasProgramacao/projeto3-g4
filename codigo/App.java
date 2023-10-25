import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class App {
    List<Cliente>clientesAleatorios = new ArrayList<>();
    List<Estacionamento>estacionamentosAletorios = new ArrayList<>();
    /**gera Id para gerar aleatoriamente as classes cientes e veiculos */
    class GerarID {
        private static int lastID = 0;

        public static int ID() {
            lastID++;
            return lastID;
        }
    }

    public void criarEstacionamentoAleatorio() {
        for(int i=0;i<3;i++){
            Random rand = new Random();
            String nome = "Estacionamento " + rand.nextInt(100);
            int fileiras = rand.nextInt(10) + 1; // Mínimo de 1 fileira
            int vagasPorFila = rand.nextInt(20) + 1; // Mínimo de 1 vaga por fileira
            Estacionamento n = new Estacionamento(nome, fileiras, vagasPorFila);
            estacionamentosAletorios.add(n);
            }
        };
    public void criarClienteAleatorio() {
        for(int i=0;i<9;i++){
            Random rand = new Random();
            String nome = "Cliente " + rand.nextInt(1000);
            String id = Integer.toString(GerarID.ID());
            Cliente n = new Cliente(nome, id);
            clientesAleatorios.add(n);
        }
    }
        public void criarUsosDeVagaAleatorios(List<Cliente> clientes, List<Estacionamento> estacionamentos) {
            Random rand = new Random();
            for (int i = 0; i < 50; i++) { // Gere pelo menos 50 usos de vaga
                Cliente cliente = clientes.get(rand.nextInt(clientes.size()));
                Estacionamento estacionamento = estacionamentos.get(rand.nextInt(estacionamentos.size()));
                cliente.addVeiculo(new Veiculo("Placa" + Integer.toString(GerarID.ID())));
                estacionamento.addCliente(cliente);
                Veiculo veiculo = cliente.getVeiculo();
                estacionamento.addVeiculo(veiculo, cliente.getId());
                estacionamento.estacionar(veiculo.getPlaca());
            
            }
        }
    
    }


