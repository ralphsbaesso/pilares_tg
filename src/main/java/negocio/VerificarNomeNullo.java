package negocio;

import controle.ITransportador;
import dominio.CentroCusto;
import dominio.Entidade;
import dominio.Especialidade;
import dominio.Mantenedor;
import enuns.ESemafaro;

public class VerificarNomeNullo implements IStrategy {

	@Override
	public boolean processar(ITransportador transportador) {
		
		Entidade entidade = (Entidade) transportador.getEntidade();
		
		try {
			
			// verificar se eh uma instancia de Mantenedor
			if(entidade instanceof Mantenedor){
				Mantenedor man = (Mantenedor) entidade;
				// verificar se o atributo nome est? vazio ou se ? null
				if(man.getNome().equals("")){
					transportador.setMensagens("É obrigatório o preenchimento do nome!!!");
					return transportador.setSemafaro(ESemafaro.AMARELO);
				}
			}else if(entidade instanceof Especialidade){
				Especialidade esp = (Especialidade) entidade;
				// verificar se o atributo nome est? vazio ou se ? null
				if(esp.getDescricao().equals("")){
					transportador.setMensagens("É obrigatório o preenchimento da descrição");
					return transportador.setSemafaro(ESemafaro.AMARELO);
				}
			}else if(entidade instanceof CentroCusto){
				CentroCusto cc = (CentroCusto) entidade;
				// verificar se o atributo nome est? vazio ou se ? null
				if(cc.getDescricao().equals("")){
					transportador.setMensagens("É obrigatório o preenchimento da descrição");
					return transportador.setSemafaro(ESemafaro.AMARELO);
				}
			}
		}catch(NullPointerException e) {
			System.err.println("Atributo = NULL");
			transportador.setMensagens("Erro desconhecido");
			return transportador.setSemafaro(ESemafaro.VERMELHO);
		}catch(Exception e) {
			e.printStackTrace();
			return transportador.setSemafaro(ESemafaro.VERMELHO);
		}
		
		return true;
	}
}
