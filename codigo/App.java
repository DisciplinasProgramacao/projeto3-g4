import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class App {
    /**gera Id para gerar aleatoriamente as classes cientes e veiculos */
    class GerarID {
        private static int lastID = 0;

        public static int ID() {
            lastID++;
            return lastID;
        }
    }

    public void GerarDados() {

        List<Estacionamento>estacionamentosAletorios = new ArrayList<>();
        Random rand = new Random();

        for(int i=0;i<3;i++){
                String nome = "Estacionamento " + rand.nextInt(100);
                int fileiras = rand.nextInt(10) + 1; // Mínimo de 1 fileira
                int vagasPorFila = rand.nextInt(20) + 1; // Mínimo de 1 vaga por fileira
                Estacionamento n = new Estacionamento(nome, fileiras, vagasPorFila);
                
                estacionamentosAletorios.add(n);
        }

        for (Estacionamento x : estacionamentosAletorios) {
            for(int i=0;i<9;i++){
                
                String placa =  Integer.toString(GerarID.ID());
                Veiculo veiculo = new Veiculo(placa);

                String nome = "Cliente " + rand.nextInt(1000);
                String id = Integer.toString(GerarID.ID());
                Cliente n = new Cliente(nome, id);

                x.addCliente(n);
                x.addVeiculo(veiculo, id);

                for (int j = 0; j <2; j++) {
                        x.estacionar(placa);
                        x.sair(placa);
                }
            }
        }
    };
}

