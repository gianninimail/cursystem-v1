package dao;

import java.sql.SQLException;
import java.util.List;
import modelo.Pesquisador;
import modelo.Usuario;

public interface InterfacePesquisadorDAO {

	public void InserirNovo(Pesquisador _pesquisador) throws SQLException;
	
	public void InserirExiste(Pesquisador _pesquisador) throws SQLException;
	
	public List<Pesquisador> listarTodos() throws SQLException;
	
	public void Editar(Pesquisador _pesquisador) throws SQLException;
	
	public void Excluir(Pesquisador _pesquisador) throws SQLException;
	
	public Long PegarProximoID() throws SQLException;
	
	public Pesquisador PegarPeloID(Long idPesquisador) throws SQLException;

	public List<Usuario> listarUsuariosParaPesquisador();

	List<Pesquisador> listarTodosPorEspecialidade(Long _idEspecialidade) throws SQLException;
}
