package negocio;

import dominio.Entidade;
import dominio.Mantenedor;

public class VerificarEspecialidade implements IStrategy {

	@Override
	public String processar(Entidade entidade) {
		
		// verificar se eh uma instancia de Mantenedor
		if(entidade instanceof Mantenedor){
			Mantenedor man = (Mantenedor) entidade;
			// verificar se h? pelo menos uma especialidade
			if(man.getEspecialidades().size() < 1){
				return "? obrigat?rio escolher uma especialidade!!!";
			}
		}
		return null;
	}

}
