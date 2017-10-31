package controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Idao;
import dao.implementacao.*;
import dominio.*;
import negocio.*;

public class Fachada implements IFachada {
	
	//atributos
	private Map<String, List<IStrategy>> regrasNegociosSalvar;
	private Map<String, List<IStrategy>> regrasNegociosExcluir;
	private Map<String, Idao> daos;
	
	//construtor
	public Fachada(){
		regrasNegociosSalvar = new HashMap();
		regrasNegociosExcluir = new HashMap();
		daos = new HashMap();
		
		// REGRAS PARA SALVAR
		// regras de negocios salvar mantenedor
		List<IStrategy> sttSalvarMantenedor = new ArrayList();
		sttSalvarMantenedor.add(new InserirDataCadastro());
		sttSalvarMantenedor.add(new VerificarNomeNullo());
		sttSalvarMantenedor.add(new VerificarEspecialidade());
		sttSalvarMantenedor.add(new VerificarCpfExistente());
		
		// regras salvar especialidade
		List<IStrategy> sttSalvarEspecialidade = new ArrayList();
		sttSalvarEspecialidade.add(new InserirDataCadastro());
		sttSalvarEspecialidade.add(new InserirCodigo());
		
		// regras salvar especialidade
		List<IStrategy> sttSalvarOrdemServico = new ArrayList();
		sttSalvarOrdemServico.add(new InserirDataCadastro());
		
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
		
		regrasNegociosSalvar.put(Mantenedor.class.getName(), sttSalvarMantenedor);
		regrasNegociosSalvar.put(Especialidade.class.getName(), sttSalvarEspecialidade);
		regrasNegociosSalvar.put(OrdemDeServico.class.getName(), sttSalvarOS);
		regrasNegociosSalvar.put(Resultado.class.getName(), sttResultado);
		regrasNegociosSalvar.put(Tarefa.class.getName(), sttSalvarTarefa);
		
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

	@Override
	public String salvar(Entidade entidade) {
		StringBuilder sb = new StringBuilder();
		String nomeEntidade = entidade.getClass().getName();
		List<IStrategy> regrasEntidade = this.regrasNegociosSalvar.get(nomeEntidade);
		
		String mensagem = null;
		for(IStrategy st: regrasEntidade){
			mensagem = st.processar(entidade);
			validarMensagem(sb,mensagem);
		}
		
		if(sb.length() > 0)	//tem mensagem?
			return sb.toString();
		else{
			Idao dao = daos.get(nomeEntidade);
			if(dao != null){
				if(!dao.salvar(entidade)){
					return "Erro no salvar";
				}
			}else{
				//System.out.println("Erro na fachada no dao.salvar()");
			}
		}
		
		return null;
	}

	@Override
	public String alterar(Entidade entidade) {
		
		Idao dao = daos.get(entidade.getClass().getName());
		
		if(!dao.alterar(entidade)){
			System.out.println("ERRO Alterar");
			return "Erro na Altera��o!";
		}
		
		return null;
	}

	@Override
	public String excluir(Entidade entidade) {

		StringBuilder sb = new StringBuilder();
		String nomeEntidade = entidade.getClass().getName();
		List<IStrategy> regrasEntidade = this.regrasNegociosExcluir.get(nomeEntidade);
		
		String mensagem = null;
		
		// verificar se h� regras para esta entidade
		if(regrasEntidade != null){
			for(IStrategy st: regrasEntidade){
				mensagem = st.processar(entidade);
				validarMensagem(sb,mensagem);
			}
		}
		
		if(sb.length() > 0)	//tem mensagem?
			return sb.toString();
		else{
			Idao dao = daos.get(nomeEntidade);
			if(dao != null){
				if(!dao.excluir(entidade)){
					return "Erro de EXCLUS�O";
				}
			}else{
				System.out.println("Erro na exclus�o no fachada");
			}
		}
		
		return null;
	}

	@Override
	public List<Entidade> listar(Entidade entidade) {

		Idao dao = daos.get(entidade.getClass().getName());
		
		List<Entidade> entidades = dao.listar(entidade);
		
		return entidades;
	}
	
	private void validarMensagem(StringBuilder sb, String msg){
		if(msg != null){
			sb.append(msg);
			sb.append("\n");
		}
	}

}
