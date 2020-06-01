package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modelo.Metabolito;

public class MetabolitoDAO implements InterfaceMetabolitoDAO {

	Connection conexao;
	
	public MetabolitoDAO(Connection _conexao) {
		super();
		this.conexao = _conexao;
	}
	
	@Override
	public void Inserir(Metabolito _metabolito) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Metabolito> listarTodos() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Editar(Metabolito _metabolito) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Excluir(Metabolito _metabolito) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	public Metabolito BuscarMetabolitoPorAbbreviation(String _abbreviation) throws SQLException {
		ResultSet rs = null;
		Metabolito metabolito = null;
		
		try {
			
			String comando = "SELECT * FROM ccbh_metabolitos WHERE abbreviation = '?'";
						
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			ps.setString(0, _abbreviation);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
								
				String abbreviation = rs.getString(1);
				String name = rs.getString(2);
				String formula = rs.getString(3);
				String formulaAlt = rs.getString(4);
				String compAlternativos = rs.getString(5);
				String keggId = rs.getString(6);
				
				metabolito = new Metabolito(abbreviation, name, formula, formulaAlt, compAlternativos, keggId);
            }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return metabolito;
	}

}
