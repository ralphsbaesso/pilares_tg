package web.command;

import controle.AbstractMensagem;
import dominio.Entidade;

public class CommandExcluir extends AbstractCommand{

	@Override
	public AbstractMensagem executar(Entidade entidade) {
		// TODO Auto-generated method stub
		return fachada.excluir(entidade);
	}

}
