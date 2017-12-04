package controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Idao;
import dao.implementacao.*;
import dominio.*;
import enuns.ESemafaro;
import negocio.*;
import negocio.mapadenegocio.IMapaDeNegocio;
import negocio.mapadenegocio.MapaEspecialidade;

public class Fachada implements IFachada {
	
	//atributos
	private Map<String, IMapaDeNegocio> mapaEstrategias = new HashMap();
	private Map<String, Idao> mapaDao = new HashMap();
	private TransportadorFachada transportador = new TransportadorFachada();
	private List<IStrategy> estrategias;
	
	//construtor
	/**
	 * construtor
	 * Carrega todos os mapas de estratégias
	 */
	public Fachada(){
		this.mapaEstrategias.put(Especialidade.class.getName(),new MapaEspecialidade());
		this.mapaDao.put(Especialidade.class.getName(),new DaoEspecialidade());
		this.transportador.setSemafaro(ESemafaro.VERDE);
	}

	@Override
	public ITransportador salvar(Entidade entidade) {
		
		String nomeEntidade = entidade.getClass().getName();
		this.estrategias = this.mapaEstrategias.get(nomeEntidade).estrategiasSalvar();
		Idao dao = this.mapaDao.get(nomeEntidade);
		
		this.transportador.setEntidade(entidade);
		this.transportador.mapaObjetos().put("dao", dao);
		
		this.executarEstrategias(this.transportador);
		return this.transportador;
		
	}

	@Override
	public AbstractMensagem alterar(Entidade entidade) {
		
//		this.estrategias = entidade.getClass().getName();
//		List<IStrategy> regrasEntidade = this.mapaEstrategias.get(nomeEntidade).estrategiasAlterar();
//		Idao dao = this.mapaDao.get(nomeEntidade);
//		this.transportador.mapaObjetos().put("regras", dao);
//		
//		this.executarEstrategias(this.transportador);
		return this.transportador;
		
	}

	@Override
	public AbstractMensagem excluir(Entidade entidade) {

		String nomeEntidade = entidade.getClass().getName();
		this.estrategias = this.mapaEstrategias.get(nomeEntidade).estrategiasExcluir();
		Idao dao = this.mapaDao.get(nomeEntidade);
		
		this.transportador.setEntidade(entidade);
		this.transportador.mapaObjetos().put("regras", dao);
		
		this.executarEstrategias(this.transportador);
		return this.transportador;
		
	}

	@Override
	public AbstractMensagem listar(Entidade entidade) {

		String nomeEntidade = entidade.getClass().getName();
		this.estrategias = this.mapaEstrategias.get(nomeEntidade).estrategiasListar();
		Idao dao = this.mapaDao.get(nomeEntidade);
		
		this.transportador.setEntidade(entidade);
		this.transportador.mapaObjetos().put("dao", dao);
		
		this.executarEstrategias(this.transportador);
		return this.transportador;
		
	}
	
	private void executarEstrategias(ITransportador transportador) {
		
		for(IStrategy st: this.estrategias){
			
			st.processar(transportador);
			
			if(transportador.getSemafaro().getValor() >= ESemafaro.VERMELHO.getValor()) {
				return;
			}
		}
	}
}
