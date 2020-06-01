package dao;

import java.sql.SQLException;
import java.util.List;

import modelo.Usuario;

public interface InterfaceUsuarioDAO {

	public Long Inserir(Usuario _usuario) throws SQLException;
	public List<Usuario> listarTodos() throws SQLException;
	public void Editar(Usuario _usuario) throws SQLException;
	public void Excluir(Usuario _usuario) throws SQLException;
	public Usuario PegarPeloID(Long _id) throws SQLException;
	Long PegarProximoID() throws SQLException;
	Usuario VerificarUsuario(String _login, String _senha) throws SQLException;
	Usuario PegarPeloEmail(String email) throws SQLException;
}
