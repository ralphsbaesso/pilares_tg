package dominio;

import java.util.ArrayList;
import java.util.List;

public class Atividade extends Entidade {

	
	private String descricao;
	private String codigo;
	private Especialidade especialidade = new Especialidade();
	private List<Tarefa> tarefas = new ArrayList();
	private OrdemDeServico os = new OrdemDeServico();
	private int qtdeTarefas;
	private double totalHorasEstimada;
	private double totalHorasApontada;
	
	
	public double getTotalHorasApontada() {
		double aux = 0;
		if(this.tarefas.size() == 0){
			aux = 0;
		}else{
			for(Tarefa t: this.tarefas){
				aux += t.getTotalHorasApontadas();
			}
		}
		totalHorasApontada = aux;
		return totalHorasApontada;
	}
	public int getQtdeTarefas() {
		return qtdeTarefas = tarefas.size();
	}
	public double getTotalHorasEstimada() {
		double aux = 0;
		if(tarefas.size() == 0){
			aux = 0;
		}else{
			for(Tarefa t: tarefas){
				aux += t.getTotalHorasEstimada();
			}
			totalHorasEstimada = aux;
		}
		return totalHorasEstimada;
	}
	
	public OrdemDeServico getOs() {
		return os;
	}
	public void setOs(OrdemDeServico os) {
		this.os = os;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Especialidade getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	public List<Tarefa> getTarefas() {
		return tarefas;
	}
	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String numero) {
		this.codigo = numero;
	}
	
	
}
