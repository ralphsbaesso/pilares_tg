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
import dominio.Entidade;
import dominio.Planejamento;
import enuns.EOperacao;
import web.TransportadorWeb;

public class VhPlanejamentos extends AbstractVH{
	
	private Planejamento planejamento;

	@Override
	public Entidade getEntidade(HttpServletRequest request) {
		

		Gson gson = FactoryGson.getGson();

		this.planejamento = new Planejamento();

		String jsonRequisicao = request.getParameter("requisicao");
		requisicao = gson.fromJson(jsonRequisicao, Requisicao.class);

		String jsonPlanejamento = request.getParameter("planejamento");

		if (jsonPlanejamento != null) {

			try {
				this.planejamento = gson.fromJson(jsonPlanejamento, Planejamento.class);
			} catch (Exception e) {
				this.planejamento = null;
				e.printStackTrace();
			}
		}

		if (requisicao.getOperacao().equals(EOperacao.SALVAR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.ALTERAR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.EXCLUIR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.LISTAR)) {

		}

		return this.planejamento;
	}

	public void setView(ITransportador transportador, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		PrintWriter out = response.getWriter();
		TransportadorWeb transpotadorWeb = new TransportadorWeb();

		Gson json = new Gson();

		if (requisicao.getOperacao().equals(EOperacao.SALVAR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.ALTERAR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.EXCLUIR)) {

		} else if (requisicao.getOperacao().equals(EOperacao.LISTAR)) {

		}

		if (this.requisicao.getDestino() != null) {

			request.setAttribute("apontamento", json.toJson(transportador.getEntidades().get(0)));
			RequestDispatcher rd = request.getRequestDispatcher(this.requisicao.getDestino());
			rd.forward(request, response);
			return;
		}

		out.print(transpotadorWeb.enviarObjetoWeb());
	}
}
