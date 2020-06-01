package dao;

import java.sql.SQLException;
import java.util.List;

import modelo.Desafio;
import util.Status;

public interface InterfaceDesafioDAO {

	Boolean Inserir(Desafio _desafio) throws SQLException;

	List<Desafio> listarTodos()throws SQLException;

	void Atualizar(Desafio _desafio) throws SQLException;

	void Excluir(Desafio _desafio) throws SQLException;

	//Boolean MudarStatusDesafio(Long _id, int _status) throws SQLException;

	Long PegarID() throws SQLException;

	Desafio PegarPeloID(Long _id) throws SQLException;

	List<Desafio> listarTodosPorPesquisador(Long _idPesquisador) throws SQLException;

	List<Desafio> listarTodosPorEspecialidade(Long _idEspecialidade) throws SQLException;

	Boolean MudarStatusDesafio(Long _id, Status _status) throws SQLException;

}
