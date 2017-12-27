package enuns;

public enum EOperacao {
	
	SALVAR("salvar"), ALTERAR("alterar"), EXCLUIR("excluir"), LISTAR("listar");
	
	private String valor;
	
	EOperacao(String valor){
		this.valor = valor;
	}
	
	public String getValor() {
		return valor;
	}

}
