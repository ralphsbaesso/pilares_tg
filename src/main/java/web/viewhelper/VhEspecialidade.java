package web.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import adaptergson.FactoryGson;
import controle.ITransportador;
import dominio.Especialidade;
import dominio.Entidade;
import dominio.Especialidade;
import enuns.EOperacao;
import web.TransportadorWeb;

public class VhEspecialidade extends AbstractVH {

	private Especialidade especialidade;

	@Override
	public Entidade getEntidade(HttpServletRequest request) {

		Gson gson = FactoryGson.getGson();

		this.especialidade = new Especialidade();

		String jsonRequisicao = request.getParameter("requisicao");
		requisicao = gson.fromJson(jsonRequisicao, Requisicao.class);

		String jsonEspecialidade = request.getParameter("entidade");

		if (jsonEspecialidade != null) {

			try {
				this.especialidade = gson.fromJson(jsonEspecialidade, Especialidade.class);
			} catch (Exception e) {
				this.especialidade = null;
				e.printStackTrace();
			}
		}

		if (requisicao.getOperacao().equals(EOperacao.SALVAR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.ALTERAR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.EXCLUIR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.LISTAR)) {

		}

		return this.especialidade;
	}

	@Override
	public void setView(ITransportador transportador, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter out = response.getWriter();
		TransportadorWeb transportadorWeb = new TransportadorWeb();
		transportadorWeb.recebeObjetoMensagem(transportador);

		Gson json = new Gson();

		if (requisicao.getOperacao().equals(EOperacao.SALVAR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.ALTERAR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.EXCLUIR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.LISTAR)) {

		}

		if (this.requisicao.getDestino() != null) {

			request.setAttribute("especialidade", json.toJson(transportador.getEntidades().get(0)));
			RequestDispatcher rd = request.getRequestDispatcher(this.requisicao.getDestino());
			rd.forward(request, response);
			return;
		}

		out.print(transportadorWeb.enviarObjetoWeb());
	}
}
