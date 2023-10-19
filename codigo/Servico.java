public enum Servico {

    MANOBRISTA("Manobrista",5.0,0),
    LAVAGEM("Lavagem",20.0,60),
    POLIMENTO("Polimento",45.0, 120);


    private String nome;
    private Double valor;
    private int tempo;

    Servico(String nome, double valor, int tempo){

        this.nome= nome;
        this.valor = valor;
        this.tempo = tempo;   
    }


    public String getNome() {
        return nome;
    }

    public int getTempo() {
        return tempo;
    }
    
    public Double getValor() {
        return valor;
    }

    
    
}
