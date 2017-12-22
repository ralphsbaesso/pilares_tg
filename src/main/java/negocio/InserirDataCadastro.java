package negocio;

import java.util.Calendar;

import controle.ITransportador;
import dominio.Entidade;

public class InserirDataCadastro implements IStrategy {

	@Override
	public boolean processar(ITransportador transportador) {
		
		Entidade entidade = (Entidade) transportador.getEntidade();
		Calendar data = Calendar.getInstance();
		entidade.setDataCadastro(data);
		
		return true;
	}
}
