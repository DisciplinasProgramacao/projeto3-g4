import java.time.LocalDateTime;

public interface IFactory <T> {

    T create(Vaga vaga);

    T create(Vaga vaga, Servico servico);

    T create(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico);
}
