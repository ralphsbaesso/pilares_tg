package negocio;

import java.util.List;

import controle.ITransportador;
import dao.Idao;
import dao.implementacao.DaoMantenedor;
import dominio.Mantenedor;
import enuns.ESemafaro;

public class VerificarCpfExistente implements IStrategy {

	@SuppressWarnings("unchecked")
	@Override
	public boolean processar(ITransportador transportador) {
		
		Mantenedor mantenedor = (Mantenedor) transportador.getEntidade();
		Idao dao = new DaoMantenedor();
		List<Mantenedor> mans;
		
		mans = dao.listar(mantenedor);
		if(mans.size() > 0){
			transportador.setMensagens("CPF já cadastrado!");
			return transportador.setSemafaro(ESemafaro.AMARELO);
		}
		
		return true;
	}


}
