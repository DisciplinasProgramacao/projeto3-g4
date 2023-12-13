import java.time.LocalDateTime;
import java.time.LocalTime;

public class UsoTurno extends UsoDeVaga {

    private TURNO turno;
    private UsoHorista horista;

    /**
     * Construtor classe UsoTurno
     * @param vaga A vaga utilizada
     * @param servico O serviço contratado
     */
    public UsoTurno(Vaga vaga, Servico servico) {
        super(vaga, servico);
        this.horista = new UsoHorista(vaga, servico);
    }

    /**
     * Construtor classe UsoTurno
     * @param vaga A vaga utilizada
     */
    public UsoTurno(Vaga vaga) {
        super(vaga);
        this.horista = new UsoHorista(vaga);
    }

    /**
     * Construtor classe UsoTurno
     * @param vaga A vaga utilizada
     * @param entrada Data e hora de entrada
     * @param saida Data e hora de saída
     * @param valorPago Valor pago pelo uso por turno
     * @param servico O serviço contratado
     */
    public UsoTurno(Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico) {
        super(vaga, entrada, saida, valorPago, servico);
        this.horista = new UsoHorista(vaga, entrada, saida, valorPago, servico);
    }

    /**
     * Método que calcula o valor para um uso de turno. 
     * @return Valor a ser pago.
     */
    @Override
    public double valorPago() {
        LocalTime horaAtual = LocalTime.now();

        if (ehDoTurno(horaAtual)) {
            return 0d;
        } else {
            if(servico == null)
                return horista.sair();
            else{
                return horista.sair() - super.servico.getValor();
            }
        }
    }
    /**
     * Método que retorna se o uso está sendo feito no turno correto
     * @param momento hora do uso
     * @return false para fora do turno e true para dentro do turno.
     */
    public boolean ehDoTurno(LocalTime momento) {
        boolean dentroDoTurno = false;

        if (momento.isAfter(turno.getHoraInicial()) && momento.isBefore(turno.getHoraFinal())) {
            dentroDoTurno = true;
        }
        return dentroDoTurno;
    }

    public void setTurno(TURNO turno) {
        this.turno = turno;
    }
}