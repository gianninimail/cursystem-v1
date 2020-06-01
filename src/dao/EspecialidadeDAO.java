package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Especialidade;

public class EspecialidadeDAO implements InterfaceEspecialidadeDAO {
	
	Connection conexao;
	
	public EspecialidadeDAO(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public List<Especialidade> listarTodosAtivos() throws SQLException {
		
		ResultSet rs = null;
		List<Especialidade> listaEspecialidades = new ArrayList<Especialidade>();
		
		try {
			
			String comando = "select * from especialidade WHERE ativo_iu = 1 order by id ";
			
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
                Long id = rs.getLong(1);
                String nome = rs.getString(2);

                listaEspecialidades.add(new Especialidade(id, nome));
            }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return listaEspecialidades;
	}
	
	@Override
	public List<Especialidade> listarTodos() throws SQLException {
		
		ResultSet rs = null;
		List<Especialidade> listaEspecialidades = new ArrayList<Especialidade>();
		
		try {
			
			String comando = "select * from especialidade order by id ";
			
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
                Long id = rs.getLong(1);
                String nome = rs.getString(2);

                listaEspecialidades.add(new Especialidade(id, nome));
            }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return listaEspecialidades;
	}

	@Override
	public Especialidade PegarPeloID(int _id) throws SQLException {

		String comando = "SELECT * FROM especialidade WHERE id = ?";
	
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setInt(1, _id);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			Long id = rs.getLong(1);
            String nome = rs.getString(2);
            
            return new Especialidade(id, nome);
		}
		else {
			return null;
		}

	}
}
