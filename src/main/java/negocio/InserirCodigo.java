package negocio;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import dao.Idao;
import dao.implementacao.DaoMantenedor;
import dominio.*;

public class InserirCodigo implements IStrategy {

	@Override
	public String processar(Entidade entidade) {
	
		// ordem de serviço
		if(entidade instanceof OrdemDeServico){
			// inserir código na os
			OrdemDeServico os = (OrdemDeServico)entidade;
			os.setCodigo(gerarCodigo(os));
			
			// inserir código nas atividades
			for(Atividade ati: os.getAtividades()){
				ati.setCodigo(gerarCodigo(ati));
				
				// inserir código nas tarefas
				for(Tarefa taf: ati.getTarefas()){
					taf.setCodigo(gerarCodigo(taf));
				}
			}
			return null;
		}
		
		// tarefa
		if(entidade instanceof Tarefa){
			Tarefa tarefa = (Tarefa)entidade;
			if(tarefa.getApontamentos().size() > 0){
				for(Apontamento ap: tarefa.getApontamentos()){
					ap.setCodigo(gerarCodigo(ap));
				}
			}
			if(tarefa.getPlanejamentos().size() > 0){
				for(Planejamento plan: tarefa.getPlanejamentos()){
					plan.setCodigo(gerarCodigo(plan));
				}
			}
			return null;
		}
		
		// especialidade
		if( entidade instanceof Especialidade){
			Especialidade especialidade = (Especialidade)entidade;
			especialidade.setCodigo(gerarCodigo(entidade));
			return null;
		}
		
		return "Erro na inserção do código";
	}
	
	private String gerarCodigo(Entidade ent){
		
		StringBuilder codigo = new StringBuilder();;
		
		try{
			
			//pegar a data
			
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
			
			String data = sdf.format(ent.getDataCadastro().getTime());
			
			codigo.append(data);
			
			// pegar os ultimos 4 digitos do Random
			
			Random r = new Random();
			
			String random = String.valueOf(r.nextInt(9999));
			
			codigo.append(random);
			
		}catch(Exception e){
			System.out.println("Erro na inserção do código");
			e.printStackTrace();
		}
		
		return codigo.toString();
	}

}
