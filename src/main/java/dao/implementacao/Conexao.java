package dao.implementacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
	static private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	static private final String usuario = "Pilares_tg";
	static private final String senha = "123";
    static public Connection conexao;
	
	static public void conectar(){
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
	
	static public void desconectar(){
		try {
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
