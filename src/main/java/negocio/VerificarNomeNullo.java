package negocio;

import controle.ITransportador;
import dominio.Entidade;
import dominio.Especialidade;
import dominio.Mantenedor;
import enuns.ESemafaro;

public class VerificarNomeNullo implements IStrategy {

	@Override
	public boolean processar(ITransportador transportador) {
		
		Entidade entidade = (Entidade) transportador.getEntidade();
		
		// verificar se eh uma instancia de Mantenedor
		if(entidade instanceof Mantenedor){
			Mantenedor man = (Mantenedor) entidade;
			// verificar se o atributo nome est? vazio ou se ? null
			if(man.getNome().equals("") || man.getNome().equals(null)){
				transportador.setMensagens("É obrigatório o preenchimento do nome!!!");
				return transportador.setSemafaro(ESemafaro.AMARELO);
			}
		}
		if(entidade instanceof Especialidade){
			Especialidade esp = (Especialidade) entidade;
			// verificar se o atributo nome est? vazio ou se ? null
			if(esp.getDescricao().equals("") || esp.getDescricao().equals(null)){
				transportador.setMensagens("É obrigatório o preenchimento da descrição");
				return transportador.setSemafaro(ESemafaro.AMARELO);
			}
		}
		
		return true;
	}
}
