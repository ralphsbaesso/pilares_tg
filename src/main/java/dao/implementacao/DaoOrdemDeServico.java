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

public class DaoOrdemDeServico implements Idao {

	// objetos para coneccao
    private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String usuario = "les";
    private final String senha = "123";
    private String sql;
    private String msg;
    private Connection conexao;
    private PreparedStatement preparedStatement;
    private ResultSet resultset;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
    
	@Override
	public boolean salvar(Entidade entidade) {

		OrdemDeServico ordem = (OrdemDeServico)entidade;
		
		conectar();
	        
        sql = "INSERT INTO ordem_de_servico"
        		+ " (codigo, criticidade, status, data_cadastro, equipamento_id,"
        		+ " titulo, observacao, autor_id, data_limite, tipo_manutencao)"
        		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        //System.out.println(sql);
        
        try {
            preparedStatement = conexao.prepareStatement(sql);
            
            preparedStatement.setString(1, ordem.getCodigo());
            System.out.println(ordem.getCodigo());
            preparedStatement.setString(2, ordem.getCriticidade());
            preparedStatement.setString(3, ordem.getStatus());
            preparedStatement.setDate(4, new Date(ordem.getDataCadastro().getTimeInMillis()));
            preparedStatement.setInt(5, ordem.getEquipamento().getId());
            preparedStatement.setString(6, ordem.getTitulo());
            preparedStatement.setString(7, ordem.getObservacao());
            preparedStatement.setInt(8, ordem.getAutor().getId());
            preparedStatement.setDate(9, new Date(ordem.getDataLimite().getTimeInMillis()));
            preparedStatement.setString(10, ordem.getTipoManutencao());
            preparedStatement.execute();
            
            // resgatar a id deste ordem salvo
            sql = "select id from ordem_de_servico where codigo = ?";
            preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setString(1, ordem.getCodigo());
            resultset = preparedStatement.executeQuery();
			
			if(resultset.next()){
				ordem.setId(resultset.getInt("id"));
			}else{
				// erro
				System.out.println("Erro na procura do id da ordem de servi?o");
			}
            
            // inserir dados na tabela Atividade
            sql = "INSERT INTO atividades"
            		+ " (descricao, data_cadastro, especialidades_id, "
            		+ " codigo, os_id) "
            		+ " VALUES (?, ?, ?, ?, ?)";
            
            for(Atividade ati:  ordem.getAtividades()){
            	preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.setString(1, ati.getDescricao());
                preparedStatement.setDate(2, new Date(ati.getDataCadastro().getTimeInMillis()));
                preparedStatement.setInt(3, ati.getEspecialidade().getId());
                preparedStatement.setString(4, ati.getCodigo());
                preparedStatement.setInt(5, ordem.getId());
                preparedStatement.execute();
                
                // resgatar a id deste ordem salvo
                String sql2 = "select id from atividades where codigo = ?";
                preparedStatement = conexao.prepareStatement(sql2);
                preparedStatement.setString(1, ati.getCodigo());
                resultset = preparedStatement.executeQuery();
                
                if(resultset.next()){
    				ati.setId(resultset.getInt("id"));
    			}else{
    				// erro
    				System.out.println("Erro na procura do id da tabela atividade");
    			}
                
                //inserir dados na tabela TAREFAS
                
                String sql3 = "INSERT INTO tarefas"
	                		+ " (detalhamento, data_cadastro, "
	                		+ " codigo, atividade_id,"
	                		+ " qtde_homem, qtde_hora) "
	                		+ " VALUES (?, ?, ?, ?, ?, ?)";
                
                for(Tarefa tar: ati.getTarefas()){
                	preparedStatement = conexao.prepareStatement(sql3);
                    preparedStatement.setString(1, tar.getDetalhamento());
                    preparedStatement.setDate(2, new Date(tar.getDataCadastro().getTimeInMillis()));
                    preparedStatement.setString(3, tar.getCodigo());
                    preparedStatement.setInt(4, ati.getId());
                    preparedStatement.setInt(5, tar.getQtdeHomemEstimado());
                    preparedStatement.setDouble(6, tar.getQtdeHoraEstimada());
                    preparedStatement.execute();
                }
                
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
		OrdemDeServico ordem = (OrdemDeServico)entidade;
		
		conectar();
	        
       
        
        
        
		return true;
	}

	@Override
	public boolean excluir(Entidade entidade) {
		
		OrdemDeServico ordem = (OrdemDeServico)entidade;
		List<Integer> atividade_id = new ArrayList();
		
		conectar();
        
		try{
			
			// resgatar id de Ordem de Servi?o
			sql = "SELECT * FROM ordem_de_servico "
					+ " WHERE codigo = ?";
			preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, ordem.getCodigo());
			resultset = preparedStatement.executeQuery();
			while(resultset.next()){
				ordem.setId(resultset.getInt("id"));
			}
			
			// Deletar Ordem de Servi?o
			sql = "DELETE FROM ordem_de_servico "
	        		+ "WHERE  codigo = ?";
	        
	        preparedStatement = conexao.prepareStatement(sql);
	        preparedStatement.setString(1, ordem.getCodigo());
	        preparedStatement.execute();
			
	        sql = "SELECT * FROM atividades "
        		+ "WHERE  os_id = " + ordem.getId();
	        
	        // resgatar id's das atividades que ser? apagada
            preparedStatement = conexao.prepareStatement(sql);
            resultset = preparedStatement.executeQuery();
            
            while(resultset.next()){
            	atividade_id.add(resultset.getInt("id"));
            }
            
            // Deletar todas as atividades da ordem de servi?o
            sql = "DELETE FROM atividades"
            		+ " WHERE os_id = " + ordem.getId();
            preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.execute();
            
            // Deletar as tarefas das atividades
            for(int id: atividade_id){
            	
            	sql = "DELETE FROM tarefas"
                		+ " WHERE atividade_id = " + id;
                preparedStatement = conexao.prepareStatement(sql);
                preparedStatement.execute();
                
            }

           preparedStatement.close();
           
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }finally{
        	desconectar();
        }
        
		return true;
	}

	@Override
	public List listar(Entidade entidade) {
		
		List <OrdemDeServico> ordens = new ArrayList();
		OrdemDeServico ordem = (OrdemDeServico)entidade;
		
		try {
			conectar();
			
			// verificar se ? para lista tudo
			try{
				if(ordem.getCodigo().equals("tudo")){
					ordem = null;
				}
			}catch(Exception e){
				ordem = null;
			}
			
			// Primeira op??o
			// Buscar tudo sem filtro
			if(ordem == null ){	// objeto n?o vazio?
				
				sql = "SELECT * "
						+ "FROM ordem_de_servico"
						+ " order by id desc";
				preparedStatement = conexao.prepareStatement(sql);
				
				ordens = selectEntidade();
			
			}else{ // objeto n?o nulo, verificar filtros
				
				if(ordem.getCodigo().equals("mesAno")){
					// filtro pela data da finaliza??o das a??es
					String dataFinal = ordem.getObservacao();
					
					sql = "SELECT DISTINCT os.*" 
						   + " from ORDEM_DE_SERVICO os "
						   + " join "
						   + " atividades ati "
						   + " on (os.id = ati.os_id) "
						   + " join " 
						   + " tarefas tar "
						   + " on (ati.id = tar.atividade_id) "
						   + " join "
						   + " acoes "
						   + " on(tar.id = acoes.tarefa_id) "
						   + " where to_char(acoes.DATA_FINAL, 'MM/yyyy') = '" + dataFinal + "'";
					
					System.out.println(sql);
					preparedStatement = conexao.prepareStatement(sql);
					
					ordens = selectEntidade();
					
				}else if(ordem.getCodigo().equals("ano")){
					
					String dataFinal = ordem.getObservacao();
					
					sql = "SELECT DISTINCT os.*" 
						   + " from ORDEM_DE_SERVICO os "
						   + " join "
						   + " atividades ati "
						   + " on (os.id = ati.os_id) "
						   + " join " 
						   + " tarefas tar "
						   + " on (ati.id = tar.atividade_id) "
						   + " join "
						   + " acoes "
						   + " on(tar.id = acoes.tarefa_id) "
						   + " where to_char(acoes.DATA_FINAL, 'yyyy') = '" + dataFinal + "'";
					
					preparedStatement = conexao.prepareStatement(sql);
					
					ordens = selectEntidade();
					
				}else if(ordem.getCodigo() != null){
					// filtro: c?digo
					sql = "SELECT * "
							+ "FROM ordem_de_servico "
							+ "WHERE codigo = ?";
					
					preparedStatement = conexao.prepareStatement(sql);
					preparedStatement.setString(1, ordem.getCodigo());
					
					ordens = selectEntidade();
					
				}
			}
			
			// fechar o PreparedStatement
			preparedStatement.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		desconectar();
		
		return ordens;
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
		
		OrdemDeServico ordemDB = null;
		List<OrdemDeServico> ordens = new ArrayList();
		
		try {
			resultset = preparedStatement.executeQuery();
			
			while(resultset.next()){
				ordemDB = new OrdemDeServico();
				ordemDB.setId(resultset.getInt("id"));
				ordemDB.setCodigo(resultset.getString("codigo"));
				ordemDB.setCriticidade(resultset.getString("criticidade"));
				ordemDB.setStatus(resultset.getString("status"));
				ordemDB.getEquipamento().setId(resultset.getInt("equipamento_id"));
				ordemDB.setTitulo(resultset.getString("titulo"));
				ordemDB.setObservacao(resultset.getString("observacao"));
				ordemDB.getAutor().setId(resultset.getInt("autor_id"));
				ordemDB.setTipoManutencao(resultset.getString("tipo_manutencao"));
				try{
					ordemDB.getDataLimite().setTime(resultset.getTimestamp("data_limite"));
					}catch(java.lang.NullPointerException e){
					System.out.println("erro na data limite");
					ordemDB.getDataCadastro().getInstance();
				}
				try{
					ordemDB.getDataCadastro().setTime(resultset.getTimestamp("data_cadastro"));
					}catch(java.lang.NullPointerException e){
					System.out.println("erro");
					ordemDB.getDataCadastro().getInstance();
				}
				
				// buscar atividades das OSs
				String sql2 = "select ati.* " +
								  " from ORDEM_DE_SERVICO os " +
								  " join " +
								  " ATIVIDADES ati " +
								  " on (os.ID = ati.os_id) " +
								  " where os.id = ? ";
				preparedStatement = conexao.prepareStatement(sql2);
				preparedStatement.setInt(1, ordemDB.getId());
				ResultSet rs2 = preparedStatement.executeQuery();
				Atividade ati = null;
				while(rs2.next()){
					ati = new Atividade();
					ati.setId(rs2.getInt("id"));
					ati.setDescricao(rs2.getString("descricao"));
					ati.getEspecialidade().setId(rs2.getInt("especialidades_id"));
					ati.setCodigo(rs2.getString("codigo"));
					try{
						ati.getDataCadastro().setTime(resultset.getTimestamp("data_cadastro"));
						}catch(java.lang.NullPointerException e){
						System.out.println("erro em pegar a data de cadastro de atividade");
						ati.getDataCadastro().getInstance();
					}
					
					// Buscar Tarefas dentro de Atividade
					String sql3 = "SELECT taf.* "
									+ " FROM atividades ati "
									+ " JOIN "
									+ " tarefas taf "
									+ " ON (ati.id = taf.atividade_id)"
									+ " WHERE ati.id = ?";
									
					preparedStatement = conexao.prepareStatement(sql3);
					preparedStatement.setInt(1, ati.getId());
					ResultSet rs3 = preparedStatement.executeQuery();
					Tarefa taf = null;
					while(rs3.next()){
						taf = new Tarefa();
						taf.setId(rs3.getInt("id"));
						taf.setDetalhamento(rs3.getString("detalhamento"));
						taf.setQtdeHomemEstimado(rs3.getInt("qtde_homem"));
						taf.setQtdeHoraEstimada(rs3.getDouble("qtde_hora"));
						taf.setCodigo(rs3.getString("codigo"));
						try{
							taf.getDataCadastro().setTime(rs3.getTimestamp("data_cadastro"));
							}catch(java.lang.NullPointerException e){
							System.out.println("erro em pegar a data de cadastro de tarefa");
							taf.getDataCadastro().getInstance();
						}
						
						// Buscar as a?oes dentro de tarefa
						
						Apontamento apontamento;
						Planejamento planejamento;
						String sql5 = "SELECT acoes.* "
										+ " FROM tarefas "
										+ " JOIN "
										+ " acoes"
										+ " ON (tarefas.id = acoes.tarefa_id)"
										+ " WHERE tarefas.id = " + taf.getId();
						preparedStatement = conexao.prepareStatement(sql5);
						ResultSet rs5 = preparedStatement.executeQuery();
						while(rs5.next()){
							String nomeTipo = rs5.getString("tipo");
							
							if(nomeTipo.equals("apontamento")){
								
								apontamento = new Apontamento();
								
								apontamento.setId(rs5.getInt("id"));
								apontamento.setCodigo(rs5.getString("codigo"));
								apontamento.setDetalhamento(rs5.getString("detalhamento"));
								apontamento.setCancelado(rs5.getString("cancelado"));
								apontamento.getTarefa().setId(rs5.getInt("tarefa_id"));
								taf.setId(rs5.getInt("tarefa_id"));
								apontamento.getExecutante().setId(rs5.getInt("executante_id"));
								apontamento.getAutor().setId(rs5.getInt("autor_id"));
								try{
									apontamento.getDataInicial().setTime(rs5.getTimestamp("data_inicial"));
									}catch(java.lang.NullPointerException e){
									System.out.println("erro na data 1");
									apontamento.getDataCadastro().getInstance();
								}
								try{
									apontamento.getDataFinal().setTime(rs5.getTimestamp("data_final"));
									}catch(java.lang.NullPointerException e){
									System.out.println("erro na data 2");
									apontamento.getDataCadastro().getInstance();
								}
								try{
									apontamento.getDataCadastro().setTime(rs5.getTimestamp("data_cadastro"));
									}catch(java.lang.NullPointerException e){
									System.out.println("erro 3");
									apontamento.getDataCadastro().getInstance();
								}
								// aciona apontamento na tarefa
								taf.getApontamentos().add(apontamento);
								
							}else if(nomeTipo.equals("planejamento")){
								planejamento = new Planejamento();
								
								planejamento.setId(rs5.getInt("id"));
								planejamento.setCodigo(rs5.getString("codigo"));
								planejamento.setDetalhamento(rs5.getString("detalhamento"));
								planejamento.setCancelado(rs5.getString("cancelado"));
								planejamento.getTarefa().setId(rs5.getInt("tarefa_id"));
								taf.setId(rs5.getInt("tarefa_id"));
								planejamento.getExecutante().setId(rs5.getInt("executante_id"));
								planejamento.getAutor().setId(rs5.getInt("autor_id"));
								try{
									planejamento.getDataInicial().setTime(rs5.getTimestamp("data_inicial"));
									}catch(java.lang.NullPointerException e){
									System.out.println("erro na data 1");
									planejamento.getDataCadastro().getInstance();
								}
								try{
									planejamento.getDataFinal().setTime(rs5.getTimestamp("data_final"));
									}catch(java.lang.NullPointerException e){
									System.out.println("erro na data 2");
									planejamento.getDataCadastro().getInstance();
								}
								try{
									planejamento.getDataCadastro().setTime(rs5.getTimestamp("data_cadastro"));
									}catch(java.lang.NullPointerException e){
									System.out.println("erro 3");
									planejamento.getDataCadastro().getInstance();
								}
								// adiciona na tarefa
								taf.getPlanejamentos().add(planejamento);
							}
						}// while - tarefa
						// fechar resulset
						rs5.close();
						
					//incrementar tarefas em atividade
					ati.getTarefas().add(taf);
					}// while - tarefa
					rs3.close();
					
					// buscar especialidade da atividade
					String sql4 = "SELECT * "
							+ " FROM especialidades "
							+ " JOIN "
							+ " WHERE id = ?";
							
					preparedStatement = conexao.prepareStatement(sql4);
					preparedStatement.setInt(1, ati.getEspecialidade().getId());
					ResultSet rs4 = preparedStatement.executeQuery();
					try{
						while(rs4.next()){
							taf = new Tarefa();
							ati.getEspecialidade().setDescricao((rs4.getString("descricao")));
							ati.getEspecialidade().setDetalhamento((rs4.getString("detalhes")));
							ati.getEspecialidade().setCodigo((rs4.getString("codigo")));
							try{
								ati.getEspecialidade().getDataCadastro().setTime(rs4.getTimestamp("data_cadastro"));
								}catch(java.lang.NullPointerException e){
								System.out.println("erro em pegar a data de cadastro da especialidade");
								ati.getEspecialidade().getDataCadastro().getInstance();
							}
						}
					}finally{
						rs4.close();
					}
					
					// incrementar atividades em ordem de servi?o
					ordemDB.getAtividades().add(ati);
				}
				rs2.close();
			
				
				// Buscar Autor da Ordem de Servi?o
				
				String sql6 = "SELECT * "
						+ " FROM mantenedores "
						+ " WHERE id = " + ordemDB.getAutor().getId();
						
				preparedStatement = conexao.prepareStatement(sql6);
				ResultSet rs6 = preparedStatement.executeQuery();
				try{
					while(rs6.next()){
						ordemDB.getAutor().getDataCadastro().setTime(rs6.getDate("data_cadastro"));
						ordemDB.getAutor().setNome(rs6.getString("nome"));
						ordemDB.getAutor().setCpf(rs6.getString("cpf"));
						ordemDB.getAutor().setEmail(rs6.getString("email"));
						ordemDB.getAutor().setRegistro(rs6.getString("registro"));
						ordemDB.getAutor().setSexo(rs6.getString("sexo"));
					}
				}finally{
					rs6.close();
				}
				
				// Buscar Equipamento da Ordem de Servi?o
				String sql7 = "SELECT * "
						+ " FROM equipamentos "
						+ " WHERE id = " + ordemDB.getEquipamento().getId();
						
				preparedStatement = conexao.prepareStatement(sql7);
				ResultSet rs7 = preparedStatement.executeQuery();
				try{
					while(rs7.next()){
						ordemDB.getEquipamento().setCriticidade(rs7.getString("criticidade"));
						ordemDB.getEquipamento().setNome(rs7.getString("nome"));
						ordemDB.getEquipamento().setTipo(rs7.getString("tipo"));;
					}
				}finally{
					rs7.close();
				}
				
				
				ordens.add(ordemDB);
				
			}// while - ordem de servi?o
			resultset.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ordens;
	}
}
