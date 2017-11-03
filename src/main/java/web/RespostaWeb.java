package web;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

import dominio.Entidade;

public class RespostaWeb {
	
	private String status;
	private String mensagem;
	private List<Entidade> entidades = new ArrayList<>();
	
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public List<Entidade> getEntidades() {
		return entidades;
	}
	public void setEntidades(Object entidade) {
		
		if(entidade instanceof Entidade){
			this.entidades.add((Entidade)entidade);
		}else if(entidade instanceof List){
			this.entidades.addAll((List<Entidade>)entidade);
		}
	}
	
	public String enviarObjetoWeb(){
		
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
