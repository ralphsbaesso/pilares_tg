package controle;

import java.util.ArrayList;
import java.util.List;

import dominio.Entidade;
import enuns.ESemafaro;

public abstract class AbstractMensagem implements ITransportador{
	
	protected Object entidade = new Entidade();
	protected List<String> mensagens = new ArrayList();
	protected List<Entidade> entidades = new ArrayList();
	protected ESemafaro semafaro = ESemafaro.VERDE;

	public List<String> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<String> mensagens) {
		if(mensagens == null)
			return;
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
	
	public Object getEntidade() {
		return entidade;
	}
	
	public void setEntidade(Object entidade) {
		this.entidade = entidade;
	}

	public void setEntidades(List<Entidade> entidades) {
		this.entidades = entidades;
	}

	public void setEntidades(Object entidade) {

		this.entidades.add((Entidade) entidade);
	}

	public ESemafaro getSemafaro() {
		return semafaro;
	}

	public void setSemafaro(ESemafaro semafaro) {
		this.semafaro = semafaro;
	}
	
	public void recebeObjetoMensagem(ITransportador obj){
		try {
			this.entidade = Class.forName(obj.getEntidade().getClass().getName()).cast(obj.getEntidade());
			this.entidades = obj.getEntidades();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mensagens = obj.getMensagens();
		this.semafaro = obj.getSemafaro();
	}

}
