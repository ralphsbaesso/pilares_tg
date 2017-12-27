package negocio.mapadenegocio;

import java.util.ArrayList;
import java.util.List;

import negocio.IStrategy;
import negocio.InserirCodigo;
import negocio.InserirDataCadastro;
import negocio.RegraAlterarEntidade;
import negocio.RegraExcluirEntidade;
import negocio.RegraListarEntidade;
import negocio.RegraSalvarEntidade;
import negocio.VerificarNomeNullo;

public class MapaEspecialidade extends AbstractMapaNegocio {

	@Override
	public List<IStrategy> estrategiasSalvar() {
		// regras salvar especialidade
		this.estrategias = new ArrayList();
		this.estrategias.add(new VerificarNomeNullo());
		this.estrategias.add(new InserirDataCadastro());
		this.estrategias.add(new InserirCodigo());
		this.estrategias.add(new RegraSalvarEntidade());
		return this.estrategias;
	}

	@Override
	public List<IStrategy> estrategiasAlterar() {

		this.estrategias = new ArrayList();
		this.estrategias.add(new RegraAlterarEntidade());
		return this.estrategias;
	}

	@Override
	public List<IStrategy> estrategiasExcluir() {

		this.estrategias = new ArrayList();
		this.estrategias.add(new RegraExcluirEntidade());
		return this.estrategias;
	}

	@Override
	public List<IStrategy> estrategiasListar() {

		this.estrategias = new ArrayList();
		this.estrategias.add(new RegraListarEntidade());
		return this.estrategias;
	}

}
