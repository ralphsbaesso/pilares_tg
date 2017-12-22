package negocio;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import controle.ITransportador;
import dao.Idao;
import dao.implementacao.DaoMantenedor;
import dominio.*;
import enuns.ESemafaro;

public class VerificarData implements IStrategy {

	public String processar(Entidade entidade) {
		
		// verifica data de ordem de servi?o
		if(entidade instanceof OrdemDeServico){
			
			OrdemDeServico om = (OrdemDeServico)entidade;
			
			Calendar agora = Calendar.getInstance();
			
			long d = om.getDataLimite().getTimeInMillis() - agora.getTimeInMillis();
			d = d/(24*60*60*1000);
			if(d < 0){
				// data limite ? inferior a data atual
				d *= -1;
				return "Data limite ? inferiro a data atual em " + d + " Dias!!!";
			}
			
			return null;
		}
		
		if(entidade instanceof Tarefa){
			
			double d;
			StringBuilder sb = new StringBuilder();
			
			Tarefa tarefa = (Tarefa)entidade;
			if(tarefa.getApontamentos().size() > 0){
				for(int i = 0; i < tarefa.getApontamentos().size(); i++){
					d = tarefa.getApontamentos().get(i).getDataFinal().getTimeInMillis()
							- tarefa.getApontamentos().get(i).getDataInicial().getTimeInMillis();
					d = d/(60*60*1000);
					if(d < 0){
						d *= -1;
						// data final inferior a data inicial
						sb.append("Data final ? superior a data inicial em " + d + " Horas na linha " + (i + 1));
						sb.append("\n");
					}
				}
			}
			if(tarefa.getPlanejamentos().size() > 0){
				for(int i = 0; i < tarefa.getPlanejamentos().size(); i++){
					d = tarefa.getPlanejamentos().get(i).getDataFinal().getTimeInMillis()
							- tarefa.getPlanejamentos().get(i).getDataInicial().getTimeInMillis();
					d = d/(60*60*1000);
					if(d < 0){
						d *= -1;
						// data final inferior a data inicial
						sb.append("Data final ? superior a data inicial em " + d + " Horas na linha " + (i + 1));
						sb.append("\n");
					}
				}
			}
			
			// tem mensagem
			if(sb.length() > 0){ 
				return sb.toString();
			}else{
				return null;
			}
		} // if - tarefa
		
		return null;
	} // processar

	@Override
	public boolean processar(ITransportador transportador) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
