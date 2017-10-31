package web.command;

import dominio.Entidade;

public class CommandSalvar extends AbstractCommand{

	@Override
	public Object executar(Entidade entidade) {
		// TODO Auto-generated method stub
		return fachada.salvar(entidade);
	}

}
