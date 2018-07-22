package dao.implementacao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Idao;
import dominio.CentroCusto;
import dominio.Entidade;

public class DaoCentroCusto implements Idao {

	// objetos para coneccao
	private String sql;
	private PreparedStatement preparedStatement;
	private ResultSet resultset;

	@Override
	public boolean salvar(Entidade entidade) {

		CentroCusto centroCusto = (CentroCusto) entidade;

		Conexao.conectar();

		sql = "INSERT INTO centros_de_custos (descricao, data_cadastro, codigo, detalhamento) VALUES (?, ?, ?, ?)";

		try {
			preparedStatement = Conexao.conexao.prepareStatement(sql);
			preparedStatement.setString(1, centroCusto.getDescricao());
			preparedStatement.setDate(2, new Date(centroCusto.getDataCadastro().getTimeInMillis()));
			preparedStatement.setString(3, centroCusto.getCodigo());
			preparedStatement.setString(4, centroCusto.getDetalhamento());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			Conexao.desconectar();
		}

		return true;
	}

	@Override
	public boolean alterar(Entidade entidade) {
		CentroCusto centroCusto = (CentroCusto) entidade;

		Conexao.conectar();

		sql = "UPDATE centros_de_custos SET descricao = ?, detalhamento = ? WHERE id = ?";

		try {
			preparedStatement = Conexao.conexao.prepareStatement(sql);
			preparedStatement.setString(1, centroCusto.getDescricao());
			preparedStatement.setString(2, centroCusto.getDetalhamento());
			preparedStatement.setInt(3, centroCusto.getId());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			Conexao.desconectar();
		}

		return true;
	}

	@Override
	public boolean excluir(Entidade entidade) {

		CentroCusto centroCusto = (CentroCusto) entidade;

		Conexao.conectar();

		sql = "DELETE FROM centros_de_custos WHERE  id = (?)";

		try {
			preparedStatement = Conexao.conexao.prepareStatement(sql);
			preparedStatement.setInt(1, centroCusto.getId());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			Conexao.desconectar();
		}

		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List listar(Entidade entidade) {

		List<CentroCusto> centroCustos = new ArrayList();
		CentroCusto centroCusto = (CentroCusto) entidade;

		try {
			Conexao.conectar();

			// Primeira opção
			// Buscar dados utilizando filtro 'codigo'
			if (centroCusto.getCodigo() != null || centroCusto.getDescricao() != null) { // objeto n�o vazio?

				// buscar uma entidade
				centroCustos = listarEntidadePorFiltro(centroCusto);

			} else {
				// Segundo op��o
				// Buscar todos os dados da tabela

				centroCustos = listarTodasEntidades();
			}

			// fechar o PreparedStatement
			preparedStatement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Conexao.desconectar();

		return centroCustos;
	}

	// *****************//
	// metodos privados

	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	private List listarEntidadePorFiltro(CentroCusto centroCusto) {

		List<CentroCusto> centroCustos = new ArrayList();
		CentroCusto cc;

		List<String> parametros = new ArrayList();

		StringBuilder sbSql = new StringBuilder();

		try {

			sbSql.append("SELECT * FROM centros_de_custos WHERE 1 = 1 ");

			if (centroCusto.getCodigo() != null && !centroCusto.getCodigo().isEmpty()) {

				sbSql.append(" AND CODIGO = ? ");
				parametros.add(centroCusto.getCodigo());

			} else if (centroCusto.getDescricao() != null) {

				sbSql.append(" AND LOWER(DESCRICAO) LIKE ? ");
				parametros.add("%" + centroCusto.getDescricao().toLowerCase() + "%");
			}

			preparedStatement = Conexao.conexao.prepareStatement(sbSql.toString());

			// carregar parametros de filtro do tipo String
			for (int i = 0; i < parametros.size(); i++) {
				preparedStatement.setString(i + 1, parametros.get(i));
			}

			resultset = preparedStatement.executeQuery();

			while (resultset.next()) {
				cc = new CentroCusto();
				cc.setId(resultset.getInt("id"));
				cc.setDescricao(resultset.getString("descricao"));
				cc.setCodigo(resultset.getString("codigo"));
				cc.setDetalhamento(resultset.getString("detalhamento"));
				try {
					cc.getDataCadastro().setTime(resultset.getTimestamp("data_cadastro"));
				} catch (java.lang.NullPointerException e) {
					cc.getDataCadastro().getInstance();
				}

				centroCustos.add(cc);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// retorno
		return centroCustos;
	}

	// listar todas as entidades
	private List listarTodasEntidades() {

		List<CentroCusto> centroCustos = new ArrayList();
		CentroCusto cc;

		sql = "SELECT * FROM centros_de_custos ORDER BY descricao ASC";

		// retorno
		return centroCustos = selectEntidade(null, sql);
	}

	// loop de select no banco
	private List selectEntidade(CentroCusto centroCusto, String sql) {

		CentroCusto cc;
		List<CentroCusto> centroCustos = new ArrayList();

		try {
			preparedStatement = Conexao.conexao.prepareStatement(sql);
			if (centroCusto != null)
				preparedStatement.setString(1, centroCusto.getCodigo());
			resultset = preparedStatement.executeQuery();

			while (resultset.next()) {
				cc = new CentroCusto();
				cc.setId(resultset.getInt("id"));
				cc.setDescricao(resultset.getString("descricao"));
				cc.setCodigo(resultset.getString("codigo"));
				cc.setDetalhamento(resultset.getString("detalhamento"));
				try {
					cc.getDataCadastro().setTime(resultset.getTimestamp("data_cadastro"));
				} catch (java.lang.NullPointerException e) {
					cc.getDataCadastro().getInstance();
				}

				centroCustos.add(cc);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return centroCustos;
	}
}
