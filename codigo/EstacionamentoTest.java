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
}