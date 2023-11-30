import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UsoFactory {

    private Map<String, IFactory<UsoDeVaga>> factories;

    public UsoFactory(){
        factories = new HashMap<>();
        factories.put("horista", new FabricaUsoHorista());
        factories.put("mensalista", new FabricaUsoMensalista());
        factories.put("turno", new FabricaUsoTurno());
    }

    public UsoDeVaga get(String desc, Vaga vaga){
        return factories.get(desc).create(vaga);
    }

    public UsoDeVaga get(String desc, Vaga vaga, Servico servico){
        return factories.get(desc).create(vaga, servico);
    }

    public UsoDeVaga get(String desc, Vaga vaga, LocalDateTime entrada, LocalDateTime saida, double valorPago, Servico servico){
        return factories.get(desc).create(vaga, entrada, saida, valorPago, servico);
    }
}
