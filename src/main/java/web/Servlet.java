package web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.AbstractMensagem;
import controle.Fachada;
import controle.IFachada;
import dominio.Entidade;
import web.command.CommandAlterar;
import web.command.CommandExcluir;
import web.command.CommandListar;
import web.command.CommandSalvar;
import web.command.ICommand;
import web.viewhelper.IViewHelper;
import web.viewhelper.VhAnalise;
import web.viewhelper.VhApontamentos;
import web.viewhelper.VhEspecialidade;
import web.viewhelper.VhMantenedor;
import web.viewhelper.VhOrdemDeServico;
import web.viewhelper.VhPlanejamentos;


public class Servlet extends HttpServlet {
	
	// atributos
	private Map<String, ICommand> commands;
	private Map<String, IViewHelper> vhs;
	private AbstractMensagem mensagem;
	
	// construtor
	public Servlet(){
		commands = new HashMap();
		commands.put("salvar", new CommandSalvar());
		commands.put("alterar", new CommandAlterar());
		commands.put("excluir", new CommandExcluir());
		commands.put("listar", new CommandListar());
		
		vhs = new HashMap();
		vhs.put("/Pilares/Mantenedor", new VhMantenedor());
		vhs.put("/PilaresTG/Especialidade", new VhEspecialidade());
		vhs.put("/Pilares/OrdemDeServico", new VhOrdemDeServico());
		vhs.put("/Pilares/Planejamento", new VhPlanejamentos());
		vhs.put("/Pilares/Apontamento", new VhApontamentos());
		vhs.put("/Pilares/Analise", new VhAnalise());
		System.out.println("aqui ralph 3333");
	}
	

	public void service(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
		
		// Obt?m a uri que invocou esta servlet (O que foi definido no methdo do form html)
		String uri = request.getRequestURI();
		System.out.println(uri  + " quase certo");
		
		// Obt?m um viewhelper indexado pela uri que invocou esta servlet
		IViewHelper vh = vhs.get(uri);
		
		// O viewhelper retorna a entidade especifica para a tela que chamou
		// esta servlet
		Entidade entidade = vh.getEntidade(request);
		
		String operacao = request.getParameter("operacao").toLowerCase();
		
		IFachada fachada = new Fachada();
		
		ICommand cmd = commands.get(operacao);
		if(cmd != null){
			this.mensagem = cmd.executar(entidade);
		}else{
			//System.out.println("apenas controle visual");
		}
		
		
		vh.setView(this.mensagem, request, response);
		
	}
}
