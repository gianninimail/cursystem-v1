package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Status;

public class StatusDAO implements InterfaceStatusDAO {
	
	Connection conexao;
	
	public StatusDAO(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public List<Status> listarTodos() throws SQLException {
		ResultSet rs = null;
		List<Status> listaStatuss = new ArrayList<Status>();
		
		try {
			
			String comando = "select * from especialidade order by id";
			
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);

                listaStatuss.add(new Status(id, nome));
            }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return listaStatuss;
	}

	@Override
	public Status PegarPeloID(int _id) throws SQLException {

		String comando = "SELECT * FROM status WHERE id = ?";
	
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setInt(1, _id);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			int id = rs.getInt(1);
            String nome = rs.getString(2);
            
            return new Status(id, nome);
		}
		else {
			return null;
		}

	}
}
