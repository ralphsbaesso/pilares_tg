package web.viewhelper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.AbstractMensagem;
import dominio.Apontamento;
import dominio.Entidade;
import dominio.Tarefa;

public class VhApontamentos extends AbstractVH{

	@Override
	public Entidade getEntidade(HttpServletRequest request) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		//Objeto Apontamento
		Tarefa tarefa = new Tarefa();
		Apontamento apontamento;
				
		operacao = request.getParameter("operacao").toLowerCase();
		
		if(operacao.equals("salvar")){
		
			try{
				// carregar dados principais do apontamento
				String status = request.getParameter("txtStatusOs");
				tarefa.getAtividade().getOs().setStatus(status);
				String encerrado = request.getParameter("chEncerrar");
				if(encerrado != null){
					tarefa.getAtividade().getOs().setEncerrada("encerrar");
				}
				
				// id da OS
				tarefa.getAtividade().getOs().setId(Integer.valueOf(request.getParameter("txtOsId")));
				
				for(int i = 1; true; ){
										
					//verifica se h? objetos na request
					if(request.getParameter("txtAtividadeId" + i) == null){
						// n?o existe
						break;
					}
					
					
					for(int j = 1; true; j++){
						//verificar tarefas
						if(request.getParameter("txtTarefaId" + i + "_" + j) == null){
							// n?o tem tarefa
							// encrementa o i
							i++;
							break;
						}
						
						
						//data inicial
						String dataInicial[] = request.getParameterValues("txtDataInicial" + i + "_" + j);
						
						// data final
						String dataFinal[] = request.getParameterValues("txtDataFinal" + i + "_" + j);
						
						// observa??es
						String observacao[] = request.getParameterValues("txtObservacao" + i + "_" + j);
						
						//adiciona mantenedor
						String mantenedorId[] = request.getParameterValues("cbMantenedor" + i + "_" + j);
						
						// loop para adicionar cada apontamento
						for(int n = 0; n < mantenedorId.length; n++){
							
							apontamento = new Apontamento();
							
							apontamento.getTarefa().setId(Integer.valueOf(request.getParameter("txtTarefaId" + i + "_" + j)));
							
							apontamento.setDetalhamento(observacao[n]);
							
							// carregar datas
							String data = dataInicial[n];
							
							try {
								apontamento.getDataInicial().setTime(sdf.parse(data));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println("Erro na data inicial");
								apontamento.getDataInicial().setTime(sdf.parse("01/01/1970 00:00"));
							}
							
							data = dataFinal[n];
							try {
								apontamento.getDataFinal().setTime(sdf.parse(data));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println("Erro na data final");
								apontamento.getDataFinal().setTime(sdf.parse("01/01/1970 00:00"));
							}
							
							// id mantenedor
							try{
								apontamento.getExecutante().setId(Integer.valueOf(mantenedorId[n]));
							}catch(NumberFormatException e){
								apontamento.getExecutante().setId(Integer.valueOf(0));
								System.out.println("Erro na inser??o do id do mantenedor");
							}
							
							// id planejador
							try{
								apontamento.getAutor().setId(Integer.valueOf(request.getParameter("cbAutorId")));
							}catch(NumberFormatException e){
								apontamento.getAutor().setId(Integer.valueOf(0));
								System.out.println("Erro na inser??o do id do planejador");
							}
							
							
							// carregar a??o em tarefa
							tarefa.getApontamentos().add(apontamento);
						}
					}
				}
				
				// armazer a entidade
				this.entidade = tarefa;
			
			}catch(Exception e){
				e.printStackTrace();
			}
		// if - salvar	
		}else if(operacao.equals("listar")){
			tarefa.setCodigo(null);
		}
		
		return tarefa;
	}

	@Override
	public void setView(AbstractMensagem resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
			Tarefa tarefa = (Tarefa)this.entidade;
			RequestDispatcher rd = request.getRequestDispatcher("apontamento.jsp");
			request.setAttribute("ctrlDiv", operacao.toLowerCase());
			
			
			// eh uma lista?
			if(true){
				
								
				if(operacao.equals("listar")){
					
					request.setAttribute("lista", resultado);
				}
			}else if(resultado != null){			
				//falha na valida??o de regras de negocio
				// exibe as mesagens
				request.setAttribute("mensagem", resultado.toString());
				
			}else{
				// resultado igual a nulo
				// opera??o com sucesso ou controle da <div>
				if(operacao.equals("salvar")){		
					request.setAttribute("mensagem", "Apontamento salvo com sucesso!!!");			
				}else if(operacao.equals("excluir")){		
					request.setAttribute("mensagem", " exclu?do com sucesso!!!");
				}else if(operacao.equals("alterar")){
					request.setAttribute("mensagem","Apontamento alterado com sucesso!");
				}else if(operacao.equals("procurar uma ordem de servico")){
					request.setAttribute("ctrlDiv", "listarUm");
				}else if(operacao.equals("listar todas ordem de servico")){
					request.setAttribute("ctrlDiv", "listarTodos");
				}else if(operacao.equals("apontar")){
					String codigo = request.getParameter("txtCodigo");
					request.setAttribute("codigo", codigo);
				}
			}
			
			rd.forward(request, response);
	}	
}
