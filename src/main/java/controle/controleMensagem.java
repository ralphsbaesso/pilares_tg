package controle;

import java.util.List;

import dominio.Entidade;
import enuns.EStatus;

public class controleMensagem extends AbstractMensagem{
	
	@Override
	public void setMensagens(String mensagem){
		if(mensagem == null || mensagem == "")
			return;
		this.status = EStatus.VERMELHO;
		this.mensagens.add(mensagem);
	}
	
	@Override
	public void setEntidades(List<Entidade> entidades) {
		this.entidades = entidades;
		
		if(this.entidades.size() == 0){
			this.status = EStatus.VERMELHO;
		}
	}
}
