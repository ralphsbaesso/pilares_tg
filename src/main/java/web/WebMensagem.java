package web;

import java.util.Map;

import com.google.gson.Gson;

import controle.AbstractMensagem;

public class WebMensagem extends AbstractMensagem{
	
	public String enviarObjetoWeb(){
		
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	@Override
	public void recebeObjetoMensagem(AbstractMensagem obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> mapaObjetos() {
		// TODO Auto-generated method stub
		return null;
	}
}
