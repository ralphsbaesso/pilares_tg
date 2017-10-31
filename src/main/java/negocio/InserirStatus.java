package negocio;

import java.text.SimpleDateFormat;
import java.util.List;

import dao.Idao;
import dao.implementacao.DaoMantenedor;
import dominio.*;

public class InserirStatus implements IStrategy {

	@Override
	public String processar(Entidade entidade) {
		
		if(entidade instanceof OrdemDeServico){
			// atualizar status
			OrdemDeServico os = (OrdemDeServico)entidade;
			os.setStatus("aberta");
		}
		return null;
	}
	
}
