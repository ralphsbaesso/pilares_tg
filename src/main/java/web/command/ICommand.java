package web.command;

import dominio.Entidade;

public interface ICommand {
	
	public Object executar(Entidade entidade);
}
