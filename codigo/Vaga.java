public class Vaga {

	private String idVaga;
	private boolean disponivel;
	private static int numeroSequencial = 1;
	private static char letraSequencial = 'A';

	/**
     * Construtor classe Vaga
     * @param idVaga O identificador único da vaga.
     */
	public Vaga(String idVaga) {
		this.idVaga = idVaga;
		this.disponivel = true;
	}

	/**
     * Construtor classe Vaga
     * @param fila O número da fila (A-Z).
     * @param numero O número da vaga.
     */
	public Vaga(int fila, int numero) {

		if (fila >= 0 && fila < 26) {

			char letra = (char) ('A' + fila);
			this.idVaga = letra + Integer.toString(numero);

		} else {

			this.idVaga = letraSequencial + Integer.toString(numeroSequencial);
			numeroSequencial++;
		}
		this.disponivel = true;
	}


	/**
	 * Método utilizado para ocupar uma vaga, ele valida se a vaga está vazia, e ocupa a vaga.
	 * @return Retorna falso se a vaga está ocupada, ou true se a vaga está vazia.
	 */

	public boolean estacionar() {

		if (disponivel) {
			disponivel = false;
			return true; 
		}
		return false; 
	}


	/**
	 * Método para informar se uma vaga está vazia ou não
	 * @return verdadeiro para vazia, e retorna falso para ocupada.
	 */
	public boolean disponivel() {
		return disponivel;
	}

	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}

	public String getIdVaga() {
		return idVaga;
	}

	@Override
	public String toString() {
		return "Vaga: " + idVaga;
	}
}