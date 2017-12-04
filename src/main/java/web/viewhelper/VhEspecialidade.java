package web.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.ITransportador;

//import org.json.JSONArray;

import dominio.Entidade;
import dominio.Especialidade;
import enuns.ESemafaro;
import web.WebMensagem;

public class VhEspecialidade extends AbstractVH {

	private Especialidade especialidade;

	@Override
	public Entidade getEntidade(HttpServletRequest request) {

		especialidade = new Especialidade();

		if (this.especialidade == null)
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
	public void setView(ITransportador mensagem, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter out = response.getWriter();
		WebMensagem respWeb = new WebMensagem();

		Especialidade esp = (Especialidade) this.entidade;

		if (operacao.equals("salvar")) {

			out.print(respWeb.enviarObjetoWeb());
			return;

		} else if (operacao.equals("excluir")) {
			request.setAttribute("mensagem", esp.getDescricao() + " exclu?do com sucesso!!!");
		} else if (operacao.equals("alterar")) {
			request.setAttribute("mensagem", "Especialidade alterado com sucesso!");
		} else if (operacao.equals("listar")) {

			respWeb.recebeObjetoMensagem(mensagem);
			out.print(respWeb.enviarObjetoWeb());
			return;
		}

		out.println("operacao: " + operacao);
		// rd.forward(request, response);
	}
}
