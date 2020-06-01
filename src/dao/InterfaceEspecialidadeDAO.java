package dao;

import java.sql.SQLException;
import java.util.List;

import modelo.Especialidade;

public interface InterfaceEspecialidadeDAO {

	public List<Especialidade> listarTodos() throws SQLException;

	Especialidade PegarPeloID(int _id) throws SQLException;

	List<Especialidade> listarTodosAtivos() throws SQLException;
}
