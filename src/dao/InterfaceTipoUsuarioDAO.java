package dao;

import java.sql.SQLException;
import java.util.List;

import modelo.TipoUsuario;

public interface InterfaceTipoUsuarioDAO {

	public List<TipoUsuario> listarTodos() throws SQLException;

	TipoUsuario PegarPeloID(Long _id) throws SQLException;

	List<TipoUsuario> listarTodosAtivos() throws SQLException;
}
