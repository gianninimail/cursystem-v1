package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Comentario;
import modelo.Pesquisador;
import modelo.Resposta;

public class ComentarioDAO implements InterfaceComentarioDAO {

	Connection conexao;
	
	public ComentarioDAO(Connection _conexao) {
		this.conexao = _conexao;
	}
	
	@Override
	public void Inserir(Comentario _comentario) throws SQLException {
		
		String comando = "INSERT INTO comentario (texto, resposta, pesquisador, dt_cadastro) "
				+ "VALUES (?, ?, ?, ?)";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setString(1, _comentario.getTexto());
		ps.setLong(2, _comentario.getResposta());
		ps.setLong(3, _comentario.getPesquisador().getId());//TROCAR
		ps.setDate(4, new Date(_comentario.getDataCadastro().getTime()));
		
		ps.execute();	
	}

	@Override
	public List<Comentario> listarTodos() throws SQLException {

		ResultSet rs = null;
		List<Comentario> listaComentarios = new ArrayList<Comentario>();

		String comando = "select * from comentario order by id";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		rs = ps.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong(1);
			String texto = rs.getString(2);
			Long idResposta = rs.getLong(3);
			Long idPesquisador = rs.getLong(4);
			Date dtCasdastro = rs.getDate(5);
			
			PesquisadorDAO daoPesquisador = new PesquisadorDAO(this.conexao);
			Pesquisador pesquisador = daoPesquisador.PegarPeloID(idPesquisador);
						
			
			listaComentarios.add(new Comentario(id, texto, idResposta, pesquisador, dtCasdastro));
		}
		
		return listaComentarios;
	}

	@Override
	public void Editar(Comentario _comentario) throws SQLException {

		String comando = "update comentario set texto = ? where id = ?";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ps.setString(1, _comentario.getTexto());
		ps.setLong(2, _comentario.getId());
		
		ps.execute();
		
	}

	@Override
	public void Excluir(Comentario _comentario) throws SQLException {
		
		String comando = "delete from comentario where id = ?";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setLong(1, _comentario.getId());
		
		ps.execute();
		
	}

	@Override
	public Comentario PegarPeloID(Long _id) throws SQLException {
		
		String comando = "SELECT * FROM comentario WHERE id = ?";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setLong(1, _id);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			Long id = rs.getLong(1);
			String texto = rs.getString(2);
			Long idResposta = rs.getLong(3);
			Long idPesquisador = rs.getLong(4);
			Date dtCasdastro = rs.getDate(5);
			
			PesquisadorDAO daoPesquisador = new PesquisadorDAO(this.conexao);
			Pesquisador pesquisador = daoPesquisador.PegarPeloID(idPesquisador);
            
            return new Comentario(id, texto, idResposta, pesquisador, dtCasdastro);
		}
		else {
			return null;
		}
	}

	@Override
	public List<Comentario> TodasComentariosDaResposta(Resposta _resposta) throws SQLException {
		
		ResultSet rs = null;
		List<Comentario> listaComentarios = new ArrayList<Comentario>();

		String comando = "select * from comentario where resposta = ? order by id";

		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setLong(1, _resposta.getId());

		rs = ps.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong(1);
			String texto = rs.getString(2);
			Long idResposta = rs.getLong(3);
			Long idPesquisador = rs.getLong(4);
			Date dtCasdastro = rs.getDate(5);
			
			PesquisadorDAO daoPesquisador = new PesquisadorDAO(this.conexao);
			Pesquisador pesquisador = daoPesquisador.PegarPeloID(idPesquisador);
						
			listaComentarios.add(new Comentario(id, texto, idResposta, pesquisador, dtCasdastro));
		}
		
		return listaComentarios;
	}
	
	@Override
	public List<Comentario> TodosComentariosDaResposta(Long _idResposta) throws SQLException {
		
		ResultSet rs = null;
		List<Comentario> listaComentarios = new ArrayList<Comentario>();

		String comando = "select * from comentario where resposta = ? order by id";

		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setLong(1, _idResposta);

		rs = ps.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong(1);
			String texto = rs.getString(2);
			Long idResposta = rs.getLong(3);
			Long idPesquisador = rs.getLong(4);
			Date dtCasdastro = rs.getDate(5);
			
			PesquisadorDAO daoPesquisador = new PesquisadorDAO(this.conexao);
			Pesquisador pesquisador = daoPesquisador.PegarPeloID(idPesquisador);
						
			listaComentarios.add(new Comentario(id, texto, idResposta, pesquisador, dtCasdastro));
		}
		
		return listaComentarios;
	}

	@Override
	public Long PegarID() throws SQLException {
		
		Long id = new Long(0);

		String comando = "SELECT Auto_increment FROM information_schema.tables WHERE table_name='comentario'";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			id = rs.getLong(1);
		}

		return id;
	}

}
