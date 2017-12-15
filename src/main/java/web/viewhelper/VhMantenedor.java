package web.viewhelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controle.ATransportador;
import controle.ITransportador;
import dominio.Entidade;
import dominio.Especialidade;
import dominio.Mantenedor;

public class VhMantenedor extends AbstractVH{

	@Override
	public Entidade getEntidade(HttpServletRequest request) {

		operacao = request.getParameter("operacao").toLowerCase();		
		
		String id = request.getParameter("txtId");
		String nome = request.getParameter("txtNome");
		String cpf = request.getParameter("txtCpf");
		String email = request.getParameter("txtEmail");
		String registro = request.getParameter("txtRegistro");
		String sexo = request.getParameter("cbSexo");
		String idSetor = request.getParameter("cbSetor");
		String[] idsEspecialidade = request.getParameterValues("cbEspecialidade_id");
		
		//System.out.println("id especialidade: " + request.getParameterValues("cbEspecialidade_id"));
		
		Mantenedor mantenedor = new Mantenedor();
		if(id == null){
			id = "0";
		}
		mantenedor.setId(Integer.valueOf(id));
		mantenedor.setNome(nome);
		mantenedor.setCpf(cpf);
		mantenedor.setEmail(email);
		mantenedor.setSexo(sexo);
		
		//pegar os parametros de especialidades
		if(idsEspecialidade != null){	// tem algo?
		Especialidade e;
			for(int i = 0; i < idsEspecialidade.length; i++){
				try{
					e = new Especialidade();
					e.setId(Integer.valueOf(idsEspecialidade[i]));
					mantenedor.getEspecialidades().add(e);
				}catch(Exception ex){
					System.out.println(ex.getMessage());
				}
			}
		}
		
		// armazer a entidade
		this.entidade = mantenedor;
		
		return mantenedor;
		
	}

	public void setView(ATransportador resultado, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
			Mantenedor man = (Mantenedor)this.entidade;
			SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			RequestDispatcher rd = request.getRequestDispatcher("mantenedor.jsp");
			request.setAttribute("ctrlDiv", operacao.toLowerCase());
			
			// eh uma lista?
			if(true){
				
				List<Mantenedor> lista = (List) resultado;
								
				if(operacao.equals("listar")){
					
					// verificar se lista est? vazia
					if(lista.size() == 0){
						// vazio
						request.setAttribute("mensagem", "CPF " + man.getCpf() + " n?o encontrada!");
						request.setAttribute("mantenedores", lista);
					}else{
						// com algum objeto
						request.setAttribute("mantenedores", lista);
					}
					
				}
			}else if(resultado != null){			
				//falha na valida??o de regras de negocio
				// exibe as mesagens
				request.setAttribute("mensagem", resultado.toString());
				
			}else{
				// resultado igual a nulo
				// opera??o com sucesso ou controle da <div>
				if(operacao.equals("salvar")){		
					request.setAttribute("mensagem", man.getNome() + " salvo com sucesso!!!");			
				}else if(operacao.equals("excluir")){		
					request.setAttribute("mensagem", man.getNome() + " exclu?do com sucesso!!!");
				}else if(operacao.equals("alterar")){
					request.setAttribute("mensagem","Mantenedor alterado com sucesso!");
				}
			}
			
			
			rd.forward(request, response);
	}

	@Override
	public void setView(ITransportador mensagem, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}	
}
