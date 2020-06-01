package dao;

import java.sql.SQLException;
import java.util.List;
import modelo.Reacao;


public interface InterfaceReacaoDAO {
	public void Inserir(Reacao _reaction) throws SQLException;
	public List<Reacao> listarTodos() throws SQLException;
	public void Editar(Reacao _reaction) throws SQLException;
	public void Excluir(Reacao _reaction) throws SQLException;
	public Reacao PegarPeloID(Long _id) throws SQLException;
}
