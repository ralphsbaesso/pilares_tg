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

public class DaoTarefa implements Idao {

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

		Tarefa tarefa = (Tarefa)entidade;
		
		conectar();
        
        try {
        	// persistir os apontamentos
        	if(tarefa.getQtdeApontamentos() > 0){
        		
        		for(Apontamento ap: tarefa.getApontamentos()){
        			
        			sql = "INSERT INTO acoes"
        	        		+ " (tarefa_id, data_inicial, data_final,"
        	        		+ "data_cadastro, codigo, detalhamento, tipo,"
        	        		+ " executante_id, autor_id)"
        	        		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        			
        			preparedStatement = conexao.prepareStatement(sql);
                	
    	            preparedStatement.setInt(1, ap.getTarefa().getId());
    	            preparedStatement.setDate(2, new Date(ap.getDataInicial().getTimeInMillis()));
    	            preparedStatement.setDate(3, new Date(ap.getDataFinal().getTimeInMillis()));
    	            preparedStatement.setDate(4, new Date(ap.getDataCadastro().getTimeInMillis()));
    	            preparedStatement.setString(5, ap.getCodigo());
    	            preparedStatement.setString(6, ap.getDetalhamento());
    	            preparedStatement.setString(7, "apontamento");
    	            preparedStatement.setInt(8, ap.getExecutante().getId());
    	            preparedStatement.setInt(9, ap.getAutor().getId());
    	            preparedStatement.execute();
        		}
        		
        		// Apontamento salvo, atualizar o status da ordem de servi?o
                sql = "UPDATE ordem_de_servico"
                		+ " set status = ? "
                		+ " where id = ?";
                preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setString(1, tarefa.getAtividade().getOs().getStatus());
                preparedStatement.setInt(2, tarefa.getAtividade().getOs().getId());
                preparedStatement.execute();
        	}
        	
        	// persistir os planejamentos
        	if(tarefa.getQtdePlanejamentos() > 0){
        		
        		for(Planejamento plan: tarefa.getPlanejamentos()){
        			
        			sql = "INSERT INTO acoes"
        	        		+ " (tarefa_id, data_inicial, data_final,"
        	        		+ "data_cadastro, codigo, detalhamento, tipo,"
        	        		+ " executante_id, autor_id)"
        	        		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        			
        			preparedStatement = conexao.prepareStatement(sql);
                	
    	            preparedStatement.setInt(1, plan.getTarefa().getId());
    	            preparedStatement.setDate(2, new Date(plan.getDataInicial().getTimeInMillis()));
    	            preparedStatement.setDate(3, new Date(plan.getDataFinal().getTimeInMillis()));
    	            preparedStatement.setDate(4, new Date(plan.getDataCadastro().getTimeInMillis()));
    	            preparedStatement.setString(5, plan.getCodigo());
    	            preparedStatement.setString(6, plan.getDetalhamento());
    	            preparedStatement.setString(7, "planejamento");
    	            preparedStatement.setInt(8, plan.getExecutante().getId());
    	            preparedStatement.setInt(9, plan.getAutor().getId());
    	            preparedStatement.execute();
        		}
        		
        		// Planejamento salvo, atualizar o status da ordem de servi?o
                sql = "UPDATE ordem_de_servico"
                		+ " set status = ? "
                		+ " where id = ?";
                preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setString(1, tarefa.getAtividade().getOs().getStatus());
                preparedStatement.setInt(2, tarefa.getAtividade().getOs().getId());
                preparedStatement.execute();
        	}
			
        preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            desconectar();
            return false;
        }finally{
        	desconectar();
        }
        
		return true;
	}

	@Override
	public boolean alterar(Entidade entidade) {
		Planejamento listaPla = (Planejamento)entidade;
		
		conectar();
	        
        sql = "UPDATE Mantenedores"
        		+ " SET nome = ?, registro = ?, email = ?"
        		+ " WHERE cpf = ?";
        //System.out.println(sql + " - cpf: " + listaPla.getObservacao());
        
        try {
            preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.execute();
            preparedStatement.close();
            
            // alterar dados da tabela MANTENEDORES_ESPECIALIDADES
            
            // primeiro apagar todos dados existente desta entidade
            
            sql = "delete from MANTENEDORES_ESPECIALIDADES "
				  + " where MANTENEDORES_ESPECIALIDADES.MANTENEDORES_ID = " 
				  + "(select id from ordens "
				  + " where cpf = ?) ";
				  
			  preparedStatement = conexao.prepareStatement(sql);
			  //preparedStatement.setInt(1, listaPla.getObservacao());
			  preparedStatement.execute();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
        	desconectar();
        }
        
		return true;
	}

	@Override
	public boolean excluir(Entidade entidade) {
		
		Planejamento listaPla = (Planejamento)entidade;
		
		conectar();
        
		// Deletar a dependencia de listaPla com especialidade
		
		sql = "DELETE FROM MANTENEDORES_ESPECIALIDADES "
        		+ "WHERE  mantenedores_id = ?";
        
        try {
            preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, listaPla.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
		
		// Deletar o listaPla
        sql = "DELETE FROM Mantenedores "
        		+ "WHERE  cpf = (?)";
        System.out.println(sql);
        
        try {
            preparedStatement = conexao.prepareStatement(sql);
            //preparedStatement.setInt(1, listaPla.getObservacao());
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
		
		Tarefa tarefa = (Tarefa)entidade;
		List<Tarefa> tarefas = new ArrayList();
		SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
		String mes;
		
		try {
			conectar();
			
			// Primeira op??o
			// Buscar tudo sem filtro
			if(tarefa.getCodigo() == null){	// objeto vazio?
				
				sql = "SELECT * "
						+ "FROM acoes"
						+ " order by id desc";
				preparedStatement = conexao.prepareStatement(sql);
				
				tarefas = selectEntidade();
			
			}else{ // objeto n?o nulo, verificar filtros
					
				// tem algum apontamento para fazer filtro?
				if(tarefa.getApontamentos().size() != 0){
					// pegar data final do apontamento
					mes = sdfMes.format(tarefa.getApontamentos().get(0).getDataFinal().getTime());
					
					sql = "SELECT * "
							+ "FROM acoes "
							+ "WHERE to_char(data_final, 'MM')  = ? "
							+ " and tipo = 'apontamento' "
							+ " order by id";
					
					preparedStatement = conexao.prepareStatement(sql);
					preparedStatement.setString(1, mes);
					
					tarefas = selectEntidade();
				}else if(tarefa.getPlanejamentos().size() != 0){
					// pegar data final do planejamento
					mes = sdfMes.format(tarefa.getApontamentos().get(0).getDataFinal().getTime());
					
					sql = "SELECT * "
							+ "FROM acoes "
							+ "WHERE to_char(data_final, 'MM')  = ? "
							+ " and tipo = 'planejamento' "
							+ " order by id";
					
					preparedStatement = conexao.prepareStatement(sql);
					preparedStatement.setString(1, mes);
					
					tarefas = selectEntidade();
				}
				
			}
			
			// fechar o PreparedStatement
			preparedStatement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		desconectar();
		
		
		return tarefas;
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
	
	
	// loop de select no banco
	private List selectEntidade(){
		
		
		Apontamento apontamento;
		Planejamento planejamento;
		Tarefa tarefa = new Tarefa();
		List<Tarefa> tarefas = new ArrayList();
		String nomeTipo;
		
		try {
			
			resultset = preparedStatement.executeQuery();
			
			while(resultset.next()){
				
				
				nomeTipo = resultset.getString("tipo");
				
				if(nomeTipo.equals("apontamento")){
					
					apontamento = new Apontamento();
					
					apontamento.setId(resultset.getInt("id"));
					apontamento.setCodigo(resultset.getString("codigo"));
					apontamento.setDetalhamento(resultset.getString("detalhamento"));
					apontamento.setCancelado(resultset.getString("cancelado"));
					apontamento.getTarefa().setId(resultset.getInt("tarefa_id"));
					tarefa.setId(resultset.getInt("tarefa_id"));
					apontamento.getExecutante().setId(resultset.getInt("executante_id"));
					apontamento.getAutor().setId(resultset.getInt("autor_id"));
					try{
						apontamento.getDataInicial().setTime(resultset.getTimestamp("data_inicial"));
						}catch(java.lang.NullPointerException e){
						System.out.println("erro na data 1");
						apontamento.getDataCadastro().getInstance();
					}
					try{
						apontamento.getDataFinal().setTime(resultset.getTimestamp("data_final"));
						}catch(java.lang.NullPointerException e){
						System.out.println("erro na data 2");
						apontamento.getDataCadastro().getInstance();
					}
					try{
						apontamento.getDataCadastro().setTime(resultset.getTimestamp("data_cadastro"));
						}catch(java.lang.NullPointerException e){
						System.out.println("erro 3");
						apontamento.getDataCadastro().getInstance();
					}
					
					// Buscar dados do planejador
					sql ="SELECT * "
							+ "	FROM mantenedores "
							+ " WHERE id = " + apontamento.getAutor().getId();
					try{
						preparedStatement = conexao.prepareStatement(sql);
						ResultSet rs;
						rs = preparedStatement.executeQuery();
						while(rs.next()){
							Mantenedor p = new Mantenedor();
							p.getDataCadastro().setTime(rs.getDate("data_cadastro"));
							p.setNome(rs.getString("nome"));
							p.setCpf(rs.getString("cpf"));
							p.setEmail(rs.getString("email"));
							p.setRegistro(rs.getString("registro"));
							p.setSexo(rs.getString("sexo"));
							apontamento.setAutor(p);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					
					// Buscar dados do executante
					sql ="SELECT * "
							+ "	FROM mantenedores "
							+ " WHERE id = " + apontamento.getExecutante().getId();
					try{
						preparedStatement = conexao.prepareStatement(sql);
						ResultSet rs2;
						rs2 = preparedStatement.executeQuery();
						while(rs2.next()){
							Mantenedor e = new Mantenedor();
							e.getDataCadastro().setTime(rs2.getDate("data_cadastro"));
							e.setNome(rs2.getString("nome"));
							e.setCpf(rs2.getString("cpf"));
							e.setEmail(rs2.getString("email"));
							e.setRegistro(rs2.getString("registro"));
							e.setSexo(rs2.getString("sexo"));
							apontamento.setExecutante(e);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					
					
					// aciona apontamento na tarefa
					tarefa.getApontamentos().add(apontamento);
					
				}else if(nomeTipo.equals("planejamento")){
					planejamento = new Planejamento();
					
					planejamento.setId(resultset.getInt("id"));
					planejamento.setCodigo(resultset.getString("codigo"));
					planejamento.setDetalhamento(resultset.getString("detalhamento"));
					planejamento.setCancelado(resultset.getString("cancelado"));
					planejamento.getTarefa().setId(resultset.getInt("tarefa_id"));
					tarefa.setId(resultset.getInt("tarefa_id"));
					planejamento.getExecutante().setId(resultset.getInt("executante_id"));
					planejamento.getAutor().setId(resultset.getInt("autor_id"));
					try{
						planejamento.getDataInicial().setTime(resultset.getTimestamp("data_inicial"));
						}catch(java.lang.NullPointerException e){
						System.out.println("erro na data 1");
						planejamento.getDataCadastro().getInstance();
					}
					try{
						planejamento.getDataFinal().setTime(resultset.getTimestamp("data_final"));
						}catch(java.lang.NullPointerException e){
						System.out.println("erro na data 2");
						planejamento.getDataCadastro().getInstance();
					}
					try{
						planejamento.getDataCadastro().setTime(resultset.getTimestamp("data_cadastro"));
						}catch(java.lang.NullPointerException e){
						System.out.println("erro 3");
						planejamento.getDataCadastro().getInstance();
					}
					
					// Buscar dados do planejador
					sql ="SELECT * "
							+ "	FROM mantenedores "
							+ " WHERE id = " + planejamento.getAutor().getId();
					try{
						preparedStatement = conexao.prepareStatement(sql);
						ResultSet rs;
						rs = preparedStatement.executeQuery();
						while(rs.next()){
							Mantenedor p = new Mantenedor();
							p.getDataCadastro().setTime(rs.getDate("data_cadastro"));
							p.setNome(rs.getString("nome"));
							p.setCpf(rs.getString("cpf"));
							p.setEmail(rs.getString("email"));
							p.setRegistro(rs.getString("registro"));
							p.setSexo(rs.getString("sexo"));
							planejamento.setAutor(p);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					
					// Buscar dados do executante
					sql ="SELECT * "
							+ "	FROM mantenedores "
							+ " WHERE id = " + planejamento.getExecutante().getId();
					try{
						preparedStatement = conexao.prepareStatement(sql);
						ResultSet rs2;
						rs2 = preparedStatement.executeQuery();
						while(rs2.next()){
							Mantenedor e = new Mantenedor();
							e.getDataCadastro().setTime(rs2.getDate("data_cadastro"));
							e.setNome(rs2.getString("nome"));
							e.setCpf(rs2.getString("cpf"));
							e.setEmail(rs2.getString("email"));
							e.setRegistro(rs2.getString("registro"));
							e.setSexo(rs2.getString("sexo"));
							planejamento.setExecutante(e);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					
					// adiciona na tarefa
					tarefa.getPlanejamentos().add(planejamento);
				}
			}// while
				
				// Buscar dados da tarefa
				sql ="SELECT * "
						+ " FROM tarefas "
						+ " WHERE id = " + tarefa.getId();
				
				preparedStatement = conexao.prepareStatement(sql);
				ResultSet rs3;
				rs3 = preparedStatement.executeQuery();
				while(rs3.next()){
					tarefa.setDetalhamento(rs3.getString("detalhamento"));
					tarefa.setCodigo(rs3.getString("codigo"));
					tarefa.setQtdeHomemEstimado(rs3.getInt("qtde_homem"));
					tarefa.setQtdeHoraEstimada(rs3.getDouble("qtde_hora"));
					tarefa.getAtividade().setId(rs3.getInt("atividade_id"));
					try{
						tarefa.getDataCadastro().setTime(rs3.getTimestamp("data_cadastro"));
						}catch(java.lang.NullPointerException e){
						System.out.println("erro 4");
						tarefa.getDataCadastro().getInstance();
					}
				}
				
				// Buscar dados de Atividade
				sql ="SELECT * "
						+ " FROM atividades "
						+ " WHERE id = " + tarefa.getAtividade().getId();
				
				preparedStatement = conexao.prepareStatement(sql);
				ResultSet rs4;
				rs4 = preparedStatement.executeQuery();
				while(rs4.next()){
					tarefa.getAtividade().setDescricao(rs4.getString("descricao"));
					tarefa.getAtividade().setCodigo(rs4.getString("codigo"));
					tarefa.getAtividade().getEspecialidade().setId(rs4.getInt("especialidades_id"));
					try{
						tarefa.getAtividade().getDataCadastro().setTime(rs4.getTimestamp("data_cadastro"));
						}catch(java.lang.NullPointerException e){
						System.out.println("erro 4");
						tarefa.getAtividade().getDataCadastro().getInstance();
					}
				}
				
				
				
				// Buscar dados da Ordem de Servi?o
				sql = "SELECT * "
						+ "FROM ordem_de_servico "
						+ "WHERE id = " + tarefa.getAtividade().getOs().getId();
				
				preparedStatement = conexao.prepareStatement(sql);
				ResultSet rs5;
				rs5 = preparedStatement.executeQuery();
				while(rs5.next()){
					tarefa.getAtividade().getOs().setTitulo(rs5.getString("titulo"));
					tarefa.getAtividade().getOs().setCodigo(rs5.getString("codigo"));
					tarefa.getAtividade().getOs().setCriticidade(rs5.getString("criticidade"));
					tarefa.getAtividade().getOs().setTipoManutencao(rs5.getString("tipo_manutencao"));
				}
					
				tarefas.add(tarefa);
			
			
		} catch (SQLException e) {
			System.out.println("Erro no select");
			e.printStackTrace();
		}

		
		return tarefas;
	}
}
