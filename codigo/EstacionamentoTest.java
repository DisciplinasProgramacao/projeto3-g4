import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EstacionamentoTest {

    private static Estacionamento estacionamento;
    private static Cliente cliente1;
    private static Veiculo veiculo1;

    @BeforeAll
    public static void setUpAll() {
        estacionamento = new Estacionamento("Estacionamento Teste", 2, 3);
        cliente1 = new Cliente("Cliente1", "1");
        veiculo1 = new Veiculo("ABC1234");
    }

    @Test
    public void testarAddCliente() {
        assertTrue(estacionamento.addCliente(cliente1));
    }

    @Test
    public void testarAddVeiculo() {
        estacionamento.addCliente(cliente1);
        assertTrue(estacionamento.addVeiculo(veiculo1, "1"));
    }


    @Test
    public void testarProcurarVaga() {
        estacionamento.addCliente(cliente1);
        assertNotNull(estacionamento.procuraVaga());
    }

    @Test
    public void testarProcurarVeiculo() {
        estacionamento.addCliente(cliente1);
        cliente1.addVeiculo(veiculo1);
        assertNotNull(estacionamento.procuraVeiculo("ABC1234"));
    }

    @Test
    public void testarEstacionar(){
        estacionamento.addCliente(cliente1);
        estacionamento.addVeiculo(veiculo1, "1");
        assertTrue(estacionamento.estacionar("ABC1234"));
    }

    @Test
    public void testarEstacionarMesmocarro(){
        estacionamento.addCliente(cliente1);
        estacionamento.addVeiculo(veiculo1, "1");
        estacionamento.estacionar("ABC1234");
        assertFalse(estacionamento.estacionar("ABC1234"));
    }

    @Test
    public void testarSair(){
        estacionamento.addCliente(cliente1);
        estacionamento.addVeiculo(veiculo1, "1");
        estacionamento.estacionar("ABC1234");
        assertNotNull(estacionamento.sair("ABC1234"));
    }

    @Test
    public void calcularArrecadadoTotal(){
        estacionamento.addCliente(cliente1);
        estacionamento.addVeiculo(veiculo1, "1");
        estacionamento.estacionar("ABC1234");
        estacionamento.sair("ABC1234");
        assertNotNull(estacionamento.totalArrecadado());
    }

    @Test
    public void calcularArrecadadoMes(){
        estacionamento.addCliente(cliente1);
        estacionamento.addVeiculo(veiculo1, "1");
        estacionamento.estacionar("ABC1234");
        estacionamento.sair("ABC1234");
        assertNotNull(estacionamento.arrecadacaoNoMes(1));
    }

    @Test
    public void calcularArrecadadoMedia(){
        estacionamento.addCliente(cliente1);
        estacionamento.addVeiculo(veiculo1, "1");
        estacionamento.estacionar("ABC1234");
        estacionamento.sair("ABC1234");
        assertNotNull(estacionamento.valorMedioPorUso());
    }

}