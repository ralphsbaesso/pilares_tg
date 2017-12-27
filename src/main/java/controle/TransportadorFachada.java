package controle;

import java.util.HashMap;
import java.util.Map;

import dao.Idao;
import enuns.ESemafaro;

public class TransportadorFachada extends ATransportador {

	private Map<String, Idao> mapaDao = new HashMap();
	
	public TransportadorFachada() {
		// garantir que o semafaro inicialize com VERDE
		this.semafaro = ESemafaro.VERDE;
	}

	public Map<String, Idao> mapaObjetos() {
		
		return mapaDao;
	}

}
