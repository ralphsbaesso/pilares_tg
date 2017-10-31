package web.command;

import dominio.Entidade;

public class CommandAlterar extends AbstractCommand{

	@Override
	public Object executar(Entidade entidade) {
		// TODO Auto-generated method stub
		return fachada.alterar(entidade);
	}

}
