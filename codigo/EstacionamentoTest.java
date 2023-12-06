import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class EstacionamentoTest {

    private static Estacionamento estacionamento;
    private static Cliente clienteHorista;
    private static Cliente clienteTurno;
    private static Cliente clienteMensalista;
    private static Veiculo veiculoHorista;
    private static Veiculo veiculoTurno;
    private static Veiculo veiculoMensalista;
    private static double valorHorista;
    private static double valorTurno;
    private static double valorMensalista;

    @BeforeAll
    public static void setUpAll() {
        estacionamento = new Estacionamento("Estacionamento Teste", 2, 3);

        clienteHorista = new Cliente("Cliente1", "1", TipoCliente.HORISTA);
        clienteTurno = new Cliente("Cliente2", "2", TipoCliente.DE_TURNO);
        clienteTurno.getTipoCliente().setTurno(geraTurnoDoSistemaAutomaticamente());
        clienteMensalista = new Cliente("Cliente3", "3", TipoCliente.MENSALISTA);

        veiculoHorista = new Veiculo("ABC1234");
        veiculoTurno = new Veiculo("ABCD");
        veiculoMensalista = new Veiculo("12345");

        estacionamento.addCliente(clienteHorista);
        estacionamento.addCliente(clienteTurno);
        estacionamento.addCliente(clienteMensalista);

        estacionamento.addVeiculo(veiculoHorista, clienteHorista);
        estacionamento.addVeiculo(veiculoTurno, clienteTurno);
        estacionamento.addVeiculo(veiculoMensalista, clienteMensalista);

        estacionamento.estacionar(veiculoHorista.getPlaca());
        valorHorista = estacionamento.sair(veiculoHorista.getPlaca());

        estacionamento.estacionar(veiculoTurno.getPlaca());
        valorTurno = estacionamento.sair(veiculoTurno.getPlaca());

        estacionamento.estacionar(veiculoMensalista.getPlaca());
        valorMensalista = estacionamento.sair(veiculoMensalista.getPlaca());
    }

    @Test
    public void deveriaAdicionarClienteCorretamente() {
        assertEquals(3, estacionamento.getClientes().size());
    }

    @Test
    public void naoDeveriaAdicionarClienteRepetido() {
        estacionamento.addCliente(clienteHorista);
        assertEquals(3, estacionamento.getClientes().size());
    }

    @Test
    public void naoDeveriaEstacionarVeiculoNaoCadastrado() {
        assertFalse(estacionamento.estacionar("1111111"));
    }

    @Test
    public void deveriaRetornaroValorCorretoAPagarAposSairDoEstacionamentoParaClienteHorista() {
        assertEquals(4.0, valorHorista);
    }

    @Test
    public void deveriaRetornaroValorCorretoAPagarAposSairDoEstacionamentoParaClientDeTurno() {
        assertEquals(0.0, valorTurno);
    }

    @Test
    public void deveriaRetornaroValorCorretoAPagarAposSairDoEstacionamentoParaClienteMensalista() {
        assertEquals(0.0, valorMensalista);
    }

    @Test
    public void deveriaRetornarArrecadacaoTotalDoEstacionamento() {
        assertEquals(704, estacionamento.totalArrecadado());
    }

    @Test
    public void deveriaRetornarArrecadacaoDoEstacionamentoNoMes() {
        assertEquals(704, estacionamento.arrecadacaoNoMes(LocalDate.now().getMonthValue()));
    }

    @Test
    public void deveriaRetornarArrecadacaoDoEstacionamentoNoMesDosClientesHorista() {
        assertEquals(4.0, estacionamento.arrecadacaoNoMesPorTipo(LocalDate.now().getMonthValue(), TipoCliente.HORISTA));
    }

    @Test
    public void deveriaRetornarArrecadacaoDoEstacionamentoNoMesDosClientesDeTurno() {
        assertEquals(200.0, estacionamento.arrecadacaoNoMesPorTipo(LocalDate.now().getMonthValue(), TipoCliente.DE_TURNO));
    }

    @Test
    public void deveriaRetornarArrecadacaoDoEstacionamentoNoMesDosClientesMensalistas() {
        assertEquals(500.0, estacionamento.arrecadacaoNoMesPorTipo(LocalDate.now().getMonthValue(), TipoCliente.MENSALISTA));
    }

    @Test
    public void deveriaRetornaValorMedioPorUso() {
        DecimalFormat formato = new DecimalFormat("#.##");
        assertEquals("1,33", formato.format(estacionamento.valorMedioPorUso()));
    }

    @Test
    public void deveriaRetornaOTop5Clientes() {
        int mes = LocalDate.now().getMonthValue();

        StringBuilder builder = new StringBuilder();

        builder.append("Top 5 clientes do mês " + mes + " :\n");
        builder.append(clienteMensalista).append("\n");
        builder.append(clienteTurno).append("\n");
        builder.append(clienteHorista).append("\n");

        assertEquals(builder.toString(), estacionamento.top5Clientes(mes));
    }

    @Test
    public void deveriaRetornaAQuantidadeDeUsosDoMesDosClientesHoristas() {
        assertEquals(1, estacionamento.quantidadeUsosMes(LocalDate.now().getMonthValue(), TipoCliente.HORISTA));
    }

    @Test
    public void deveriaRetornaAQuantidadeDeUsosDoMesDosClientesDeTurno() {
        assertEquals(1, estacionamento.quantidadeUsosMes(LocalDate.now().getMonthValue(), TipoCliente.DE_TURNO));
    }

    @Test
    public void deveriaRetornaAQuantidadeDeUsosDoMesDosClientesMensalistas() {
        assertEquals(1, estacionamento.quantidadeUsosMes(LocalDate.now().getMonthValue(), TipoCliente.MENSALISTA));
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