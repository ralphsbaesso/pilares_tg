package web.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import org.json.JSONArray;

import dominio.Entidade;
import dominio.Especialidade;

public class VhEspecialidade extends AbstractVH {

	@Override
	public Entidade getEntidade(HttpServletRequest request) {

		Especialidade especialidade = new Especialidade();

		operacao = request.getParameter("operacao").toLowerCase();

		System.out.println("teste");
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
		JSONArray listJ = new JSONArray();
		JSONObject objJ = new JSONObject();

		Especialidade esp = (Especialidade) this.entidade;

		try {
			objJ.put("lista", resultado);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// resultado igual a nulo
		// opera??o com sucesso ou controle da <div>
		if (operacao.equals("salvar")) {
			out.print("<h1>Deu Certo</h1>");
			return;
		} else if (operacao.equals("excluir")) {
			request.setAttribute("mensagem", esp.getDescricao() + " exclu?do com sucesso!!!");
		} else if (operacao.equals("alterar")) {
			request.setAttribute("mensagem", "Especialidade alterado com sucesso!");
		} else if (operacao.equals("listar")) {

			listJ.put(resultado);
			out.print(objJ);
			System.out.println("listando");
			return;
		}

		out.println("operacao: " + operacao);
		// rd.forward(request, response);
	}
}
