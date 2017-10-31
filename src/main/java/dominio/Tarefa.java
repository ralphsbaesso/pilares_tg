package dominio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Tarefa extends Entidade {

	// variavaies para o banco de dados
	private String codigo;
	private String detalhamento;
	private double qtdeHoraEstimada;
	private int qtdeHomemEstimado;
	private Atividade atividade = new Atividade();
	
	private double totalHorasEstimada;
	private double totalHorasApontadas;
	private double totalHorasPlanejadas;
	private int qtdeApontamentos;
	private int qtdePlanejamentos;
	private List<Apontamento> apontamentos = new ArrayList();
	private List<Planejamento> planejamentos = new ArrayList();
	
	
	public double getQtdeHoraEstimada() {
		return qtdeHoraEstimada;
	}
	public void setQtdeHoraEstimada(double qtdeHoraEstimada) {
		this.qtdeHoraEstimada = qtdeHoraEstimada;
	}
	public int getQtdeHomemEstimado() {
		return qtdeHomemEstimado;
	}
	public void setQtdeHomemEstimado(int qtdeHomemEstimado) {
		this.qtdeHomemEstimado = qtdeHomemEstimado;
	}
	public List<Apontamento> getApontamentos() {
		return apontamentos;
	}
	public void setApontamentos(List<Apontamento> apontamentos) {
		this.apontamentos = apontamentos;
	}
	public List<Planejamento> getPlanejamentos() {
		return planejamentos;
	}
	public void setPlanejamentos(List<Planejamento> planejamentos) {
		this.planejamentos = planejamentos;
	}
	public double getTotalHorasApontadas() {
		if(apontamentos.size() == 0){
			totalHorasApontadas = 0;
		}else{
			double aux = 0;
			for(Apontamento a: apontamentos){
				aux += a.getTotalHoras();
			}
			totalHorasApontadas = aux;
		}
		return totalHorasApontadas;
	}
	public double getTotalHorasPlanejadas() {
		if(planejamentos.size() == 0){
			totalHorasApontadas = 0;
		}else{
			double aux = 0;
			for(Planejamento p: planejamentos){
				aux += p.getTotalHoras();
			}
			totalHorasPlanejadas = aux;
		}
		return totalHorasPlanejadas;
	}
	public int getQtdeApontamentos() {
		return qtdeApontamentos = apontamentos.size();
	}
	public int getQtdePlanejamentos() {
		return qtdePlanejamentos = planejamentos.size();
	}
	public double getTotalHorasEstimada() {
		// calcular o total de horas de tarefa
		return totalHorasEstimada = this.qtdeHoraEstimada * (double)this.qtdeHomemEstimado;
	}
	public Atividade getAtividade() {
		return atividade;
	}
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	public String getDetalhamento() {
		return detalhamento;
	}
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}
