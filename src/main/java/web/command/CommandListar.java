package web.command;

import controle.AbstractMensagem;
import dominio.Entidade;

public class CommandListar extends AbstractCommand{

	@Override
	public AbstractMensagem executar(Entidade entidade) {
		
		return fachada.listar(entidade);
	}

}
