package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.TipoUsuario;
import modelo.Usuario;

public class UsuarioDAO implements InterfaceUsuarioDAO {
		
	Connection conexao;
	
	public UsuarioDAO(Connection _conexao) {
		this.conexao = _conexao;
	}

	@Override
	public void Inserir(Usuario _usuario) throws SQLException {

			String comando = "insert into usuario (id, nome, login, senha, tel, email, dtCadastro, tipo_usuario) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
					
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			ps.setLong(1, _usuario.getId());
			ps.setString(2, _usuario.getNome());
			ps.setString(3, _usuario.getLogin());
			ps.setString(4, _usuario.getSenha());
			ps.setString(5, _usuario.getTel());
			ps.setString(6, _usuario.getEmail());
			ps.setDate(7, new Date(_usuario.getDataCadastro().getTime()));
			ps.setLong(8, _usuario.getTipoUsuario().getId());
			
			ps.execute();			
	}

	@Override
	public List<Usuario> listarTodos() {
		
		ResultSet rs = null;
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		try {
			
			String comando = "select * from usuario order by id";
			
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
                Long id = rs.getLong(1);
                String nome = rs.getString(2);
                String login = rs.getString(3);
                String senha = rs.getString(4);
                String tel = rs.getString(5);
                String email = rs.getString(6);
                Date dtCadastro = rs.getDate(7);
                Long idTipoUsuario = rs.getLong(8);
                
                TipoUsuarioDAO daoTU = new TipoUsuarioDAO(conexao);
                TipoUsuario tipoUsuario = daoTU.PegarPeloID(idTipoUsuario);

                listaUsuarios.add(new Usuario(id, nome, login, senha, tel, email, dtCadastro, tipoUsuario));
            }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return listaUsuarios;
	}

	@Override
	public void Editar(Usuario _usuario) throws SQLException {
		
		String comando = "update usuario set nome = ?, login = ?, senha = ?, tel = ?, email = ?, tipo_usuario = ? where id = ?";
				
		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ps.setString(1, _usuario.getNome());
		ps.setString(2, _usuario.getLogin());
		ps.setString(3, _usuario.getSenha());
		ps.setString(4, _usuario.getTel());
		ps.setString(5, _usuario.getEmail());
		ps.setLong(6, _usuario.getTipoUsuario().getId());
		
		ps.setLong(7, _usuario.getId());
		
		ps.execute();	
	}
	
	@Override
	public Long PegarProximoID()throws SQLException {
		
		Long id = new Long(0);
		
		String comando = "SELECT Auto_increment FROM information_schema.tables WHERE table_name='usuario'";
	
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			id = rs.getLong(1);
		}
		
		return id;
	}
	
	@Override
	public Usuario VerificarUsuario(String _login, String _senha) throws SQLException {

		String comando = "SELECT * FROM usuario WHERE login = ? AND senha = ?";
	
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setString(1, _login);
		ps.setString(2, _senha);
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			Long id = rs.getLong(1);
            String nome = rs.getString(2);
            String login = rs.getString(3);
            String senha = rs.getString(4);
            String tel = rs.getString(5);
            String email = rs.getString(6);
            Date dtCadastro = rs.getDate(7);
            Long idTipoUsuario = rs.getLong(8);
            
            TipoUsuarioDAO daoTU = new TipoUsuarioDAO(conexao);
            TipoUsuario tipoUsuario = daoTU.PegarPeloID(idTipoUsuario);
            
            return new Usuario(id, nome, login, senha, tel, email, dtCadastro, tipoUsuario);
		}
		else {
			return null;
		}

	}

	@Override
	public void Excluir(Usuario _usuario) throws SQLException {

		String comando = "delete from usuario where id = ?";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setLong(1, _usuario.getId());
		
		ps.execute();
		
	}
	
	@Override
	public Usuario PegarPeloID(Long _id) throws SQLException {
		
		String comando = "SELECT * FROM usuario WHERE id = ?";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setLong(1, _id);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			Long id = rs.getLong(1);
            String nome = rs.getString(2);
            String login = rs.getString(3);
            String senha = rs.getString(4);
            String tel = rs.getString(5);
            String email = rs.getString(6);
            Date dtCadastro = rs.getDate(7);
            Long idTipoUsuario = rs.getLong(8);
            
            TipoUsuarioDAO daoTU = new TipoUsuarioDAO(conexao);
            TipoUsuario tipoUsuario = daoTU.PegarPeloID(idTipoUsuario);
            
            return new Usuario(id, nome, login, senha, tel, email, dtCadastro, tipoUsuario);
		}
		else {
			return null;
		}
	}
	
}
