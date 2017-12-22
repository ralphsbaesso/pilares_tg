package negocio;

import controle.ITransportador;
import dominio.Entidade;
import dominio.Mantenedor;
import dominio.OrdemDeServico;
import enuns.ESemafaro;

public class VerificarCamposVazioOS implements IStrategy {

	public String processar(Entidade entidade) {
		
		StringBuilder sb = new StringBuilder();
		
		// verificar se eh uma instancia de Mantenedor
		if(entidade instanceof OrdemDeServico){
			OrdemDeServico os = (OrdemDeServico) entidade;
			
			// verificar se Autor est? vazio. Se for zero ? porque est? vazio
			if(os.getAutor().getId() == 0){
				sb.append("É obrigatório o preenchimento do campo 'Autor'");
				sb.append("\n");
			}
			
			// verificar campo T?tulo est? vazio
			if(os.getTitulo().equals("") || os.getTitulo().equals(null)){
				sb.append("? obrigat?rio o preenchimento do T?tulo!!!");
				sb.append("\n");
			}
			
			// verificar campo equipamento
			if(os.getEquipamento().getId() == 0){
				sb.append("? obrigat?rio o preenchimento do campo Equipamento!");
				sb.append("\n");
			}
			
			// verificar criticidade
			if(os.getCriticidade().equals("") || os.getCriticidade().equals(null)){
				sb.append("? obrigat?rio o preenchimento do campo Criticidade");
				sb.append("\n");
			}
		}
		if(sb.length() > 0){
			return sb.toString();
		}else{
			return null;
		}
	}

	@Override
	public boolean processar(ITransportador transportador) {
		// TODO Auto-generated method stub
		return false;
	}


}
