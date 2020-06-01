package dao;

import java.sql.SQLException;
import java.util.List;

import modelo.Metabolito;

public interface InterfaceMetabolitoDAO {
	public void Inserir(Metabolito _metabolito) throws SQLException;
	public List<Metabolito> listarTodos() throws SQLException;
	public void Editar(Metabolito _metabolito) throws SQLException;
	public void Excluir(Metabolito _metabolito) throws SQLException;
}
