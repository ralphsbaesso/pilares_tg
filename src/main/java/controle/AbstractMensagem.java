package controle;

import java.util.ArrayList;
import java.util.List;

import dominio.Entidade;

public abstract class AbstractMensagem {

	protected List<String> mensagens = new ArrayList();
	protected List<Entidade> entidades = new ArrayList();
	protected String status;

	public List<String> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<String> mensagens) {
		this.mensagens = mensagens;
	}
	public void addMensagem(String mensagem){
		this.mensagens.add(mensagem);
	}

	public List<Entidade> getEntidades() {
		return entidades;
	}

	public void setEntidades(List<Entidade> entidades) {
		this.entidades = entidades;
	}

	public void setEntidades(Entidade entidade) {

		this.entidades.add(entidade);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
