package dao;

import java.sql.SQLException;
import java.util.List;

import modelo.Resposta;

public interface InterfaceRespostaDAO {

	void Inserir(Resposta _resposta) throws SQLException;

	List<Resposta> listarTodos()throws SQLException;

	void Atualizar(Resposta _resposta) throws SQLException;

	void Excluir(Resposta _resposta) throws SQLException;

	Resposta PegarPeloID(Long _id) throws SQLException;

	List<Resposta> TodasRespostasDoDesafio(Long id) throws SQLException;

	Long PegarID() throws SQLException;

	Boolean MudarStatusRespostas(Resposta _resposta) throws SQLException;
}
