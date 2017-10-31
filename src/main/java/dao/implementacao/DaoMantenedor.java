package dao.implementacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import dao.Idao;
import dominio.*;

public class DaoMantenedor implements Idao {

	// objetos para coneccao
    private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String usuario = "les";
    private final String senha = "123";
    private String sql;
    private String msg;
    private Connection conexao;
    private PreparedStatement preparedStatement;
    private ResultSet resultset;
    
	@Override
	public boolean salvar(Entidade entidade) {

		Mantenedor mantenedor = (Mantenedor)entidade;
		
		conectar();
	        
        sql = "INSERT INTO Mantenedores"
        		+ " (nome, cpf, registro, email,data_cadastro, sexo)"
        		+ " VALUES (?, ?, ?, ?, ?, ?)";
        //System.out.println(sql);
        
        try {
            preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, mantenedor.getNome());
            preparedStatement.setString(2, mantenedor.getCpf());
            preparedStatement.setString(3, mantenedor.getRegistro());
            preparedStatement.setString(4, mantenedor.getEmail());
            preparedStatement.setDate(5, new Date(mantenedor.getDataCadastro().getTimeInMillis()));
            preparedStatement.setString(6, mantenedor.getSexo());
            preparedStatement.execute();
            
            // resgatar a id deste mantenedor salvo
            sql = "select id from mantenedores where cpf = ?";
            preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, mantenedor.getCpf());
            resultset = preparedStatement.executeQuery();
			
			while(resultset.next()){
				mantenedor.setId(resultset.getInt("id"));
			}
            
            // dados da tabela MANTENEDORES_ESPECIALIDADES
            sql = "INSERT INTO MANTENEDORES_ESPECIALIDADES"
            		+ " (mantenedores_id, especialidades_id)"
            		+ " VALUES (?, ?)";
            for(Especialidade esp:  mantenedor.getEspecialidades()){
            	preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setInt(1, mantenedor.getId());
                preparedStatement.setInt(2, esp.getId());
                preparedStatement.execute();
            }
            
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
        	desconectar();
        }
        
		return true;
	}

	@Override
	public boolean alterar(Entidade entidade) {
		Mantenedor mantenedor = (Mantenedor)entidade;
		
		conectar();
	        
        sql = "UPDATE Mantenedores"
        		+ " SET nome = ?, registro = ?, email = ?, sexo = ? "
        		+ " WHERE cpf = ?";
        //System.out.println(sql + " - cpf: " + mantenedor.getCpf());
        
        try {
            preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, mantenedor.getNome());
            preparedStatement.setString(2, mantenedor.getRegistro());
            preparedStatement.setString(3, mantenedor.getEmail());
            preparedStatement.setString(4, mantenedor.getSexo());
            preparedStatement.setString(5, mantenedor.getCpf());
            preparedStatement.execute();
            preparedStatement.close();
            
            // alterar dados da tabela MANTENEDORES_ESPECIALIDADES
            
            // primeiro apagar todos dados existente desta entidade
            
            sql = "delete from MANTENEDORES_ESPECIALIDADES "
				  + " where MANTENEDORES_ESPECIALIDADES.MANTENEDORES_ID = " 
				  + "(select id from mantenedores "
				  + " where cpf = ?) ";
				  
			  preparedStatement = conexao.prepareStatement(sql);
			  preparedStatement.setString(1, mantenedor.getCpf());
			  preparedStatement.execute();
            
			// inserir novas especialidades para mantenedor
            sql = "INSERT INTO MANTENEDORES_ESPECIALIDADES"
            		+ " (mantenedores_id, especialidades_id)"
            		+ " VALUES (?, ?)";
            for(Especialidade esp:  mantenedor.getEspecialidades()){
            	preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setInt(1, mantenedor.getId());
                preparedStatement.setInt(2, esp.getId());
                preparedStatement.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
        	desconectar();
        }
        
		return true;
	}

	@Override
	public boolean excluir(Entidade entidade) {
		
		Mantenedor mantenedor = (Mantenedor)entidade;
		
		conectar();
        
		// Deletar a dependencia de mantenedor com especialidade
		
		sql = "DELETE FROM MANTENEDORES_ESPECIALIDADES "
        		+ "WHERE  mantenedores_id = ?";
        
        try {
            preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, mantenedor.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
		
		// Deletar o mantenedor
        sql = "DELETE FROM Mantenedores "
        		+ "WHERE  cpf = (?)";
        
        try {
            preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, mantenedor.getCpf());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
        	desconectar();
        }
        
		return true;
	}

	@Override
	public List listar(Entidade entidade) {
		
		List <Mantenedor> mantenedores = new ArrayList();
		Mantenedor mantenedor = (Mantenedor)entidade;
		
		try {
			conectar();
			
			// Primeira op??o
			// Buscar dados utilizando filtro 'cpf'
			if(mantenedor.getCpf() != null){	// objeto n?o vazio?
				
				// buscar uma entidade
				mantenedores = listarUmaEntidade(mantenedor);
			
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
		
		desconectar();
		
		return mantenedores;
	}
	
	//*****************//
	// metodos privados
	private void conectar(){
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conexao = DriverManager.getConnection(url, usuario, senha);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void desconectar(){
		try {
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private List listarUmaEntidade(Mantenedor mantenedor){
		
		List<Mantenedor> mantenedores = new ArrayList();
		
		sql = "SELECT * "
				+ "FROM mantenedores "
				+ "WHERE cpf = ?";
		
		// retorno
		return mantenedores = selectEntidade(mantenedor, sql);
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
			preparedStatement = conexao.prepareStatement(sql);
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
		if(mantenedores.size() == 1){
			try{
				//buscar especialidades do mantenedor
				String sql2 = "SELECT ESPECIALIDADES.id,"
						  + "ESPECIALIDADES.descricao," 
						  + "ESPECIALIDADES.data_cadastro," 
						  + "ESPECIALIDADES.codigo " 
						  + "from MANTENEDORES_ESPECIALIDADES join ESPECIALIDADES "
						  + "on(ESPECIALIDADES.ID = MANTENEDORES_ESPECIALIDADES.especialidades_id) "
						  + " join mantenedores"
						  + " on (MANTENEDORES.ID = MANTENEDORES_ESPECIALIDADES.mantenedores_id)"
						  + " where cpf = ?";
				
				preparedStatement = conexao.prepareStatement(sql2);
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
