import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VagaTest {

  @Test
  public void testCriarVagaComFilaENumero() {
      Vaga vaga = new Vaga(1, 5);
      assertEquals("B5", vaga.getId());
      assertTrue(vaga.disponivel());
  }

  @Test
  public void testEstacionar() {
      Vaga vaga = new Vaga(0, 1);
      assertTrue(vaga.estacionar());
      assertFalse(vaga.disponivel());
      assertFalse(vaga.estacionar()); // Não pode estacionar na mesma vaga duas vezes
  }

  @Test
  public void testSair() {
      Vaga vaga = new Vaga(0, 1);
      vaga.estacionar();
      assertTrue(vaga.sair());
      assertTrue(vaga.disponivel());
      assertFalse(vaga.sair()); // Não pode sair de uma vaga já vazia
  }

  @Test
  public void testDisponivel() {
      Vaga vaga = new Vaga(2, 3);
      assertTrue(vaga.disponivel());
      vaga.estacionar();
      assertFalse(vaga.disponivel());
      vaga.sair();
      assertTrue(vaga.disponivel());
  }
}