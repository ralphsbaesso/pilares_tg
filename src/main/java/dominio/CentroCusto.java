package dominio;

public class CentroCusto extends Entidade {
	
	private String numeroCC;
	private String nome;

	public String getNumeroCC() {
		return numeroCC;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setNumeroCC(String numeroCC) {
		this.numeroCC = numeroCC;
	}
	
}
