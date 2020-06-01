package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Pesquisador;
import modelo.Resposta;

public class RespostaDAO implements InterfaceRespostaDAO {

	Connection conexao;
	
	public RespostaDAO(Connection _conexao) {
		this.conexao = _conexao;
	}
	
	@Override
	public Long PegarID() throws SQLException {

		Long id = new Long(0);

		String comando = "SELECT Auto_increment FROM information_schema.tables WHERE table_name='resposta'";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			id = rs.getLong(1);
		}

		return id;
	}
	
	@Override
	public void Inserir(Resposta _resposta) throws SQLException {

		String comando = "INSERT INTO resposta (descricao, validacoes, status, desafio, pesquisador, dt_cadastro, novaEquacao, titulo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setString(1, _resposta.getDesc());
		ps.setInt(2, _resposta.getValidacoes());
		ps.setBoolean(3, _resposta.getStatus());
		ps.setLong(4, _resposta.getDesafio());
		ps.setLong(5, _resposta.getPesquisador().getId());
		ps.setDate(6, new Date(_resposta.getDataCadastro().getTime()));
		ps.setString(7, _resposta.getNovaEquacao());
		ps.setString(8, _resposta.getTitulo());
		
		ps.execute();			
	}

	@Override
	public List<Resposta> listarTodos() throws SQLException {

		ResultSet rs = null;
		List<Resposta> listaRespostas = new ArrayList<Resposta>();

		String comando = "select * from resposta order by id";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		rs = ps.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong(1);
			String desc = rs.getString(2);
			int validacoes = rs.getInt(3);
			Boolean status = rs.getBoolean(4);
			Long desafio = rs.getLong(5);
			Long idPesquisador = rs.getLong(6);
			Date dt_cadastro = rs.getDate(7);
			String novaEquacao = rs.getString(8);
			String titulo = rs.getString(9);
			String justificativa = rs.getString(10);
			
			PesquisadorDAO daoPesquisador = new PesquisadorDAO(conexao);
			Pesquisador pesquisador = daoPesquisador.PegarPeloID(idPesquisador);
						
			
			listaRespostas.add(new Resposta(id, desc, validacoes, status, desafio, pesquisador, dt_cadastro, novaEquacao, titulo, justificativa));
		}
		
		return listaRespostas;
	}

	@Override
	public void Atualizar(Resposta _resposta) throws SQLException {
		
		String comando = "UPDATE resposta SET titulo = ?, descricao = ? WHERE id = ?";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ps.setString(1, _resposta.getTitulo());
		ps.setString(2, _resposta.getDesc());
		//ps.setLong(3, _resposta.getDesafio());
		//ps.setBoolean(4, _resposta.getStatus());
		//ps.setString(5, _resposta.getJustificativa());
		
		ps.setLong(3, _resposta.getId());
		
		ps.execute();
		
	}

	@Override
	public void Excluir(Resposta _resposta) throws SQLException {

		String comando = "delete from resposta where id = ?";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setLong(1, _resposta.getId());
		
		ps.execute();
		
	}

	@Override
	public Boolean MudarStatusRespostas(Resposta _resposta) throws SQLException {
				
		for (Resposta r : TodasRespostasDoDesafio(_resposta.getDesafio())) {
			
			if (!(r.equals(_resposta))) {
				
				String comando = "update resposta set status = ?, justificativa = '' where id = ?";
				
				PreparedStatement ps = this.conexao.prepareStatement(comando);

				ps.setBoolean(1, false);
				ps.setLong(2, r.getId());
				
				ps.execute();
			}
			else {
				
				String comando = "update resposta set status = ?, justificativa = ? where id = ?";
				
				PreparedStatement ps = this.conexao.prepareStatement(comando);

				ps.setBoolean(1, _resposta.getStatus());
				ps.setString(2, _resposta.getJustificativa());
				ps.setLong(3, _resposta.getId());
				
				ps.execute();
			}
		}
		return true;
	}

	@Override
	public Resposta PegarPeloID(Long _id) throws SQLException {
		
		String comando = "SELECT * FROM resposta WHERE id = ?";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setLong(1, _id);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			Long id = rs.getLong(1);
			String desc = rs.getString(2);
			int validacoes = rs.getInt(3);
			Boolean status = rs.getBoolean(4);
			Long desafio = rs.getLong(5);
			Long idPesquisador = rs.getLong(6);
			Date dt_cadastro = rs.getDate(7);
			String novaEquacao = rs.getString(8);
			String titulo = rs.getString(9);
			String justificativa = rs.getString(10);
			
			PesquisadorDAO daoPesquisador = new PesquisadorDAO(conexao);
			Pesquisador pesquisador = daoPesquisador.PegarPeloID(idPesquisador);
            
            return new Resposta(id, desc, validacoes, status, desafio, pesquisador, dt_cadastro, novaEquacao, titulo, justificativa);
		}
		else {
			return null;
		}
	}

	@Override
	public List<Resposta> TodasRespostasDoDesafio(Long _idDesafio) throws SQLException {
		
		ResultSet rs = null;
		List<Resposta> listaRespostas = new ArrayList<Resposta>();

		String comando = "select * from resposta where desafio = ? order by id";

		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setLong(1, _idDesafio);

		rs = ps.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong(1);
			String desc = rs.getString(2);
			int validacoes = rs.getInt(3);
			Boolean status = rs.getBoolean(4);
			Long desafio = rs.getLong(5);
			Long idPesquisador = rs.getLong(6);
			Date dt_cadastro = rs.getDate(7);
			String novaEquacao = rs.getString(8);
			String titulo = rs.getString(9);
			String justificativa = rs.getString(10);
			
			PesquisadorDAO daoPesquisador = new PesquisadorDAO(conexao);
			Pesquisador pesquisador = daoPesquisador.PegarPeloID(idPesquisador);
						
			
			listaRespostas.add(new Resposta(id, desc, validacoes, status, desafio, pesquisador, dt_cadastro, novaEquacao, titulo, justificativa));
		}
		
		return listaRespostas;
	}

}
