package dao;

import java.sql.SQLException;
import java.util.List;

import modelo.Comentario;
import modelo.Resposta;

public interface InterfaceComentarioDAO {

	void Inserir(Comentario _comentario) throws SQLException;

	List<Comentario> listarTodos() throws SQLException;

	void Editar(Comentario _comentario) throws SQLException;

	void Excluir(Comentario _comentario) throws SQLException;

	Comentario PegarPeloID(Long _id) throws SQLException;

	List<Comentario> TodasComentariosDaResposta(Resposta _resposta) throws SQLException;
	
	List<Comentario> TodosComentariosDaResposta(Long _idResposta) throws SQLException;

	Long PegarID() throws SQLException;
}
