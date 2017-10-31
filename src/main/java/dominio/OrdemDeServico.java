package dominio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrdemDeServico extends Entidade {
	
	private String codigo;
	private Mantenedor autor = new Mantenedor();
	private String criticidade;
	private String status;
	private String titulo;
	private String observacao;
	private Equipamento equipamento = new Equipamento();
	private String tipoManutencao;
	private List<Atividade> atividades = new ArrayList();
	private Calendar dataLimite = Calendar.getInstance();
	protected Calendar dataEncerramento = Calendar.getInstance();
	private int qtdeAtividades = 0;
	private double totalHorasEstimada = 0;
	private String encerrada = "";
	private int qtdeTarefas;
	
	
	public int getQtdeTarefas() {
		
		int aux = 0;
		for(Atividade ati: this.getAtividades()){
			aux = ati.getTarefas().size();
		}
		return qtdeTarefas = aux;
	}
	public String getEncerrada() {
		return encerrada;
	}
	public void setEncerrada(String encerrada) {
		this.encerrada = encerrada;
	}
	public int getQtdeAtividades() {
		return qtdeAtividades = atividades.size();
	}
	
	public double getTotalHorasEstimada() {
		if(atividades.size() == 0){
			totalHorasEstimada = 0;
		}else{
			double aux = 0;
			for(Atividade a: atividades){
				aux += a.getTotalHorasEstimada();
			}
			totalHorasEstimada = aux;
		}
		return totalHorasEstimada;
	}
	

	public Mantenedor getAutor() {
		return autor;
	}

	public void setAutor(Mantenedor autor) {
		this.autor = autor;
	}

	public Calendar getDataEncerramento() {
		return dataEncerramento;
	}

	public void setDataEncerramento(Calendar dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}
	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCriticidade() {
		return criticidade;
	}
	public void setCriticidade(String prioridade) {
		this.criticidade = prioridade;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Equipamento getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
	public String getTipoManutencao() {
		return tipoManutencao;
	}
	public void setTipoManutencao(String tipoManutencao) {
		this.tipoManutencao = tipoManutencao;
	}
	public Calendar getDataLimite() {
		return dataLimite;
	}
	public void setDataLimite(Calendar dataLimite) {
		this.dataLimite = dataLimite;
	}
	
	
	

}
