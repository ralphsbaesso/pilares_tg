package controle;

import java.util.ArrayList;
import java.util.List;

import dominio.Entidade;
import enuns.EStatus;

public abstract class AbstractMensagem {

	protected List<String> mensagens = new ArrayList();
	protected List<Entidade> entidades = new ArrayList();
	protected EStatus status = EStatus.VERDE;

	public List<String> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<String> mensagens) {
		this.mensagens = mensagens;
	}
	public void setMensagens(String mensagem){
		if(mensagem == null || mensagem == "")
			return;
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

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}
	
	public void recebeObjetoMensagem(AbstractMensagem obj){
		this.entidades = obj.entidades;
		this.mensagens = obj.mensagens;
		this.status = obj.status;
	}

}
