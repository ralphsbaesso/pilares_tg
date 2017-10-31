package negocio;

import dominio.Entidade;
import dominio.Mantenedor;
import dominio.OrdemDeServico;

public class VerificarNomeNullo implements IStrategy {

	@Override
	public String processar(Entidade entidade) {
		
		// verificar se eh uma instancia de Mantenedor
		if(entidade instanceof Mantenedor){
			Mantenedor man = (Mantenedor) entidade;
			// verificar se o atributo nome est? vazio ou se ? null
			if(man.getNome().equals("") || man.getNome().equals(null)){
				return "? obrigat?rio o preenchimento do nome!!!";
			}
		}
		return null;
	}

}
