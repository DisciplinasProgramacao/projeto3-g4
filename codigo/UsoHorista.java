import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class UsoHorista extends UsoDeVaga{


    public  UsoHorista(Vaga vaga, Servico servico) {
        super(vaga, servico);
        this.vaga = vaga;
        this.entrada = LocalDateTime.now();
        contratarServico(servico);
    }

    public double sair() {
        this.saida = LocalDateTime.now();
        int tempoPermanenciaMinutos = (int) entrada.until(saida, ChronoUnit.MINUTES);
        if (servico != null) {
            if (tempoPermanenciaMinutos >= servico.getTempo()) {
                return valorPago() + servico.getValor();
            } else {
                return valorPago();
            }
        }
        return valorPago();
    }


    
}
