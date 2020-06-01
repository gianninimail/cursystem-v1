package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Subsistema;

public class FuncaoSubsistemaDAO implements InterfaceFuncaoSubsistemaDAO {

Connection conexao;
	
	public FuncaoSubsistemaDAO(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public List<Subsistema> listarTodos() throws SQLException {
		ResultSet rs = null;
		List<Subsistema> listaFuncaoSubsistemas = new ArrayList<Subsistema>();
		
		try {
			
			String comando = "select * from function_subsystem order by id";
			
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
                Long id = rs.getLong(1);
                String nome = rs.getString(2);

                listaFuncaoSubsistemas.add(new Subsistema(id, nome));
            }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return listaFuncaoSubsistemas;
	}

	@Override
	public Subsistema PegarPeloID(int _id) throws SQLException {

		String comando = "SELECT * FROM function_subsystem WHERE id = ?";
	
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setInt(1, _id);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			Long id = rs.getLong(1);
            String nome = rs.getString(2);
            
            return new Subsistema(id, nome);
		}
		else {
			return null;
		}

	}
}
