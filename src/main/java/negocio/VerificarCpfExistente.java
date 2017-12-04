package negocio;

import java.util.List;

import controle.ITransportador;
import dao.Idao;
import dao.implementacao.DaoMantenedor;
import dominio.Entidade;

public class VerificarCpfExistente implements IStrategy {

	public String processar(Entidade entidade) {
		
		Idao dao = new DaoMantenedor();
		List mans;
		
		mans = dao.listar(entidade);
		if(mans.size() > 0){
			return "CPF j? cadastrado! ";
		}
		return null;
	}

	@Override
	public void processar(ITransportador transportador) {
		// TODO Auto-generated method stub
		
	}

}
