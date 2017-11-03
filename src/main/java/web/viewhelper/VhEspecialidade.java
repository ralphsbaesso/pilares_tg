package web.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.json.JSONArray;

import dominio.Entidade;
import dominio.Especialidade;
import web.RespostaWeb;

public class VhEspecialidade extends AbstractVH {
	
	private Especialidade especialidade = new Especialidade();

	@Override
	public Entidade getEntidade(HttpServletRequest request) {
		
		if(this.especialidade == null)
			especialidade = new Especialidade();

		operacao = request.getParameter("operacao").toLowerCase();

		if (operacao.equals("salvar") || operacao.equals("alterar")) {
			String descricao = request.getParameter("txtDescricao");
			String detalhamento = request.getParameter("txtDetalhamento");

			especialidade.setDescricao(descricao);
			especialidade.setDetalhamento(detalhamento);
		}
		// armazer a entidade
		this.entidade = especialidade;

		return especialidade;

	}

	@Override
	public void setView(Object resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter out = response.getWriter();
		RespostaWeb respWeb = new RespostaWeb();

		Especialidade esp = (Especialidade) this.entidade;

		if (operacao.equals("salvar")) {
			
			respWeb.setStatus("sucesso");
			respWeb.setMensagem("Especialidade: " + this.especialidade.getDescricao() + " salvo com sucesso!");
			out.print(respWeb.enviarObjetoWeb());
			especialidade = null;
			return;
			
		} else if (operacao.equals("excluir")) {
			request.setAttribute("mensagem", esp.getDescricao() + " exclu?do com sucesso!!!");
		} else if (operacao.equals("alterar")) {
			request.setAttribute("mensagem", "Especialidade alterado com sucesso!");
		} else if (operacao.equals("listar")) {
			
			respWeb.setStatus("sucesso");
			respWeb.setEntidades(resultado);
			out.print(respWeb.enviarObjetoWeb());
			especialidade = null;
			return;
			
		}

		out.println("operacao: " + operacao);
		// rd.forward(request, response);
	}
}
