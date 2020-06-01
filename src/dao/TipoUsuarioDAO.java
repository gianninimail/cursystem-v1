package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.TipoUsuario;

public class TipoUsuarioDAO implements InterfaceTipoUsuarioDAO {

	Connection conexao;
	
	public TipoUsuarioDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	@Override
	public List<TipoUsuario> listarTodos() throws SQLException {
		
		ResultSet rs = null;
		List<TipoUsuario> listaTipoUsuarios = new ArrayList<TipoUsuario>();
		
		try {
			
			String comando = "select * from tipo_usuario order by id ";
			
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
                Long id = rs.getLong(1);
                String nome = rs.getString(2);

                listaTipoUsuarios.add(new TipoUsuario(id, nome));
            }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return listaTipoUsuarios;
	}

	@Override
	public TipoUsuario PegarPeloID(Long _id) throws SQLException {
		
		String comando = "SELECT * FROM tipo_usuario WHERE id = ?";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setLong(1, _id);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			Long id = rs.getLong(1);
            String nome = rs.getString(2);
            
            return new TipoUsuario(id, nome);
		}
		else {
			return null;
		}
	}

	@Override
	public List<TipoUsuario> listarTodosAtivos() throws SQLException {
		
		ResultSet rs = null;
		List<TipoUsuario> listaTipoUsuarios = new ArrayList<TipoUsuario>();
		
		try {
			
			String comando = "select * from tipo_usuario WHERE ativo_iu = 1 order by id ";
			
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
                Long id = rs.getLong(1);
                String nome = rs.getString(2);

                listaTipoUsuarios.add(new TipoUsuario(id, nome));
            }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return listaTipoUsuarios;
	}

}
