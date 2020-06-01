package dao;

import java.sql.SQLException;
import java.util.List;

import modelo.Especialidade;

public interface InterfaceEspecialidadeDAO {
	
	Boolean Inserir(Especialidade _especialidade) throws SQLException;

	public List<Especialidade> listarTodos() throws SQLException;
	
	public void Editar(Especialidade _especialidade) throws SQLException;
	
	public void Excluir(Especialidade _especialidade) throws SQLException;

	Especialidade PegarPeloID(int _id) throws SQLException;

	List<Especialidade> listarTodosAtivos() throws SQLException;
}
