package controle;

import java.util.List;

import dominio.Entidade;

public interface IFachada {

	public String salvar(Entidade entidade);
	public String alterar(Entidade entidade);
	public String excluir(Entidade entidade);
	public List<Entidade> listar(Entidade entidade);
}
