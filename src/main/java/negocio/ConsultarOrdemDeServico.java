package negocio;

import java.text.SimpleDateFormat;

import controle.ITransportador;
import dao.Idao;
import dao.implementacao.DaoOrdemDeServico;
import dominio.Apontamento;
import dominio.Atividade;
import dominio.Entidade;
import dominio.OrdemDeServico;
import dominio.Planejamento;
import dominio.Resultado;
import dominio.Tarefa;
import enuns.ESemafaro;

public class ConsultarOrdemDeServico implements IStrategy {

	public String processar(Entidade entidade) {
		
		Resultado resultado = (Resultado)entidade;
		Idao dao = new DaoOrdemDeServico();
		OrdemDeServico os = new OrdemDeServico();
		
		String data;
		
		os.setCodigo(null);
		
		// limpar lista de os de resultado
		resultado.getOrdens().clear();
		
		resultado.getOrdens().addAll(dao.listar(os));
		if(resultado.getOrdens().size() == 0){
			return "N?o ha dados!";
		}
		
		// filtrar por ano
		if(resultado.getMes().equals("vazio")){
			
			data = resultado.getAno();
			
			filtroAno(resultado, data);
		}else{
			
			// filtrar por mes e ano
			data = resultado.getMes() + "/" + resultado.getAno();
			
			filtroMesAno(resultado, data);
		}
		
		// verificar se h? dados em planejamento ou em apontamento
		if(resultado.getApontamentos().size() == 0 && resultado.getPlanejamentos().size() == 0){
			
			return "N?o h? nenhum dado a ser analizado!";
		}
		
		return null;
	}
	
	private void filtroMesAno(Resultado resultado, String data){
		
		SimpleDateFormat sdfMesAno = new SimpleDateFormat("MM/yyyy");
		
		// filtrar por m?s e ano
		for(OrdemDeServico o: resultado.getOrdens()){
			
			
			for(Atividade ati: o.getAtividades()){
				for(Tarefa tar: ati.getTarefas()){
					// pegar apontamentos
					
					for(Apontamento ap: tar.getApontamentos()){
						if(data.equals(sdfMesAno.format(ap.getDataFinal().getTime()))){
							
							resultado.getApontamentos().add(ap);
							
							try{
								//contar quantas horas ? gasta por tipo de manuten??o
								if(o.getTipoManutencao().equals("Preventiva")){
									resultado.getContApontamento().horasPreventiva += ap.getTotalHoras();
								}else if(o.getTipoManutencao().equals("Corretiva Emergencial")){
									resultado.getContApontamento().horasCorretivaEmergencial += ap.getTotalHoras();
								}
							
								// contar quantas horas por equipamento
								if(o.getEquipamento().getTipo().equals("Bomba Mec?nica")){
									resultado.getContApontamento().bombaMecanica += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Bebedouro")){
									resultado.getContApontamento().bebedouro += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Tanque")){
									resultado.getContApontamento().tanque += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Ventilador")){
									resultado.getContApontamento().ventilador += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Filtro")){
									resultado.getContApontamento().filtro += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Geladeira")){
									resultado.getContApontamento().geladeira += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Aquecedor")){
									resultado.getContApontamento().aquecedor += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Ar Condicionado")){
									resultado.getContApontamento().arCondicionado += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Desconhecido")){
									resultado.getContApontamento().desconhecido += ap.getTotalHoras();
								}
							}catch(Exception e){
								e.printStackTrace();
								if(o.getEquipamento().getTipo() == null){
									System.out.println(o.getEquipamento().getId());
								}
							}
						}
					}
					// pegar planejamentos
					for(Planejamento plan: tar.getPlanejamentos()){
						if(data.equals(sdfMesAno.format(plan.getDataFinal().getTime()))){
							
							resultado.getPlanejamentos().add(plan);
						
							try{
								//contar quantas horas ? gasta por tipo de manuten??o
								if(o.getTipoManutencao().equals("Preventiva")){
									resultado.getContPlanejamento().horasPreventiva += plan.getTotalHoras();
								}else if(o.getTipoManutencao().equals("Corretiva Emergencial")){
									resultado.getContPlanejamento().horasCorretivaEmergencial += plan.getTotalHoras();
								}
								
								// contar quantas horas por equipamento
								if(o.getEquipamento().getTipo().equals("Bomba Mec?nica")){
									resultado.getContPlanejamento().bombaMecanica += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Bebedouro")){
									resultado.getContPlanejamento().bebedouro += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Tanque")){
									resultado.getContPlanejamento().tanque += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Ventilador")){
									resultado.getContPlanejamento().ventilador += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Filtro")){
									resultado.getContPlanejamento().filtro += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Geladeira")){
									resultado.getContPlanejamento().geladeira += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Aquecedor")){
									resultado.getContPlanejamento().aquecedor += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Ar Condicionado")){
									resultado.getContPlanejamento().arCondicionado += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Desconhecido")){
									resultado.getContPlanejamento().desconhecido += plan.getTotalHoras();
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
	
	private void filtroAno(Resultado resultado, String data){
		
		SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
		
		// filtrar por ano
		for(OrdemDeServico o: resultado.getOrdens()){
			for(Atividade ati: o.getAtividades()){
				for(Tarefa tar: ati.getTarefas()){
					// pegar apontamentos
					for(Apontamento ap: tar.getApontamentos()){
						if(data.equals(sdfAno.format(ap.getDataFinal().getTime()))){
							
							resultado.getApontamentos().add(ap);
							
							try{
								//contar quantas horas ? gasta por tipo de manuten??o
								if(o.getTipoManutencao().equals("Preventiva")){
									resultado.getContApontamento().horasPreventiva += ap.getTotalHoras();
								}else if(o.getTipoManutencao().equals("Corretiva Emergencial")){
									resultado.getContApontamento().horasCorretivaEmergencial += ap.getTotalHoras();
								}
							
								// contar quantas horas por equipamento
								if(o.getEquipamento().getTipo().equals("Bomba Mec?nica")){
									resultado.getContApontamento().bombaMecanica += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Bebedouro")){
									resultado.getContApontamento().bebedouro += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Tanque")){
									resultado.getContApontamento().tanque += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Ventilador")){
									resultado.getContApontamento().ventilador += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Filtro")){
									resultado.getContApontamento().filtro += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Geladeira")){
									resultado.getContApontamento().geladeira += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Aquecedor")){
									resultado.getContApontamento().aquecedor += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Ar Condicionado")){
									resultado.getContApontamento().arCondicionado += ap.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Desconhecido")){
									resultado.getContApontamento().desconhecido += ap.getTotalHoras();
								}
							}catch(Exception e){
								e.printStackTrace();
								if(o.getEquipamento().getTipo() == null){
									System.out.println(o.getEquipamento().getId());
								}
							}
						}
					}
					// pegar planejamentos
					for(Planejamento plan: tar.getPlanejamentos()){
						if(data.equals(sdfAno.format(plan.getDataFinal().getTime()))){

							resultado.getPlanejamentos().add(plan);
						
							try{
								//contar quantas horas ? gasta por tipo de manuten??o
								if(o.getTipoManutencao().equals("Preventiva")){
									resultado.getContPlanejamento().horasPreventiva += plan.getTotalHoras();
								}else if(o.getTipoManutencao().equals("Corretiva Emergencial")){
									resultado.getContPlanejamento().horasCorretivaEmergencial += plan.getTotalHoras();
								}
								
								// contar quantas horas por equipamento
								if(o.getEquipamento().getTipo().equals("Bomba Mec?nica")){
									resultado.getContPlanejamento().bombaMecanica += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Bebedouro")){
									resultado.getContPlanejamento().bebedouro += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Tanque")){
									resultado.getContPlanejamento().tanque += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Ventilador")){
									resultado.getContPlanejamento().ventilador += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Filtro")){
									resultado.getContPlanejamento().filtro += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Geladeira")){
									resultado.getContPlanejamento().geladeira += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Aquecedor")){
									resultado.getContPlanejamento().aquecedor += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Ar Condicionado")){
									resultado.getContPlanejamento().arCondicionado += plan.getTotalHoras();
								}else if(o.getEquipamento().getTipo().equals("Desconhecido")){
									resultado.getContPlanejamento().desconhecido += plan.getTotalHoras();
								}
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean processar(ITransportador transportador) {
		// TODO Auto-generated method stub
		return false;
	}


}
