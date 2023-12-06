import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class VeiculoTest {
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
    }

    @Test
    public void deveEstacionarCorretamente() {
        assertEquals(1, veiculoHorista.totalDeUsos());
    }

    @Test
    public void calculaOPrecoAoSairDoEstacionamentoComServicoConcluidoParaClienteHorista() {
        assertEquals(9.0, valorPagoHorista);
    }

    @Test
    public void calculaOPrecoAoSairDoEstacionamentoComServicoConcluidoParaClienteDeTurno() {
        clienteTurno.getTipoCliente().setTurno(geraTurnoDoSistemaAutomaticamente());
        estacionamento.estacionar(veiculoTurno.getPlaca(), Servico.MANOBRISTA);
        assertEquals(5.0, estacionamento.sair(veiculoTurno.getPlaca()));
    }

    @Test
    public void calculaOPrecoAoSairDoEstacionamentoComServicoConcluidoParaClienteMensalista() {
        estacionamento.estacionar(veiculoMensalista.getPlaca(), Servico.MANOBRISTA);
        assertEquals(5.0, estacionamento.sair(veiculoMensalista.getPlaca()));
    }

    @Test
    public void retornaTotalArrecadadoEmUsosDeVagaDosVeiculosDeClienteHorista() {
        assertEquals(4.0, veiculoHorista.totalArrecadado());
    }

    @Test
    public void retornaTotalArrecadadoEmUsosDeVagaDosVeiculosDeClienteDeTurno() {
        assertEquals(0.0, veiculoTurno.totalArrecadado());
    }

    @Test
    public void retornaTotalArrecadadoEmUsosDeVagaDosVeiculosDeClienteMensalista() {
        assertEquals(0.0, veiculoMensalista.totalArrecadado());
    }

    @Test
    public void retornarArrecadacaoNoMesEmUsosDeVagaDosVeiculosDeClienteHorista() {
        assertEquals(4.0, veiculoHorista.arrecadadoNoMes(LocalDate.now().getMonthValue()));
    }

    @Test
    public void retornarArrecadacaoNoMesUsosDeVagaDosVeiculosDeClienteDeTurno() {
        assertEquals(0.0, veiculoTurno.arrecadadoNoMes(LocalDate.now().getMonthValue()));
    }

    @Test
    public void retornarArrecadacaoNoMesUsosDeVagaDosVeiculosDeClienteMensalista() {
        assertEquals(0.0, veiculoMensalista.arrecadadoNoMes(LocalDate.now().getMonthValue()));
    }

    @Test
    public void retornaTotalDeUsosCorretamenteParaCadaVeiculo() {
        assertEquals(1, veiculoHorista.totalDeUsos());
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