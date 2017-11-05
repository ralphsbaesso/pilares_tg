package web.viewhelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.AbstractMensagem;
import dominio.Entidade;

public interface IViewHelper {

	public Entidade getEntidade(HttpServletRequest request);
	
	public void setView(AbstractMensagem mensagem, 
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException;

}
