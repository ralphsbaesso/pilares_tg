package controle;

import java.util.ArrayList;
import java.util.List;

import dominio.Entidade;
import enuns.EStatus;

public abstract class AbstractMensagem {
	
	protected Object entidade = new Entidade();
	protected List<String> mensagens = new ArrayList();
	protected Object entidades = new ArrayList();
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

	public Object getEntidades() {
		return entidades;
	}
	
	public Object getEntidade() {
		return entidade;
	}
	
	public void setEntidade(Object entidade) {
		this.entidade = entidade;
	}

	public void setEntidades(List<Object> entidades) {
		this.entidades = entidades;
	}

	public void setEntidades(Object entidade) {

		((List)this.entidades).add(entidade);
	}

	public EStatus getStatus() {
		return status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}
	
	public void recebeObjetoMensagem(AbstractMensagem obj){
		try {
			this.entidade = Class.forName(obj.entidade.getClass().getName()).cast(obj.entidade);
			this.entidades = Class.forName(obj.entidades.getClass().getName()).cast(obj.entidades);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mensagens = obj.mensagens;
		this.status = obj.status;
	}

}
