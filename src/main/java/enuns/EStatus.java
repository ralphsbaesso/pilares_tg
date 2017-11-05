package enuns;

public enum EStatus {
	
	VERDE(1), AMARELO(0), VERMELHO(-1);
	
	public int valor;
	
	EStatus(int valor){
		this.valor = valor;
	}

}
