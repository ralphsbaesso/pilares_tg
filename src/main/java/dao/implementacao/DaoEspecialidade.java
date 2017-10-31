package dao.implementacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Idao;
import dominio.Entidade;
import dominio.Especialidade;
import dominio.Especialidade;
import dao.implementacao.*;

public class DaoEspecialidade implements Idao {

	// objetos para coneccao
    private String sql;
    private String msg;
    private PreparedStatement preparedStatement;
    private ResultSet resultset;
	
	@Override
	public boolean salvar(Entidade entidade) {
		
		Especialidade especialidade = (Especialidade)entidade;
		
		Conexao.conectar();
	        
        sql = "INSERT INTO Especialidades"
        		+ " (descricao,data_cadastro, codigo, detalhamento)"
        		+ " VALUES (?,?,?,?)";
        System.out.println(sql);
        System.out.println(especialidade.getDetalhamento());
        
        try {
            preparedStatement = Conexao.conexao.prepareStatement(sql);
            preparedStatement.setString(1, especialidade.getDescricao());
            preparedStatement.setDate(2, new Date(especialidade.getDataCadastro().getTimeInMillis()));
            preparedStatement.setString(3, especialidade.getCodigo());
            preparedStatement.setString(4, especialidade.getDetalhamento());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
        	Conexao.desconectar();
        }
        
		return true;
	}

	@Override
	public boolean alterar(Entidade entidade) {
		Especialidade especialidade = (Especialidade)entidade;
		
		Conexao.conectar();
	        
        sql = "UPDATE Especialidades"
        		+ " SET descricao = ?, "
        		+ " detalhamento = ? "
        		+ " WHERE codigo = ?";
        
        try {
            preparedStatement = Conexao.conexao.prepareStatement(sql);
            preparedStatement.setString(1, especialidade.getDescricao());
            preparedStatement.setString(2, especialidade.getDetalhamento());
            preparedStatement.setString(3, especialidade.getCodigo());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erro na alterção");
            return false;
        }finally{
        	Conexao.desconectar();
        }
        
		return true;
	}

	@Override
	public boolean excluir(Entidade entidade) {
		
		Especialidade especialidade = (Especialidade)entidade;
		
		Conexao.conectar();
        
        sql = "DELETE FROM Especialidades "
        		+ "WHERE  codigo = (?)";
        System.out.println(sql);
        
        try {
            preparedStatement = Conexao.conexao.prepareStatement(sql);
            preparedStatement.setString(1, especialidade.getCodigo());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
        	Conexao.desconectar();
        }
        
		return true;
	}

	@Override
	public List listar(Entidade entidade) {
		
		List <Especialidade> especialidades = new ArrayList();
		Especialidade especialidade = (Especialidade)entidade;
		
		try {
			Conexao.conectar();
			
			// Primeira opção
			// Buscar dados utilizando filtro 'codigo'
			if(especialidade.getCodigo() != null){	// objeto n�o vazio?
				
				// buscar uma entidade
				especialidades = listarUmaEntidade(especialidade);
			
			}else{
				// Segundo op��o
				// Buscar todos os dados da tabela
				
				especialidades = listarTodasEntidades();
			}
			
			// fechar o PreparedStatement
			preparedStatement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Conexao.desconectar();
		
		return especialidades;
	}
	
	//*****************//
	// metodos privados
	
	private List listarUmaEntidade(Especialidade especialidade){
		
		List<Especialidade> especialidades = new ArrayList();
		Especialidade esp;
		
		sql = "SELECT * "
				+ "FROM especialidades "
				+ "WHERE codigo = ?";
		
		// retorno
		return especialidades = selectEntidade(especialidade, sql);
	}
	
	// listar todas as entidades
	private List listarTodasEntidades(){
		
		List<Especialidade> especialidades = new ArrayList();
		Especialidade esp;
		
		sql = "SELECT * "
				+ "FROM especialidades "
				+ "ORDER BY descricao ASC";
		
		
		// retorno
		return especialidades = selectEntidade(null, sql);
	}
	
	// loop de select no banco
	private List selectEntidade(Especialidade especialidade, String sql){
		
		Especialidade esp;
		List<Especialidade> especialidades = new ArrayList();
		
		try {
			preparedStatement = Conexao.conexao.prepareStatement(sql);
			if(especialidade != null)
				preparedStatement.setString(1, especialidade.getCodigo());
			resultset = preparedStatement.executeQuery();
			
			while(resultset.next()){
				esp = new Especialidade();
				esp.setId(resultset.getInt("especialidade_id"));
				esp.setDescricao(resultset.getString("descricao"));
				esp.setCodigo(resultset.getString("codigo"));
				esp.setDetalhamento(resultset.getString("detalhamento"));
				try{
					esp.getDataCadastro().setTime(resultset.getTimestamp("data_cadastro"));
					}catch(java.lang.NullPointerException e){
					System.out.println("erro");
					esp.getDataCadastro().getInstance();
				}
				
				especialidades.add(esp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return especialidades;
	}
}
