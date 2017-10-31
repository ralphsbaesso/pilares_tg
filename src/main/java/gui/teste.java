package gui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import dao.Idao;
import dao.implementacao.DaoOrdemDeServico;
import dominio.Apontamento;
import dominio.Atividade;
import dominio.OrdemDeServico;
import dominio.Planejamento;
import dominio.Resultado;
import dominio.Tarefa;

public class teste {


	public static void main(String[] args) throws ParseException, IOException{
		
		Resultado r = new Resultado();
		Idao dao = new DaoOrdemDeServico();
		OrdemDeServico os = new OrdemDeServico();
		Atividade ati = new Atividade();
		Tarefa tar = new Tarefa();
		Apontamento ap = new Apontamento();
		Planejamento plan = new Planejamento();
		
		os.setCodigo("mesAno");
		os.setObservacao("01/2017");
		
		System.out.println("teste teste");
		
		r.setOrdens(dao.listar(os));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		SimpleDateFormat sdfMesAno = new SimpleDateFormat("MM/yyyy");
		
		String mes = "01";
		String ano = "2017";
		String data = mes + "/" + ano;
		
		
		try {
			ap.getDataFinal().setTime(sdfMesAno.parse(data));
			System.out.println(sdfMesAno.format(ap.getDataFinal().getTime()) + " - " + ap.toString());
		} catch (ParseException e) {
			System.out.println("Erro 1");
			e.printStackTrace();
		}
		
		// inserir data de pesquisa no resultado atrav�s de apontamento
		tar.getApontamentos().add(ap);
		ati.getTarefas().add(tar);
		os.getAtividades().add(ati);
		//r.getOrdens().add(os);
		
		//r.getOrdens().addAll(dao.listar(os));
		
		for(OrdemDeServico o: r.getOrdens()){
			for(Atividade ativ: o.getAtividades()){
				for(Tarefa tare: ativ.getTarefas()){
					// pegar apontamentos
					for(Apontamento apo: tare.getApontamentos()){
						System.out.println("Apontamentos Inicial: " + sdf.format(apo.getDataInicial().getTime()) 
						+ " final: " + sdf.format(apo.getDataFinal().getTime()) 
						+ " Total: " + apo.getTotalHoras() + " id: " + apo.getId());
					}
					// pegar planejamentos
					for(Planejamento pla: tare.getPlanejamentos()){
						System.out.println("Planejamemto Inicial: " + sdf.format(pla.getDataInicial().getTime()) 
						+ " final: " + sdf.format(pla.getDataFinal().getTime()) 
						+ " Total: " + pla.getTotalHoras()+ " id: " + pla.getId());
					}
				}
			}
		}
		System.out.println("teste teste");
		System.out.println("teste teste");
	}

}
