package dominio;

import java.util.Calendar;

public abstract class AbstractAcao extends Entidade {
	
	protected String codigo;
	protected Tarefa tarefa = new Tarefa();
	protected String detalhamento;
	protected Calendar dataInicial = Calendar.getInstance();
	protected Calendar dataFinal = Calendar.getInstance();
	/**
	 * respons?vel pelo o preechimento da a??o
	 */
	protected Mantenedor autor = new Mantenedor();
	/**
	 * respons?vel pela a execu??o da a??o
	 */
	protected Mantenedor executante = new Mantenedor();
	protected double totalHoras;
	/**
	 * cancelado a a??o
	 * sim - cancelado
	 * n?o - n?o cancelado
	 */
	protected String cancelado;
	
	
	public String getCancelado() {
		return cancelado;
	}
	public void setCancelado(String cancelado) {
		this.cancelado = cancelado;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Tarefa getTarefa() {
		return tarefa;
	}
	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	public String getDetalhamento() {
		return detalhamento;
	}
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	public Calendar getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Calendar dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Calendar getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Calendar dataFinal) {
		this.dataFinal = dataFinal;
	}
	public Mantenedor getAutor() {
		return autor;
	}
	public void setAutor(Mantenedor autor) {
		this.autor = autor;
	}
	public Mantenedor getExecutante() {
		return executante;
	}
	public void setExecutante(Mantenedor executante) {
		this.executante = executante;
	}
	public double getTotalHoras() {
		totalHoras = (this.getDataFinal().getTimeInMillis() - this.getDataInicial().getTimeInMillis())/ Double.valueOf((60*60*1000));
		return totalHoras;
	}
	

}
