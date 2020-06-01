package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Especialidade;
import modelo.Pesquisador;
import modelo.TipoUsuario;
import modelo.Usuario;

public class PesquisadorDAO implements InterfacePesquisadorDAO {

	Connection conexao;

	public PesquisadorDAO(Connection _conexao) {
		this.conexao = _conexao;
	}

	@Override
	public void InserirNovo(Pesquisador _pesquisador) throws SQLException {

		String comando = "insert into usuario (id, nome, login, senha, tel, email, dtCadastro, tipo_usuario) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = this.conexao.prepareStatement(comando);
		ps.setLong(1, _pesquisador.getId());
		ps.setString(2, _pesquisador.getNome());
		ps.setString(3, _pesquisador.getLogin());
		ps.setString(4, _pesquisador.getSenha());
		ps.setString(5, _pesquisador.getTel());
		ps.setString(6, _pesquisador.getEmail());
		ps.setDate(7, new Date(_pesquisador.getDataCadastro().getTime()));
		ps.setLong(8, _pesquisador.getTipoUsuario().getId());

		ps.execute();			

		comando = "insert into pesquisador (id, instituto, especialidade) "
				+ "values (?, ?, ?)";

		ps = this.conexao.prepareStatement(comando);

		ps.setLong(1, _pesquisador.getId());
		ps.setString(2, _pesquisador.getInstituto());
		ps.setLong(3, _pesquisador.getEspecialidade().getId());

		ps.execute();
	}

	@Override
	public void InserirExiste(Pesquisador _pesquisador) throws SQLException {

		String comando = "insert into pesquisador (id, instituto, especialidade) "
				+ "values (?, ?, ?)";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ps = this.conexao.prepareStatement(comando);

		ps.setLong(1, _pesquisador.getId());
		ps.setString(2, _pesquisador.getInstituto());
		ps.setLong(3, _pesquisador.getEspecialidade().getId());

		ps.execute();

	}

	@Override
	public List<Pesquisador> listarTodos() throws SQLException {

		ResultSet rs = null;
		List<Pesquisador> listaPesquisadors = new ArrayList<Pesquisador>();

		String comando = "SELECT "
				+ "pesquisador.id, "
				+ "usuario.nome, "
				+ "login, "
				+ "senha, "
				+ "tel, "
				+ "email, "
				+ "dtCadastro, "
				+ "tipo_usuario, "
				+ "instituto, "
				+ "especialidade "
				+ "FROM pesquisador, usuario "
				+ "WHERE pesquisador.id = usuario.id;";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		rs = ps.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong(1);
			String nome = rs.getString(2);
			String login = rs.getString(3);
			String senha = rs.getString(4);
			String tel = rs.getString(5);
			String email = rs.getString(6);
			Date dtCadastro = rs.getDate(7);
			Long idTipoUsuario = rs.getLong(8);

			String instituto = rs.getString(9);
			int idEspecialidade = rs.getInt(10);

			EspecialidadeDAO daoEsp = new EspecialidadeDAO(this.conexao);
			Especialidade especialidade = daoEsp.PegarPeloID(idEspecialidade);
			
			TipoUsuarioDAO daoTU = new TipoUsuarioDAO(conexao);
            TipoUsuario tipoUsuario = daoTU.PegarPeloID(idTipoUsuario);

			Pesquisador o = new Pesquisador(id, nome, login, senha, tel, email, dtCadastro, instituto, especialidade, tipoUsuario);
			
			listaPesquisadors.add(o);
		}

		return listaPesquisadors;
	}

	@Override
	public void Editar(Pesquisador _pesquisador) throws SQLException {

		String comandoU = "update usuario set nome = ?, login = ?, senha = ?, tel = ?, email = ?, tipo_usuario = ? where id = ?";

		PreparedStatement ps = this.conexao.prepareStatement(comandoU);

		ps.setString(1, _pesquisador.getNome());
		ps.setString(2, _pesquisador.getLogin());
		ps.setString(3, _pesquisador.getSenha());
		ps.setString(4, _pesquisador.getTel());
		ps.setString(5, _pesquisador.getEmail());
		ps.setLong(6, _pesquisador.getTipoUsuario().getId());
		
		ps.setLong(7, _pesquisador.getId());

		ps.execute();	
		
		String comandoP = "update pesquisador set instituto = ?, especialidade = ? where id = ?";

		ps = this.conexao.prepareStatement(comandoP);
		
		ps.setString(1, _pesquisador.getInstituto());
		ps.setLong(2, _pesquisador.getEspecialidade().getId());
		
		ps.setLong(3, _pesquisador.getId());
		
		ps.execute();
	}

	@Override
	public Long PegarProximoID()throws SQLException {

		Long id = new Long(0);

		String comando = "SELECT Auto_increment FROM information_schema.tables WHERE table_name='usuario'";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			id = rs.getLong(1);
		}

		return id;
	}

	@Override
	public void Excluir(Pesquisador _pesquisador) throws SQLException {

		String comando = "delete from pesquisador where id = ?";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ps.setLong(1, _pesquisador.getId());

		ps.execute();

	}

	@Override
	public Pesquisador PegarPeloID(Long _id) throws SQLException {

		ResultSet rs = null;

		String comando = "SELECT "
				+ "pesquisador.id, "
				+ "usuario.nome, "
				+ "login, "
				+ "senha, "
				+ "tel, "
				+ "email, "
				+ "dtCadastro, "
				+ "tipo_usuario, "
				+ "instituto, "
				+ "especialidade "
				+ "FROM pesquisador, usuario "
				+ "WHERE pesquisador.id = usuario.id "
				+ "AND pesquisador.id = ?";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ps.setLong(1, _id);
		
		rs = ps.executeQuery();

		if (rs.next()) {
			Long id = rs.getLong(1);
			String nome = rs.getString(2);
			String login = rs.getString(3);
			String senha = rs.getString(4);
			String tel = rs.getString(5);
			String email = rs.getString(6);
			Date dtCadastro = rs.getDate(7);
			Long idTipoUsuario = rs.getLong(8);
			String instituto = rs.getString(9);
			int idEspecialidade = rs.getInt(10);

			EspecialidadeDAO daoEsp = new EspecialidadeDAO(this.conexao);
			Especialidade especialidade = daoEsp.PegarPeloID(idEspecialidade);

            TipoUsuarioDAO daoTU = new TipoUsuarioDAO(conexao);
            TipoUsuario tipoUsuario = daoTU.PegarPeloID(idTipoUsuario);

			return new Pesquisador(id, nome, login, senha, tel, email, dtCadastro, instituto, especialidade, tipoUsuario);
		}
		else {
			return null;
		}
	}
	
	@Override
	public List<Usuario> listarUsuariosParaPesquisador() {
		
		ResultSet rs = null;
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		try {
			
			String comando = "SELECT u.id, u.nome, u.login, u.senha, u.tel, u.email, u.dtCadastro, u.tipo_usuario "
					+ "FROM usuario u WHERE u.id NOT IN (SELECT p.id FROM pesquisador p);";
			
			PreparedStatement ps = this.conexao.prepareStatement(comando);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
                Long id = rs.getLong(1);
                String nome = rs.getString(2);
                String login = rs.getString(3);
                String senha = rs.getString(4);
                String tel = rs.getString(5);
                String email = rs.getString(6);
                Date dtCadastro = rs.getDate(7);
                Long idTipoUsuario = rs.getLong(8);

                TipoUsuarioDAO daoTU = new TipoUsuarioDAO(conexao);
                TipoUsuario tipoUsuario = daoTU.PegarPeloID(idTipoUsuario);
                
                listaUsuarios.add(new Usuario(id, nome, login, senha, tel, email, dtCadastro, tipoUsuario));
            }
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return listaUsuarios;
	}

	@Override
	public List<Pesquisador> listarTodosPorEspecialidade(Long _idEspecialidade) throws SQLException {

		ResultSet rs = null;
		List<Pesquisador> listaPesquisadors = new ArrayList<Pesquisador>();

		String comando = "SELECT "
				+ "pesquisador.id, "
				+ "usuario.nome, "
				+ "login, "
				+ "senha, "
				+ "tel, "
				+ "email, "
				+ "dtCadastro, "
				+ "tipo_usuario, "
				+ "instituto, "
				+ "especialidade "
				+ "FROM pesquisador, usuario "
				+ "WHERE pesquisador.id = usuario.id "
				+ "AND (especialidade = 3 "
				+ "OR especialidade = ?);";

		PreparedStatement ps = this.conexao.prepareStatement(comando);

		ps.setLong(1, _idEspecialidade);
		
		rs = ps.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong(1);
			String nome = rs.getString(2);
			String login = rs.getString(3);
			String senha = rs.getString(4);
			String tel = rs.getString(5);
			String email = rs.getString(6);
			Date dtCadastro = rs.getDate(7);
			Long idTipoUsuario = rs.getLong(8);

			String instituto = rs.getString(9);
			int idEspecialidade = rs.getInt(10);

			EspecialidadeDAO daoEsp = new EspecialidadeDAO(this.conexao);
			Especialidade especialidade = daoEsp.PegarPeloID(idEspecialidade);
			
			TipoUsuarioDAO daoTU = new TipoUsuarioDAO(conexao);
            TipoUsuario tipoUsuario = daoTU.PegarPeloID(idTipoUsuario);

			Pesquisador o = new Pesquisador(id, nome, login, senha, tel, email, dtCadastro, instituto, especialidade, tipoUsuario);
			
			listaPesquisadors.add(o);
		}

		return listaPesquisadors;
	}
}
