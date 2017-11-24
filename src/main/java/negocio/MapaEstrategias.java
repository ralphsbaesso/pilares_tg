package negocio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.implementacao.DaoEspecialidade;
import dao.implementacao.DaoMantenedor;
import dao.implementacao.DaoOrdemDeServico;
import dao.implementacao.DaoTarefa;
import dominio.Especialidade;
import dominio.Mantenedor;
import dominio.OrdemDeServico;
import dominio.Resultado;
import dominio.Tarefa;

public class MapaEstrategias {
	
	public MapaEstrategias() {
		
		// REGRAS PARA SALVAR
			// regras de negocios salvar mantenedor
			List<IStrategy> sttSalvarMantenedor = new ArrayList();
			sttSalvarMantenedor.add(new InserirDataCadastro());
			sttSalvarMantenedor.add(new VerificarNomeNullo());
			sttSalvarMantenedor.add(new VerificarEspecialidade());
			sttSalvarMantenedor.add(new VerificarCpfExistente());
			
			// regras salvar especialidade
			List<IStrategy> sttSalvarEspecialidade = new ArrayList();
			sttSalvarEspecialidade.add(new VerificarNomeNullo());
			sttSalvarEspecialidade.add(new InserirDataCadastro());
			sttSalvarEspecialidade.add(new InserirCodigo());
			
			
			
			// regras salvar ordem de servi�o
			List<IStrategy> sttSalvarOS = new ArrayList();
			sttSalvarOS.add(new InserirDataCadastro());
			sttSalvarOS.add(new VerificarCamposVazioOS());
			sttSalvarOS.add(new VerificarData());
			sttSalvarOS.add(new InserirCodigo());
			sttSalvarOS.add(new InserirStatus());
			
			//regras de resultado
			List<IStrategy> sttResultado = new ArrayList();
			sttResultado.add(new CamposResultado());
			sttResultado.add(new ConsultarOrdemDeServico());
			
			// regras salvar tarefas com suas a�oes
			List<IStrategy> sttSalvarTarefa = new ArrayList();
			sttSalvarTarefa.add(new InserirDataCadastro());
			sttSalvarTarefa.add(new InserirCodigo());
			sttSalvarTarefa.add(new VerificarData());
			sttSalvarTarefa.add(new AtualizarStatusOs());
			
			estrategias.put(Mantenedor.class.getName(), sttSalvarMantenedor);
			estrategias.put(Especialidade.class.getName(), sttSalvarEspecialidade);
			estrategias.put(OrdemDeServico.class.getName(), sttSalvarOS);
			estrategias.put(Resultado.class.getName(), sttResultado);
			estrategias.put(Tarefa.class.getName(), sttSalvarTarefa);
			
			//REGRAS PARA DELETAR
			List<IStrategy> sttDeletarOS = new ArrayList();
			sttDeletarOS.add(new VerificarStatusNoDeletar());
			
			regrasNegociosExcluir.put(OrdemDeServico.class.getName(), sttDeletarOS);
			
			// carregar os DAOs
			daos.put(Mantenedor.class.getName(), new DaoMantenedor());
			daos.put(Especialidade.class.getName(), new DaoEspecialidade());
			daos.put(OrdemDeServico.class.getName(), new DaoOrdemDeServico());
			daos.put(Tarefa.class.getName(), new DaoTarefa());
	}
	
	private  Map<String, List<IStrategy>> mapaOperacao = new HashMap();
	private  Map<String, List<IStrategy>> estrategias = new HashMap();
	
	public Map<String, List<IStrategy>> getEstrategias(String nomeEntidade, String operacao){
		
		
		return this.estrategias;
	}
	
	private void salvarEspecialidade(String nomeEntidade) {
		
		// regras salvar especialidade
		List<IStrategy> sttSalvarEspecialidade = new ArrayList();
		sttSalvarEspecialidade.add(new VerificarNomeNullo());
		sttSalvarEspecialidade.add(new InserirDataCadastro());
		sttSalvarEspecialidade.add(new InserirCodigo());
		
		this.estrategias.put(nomeEntidade, sttSalvarEspecialidade);
	}

}
