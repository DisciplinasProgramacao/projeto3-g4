public enum VALORES {
    FRACAO_USO(0.25),
    VALOR_FRACAO(4.0),
    VALOR_MAXIMO(50.0),

    T_MENSAL(200.0),

    MENSALIDADE(500.0);

    private double valor;

    public double getValor() {
      return valor;
    }

    private VALORES(double valor) {
      this.valor = valor;
    }
  }
  

