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

import dominio.Apontamento;
import dominio.Atividade;
import dominio.Entidade;
import dominio.OrdemDeServico;
import dominio.Planejamento;
import dominio.Resultado;
import dominio.Tarefa;

public class VhAnalise extends AbstractVH{

	@Override
	public Entidade getEntidade(HttpServletRequest request) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
		SimpleDateFormat sdfMesAno = new SimpleDateFormat("MM/yyyy");
		SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
		Resultado resultado = new Resultado();
		OrdemDeServico os = new OrdemDeServico();
		Atividade ati = new Atividade();
		Tarefa tar = new Tarefa();
		Apontamento ap = new Apontamento();
		Planejamento plan = new Planejamento();
		
		operacao = request.getParameter("operacao").toLowerCase();
		
		if(operacao.equals("salvar")){

			resultado.setMes(request.getParameter("cbMes"));
			resultado.setAno(request.getParameter("cbAno"));
			
			if(resultado.getMes() == null){
				resultado.setMes("vazio");
			}
			
		}else if(operacao.equals("listar")){
			
		}
		
		// armazer a entidade
		this.entidade = resultado;
		
		return resultado;
		
	}

	@Override
	public void setView(Object resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
			RequestDispatcher rd = request.getRequestDispatcher("analise.jsp");
			request.setAttribute("ctrlDiv", operacao.toLowerCase());
			
			Resultado r = (Resultado)this.entidade;
			
			// há mensagem em rusultado?
			if(resultado != null){
				request.setAttribute("mensagem", resultado);
			}else{
				if(operacao.equals("salvar")){
					
					
					request.setAttribute("tarefasApontada", r.getApontamentos());
					request.setAttribute("tarefasPlanejada", r.getPlanejamentos());
					request.setAttribute("ano", (request.getParameter("cbAno")));
					request.setAttribute("resultado", r);
					
					String mes = request.getParameter("cbMes");
					
					if(mes != null){
						if(mes.equals("01")){
							mes = "Janeiro";
						}else if(mes.equals("02")){
							mes = "Fevereiro";
						}else if(mes.equals("03")){
							mes = "Março";
						}else if(mes.equals("04")){
							mes = "Abril";
						}else if(mes.equals("05")){
							mes = "Maio";
						}else if(mes.equals("06")){
							mes = "Junho";
						}else if(mes.equals("07")){
							mes = "Julho";
						}else if(mes.equals("08")){
							mes = "Agosto";
						}else if(mes.equals("09")){
							mes = "Setembro";
						}else if(mes.equals("10")){
							mes = "Outubro";
						}else if(mes.equals("11")){
							mes = "Novembro";
						}else if(mes.equals("12")){
							mes = "Desembro";
						}
					}
					
					// enviar mes
					request.setAttribute("mes", mes);
					
					if(!r.getMes().equals("vazio")){
						request.setAttribute("grafico", "1");
					}else{
						request.setAttribute("grafico", "2");
					}
					
				}
				
			}
			
			rd.forward(request, response);
	}	
	
	
}
