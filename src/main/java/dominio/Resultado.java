package dominio;

import java.util.ArrayList;
import java.util.List;

public class Resultado extends Entidade{

	private List<OrdemDeServico> ordens = new ArrayList();
	private String mes;
	private String ano;
	private List<Planejamento> planejamentos = new ArrayList();
	private List<Apontamento> apontamentos = new ArrayList();
	private Contagem contPlanejamento = new Contagem();
	private Contagem contApontamento = new Contagem();
	
	
	public Contagem getContApontamento() {
		return contApontamento;
	}

	public void setContApontamento(Contagem contApontamento) {
		this.contApontamento = contApontamento;
	}

	public Contagem getContPlanejamento() {
		return contPlanejamento;
	}

	public void setContPlanejamento(Contagem contPlanejamento) {
		this.contPlanejamento = contPlanejamento;
	}

	public List<Planejamento> getPlanejamentos() {
		return planejamentos;
	}

	public void setPlanejamentos(List<Planejamento> planejamentos) {
		this.planejamentos = planejamentos;
	}

	public List<Apontamento> getApontamentos() {
		return apontamentos;
	}

	public void setApontamentos(List<Apontamento> apontamentos) {
		this.apontamentos = apontamentos;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public List<OrdemDeServico> getOrdens() {
		return ordens;
	}

	public void setOrdens(List<OrdemDeServico> ordens) {
		this.ordens = ordens;
	}
	
	// Classe para contagem
	public class Contagem{
		public double qtdePreventiva = 0;
		public double qtdeCorretivaEmergencial = 0;
		public double horasPreventiva = 0;
		public double horasCorretivaEmergencial = 0;
		public double bombaMecanica= 0;
		public double bebedouro= 0;
		public double tanque= 0;
		public double ventilador= 0;
		public double filtro= 0;
		public double geladeira= 0;
		public double aquecedor= 0;
		public double arCondicionado= 0;
		public double desconhecido = 0;
	}
	
}
