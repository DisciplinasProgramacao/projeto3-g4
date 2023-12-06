import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class UsoDeVagaTest {

    private static UsoDeVaga usoDeVagaHorista;
    private static UsoDeVaga usoDeVagaMensalista;
    private static UsoTurno usoDeVagaTurno;
    private static Vaga vaga;
    private static Servico servicoReal;

    @BeforeAll
    public static void setup() {
        vaga = new Vaga(0, 0);

        usoDeVagaHorista = new UsoHorista(vaga);
        usoDeVagaMensalista = new UsoMensalista(vaga);
        usoDeVagaTurno = new UsoTurno(vaga);
        usoDeVagaTurno.setTurno(geraTurnoDoSistemaAutomaticamente());

        usoDeVagaHorista.contratarServico(Servico.MANOBRISTA);
        usoDeVagaMensalista.contratarServico(Servico.MANOBRISTA);
        usoDeVagaTurno.contratarServico(Servico.MANOBRISTA);
    }

    @Test
    public void testEhDoMes() {
        assertEquals(true, usoDeVagaHorista.ehDoMes(LocalDateTime.now().getMonthValue()));
    }

    @Test
    public void deveriaRetornarOValorDaVagaMaisOServicoDeManobristaParaClienteHorista() {
        assertEquals(9.0, usoDeVagaHorista.sair());
    }

    @Test
    public void deveriaRetornarSomenteOPrecoDoServicoParaClienteMensalista() {
        assertEquals(5.0, usoDeVagaMensalista.sair());
    }

    @Test
    public void deveriaRetornarSomenteOPrecoDoServicoParaClienteDeTurno() {
        assertEquals(5.0, usoDeVagaTurno.sair());
    }

    @Test
    public void deveriaRetornarOValorDoUsoDeVagaParaClienteHorista() {
        assertEquals(4.0, usoDeVagaHorista.valorPago());
    }

    @Test
    public void deveriaRetornarOValorDoUsoDeVagaParaClienteDeTurno() {
        assertEquals(0.0, usoDeVagaTurno.valorPago());
    }

    @Test
    public void deveriaRetornarOValorDoUsoDeVagaParaClienteMensalista() {
        assertEquals(0.0, usoDeVagaMensalista.valorPago());
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