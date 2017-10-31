package negocio;

import java.util.Calendar;

import dominio.Entidade;

public class InserirDataCadastro implements IStrategy {

	@Override
	public String processar(Entidade entidade) {
		Calendar data = Calendar.getInstance();
		entidade.setDataCadastro(data);
		
		return null;
	}

}
