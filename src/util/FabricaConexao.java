package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class FabricaConexao {

	private Connection conexao;
	
	public Connection fazerConexao() {
		
		try {
			String driverName = "com.mysql.jdbc.Driver";     
			
			Class.forName(driverName);
			
			Properties info = new Properties();
			info.setProperty("user", "cursystem");
			info.setProperty("password", "ccbh4851");
			info.setProperty("useSSL", "false");
			info.setProperty("autoReconnect", "true");
			
			conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/cursystem", info);
			        
			System.out.println("Conectado!");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conexao;
	}

	public void fecharConexao() {
		
		try {
			this.conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
