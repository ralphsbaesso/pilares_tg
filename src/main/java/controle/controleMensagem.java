package controle;

import java.util.List;

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
	public void setEntidades(Object entidades) {
		this.entidades = (List) entidades;
		
		if(((List) this.entidades).size() == 0){
			this.status = EStatus.VERMELHO;
		}
	}
}
