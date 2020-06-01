package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Reacao;

public class ReacaoDAO implements InterfaceReacaoDAO {

	Connection conexao;
	
	public ReacaoDAO(Connection _conexao) {
		super();
		this.conexao = _conexao;
	}

	@Override
	public void Inserir(Reacao _reaction) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Reacao> listarTodos() throws SQLException {
		ResultSet rs = null;
		List<Reacao> listaReacoes = new ArrayList<Reacao>();
		
		try {
			
			String comando = "SELECT * FROM reacoes ORDER BY id";
			
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
								
				Long id = rs.getLong(1);
				String abbreviation = rs.getString(2);
				String name = rs.getString(3);
				String equation = rs.getString(4);
				String subsystem = rs.getString(5);
				String function_subsystem = rs.getString(6);
				String protein = rs.getString(7);
				String protein_classification = rs.getString(8);
				String genePAO = rs.getString(9);
				String geneCCBH = rs.getString(10);

                listaReacoes.add(new Reacao(id, abbreviation, name, equation, subsystem, function_subsystem, protein, protein_classification, genePAO, geneCCBH));
            }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return listaReacoes;
	}
	
	public String BuscarNomeSubSistema(Long _idFunc) throws SQLException {
		
		ResultSet rs = null;
		
		try {
			
			String comando = "SELECT function_subsystem FROM function_subsystem WHERE id = ?";
			
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			
			ps.setLong(1, _idFunc);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {

				String function_subsystem = rs.getString(1);
				
				return function_subsystem;
            }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void Editar(Reacao _reaction) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Excluir(Reacao _reaction) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public List<Reacao> listarTodosPorSistemaAndSubSistema(String _subsistema, String _func_subsistema) throws SQLException {
		ResultSet rs = null;
		List<Reacao> listaReacoes = new ArrayList<Reacao>();
		
		try {
			
			String comando = "SELECT * FROM reacoes ";
			
			if (_subsistema != null) {
				comando += "WHERE subsystem LIKE '" + _subsistema + "' ";
			}
			
			if (_subsistema != null && _func_subsistema != null) {
				comando += "AND functional_subsystem LIKE '" + _func_subsistema + "' ";
			}
			
			else if (_subsistema == null && _func_subsistema != null) {
				comando += "WHERE functional_subsystem LIKE '" + _func_subsistema + "' ";
			}
			
			comando += "ORDER BY abbreviation";
			
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
								
				Long id = rs.getLong(1);
				String abbreviation = rs.getString(2);
				String name = rs.getString(2);
				String equation = rs.getString(4);
				String subsystem = rs.getString(5);
				String function_subsystem = rs.getString(6);
				String protein = rs.getString(7);
				String protein_classification = rs.getString(8);
				String genePAO = rs.getString(9);
				String geneCCBH = rs.getString(10);

                listaReacoes.add(new Reacao(id, abbreviation, name, equation, subsystem, function_subsystem, protein, protein_classification, genePAO, geneCCBH));
            }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return listaReacoes;
	}

	
	@Override
	public Reacao PegarPeloID(Long _id) throws SQLException {
		//reacao
		String comando = "SELECT * FROM reacoes WHERE id = ?";
		
		PreparedStatement ps = this.conexao.prepareStatement(comando);
		
		ps.setLong(1, _id);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			
			Long id = rs.getLong(1);
			String abbreviation = rs.getString(2);
			String name = rs.getString(3);
			String equation = rs.getString(4);
			String subsystem = rs.getString(5);
			String function_subsystem = rs.getString(6);
			String protein = rs.getString(7);
			String protein_classification = rs.getString(8);
			String genePAO = rs.getString(9);
			String geneCCBH = rs.getString(10);
            
            return new Reacao(id, abbreviation, name, equation, subsystem, function_subsystem, protein, protein_classification, genePAO, geneCCBH);
		}
		else {
			return null;
		}
	}
	
	
}
