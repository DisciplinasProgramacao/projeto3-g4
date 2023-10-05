import java.util.ArrayList;
import java.util.List;

public class Veiculo {

    private String placa;
    private List<UsoDeVaga> usos;

    public Veiculo(String placa) {
        this.placa = placa;
        usos = new ArrayList<>();
    }

    public void estacionar(Vaga vaga) {
        if (vaga.disponivel()) {
            usos.add(new UsoDeVaga(vaga));
        }
    }

    public double sair() {
        int ultimaPosicao = usos.size() - 1;
        UsoDeVaga uso = usos.get(ultimaPosicao);
        uso.sair();
        return uso.valorPago();
    }

    public double totalArrecadado() {
        double totalPago = 0;
        for (UsoDeVaga uso : usos) {
            totalPago += uso.valorPago();
        }
        return totalPago;
    }

    public double arrecadadoNoMes(int mes) {
        double arrecadacao = 0d;
        for (UsoDeVaga uso : usos) {
            if (uso.ehDoMes(mes)) {
                arrecadacao += uso.valorPago();
            }
        }
        return arrecadacao;
    }

    public int totalDeUsos() {
        return usos.size();
    }
}
