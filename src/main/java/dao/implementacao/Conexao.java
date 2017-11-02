package dao.implementacao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
//	static private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	static private final String usuario = "Pilares_tg";
//	static private final String senha = "123";
	static private final String url = "jdbc:mysql://sql10.freemysqlhosting.net:3306/sql10202547";
	static private final String usuario = "sql10202547";
	static private final String senha = "nGVuKYDml9";
    static public Connection conexao;
	
	static public void conectar(){
		try {
			System.out.println("SQL conection");
//			Class.forName("oracle.jdbc.OracleDriver");
			Class.forName("com.mysql.jdbc.Driver"); 
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
