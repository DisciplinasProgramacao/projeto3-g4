import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestCliente {

    static Veiculo veiculo;
    static Cliente cliente;
    static Vaga vaga;
    static UsoDeVaga uso;
    static final String PLACA = "321GDE";
    @BeforeAll
    public static void init() {
        veiculo = new Veiculo(PLACA);
        cliente = new Cliente("Joao", "3123213");
        vaga = new Vaga(4, 3);
        uso = new UsoHorista(vaga);

        cliente.addVeiculo(veiculo);
        veiculo.estacionar(vaga);

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        veiculo.sair();
    }

    @Test
    public void adicionaVeiculoCorretamente() {
        assertEquals(1, cliente.totalDeUsos());
    }

    @Test
    public void naoAdicionaVeiculoRepetido() {
        cliente.addVeiculo(veiculo);
        assertEquals(1, cliente.totalDeUsos());
    }

    @Test
    public void clientePossuiVeiculo() {
        assertEquals(veiculo, cliente.possuiVeiculo(PLACA));
    }

    @Test
    public void clienteNaoPossuiVeiculo() {
        assertNull(cliente.possuiVeiculo("111111"));
    }

    @Test
    public void retornaTotalDeUsosCorretamente() {
        assertEquals(1, cliente.totalDeUsos());
    }

    @Test
    public void retornaArrecadacaoPorVeiculoCorretamente() {
        assertEquals(4, cliente.arrecadadoPorVeiculo(PLACA));
    }

    @Test
    public void retornaArrecadacaoTotalCorretamente() {
        assertEquals(4, cliente.arrecadadoTotal());
    }

    @Test
    public void retornaArrecadacaoNoMesCorretamente() {
        assertEquals(4, cliente.gastoNoMes(LocalDate.now().getMonthValue()));
    }
}
