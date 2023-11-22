public enum Turno {

  HORISTA(0.25, 4.0, 50.0, 0.0), 
  TURNO(0.0, 0.0, 0.0, 200.0), 
  MENSALIDADE(0.0, 0.0, 0.0, 500.0);

  private final double fracaoUso;
  private final double valorFracao;
  private final double valorMaximo;
  private final double mensalidade;

  Turno(double fracaoUso, double valorFracao, double valorMaximo, double mensalidade) {
    this.fracaoUso = fracaoUso;
    this.valorFracao = valorFracao;
    this.valorMaximo = valorMaximo;
    this.mensalidade = mensalidade;
  }
  public double getFracaoUso() {
    return fracaoUso;
  }

  public double getValorFracao() {
    return valorFracao;
  }

  public double getValorMaximo() {
    return valorMaximo;
  }

  public double getMensalidade() {
    return mensalidade;
  }
}