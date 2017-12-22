package negocio;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import controle.ITransportador;
import dao.Idao;
import dao.implementacao.DaoMantenedor;
import dominio.*;
import enuns.ESemafaro;

public class InserirCodigo implements IStrategy {

	@Override
	public boolean processar(ITransportador transportador) {
		
		Entidade entidade = (Entidade) transportador.getEntidade();
	
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
			return true;
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
			return true;
		}
		
		// especialidade
		if( entidade instanceof Especialidade){
			Especialidade especialidade = (Especialidade)entidade;
			especialidade.setCodigo(gerarCodigo(entidade));
			return true;
		}
		
		transportador.setMensagens("Erro na inserção do código");
		return transportador.setSemafaro(ESemafaro.AMARELO);
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
