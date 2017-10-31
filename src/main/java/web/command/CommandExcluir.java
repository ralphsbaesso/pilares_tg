package web.command;

import dominio.Entidade;

public class CommandExcluir extends AbstractCommand{

	@Override
	public Object executar(Entidade entidade) {
		// TODO Auto-generated method stub
		return fachada.excluir(entidade);
	}

}
