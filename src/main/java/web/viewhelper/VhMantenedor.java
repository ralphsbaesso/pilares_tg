package web.viewhelper;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import adaptergson.EmptyStringToNumber;
import controle.ATransportador;
import controle.ITransportador;
import dominio.Entidade;
import dominio.Especialidade;
import dominio.Mantenedor;
import enuns.EOperacao;
import web.TransportadorWeb;

public class VhMantenedor extends AbstractVH{
	
	private Mantenedor mantenedor;

	@Override
	public Entidade getEntidade(HttpServletRequest request) {

			Gson gson = new GsonBuilder()
					.registerTypeAdapter(int.class, new EmptyStringToNumber())
					.registerTypeAdapter(Integer.class, new EmptyStringToNumber())
					.create();
			
			this.mantenedor = new Mantenedor();
			
			String jsonMantenedor = request.getParameter("mantenedor");
			
			if(jsonMantenedor != null) {
				
				try {
					this.mantenedor = gson.fromJson(jsonMantenedor, Mantenedor.class);			
				}catch (Exception e) {
					this.mantenedor = null;
					e.printStackTrace();
				}
			}

			operacao = request.getParameter("operacao").toLowerCase();

			if (operacao.equals(EOperacao.SALVAR.getValor())) {
				
			}else if( operacao.equals(EOperacao.ALTERAR.getValor())) {
				
			}else if(operacao.equals(EOperacao.EXCLUIR.getValor())) {
			
				
			}

			return this.mantenedor;
		
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
