package web.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import controle.ITransportador;

import dominio.Entidade;
import dominio.Especialidade;
import enuns.EOperacao;
import web.TransportadorWeb;

public class VhEspecialidade extends AbstractVH {

	private Especialidade especialidade;

	@Override
	public Entidade getEntidade(HttpServletRequest request) {

		Gson gson = new Gson();
		especialidade = new Especialidade();
		String jsonEspecialidade = request.getParameter("especialidade");
		
		if(jsonEspecialidade != null) {
			this.especialidade = gson.fromJson(jsonEspecialidade, Especialidade.class);			
		}

		operacao = request.getParameter("operacao").toLowerCase();

		if (operacao.equals(EOperacao.SALVAR.getValor())) {
			
		}else if( operacao.equals(EOperacao.ALTERAR.getValor())) {
			
		}else if(operacao.equals(EOperacao.EXCLUIR.getValor())) {
		
			
		}

		return especialidade;

	}

	@Override
	public void setView(ITransportador transportador, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		PrintWriter out = response.getWriter();
		TransportadorWeb transpotadorWeb = new TransportadorWeb();

		Especialidade esp = (Especialidade) this.entidade;
		transpotadorWeb.recebeObjetoMensagem(transportador);

		if (operacao.equals("salvar")) {

			out.print(transpotadorWeb.enviarObjetoWeb());
			return;

		} else if (operacao.equals("excluir")) {

			out.print(transpotadorWeb.enviarObjetoWeb());
			return;
			
		} else if (operacao.equals("alterar")) {

			out.print(transpotadorWeb.enviarObjetoWeb());
			return;
			
		} else if (operacao.equals("listar")) {

			out.print(transpotadorWeb.enviarObjetoWeb());
			return;
		}

		out.println("operacao: " + operacao);
		// rd.forward(request, response);
	}
}
