package web.command;

import controle.AbstractMensagem;
import dominio.Entidade;

public interface ICommand {
	
	public AbstractMensagem executar(Entidade entidade);
}
