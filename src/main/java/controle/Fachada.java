package controle;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;

import dao.Idao;
import dao.implementacao.Conexao;
import dao.implementacao.DaoCentroCusto;
import dao.implementacao.DaoEspecialidade;
import dao.implementacao.DaoMantenedor;
import dominio.CentroCusto;
import dominio.Entidade;
import dominio.Especialidade;
import dominio.Mantenedor;
import negocio.IStrategy;
import negocio.mapadenegocio.IMapaDeNegocio;
import negocio.mapadenegocio.MapaCentroCusto;
import negocio.mapadenegocio.MapaEspecialidade;
import negocio.mapadenegocio.MapaMantenedor;

public class Fachada implements IFachada {

	// atributos
	private Map<String, IMapaDeNegocio> mapaEstrategias = new HashMap();
	private Map<String, Idao> mapaDao = new HashMap();
	private TransportadorFachada transportador;
	private List<IStrategy> estrategias;

	// construtor
	/**
	 * construtor Carrega todos os mapas de estrat√©gias
	 */
	public Fachada() {

		// Carregar mapa de estrat√©gias
		this.mapaEstrategias.put(Especialidade.class.getName(), new MapaEspecialidade());
		this.mapaEstrategias.put(Mantenedor.class.getName(), new MapaMantenedor());
		this.mapaEstrategias.put(CentroCusto.class.getName(), new MapaCentroCusto());

		// Carregar mapa de DAO
		this.mapaDao.put(Especialidade.class.getName(), new DaoEspecialidade());
		this.mapaDao.put(Mantenedor.class.getName(), new DaoMantenedor());
		this.mapaDao.put(CentroCusto.class.getName(), new DaoCentroCusto());
	}

	@Override
	public ITransportador salvar(Entidade entidade) {

		String nomeEntidade = entidade.getClass().getName();
		this.transportador = new TransportadorFachada();
		Idao dao = this.mapaDao.get(nomeEntidade);
		this.estrategias = this.mapaEstrategias.get(nomeEntidade).estrategiasSalvar();

		this.transportador.setEntidade(entidade);
		this.transportador.mapaObjetos().put("dao", dao);

		this.executarEstrategias(this.transportador);
		return this.transportador;

	}

	@Override
	public ATransportador alterar(Entidade entidade) {

		String nomeEntidade = entidade.getClass().getName();
		this.transportador = new TransportadorFachada();
		Idao dao = this.mapaDao.get(nomeEntidade);
		this.estrategias = this.mapaEstrategias.get(nomeEntidade).estrategiasAlterar();

		this.transportador.setEntidade(entidade);
		this.transportador.mapaObjetos().put("dao", dao);

		this.executarEstrategias(this.transportador);
		return this.transportador;

	}

	@Override
	public ATransportador excluir(Entidade entidade) {

		String nomeEntidade = entidade.getClass().getName();
		this.transportador = new TransportadorFachada();
		this.estrategias = this.mapaEstrategias.get(nomeEntidade).estrategiasExcluir();
		Idao dao = this.mapaDao.get(nomeEntidade);

		this.transportador.setEntidade(entidade);
		this.transportador.mapaObjetos().put("dao", dao);

		this.executarEstrategias(this.transportador);
		return this.transportador;

	}

	@Override
	public ATransportador listar(Entidade entidade) {

		String nomeEntidade = entidade.getClass().getName();
		this.transportador = new TransportadorFachada();
		this.estrategias = this.mapaEstrategias.get(nomeEntidade).estrategiasListar();
		Idao dao = this.mapaDao.get(nomeEntidade);

		this.transportador.setEntidade(entidade);
		this.transportador.mapaObjetos().put("dao", dao);

		this.executarEstrategias(this.transportador);
		return this.transportador;

	}

	private void executarEstrategias(ITransportador transportador) {

		Conexao.conectar();

		try {

			for (IStrategy st : this.estrategias) {

				if (!st.processar(transportador)) {
					Conexao.conexao.rollback();
					return;
				}
			}
		} catch (MySQLNonTransientConnectionException et) {
			System.err.println("Tentando fazer rollback sem transaÁ„o");
		} catch (Exception e) {
			try {
				Conexao.conexao.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			Conexao.desconectar();
		}
	}
}
