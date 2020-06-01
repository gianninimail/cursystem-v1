package dao;

import java.sql.SQLException;
import java.util.List;

import modelo.Status;

public interface InterfaceStatusDAO {

	List<Status> listarTodos() throws SQLException;

	Status PegarPeloID(int _id) throws SQLException;

}
