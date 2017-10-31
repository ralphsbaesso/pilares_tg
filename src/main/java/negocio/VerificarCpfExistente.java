package negocio;

import java.util.List;

import dao.Idao;
import dao.implementacao.DaoMantenedor;
import dominio.Entidade;
import dominio.Mantenedor;

public class VerificarCpfExistente implements IStrategy {

	@Override
	public String processar(Entidade entidade) {
		
		Idao dao = new DaoMantenedor();
		List mans;
		
		mans = dao.listar(entidade);
		if(mans.size() > 0){
			return "CPF j? cadastrado! ";
		}
		return null;
	}

}
