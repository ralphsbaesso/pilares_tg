package web.command;

import controle.AbstractMensagem;
import dominio.Entidade;

public class CommandAlterar extends AbstractCommand{

	@Override
	public AbstractMensagem executar(Entidade entidade) {
		// TODO Auto-generated method stub
		return fachada.alterar(entidade);
	}

}
