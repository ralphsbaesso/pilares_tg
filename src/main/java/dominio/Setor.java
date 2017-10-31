package dominio;

public class Setor extends Entidade {
	
	private String nome;
	private CentroCusto centroCusto = new CentroCusto();
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public CentroCusto getCentroCusto() {
		return centroCusto;
	}
	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}
	

}
