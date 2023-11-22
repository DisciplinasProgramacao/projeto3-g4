import java.util.Set;


public enum CATEGORIA {

  HORISTA(Set.of(VALORES.VALOR_FRACAO, VALORES.VALOR_MAXIMO, VALORES.FRACAO_USO)),
  TURNO(Set.of(VALORES.T_MENSAL)),
  MENSALISTA(Set.of(VALORES.MENSALIDADE));

  private Set<VALORES> values;

  CATEGORIA(Set<VALORES> values) {
    this.values = values;
  }

  public Set<VALORES> getValues() {
    return values;
  }
}
