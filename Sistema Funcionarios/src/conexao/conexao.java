package conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexao {
	public Connection conec;
	public static String driver = "com.mysql.cj.jbdc.Driver"; 
	public static String stringDeConexao = "jdbc:mysql://localhost:3306/sistema_de_acompanhantes";
	public static String usuario = "root@localhost";
	public static String senha = "";
	
	public Connection conec() {
		try{
			Class.forName(driver);
			conec = DriverManager.getConnection(stringDeConexao, usuario, senha);
			return conec;
//			conexao = DriverManager.getConnection(servidor, usuario, senha);
			
//			instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			resultados = instrucaoSQL.executeQuery("SELECT * FROM acompanhantes");
//			System.out.println("deu certo");
		} catch(Exception erro){
			System.out.println(erro.getMessage());
			return null;
		}		
	}
}