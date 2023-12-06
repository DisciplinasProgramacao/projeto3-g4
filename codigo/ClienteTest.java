import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertNull;

public class ClienteTest {
    private static Cliente clienteHorista;
    private static Cliente clienteTurno;
    private static Cliente clienteMensalista;
    private static Estacionamento estacionamento;
    private static Veiculo veiculoHorista;
    private static Veiculo veiculoTurno;
    private static Veiculo veiculoMensalista;
    private static Vaga vaga;
    private static double valorPagoHorista;

    @BeforeAll
    public static void init() {
        estacionamento = new Estacionamento("Estacionamento", 10, 10);
        vaga = new Vaga(2, 5);

        clienteHorista = new Cliente("José", "1", TipoCliente.HORISTA);
        clienteTurno = new Cliente("Bernardo", "2", TipoCliente.DE_TURNO);
        clienteMensalista = new Cliente("João", "3", TipoCliente.MENSALISTA);

        veiculoHorista = new Veiculo("ABCD");
        veiculoTurno = new Veiculo("12345");
        veiculoMensalista = new Veiculo("A1B2C3");

        estacionamento.addCliente(clienteHorista);
        estacionamento.addCliente(clienteTurno);
        estacionamento.addCliente(clienteMensalista);

        estacionamento.addVeiculo(veiculoHorista, clienteHorista);
        estacionamento.addVeiculo(veiculoTurno, clienteTurno);
        estacionamento.addVeiculo(veiculoMensalista, clienteMensalista);

        estacionamento.estacionar(veiculoHorista.getPlaca(), Servico.MANOBRISTA);
        valorPagoHorista = estacionamento.sair(veiculoHorista.getPlaca());

        clienteTurno.getTipoCliente().setTurno(geraTurnoDoSistemaAutomaticamente());
        estacionamento.estacionar(veiculoTurno.getPlaca(), Servico.MANOBRISTA);
        estacionamento.sair(veiculoTurno.getPlaca());

        estacionamento.estacionar(veiculoMensalista.getPlaca(), Servico.MANOBRISTA);
        estacionamento.sair(veiculoMensalista.getPlaca());
    }


    @Test
    public void deveriaAdicionaVeiculoCorretamente() {
        Assertions.assertEquals(1, clienteHorista.totalDeUsos());
        Assertions.assertEquals(TipoCliente.HORISTA, clienteHorista.getTipoCliente());
    }

    @Test
    public void naoDeveriaAdicionarVeiculoRepetido() {
        clienteHorista.addVeiculo(veiculoHorista);
        Assertions.assertEquals(1, clienteHorista.totalDeUsos());
    }

    @Test
    public void clientePossuiVeiculoDeveriaRetornarOVeiculoAPartirDeSuaPlaca() {
        Assertions.assertEquals(veiculoHorista, clienteHorista.possuiVeiculo(veiculoHorista.getPlaca()));
    }

    @Test
    public void clienteNaoPossuiVeiculoDeveriaRetornarNullAoBuscarVeiculoPelaPlaca() {
        assertNull(clienteHorista.possuiVeiculo("111111"));
    }

    @Test
    public void retornaTotalDeUsosCorretamente() {
        Assertions.assertEquals(1, clienteHorista.totalDeUsos());
    }

    @Test
    public void retornaArrecadacaoPorVeiculoCorretamenteParaClienteHorista() {
        Assertions.assertEquals(4.0, clienteHorista.arrecadadoPorVeiculo(veiculoHorista.getPlaca()));
    }

    @Test
    public void retornaArrecadacaoPorVeiculoCorretamenteParaClienteDeTurno() {
        Assertions.assertEquals(0.0, clienteTurno.arrecadadoPorVeiculo(veiculoTurno.getPlaca()));
    }

    @Test
    public void retornaArrecadacaoPorVeiculoCorretamenteParaClienteMensalista() {
        Assertions.assertEquals(0.0, clienteMensalista.arrecadadoPorVeiculo(veiculoMensalista.getPlaca()));
    }

    @Test
    public void retornaArrecadacaoTotalCorretamenteParaClienteHorista() {
        Assertions.assertEquals(4.0, clienteHorista.arrecadadoTotal());
    }

    @Test
    public void retornaArrecadacaoTotalCorretamenteParaClienteTurno() {
        Assertions.assertEquals(200.0, clienteTurno.arrecadadoTotal());
    }

    @Test
    public void retornaArrecadacaoTotalCorretamenteParaClienteMensalista() {
        Assertions.assertEquals(500.0, clienteMensalista.arrecadadoTotal());
    }

    @Test
    public void retornaArrecadacaoNoMesCorretamenteParaClienteHorista() {
        Assertions.assertEquals(4.0, clienteHorista.gastoNoMes(LocalDate.now().getMonthValue()));
    }

    @Test
    public void retornaArrecadacaoNoMesCorretamenteParaClienteDeTurno() {
        Assertions.assertEquals(200.0, clienteTurno.gastoNoMes(LocalDate.now().getMonthValue()));
    }

    @Test
    public void retornaArrecadacaoNoMesCorretamenteParaClienteMensalista() {
        Assertions.assertEquals(500.0, clienteMensalista.gastoNoMes(LocalDate.now().getMonthValue()));
    }

    @Test
    public void deveriaRetornarRelatorioDeHistoricoDoClienteCorretamente() {
        int mes = LocalDate.now().getMonthValue();

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Placa: ").append(veiculoHorista.getPlaca()).append("\n");
        relatorio.append("Data: ").append(mes).append("\n");
        relatorio.append("Valor Pago: ").append(4.0).append("\n");
        relatorio.append("--------------").append("\n");
        relatorio.append("Total de Uso: ").append(1).append("\n");
        relatorio.append("Arrecadado pelo veiculo: ").append(veiculoHorista.getPlaca()).append(" ").append(4.0).append("\n");
        relatorio.append("Arrecadado Total: ").append(4.0).append("\n");

        Assertions.assertEquals(relatorio.toString(), clienteHorista.pesquisarHistorico(mes, veiculoHorista.getPlaca()));
    }

    private static TURNO geraTurnoDoSistemaAutomaticamente() {
        LocalTime now = LocalTime.now();
        int hora = now.getHour();

        if (hora >= 8 && hora <= 12)
            return TURNO.MANHA;
        if (hora > 12 && hora <= 18)
            return TURNO.TARDE;
        return TURNO.NOITE;
    }
}