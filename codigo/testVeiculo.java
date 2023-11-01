import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class testVeiculo{
    static Veiculo veiculo;
    static Vaga vaga;
    static UsoDeVaga usoDeVaga;
    static double valorPago; 

    @BeforeAll
    public static void init(){
        veiculo = new Veiculo("ABC2407");
        vaga = new Vaga(2,5); 
        usoDeVaga = new UsoDeVaga(vaga);
        veiculo.estacionar(vaga);
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        valorPago = veiculo.sair();
    }

    @Test
    public void estacionarCorretamente(){
        assertEquals(1, veiculo.totalDeUsos());
    }


    @Test
    public void sairCorretamente(){
        assertEquals(4.0,valorPago);
    }

    @Test 
    public void totalArrecadado(){   
        assertEquals(4.0, veiculo.totalArrecadado());
    }

    @Test
    public void arrecadadoNoMes(){
        assertEquals(4.0, veiculo.arrecadadoNoMes(LocalDate.now().getMonthValue()));
    }
}