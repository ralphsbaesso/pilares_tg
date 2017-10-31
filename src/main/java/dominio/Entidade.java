package dominio;

import java.util.Calendar;

public class Entidade {
	
	private int id = 0;
	private Calendar dataCadastro = Calendar.getInstance();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Calendar getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

}
