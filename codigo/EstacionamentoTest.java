import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EstacionamentoTest {

    private static Estacionamento estacionamento;
    private static Cliente cliente1;
    private static Veiculo veiculo1;

    @BeforeAll
    public static void setUpAll() {
        estacionamento = new Estacionamento("Estacionamento Teste", 5, 10);
        cliente1 = new Cliente("Cliente1", "1");
        veiculo1 = new Veiculo("ABC1234");
        estacionamento.addCliente(cliente1);
    }

    @Test
    public void testarAddCliente() {
        assertEquals(estacionamento.getArrayClientes(), 1);
    }

    @Test
    public void testarAddVeiculo() {
        estacionamento.addVeiculo(veiculo1, "1");
        assertEquals(cliente1.getArrayVeiculos(),1);
    }

    @Test
    public void testarGerarVagas(){
        estacionamento.quantFileiras = 2;
        estacionamento.vagasPorFileira = 2;
        estacionamento.gerarVagas();
        assertEquals(estacionamento.getArrayVagas(),4);
    }

    @Test
    public void testarEstacionar(){
        estacionamento.addVeiculo(veiculo1, "1");
        estacionamento.quantFileiras = 2;
        estacionamento.vagasPorFileira = 2;
        estacionamento.gerarVagas();
        estacionamento.estacionar("ABC1234");
        assertEquals(estacionamento.getArrayVagas(),3);
    }

    @Test
    public void testarProcurarVaga(){
        estacionamento.quantFileiras = 2;
        estacionamento.vagasPorFileira = 2;
        estacionamento.gerarVagas();
        estacionamento.procuraVaga();
    }

    
}

