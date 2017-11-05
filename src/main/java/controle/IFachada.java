package controle;

import dominio.Entidade;

public interface IFachada {

	public AbstractMensagem salvar(Entidade entidade);
	public AbstractMensagem alterar(Entidade entidade);
	public AbstractMensagem excluir(Entidade entidade);
	public AbstractMensagem listar(Entidade entidade);
}
