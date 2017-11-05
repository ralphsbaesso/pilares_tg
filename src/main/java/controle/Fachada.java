package controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Idao;
import dao.implementacao.*;
import dominio.*;
import enuns.EStatus;
import negocio.*;

public class Fachada implements IFachada {
	
	//atributos
	private Map<String, List<IStrategy>> regrasNegociosSalvar;
	private Map<String, List<IStrategy>> regrasNegociosExcluir;
	private Map<String, Idao> daos;
	private AbstractMensagem mensagem;
	
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
		sttSalvarEspecialidade.add(new VerificarNomeNullo());
		sttSalvarEspecialidade.add(new InserirDataCadastro());
		sttSalvarEspecialidade.add(new InserirCodigo());
		
		// regras salvar especialidade
		List<IStrategy> sttSalvarOrdemServico = new ArrayList();
		sttSalvarOrdemServico.add(new InserirDataCadastro());
		
		// regras salvar ordem de serviï¿½o
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
		
		// regras salvar tarefas com suas aï¿½oes
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
	public AbstractMensagem salvar(Entidade entidade) {
		StringBuilder sb = new StringBuilder();
		String nomeEntidade = entidade.getClass().getName();
		List<IStrategy> regrasEntidade = this.regrasNegociosSalvar.get(nomeEntidade);
		
		this.mensagem = new controleMensagem();
		
		String mensagem = null;
			for(IStrategy st: regrasEntidade){
			mensagem = st.processar(entidade);
			this.mensagem.setMensagens(mensagem);
		}
		
		if(this.mensagem.getStatus() == EStatus.VERMELHO)	//tem mensagem?
			return this.mensagem;
		else{
			Idao dao = daos.get(nomeEntidade);
			if(dao != null){
				if(!dao.salvar(entidade)){
					return this.mensagem;
				}
			}else{
				//System.out.println("Erro na fachada no dao.salvar()");
			}
		}
		
		return this.mensagem;
	}

	@Override
	public AbstractMensagem alterar(Entidade entidade) {
		
		Idao dao = daos.get(entidade.getClass().getName());
		this.mensagem = new controleMensagem();
		
		if(!dao.alterar(entidade)){
			System.out.println("ERRO Alterar");
			this.mensagem.setMensagens("Erro na alteração");
			return this.mensagem;
		}
		
		return this.mensagem;
	}

	@Override
	public AbstractMensagem excluir(Entidade entidade) {

		StringBuilder sb = new StringBuilder();
		String nomeEntidade = entidade.getClass().getName();
		List<IStrategy> regrasEntidade = this.regrasNegociosExcluir.get(nomeEntidade);
		
		this.mensagem = new controleMensagem();
		
		String mensagem = null;
		
		// verificar se hï¿½ regras para esta entidade
		if(regrasEntidade != null){
			for(IStrategy st: regrasEntidade){
				mensagem = st.processar(entidade);
				this.mensagem.setMensagens(mensagem);
			}
		}
		
		if(sb.length() > 0)	//tem mensagem?
			return this.mensagem;
		else{
			Idao dao = daos.get(nomeEntidade);
			if(dao != null){
				if(!dao.excluir(entidade)){
					this.mensagem.setMensagens("Erro na exclusão");
					return this.mensagem;
				}
			}else{
				System.out.println("Erro na exclusï¿½o no fachada");
			}
		}
		
		return this.mensagem;
	}

	@Override
	public AbstractMensagem listar(Entidade entidade) {

		this.mensagem = new controleMensagem();
		
		Idao dao = daos.get(entidade.getClass().getName());
		
		this.mensagem.setEntidades(dao.listar(entidade));
		
		return this.mensagem;
	}

}
