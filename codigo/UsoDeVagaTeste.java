import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class UsoDeVagaTeste{

    UsoDeVaga usoDeVaga;
    Vaga vagaReal; 
    Servico servicoReal;

    @BeforeEach
    public void setup() {
        vagaReal = new Vaga(0, 0);
        usoDeVaga = new UsoHorista(vagaReal);
        
        
    }

    @Test
    public void testEhDoMes() {
        assertEquals(true, usoDeVaga.ehDoMes(LocalDateTime.now().getMonthValue()));
    }

    @Test
    public void testValorPago() {
        usoDeVaga.contratarServico(servicoReal);
        usoDeVaga.sair();
        double valorEsperado = 4.0; // Supondo uma permanência de até 15 minutos.
        assertEquals(valorEsperado, usoDeVaga.valorPago());
    }

    @Test
    public void testSairAposServico() {
        usoDeVaga.contratarServico(servicoReal);
        usoDeVaga.sair();
        double valorEsperado = 0.0; // Supondo que a saída seja antes do tempo do serviço.
        assertEquals(valorEsperado, usoDeVaga.sair());
    }
}
