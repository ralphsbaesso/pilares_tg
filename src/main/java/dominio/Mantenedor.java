package dominio;

import java.util.ArrayList;
import java.util.List;

public class Mantenedor extends Funcionario {
	
	private List<Especialidade> especialidades = new ArrayList();

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}
	
	

}
