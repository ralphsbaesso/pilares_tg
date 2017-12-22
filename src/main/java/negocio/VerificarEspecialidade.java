package negocio;

import controle.ITransportador;
import dominio.Entidade;
import dominio.Mantenedor;
import enuns.ESemafaro;

public class VerificarEspecialidade implements IStrategy {

	public String processar(Entidade entidade) {
		
		// verificar se eh uma instancia de Mantenedor
		if(entidade instanceof Mantenedor){
			Mantenedor man = (Mantenedor) entidade;
			// verificar se h? pelo menos uma especialidade
			if(man.getEspecialidades().size() < 1){
				return "É obrigatório escolher uma especialidade!!!";
			}
		}
		return null;
	}

	@Override
	public boolean processar(ITransportador transportador) {
		// TODO Auto-generated method stub
		return false;
	}


}
