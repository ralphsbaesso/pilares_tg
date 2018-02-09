package dao.implementacao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Idao;
import dominio.Entidade;
import dominio.Especialidade;
import dominio.Mantenedor;

public class DaoMantenedor implements Idao {

	// objetos para coneccao
    private String sql;
    private PreparedStatement preparedStatement;
    private ResultSet resultset;
    
	@Override
	public boolean salvar(Entidade entidade) {

		Mantenedor mantenedor = (Mantenedor)entidade;
	        
        sql = "INSERT INTO Mantenedores"
        		+ " (nome, cpf, registro, email,data_cadastro, sexo)"
        		+ " VALUES (?, ?, ?, ?, ?, ?)";
        //System.out.println(sql);
        
        try {
            preparedStatement = Conexao.conexao.prepareStatement(sql);
            preparedStatement.setString(1, mantenedor.getNome());
            preparedStatement.setString(2, mantenedor.getCpf());
            preparedStatement.setString(3, mantenedor.getRegistro());
            preparedStatement.setString(4, mantenedor.getEmail());
            preparedStatement.setDate(5, new Date(mantenedor.getDataCadastro().getTimeInMillis()));
            preparedStatement.setString(6, mantenedor.getSexo());
            preparedStatement.execute();
            
            // resgatar a id deste mantenedor salvo
            sql = "select id from mantenedores where cpf = ?";
            preparedStatement = Conexao.conexao.prepareStatement(sql);
            preparedStatement.setString(1, mantenedor.getCpf());
            resultset = preparedStatement.executeQuery();
			
			while(resultset.next()){
				mantenedor.setId(resultset.getInt("id"));
			}
            
            // dados da tabela MANTENEDORES_ESPECIALIDADES
            sql = "INSERT INTO MANTENEDORES_ESPECIALIDADES"
            		+ " (mantenedor_id, especialidade_id)"
            		+ " VALUES (?, ?)";
            for(Especialidade esp:  mantenedor.getEspecialidades()){
            	preparedStatement = Conexao.conexao.prepareStatement(sql);
                preparedStatement.setInt(1, mantenedor.getId());
                preparedStatement.setInt(2, esp.getId());
                preparedStatement.execute();
            }
            
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
		return true;
	}

	@Override
	public boolean alterar(Entidade entidade) {
		Mantenedor mantenedor = (Mantenedor)entidade;
	        
        sql = "UPDATE Mantenedores"
        		+ " SET nome = ?, email = ?, sexo = ? "
        		+ " WHERE id = ?";
        //System.out.println(sql + " - cpf: " + mantenedor.getCpf());
        
        try {
            preparedStatement = Conexao.conexao.prepareStatement(sql);
            preparedStatement.setString(1, mantenedor.getNome());
            preparedStatement.setString(2, mantenedor.getEmail());
            preparedStatement.setString(3, mantenedor.getSexo());
            preparedStatement.setInt(4, mantenedor.getId());
            preparedStatement.execute();
            preparedStatement.close();
            
            // alterar dados da tabela MANTENEDORES_ESPECIALIDADES
            
            // primeiro apagar todos dados existente desta entidade
            
            sql = "delete from MANTENEDORES_ESPECIALIDADES "
				  + " where MANTENEDORES_ESPECIALIDADES.MANTENEDOR_ID = " 
				  + "(select id from mantenedores "
				  + " where id = ?) ";
				  
			  preparedStatement = Conexao.conexao.prepareStatement(sql);
			  preparedStatement.setInt(1, mantenedor.getId());
			  preparedStatement.execute();
            
			// inserir novas especialidades para mantenedor
            sql = "INSERT INTO MANTENEDORES_ESPECIALIDADES"
            		+ " (mantenedor_id, especialidade_id)"
            		+ " VALUES (?, ?)";
            for(Especialidade esp:  mantenedor.getEspecialidades()){
            	preparedStatement = Conexao.conexao.prepareStatement(sql);
                preparedStatement.setInt(1, mantenedor.getId());
                preparedStatement.setInt(2, esp.getId());
                preparedStatement.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
		return true;
	}

	@Override
	public boolean excluir(Entidade entidade) {
		
		Mantenedor mantenedor = (Mantenedor)entidade;
        
		// Deletar a dependencia de mantenedor com especialidade
		
		sql = "DELETE FROM MANTENEDORES_ESPECIALIDADES "
        		+ "WHERE  mantenedor_id = ?";
        
        try {
            preparedStatement = Conexao.conexao.prepareStatement(sql);
            preparedStatement.setInt(1, mantenedor.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
		
		// Deletar o mantenedor
        sql = "DELETE FROM Mantenedores "
        		+ "WHERE  id = ?";
        
        try {
            preparedStatement = Conexao.conexao.prepareStatement(sql);
            preparedStatement.setInt(1, mantenedor.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
		return true;
	}

	@Override
	public List listar(Entidade entidade) {
		
		List <Mantenedor> mantenedores = new ArrayList();
		Mantenedor mantenedor = (Mantenedor)entidade;
		
		try {			
			// Primeira op??o
			// Buscar dados utilizando filtros
			if(mantenedor.getCpf() != null  || mantenedor.getNome() != null){	// objeto n?o vazio?
				
				if(!mantenedor.getCpf().equals("") || !mantenedor.getNome().equals("")) {
					
					// buscar uma entidade
					mantenedores = listarUmaEntidade(mantenedor);
				}
			
			}else{
				// Segundo op??o
				// Buscar todos os dados da tabela
				
				mantenedores = listarTodasEntidades();
			}
			
			// fechar o PreparedStatement
			preparedStatement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mantenedores;
	}
	
	private List listarUmaEntidade(Mantenedor mantenedor){
		
		List<Mantenedor> mantenedores = new ArrayList();
		List<String> parametros = new ArrayList();
		StringBuilder sbSql = new StringBuilder();
		Mantenedor man = new Mantenedor();
		
		
		try {
			
			sbSql.append("SELECT * FROM MANTENEDORES WHERE 1 = 1 ");
			
			if(mantenedor.getCpf() != null && !mantenedor.getCpf().isEmpty()) {
				
				sbSql.append(" AND cpf = ? ");
				parametros.add(mantenedor.getCpf());
				
			} else if (mantenedor.getNome() != null) {

				sbSql.append(" AND LOWER(nome) LIKE ? ");
				parametros.add("%" + mantenedor.getNome().toLowerCase() + "%");
			}
			
			preparedStatement = Conexao.conexao.prepareStatement(sbSql.toString());
					
			// carregar parametros de filtro do tipo String
			for (int i = 0; i < parametros.size(); i++) {
				preparedStatement.setString(i + 1, parametros.get(i));
			}
			
			resultset = preparedStatement.executeQuery();
			
			while(resultset.next()){
				man = new Mantenedor();
				man.setId(resultset.getInt("id"));
				man.setNome(resultset.getString("nome"));
				man.setCpf(resultset.getString("cpf"));
				man.setRegistro(resultset.getString("registro"));
				man.setEmail(resultset.getString("email"));
				man.setSexo(resultset.getString("sexo"));
				try{
					man.getDataCadastro().setTime(resultset.getTimestamp("data_cadastro"));
					}catch(java.lang.NullPointerException e){
					System.out.println("erro");
					man.getDataCadastro().getInstance();
				}
				
				mantenedores.add(man);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// apenas um mantenedor? Buscar as especialidades.
		if(mantenedores.size() == 1) {
			
			try{
				
				sbSql = new StringBuilder();
				//buscar especialidades do mantenedor
				sbSql.append("SELECT esp.* ")
				.append("FROM mantenedores_especialidades me ")
				.append("JOIN especialidades esp ")
				.append("ON esp.id = me.especialidade_id ")
				.append("JOIN mantenedores man ")
				.append("ON man.id = me.mantenedor_id ")
				.append("WHERE man.id = ?");
				
				preparedStatement = Conexao.conexao.prepareStatement(sbSql.toString());
				preparedStatement.setInt(1, man.getId());
				resultset = preparedStatement.executeQuery();
				
				Especialidade esp;
				
				while(resultset.next()){
					
					esp = new Especialidade();
					esp.setId(resultset.getInt("id"));
					esp.setDescricao(resultset.getString("descricao"));
					esp.setCodigo(resultset.getString("codigo"));
					try{
						esp.getDataCadastro().setTime(resultset.getTimestamp("data_cadastro"));
					}catch(java.lang.NullPointerException e){
						System.out.println("erro");
						esp.getDataCadastro().getInstance();
					}
					man.getEspecialidades().add(esp);
				}
				
			}catch(Exception e){
				System.out.println("erro de join");
				e.printStackTrace();
			}
			
		}
		
		
		return mantenedores;
	}
	
	// listar todas as entidades
	private List listarTodasEntidades(){
		
		List<Mantenedor> mantenedores = new ArrayList();
		
		sql = "SELECT * "
				+ "FROM mantenedores "
				+ "ORDER BY nome ASC";
		
		// retorno
		return mantenedores = selectEntidade(null, sql);
	}
	
	// loop de select no banco
	private List selectEntidade(Mantenedor mantenedor, String sql){
		
		Mantenedor man;
		List<Mantenedor> mantenedores = new ArrayList();
		
		try {
			preparedStatement = Conexao.conexao.prepareStatement(sql);
			if(mantenedor != null){
				
				// buscar o mantenedor espec?fico
				preparedStatement.setString(1, mantenedor.getCpf());
				
			}
			resultset = preparedStatement.executeQuery();
			
			while(resultset.next()){
				man = new Mantenedor();
				man.setId(resultset.getInt("id"));
				man.setNome(resultset.getString("nome"));
				man.setCpf(resultset.getString("cpf"));
				man.setRegistro(resultset.getString("registro"));
				man.setEmail(resultset.getString("email"));
				man.setSexo(resultset.getString("sexo"));
				try{
					man.getDataCadastro().setTime(resultset.getTimestamp("data_cadastro"));
					}catch(java.lang.NullPointerException e){
					System.out.println("erro");
					man.getDataCadastro().getInstance();
				}
				
				mantenedores.add(man);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// listando somente um mantenedor
		if(mantenedor != null && mantenedores.size() == 1){
			try{
				//buscar especialidades do mantenedor
				String sql2 = "SELECT ESPECIALIDADES.id,"
						  + "ESPECIALIDADES.descricao," 
						  + "ESPECIALIDADES.data_cadastro," 
						  + "ESPECIALIDADES.codigo " 
						  + "from MANTENEDORES_ESPECIALIDADES join ESPECIALIDADES "
						  + "on(ESPECIALIDADES.ID = MANTENEDORES_ESPECIALIDADES.especialidade_id) "
						  + " join mantenedores"
						  + " on (MANTENEDORES.ID = MANTENEDORES_ESPECIALIDADES.mantenedor_id)"
						  + " where cpf = ?";
				
				preparedStatement = Conexao.conexao.prepareStatement(sql2);
				preparedStatement.setString(1, mantenedor.getCpf());
				resultset = preparedStatement.executeQuery();
				
				Especialidade esp;
				
				while(resultset.next()){
					
					esp = new Especialidade();
					esp.setId(resultset.getInt("id"));
					esp.setDescricao(resultset.getString("descricao"));
					esp.setCodigo(resultset.getString("codigo"));
					try{
						esp.getDataCadastro().setTime(resultset.getTimestamp("data_cadastro"));
						}catch(java.lang.NullPointerException e){
						System.out.println("erro");
						esp.getDataCadastro().getInstance();
					}
					mantenedores.get(0).getEspecialidades().add(esp);
				}
				
			}catch(Exception e){
				System.out.println("erro de join");
			}
		}
		
		return mantenedores;
	}
}
