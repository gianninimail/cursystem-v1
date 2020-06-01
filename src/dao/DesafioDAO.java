package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Desafio;
import modelo.Especialidade;
import modelo.Reacao;
import modelo.Resposta;
import modelo.Status;
import modelo.Usuario;

public class DesafioDAO implements InterfaceDesafioDAO {

	Connection conexao;

	public DesafioDAO(Connection _conexao) {
		this.conexao = _conexao;
	}

	@Override
	public Boolean Inserir(Desafio _desafio) throws SQLException {

		String comando = "insert into desafio (id, titulo, descricao, status, especialidade, reacao, usuario, dt_cadastro) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = this.conexao.prepareStatement(comando);
		ps.setLong(1, _desafio.getId());
		ps.setString(2, _desafio.getTitulo());
		ps.setString(3, _desafio.getDesc());
		ps.setInt(4, _desafio.getStatus().getId());
		ps.setLong(5, _desafio.getEspecialidade().getId());
		ps.setLong(6, _desafio.getReacao().getId());
		ps.setLong(7, _desafio.getUsuario().getId());
		//ps.setInt(8, _desafio.getId_resposta_aceita());
		ps.setDate(8, new Date(_desafio.getDataCadastro().getTime()));
		

		if(ps.execute()) {
			return true;
		}
		else {
			return false;
		}
		
	}

	@Override
	public List<Desafio> listarTodos() throws SQLException {

		ResultSet rs = null;
		List<Desafio> listaDesafios = new ArrayList<Desafio>();

		String comando = "select * from desafio order by id";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		rs = ps.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong(1);
			String titulo = rs.getString(2);
			String desc = rs.getString(3);
			int idStatus = rs.getInt(4);
			int idEspecialidade = rs.getInt(5);
			Long idReacao = rs.getLong(6);
			Long idUsuario = rs.getLong(7);
			Long idResposta_aceita = rs.getLong(8);
			Date dt_cadastro = rs.getDate(9);
			
			StatusDAO daoStatus = new StatusDAO(conexao);
			Status status = daoStatus.PegarPeloID(idStatus);
						
			EspecialidadeDAO daoEspecialidade = new EspecialidadeDAO(conexao);
			Especialidade especialidade = daoEspecialidade.PegarPeloID(idEspecialidade);
			
			ReacaoDAO daoReacao = new ReacaoDAO(conexao);
			Reacao reacao = daoReacao.PegarPeloID(idReacao);
			
			UsuarioDAO daoUsuario = new UsuarioDAO(conexao);
			Usuario usuario = daoUsuario.PegarPeloID(idUsuario);
			
			RespostaDAO daoResposta = new RespostaDAO(conexao);
			//Resposta resposta = daoResposta.PegarPeloID(idResposta_aceita);
			List<Resposta> respostas = daoResposta.TodasRespostasDoDesafio(id);
			
			listaDesafios.add(new Desafio(id, titulo, desc, status, especialidade, reacao, usuario, idResposta_aceita, dt_cadastro, respostas));
		}

		return listaDesafios;
	}

	@Override
	public void Atualizar(Desafio _desafio) throws SQLException {

		String comando = "update desafio set titulo = ?, descricao = ?, especialidade = ?, usuario = ? where id = ?";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ps.setString(1, _desafio.getTitulo());
		ps.setString(2, _desafio.getDesc());
		//ps.setInt(3, _desafio.getStatus().getId());
		ps.setLong(3, _desafio.getEspecialidade().getId());
		//ps.setLong(4, _desafio.getReacao().getId());
		ps.setLong(4, _desafio.getUsuario().getId());
		//ps.setLong(7, _desafio.getIdRespostaAceita());

		ps.setLong(5, _desafio.getId());

		ps.execute();	
	}

	@Override
	public Long PegarID() throws SQLException {

		Long id = new Long(0);

		String comando = "SELECT Auto_increment FROM information_schema.tables WHERE table_name='desafio'";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			id = rs.getLong(1);
		}

		return id;
	}

	@Override
	public Boolean MudarStatusDesafio(Long _id, util.Status _status) throws SQLException {

		String comando = "update desafio set status = ? where id = ?";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ps.setInt(1, _status.valor());
		ps.setLong(2, _id);

		//ps.execute();

		if (ps.execute()) {

			return true;
		}
		else {

			return false;
		}
	}

	@Override
	public void Excluir(Desafio _desafio) throws SQLException {

		String comando = "delete from desafio where id = ?";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ps.setLong(1, _desafio.getId());

		ps.execute();

	}

	@Override
	public Desafio PegarPeloID(Long _id) throws SQLException {

		String comando = "SELECT * FROM desafio WHERE id = ?";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setLong(1, _id);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			Long id = rs.getLong(1);
			String titulo = rs.getString(2);
			String desc = rs.getString(3);
			int idStatus = rs.getInt(4);
			int idEspecialidade = rs.getInt(5);
			Long idReacao = rs.getLong(6);
			Long idUsuario = rs.getLong(7);
			Long idResposta_aceita = rs.getLong(8);
			Date dt_cadastro = rs.getDate(9);
			
			StatusDAO daoStatus = new StatusDAO(conexao);
			Status status = daoStatus.PegarPeloID(idStatus);
						
			EspecialidadeDAO daoEspecialidade = new EspecialidadeDAO(conexao);
			Especialidade especialidade = daoEspecialidade.PegarPeloID(idEspecialidade);
			
			ReacaoDAO daoReacao = new ReacaoDAO(conexao);
			Reacao reacao = daoReacao.PegarPeloID(idReacao);
			
			UsuarioDAO daoUsuario = new UsuarioDAO(conexao);
			Usuario usuario = daoUsuario.PegarPeloID(idUsuario);
			
			RespostaDAO daoResposta = new RespostaDAO(conexao);
			//Resposta resposta = daoResposta.PegarPeloID(idResposta_aceita);
			List<Resposta> respostas = daoResposta.TodasRespostasDoDesafio(id);
            
            return new Desafio(id, titulo, desc, status, especialidade, reacao, usuario, idResposta_aceita, dt_cadastro, respostas);
		}
		else {
			return null;
		}
	}
	
	@Override
	public List<Desafio> listarTodosPorPesquisador(Long _idPesquisador) throws SQLException {

		ResultSet rs = null;
		List<Desafio> listaDesafios = new ArrayList<Desafio>();

		String comando = "select * from desafio where usuario = ? order by id";

		PreparedStatement ps = this.conexao.prepareStatement(comando);
		ps.setLong(1, _idPesquisador);

		rs = ps.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong(1);
			String titulo = rs.getString(2);
			String desc = rs.getString(3);
			int idStatus = rs.getInt(4);
			int idEspecialidade = rs.getInt(5);
			Long idReacao = rs.getLong(6);
			Long idUsuario = rs.getLong(7);
			Long idResposta_aceita = rs.getLong(8);
			Date dt_cadastro = rs.getDate(9);
			
			StatusDAO daoStatus = new StatusDAO(conexao);
			Status status = daoStatus.PegarPeloID(idStatus);
						
			EspecialidadeDAO daoEspecialidade = new EspecialidadeDAO(conexao);
			Especialidade especialidade = daoEspecialidade.PegarPeloID(idEspecialidade);
			
			ReacaoDAO daoReacao = new ReacaoDAO(conexao);
			Reacao reacao = daoReacao.PegarPeloID(idReacao);
			
			UsuarioDAO daoUsuario = new UsuarioDAO(conexao);
			Usuario usuario = daoUsuario.PegarPeloID(idUsuario);
			
			RespostaDAO daoResposta = new RespostaDAO(conexao);
			//Resposta resposta = daoResposta.PegarPeloID(idResposta_aceita);
			List<Resposta> respostas = daoResposta.TodasRespostasDoDesafio(id);
			
			listaDesafios.add(new Desafio(id, titulo, desc, status, especialidade, reacao, usuario, idResposta_aceita, dt_cadastro, respostas));
		}

		return listaDesafios;
	}
	
	@Override
	public List<Desafio> listarTodosPorEspecialidade(Long _idEspecialidade) throws SQLException {

		ResultSet rs = null;
		List<Desafio> listaDesafios = new ArrayList<Desafio>();

		String comando = "select * from desafio where especialidade = ? order by id";

		PreparedStatement ps = this.conexao.prepareStatement(comando);
		ps.setLong(1, _idEspecialidade);

		rs = ps.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong(1);
			String titulo = rs.getString(2);
			String desc = rs.getString(3);
			int idStatus = rs.getInt(4);
			int idEspecialidade = rs.getInt(5);
			Long idReacao = rs.getLong(6);
			Long idUsuario = rs.getLong(7);
			Long idResposta_aceita = rs.getLong(8);
			Date dt_cadastro = rs.getDate(9);
			
			StatusDAO daoStatus = new StatusDAO(conexao);
			Status status = daoStatus.PegarPeloID(idStatus);
						
			EspecialidadeDAO daoEspecialidade = new EspecialidadeDAO(conexao);
			Especialidade especialidade = daoEspecialidade.PegarPeloID(idEspecialidade);
			
			ReacaoDAO daoReacao = new ReacaoDAO(conexao);
			Reacao reacao = daoReacao.PegarPeloID(idReacao);
			
			UsuarioDAO daoUsuario = new UsuarioDAO(conexao);
			Usuario usuario = daoUsuario.PegarPeloID(idUsuario);
			
			RespostaDAO daoResposta = new RespostaDAO(conexao);
			//Resposta resposta = daoResposta.PegarPeloID(idResposta_aceita);
			List<Resposta> respostas = daoResposta.TodasRespostasDoDesafio(id);
			
			listaDesafios.add(new Desafio(id, titulo, desc, status, especialidade, reacao, usuario, idResposta_aceita, dt_cadastro, respostas));
		}

		return listaDesafios;
	}
}
