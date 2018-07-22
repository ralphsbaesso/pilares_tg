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
import dominio.CentroCusto;
import dominio.Entidade;
import enuns.EOperacao;
import web.TransportadorWeb;

public class VhCentroCusto extends AbstractVH {

	private CentroCusto centroCusto;

	@Override
	public Entidade getEntidade(HttpServletRequest request) {

		Gson gson = FactoryGson.getGson();

		this.centroCusto = new CentroCusto();

		String jsonRequisicao = request.getParameter("requisicao");
		requisicao = gson.fromJson(jsonRequisicao, Requisicao.class);

		String jsonCentroCusto = request.getParameter("entidade");

		if (jsonCentroCusto != null) {

			try {
				this.centroCusto = gson.fromJson(jsonCentroCusto, CentroCusto.class);
			} catch (Exception e) {
				this.centroCusto = null;
				e.printStackTrace();
			}
		}

		if (requisicao.getOperacao().equals(EOperacao.SALVAR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.ALTERAR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.EXCLUIR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.LISTAR)) {

		}

		return this.centroCusto;
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

			request.setAttribute("centroCusto", json.toJson(transportador.getEntidades().get(0)));
			RequestDispatcher rd = request.getRequestDispatcher(this.requisicao.getDestino());
			rd.forward(request, response);
			return;
		}

		out.print(transportadorWeb.enviarObjetoWeb());
	}
}
