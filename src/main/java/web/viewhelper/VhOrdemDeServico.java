package web.viewhelper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.ATransportador;
import controle.ITransportador;
import dominio.Atividade;
import dominio.Entidade;
import dominio.OrdemDeServico;
import dominio.Tarefa;

public class VhOrdemDeServico extends AbstractVH{

	@Override
	public Entidade getEntidade(HttpServletRequest request) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		OrdemDeServico om = new OrdemDeServico();
		
		operacao = request.getParameter("operacao").toLowerCase();		
		
		if(operacao.equals("salvar")){
			try{
				
				String autor = request.getParameter("cbMantenedor");
				String titulo = request.getParameter("txtTitulo");
				String observacao = request.getParameter("txtObservacao");
				String equipamentoId;
				equipamentoId = request.getParameter("cbEquipamento_id");
				String criticidade = request.getParameter("cbCriticidade");
				String dataLimite = request.getParameter("txtDataLimite");
				String tipoManutencao = request.getParameter("cbTipoManutencao");
				String codigo = request.getParameter("txtCodigo");
				
				try{
					om.getAutor().setId(Integer.valueOf(autor));
				}catch(Exception e){
					om.getAutor().setId(0);
				}
				om.setTitulo(titulo);
				om.setObservacao(observacao);
				om.setCriticidade(criticidade);
				om.setCodigo(codigo);
				try{
					om.getEquipamento().setId(Integer.valueOf(equipamentoId));
				}catch(NumberFormatException e){
					om.getEquipamento().setId(0);
				}
				om.setTipoManutencao(tipoManutencao);
				try {
					om.getDataLimite().setTime(sdf.parse(dataLimite));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// carregar atividade
				Atividade atividade;
				Tarefa tarefa;
				for(int i = 1; true; i++){
					atividade = new Atividade();
					
					// verificar se h� parametro
					if(request.getParameter("txtDescricaoAtividade" + i) == null){
						break; // n�o tem mais parametros, sair do loop
					}
					
					String descricao = request.getParameter("txtDescricaoAtividade" + i);
					String espId = request.getParameter("cbEspecialidade_id" + i);
					
					atividade.setDescricao(descricao);
					try{
						atividade.getEspecialidade().setId(Integer.valueOf(espId));
					}catch(NumberFormatException e){
						atividade.getEspecialidade().setId(0);
					}
					
					//carregar vetores de tarefas
					String[] detalhamento = request.getParameterValues("txtDetalhamento" + i);
					String[] qtdeHomem = request.getParameterValues("txtQtdeHomem" + i);
					String[] qtdeHora = request.getParameterValues("txtQtdeHora" + i);
					
					for(int j = 0; j < detalhamento.length; j++){
						tarefa = new Tarefa();
						tarefa.setDetalhamento(detalhamento[j]);
						try{
							tarefa.setQtdeHomemEstimado(Integer.valueOf(qtdeHomem[j]));
						}catch(NumberFormatException e){
							tarefa.setQtdeHomemEstimado(0);
						}
						try{
							tarefa.setQtdeHoraEstimada(Integer.valueOf(qtdeHora[j]));
						}catch(NumberFormatException e){
							tarefa.setQtdeHoraEstimada(0);
						}
						//carregar tarefas em atividades
						atividade.getTarefas().add(tarefa);
						
					}
					
					
					om.getAtividades().add(atividade);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		// if - salvar
		}else if(operacao.equals("listar")){
			om.setCodigo("tudo");
		}else if(operacao.equals("excluir")){
			om.setTitulo(request.getParameter("txtTitulo"));
			om.setCodigo(request.getParameter("txtCodigo"));
		}
		
		// armazer a entidade
		this.entidade = om;
		
		return om;
		
	}

	public void setView(ATransportador resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
			OrdemDeServico om = (OrdemDeServico)this.entidade;
			SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			RequestDispatcher rd = request.getRequestDispatcher("ordemdeservico.jsp");
			request.setAttribute("ctrlDiv", operacao.toLowerCase());
			
			
			// eh uma lista?
			if(resultado instanceof Object){
				
				List<OrdemDeServico> lista = (List) resultado;
								
				if(operacao.equals("listar")){
					
					// verificar se lista est� vazia
					if(lista.size() == 0){
						// vazio
						request.setAttribute("mensagem", "Ordem n�o encontrada!");
					}else{
						// com algum objeto
						request.setAttribute("ordens", lista);
					}
					
				}
			}else if(resultado != null){			
				//falha na valida��o de regras de negocio
				// exibe as mesagens
				request.setAttribute("mensagem", resultado.toString());
				
			}else{
				// resultado igual a nulo
				// opera��o com sucesso ou controle da <div>
				if(operacao.equals("salvar")){		
					request.setAttribute("mensagem", om.getTitulo() + " salvo com sucesso! - C�digo: " + om.getCodigo());			
				}else if(operacao.equals("excluir")){		
					request.setAttribute("mensagem", om.getTitulo() + " exclu�do com sucesso!!!");
				}else if(operacao.equals("alterar")){
					request.setAttribute("mensagem","Ordem de Servico alterado com sucesso!");
				}
			}
			
			rd.forward(request, response);
	}

	@Override
	public void setView(ITransportador mensagem, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}	
}
