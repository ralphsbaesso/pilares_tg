package web;

import com.google.gson.Gson;

import controle.AbstractMensagem;

public class WebMensagem extends AbstractMensagem{
	
	public String enviarObjetoWeb(){
		
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
