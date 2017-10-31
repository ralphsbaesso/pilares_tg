package web.command;

import dominio.Entidade;

public class CommandListar extends AbstractCommand{

	@Override
	public Object executar(Entidade entidade) {
		
		return fachada.listar(entidade);
	}

}
