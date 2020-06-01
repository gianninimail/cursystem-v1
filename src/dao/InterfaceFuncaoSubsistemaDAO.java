package dao;

import java.sql.SQLException;
import java.util.List;

import modelo.Subsistema;

public interface InterfaceFuncaoSubsistemaDAO {

	public List<Subsistema> listarTodos() throws SQLException;

	Subsistema PegarPeloID(int _id) throws SQLException;
}
