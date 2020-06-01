package controle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.ListDataModel;

import dao.EspecialidadeDAO;
import dao.PesquisadorDAO;
import dao.TipoUsuarioDAO;
import dao.UsuarioDAO;
import modelo.Especialidade;
import modelo.Pesquisador;
import modelo.Usuario;
import util.BeanBase;
import util.FabricaConexao;
import util.JSFUtil;
import util.SessaoUtil;

//@Scope(value = WebApplicationContext.SCOPE_SESSION)
//@Named("usuarioBean")
@ManagedBean
public class UsuarioBean extends BeanBase {
	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private Pesquisador pesquisador;

	private boolean checkboxPesquisador = true;
	private boolean checkboxAdmin;

	private UIComponent componenteEmail;
	private UIComponent componenteTelefone;
	private UIComponent componenteSenha;
	private UIComponent componenteSenhaConfirmar;

	public UIComponent getComponenteEmail() {
		return componenteEmail;
	}

	public void setComponenteEmail(UIComponent componenteEmail) {
		this.componenteEmail = componenteEmail;
	}

	public UIComponent getComponenteTelefone() {
		return componenteTelefone;
	}

	public void setComponenteTelefone(UIComponent componenteTelefone) {
		this.componenteTelefone = componenteTelefone;
	}

	public UIComponent getComponenteSenha() {
		return componenteSenha;
	}

	public void setComponenteSenha(UIComponent componenteSenha) {
		this.componenteSenha = componenteSenha;
	}

	public UIComponent getComponenteSenhaConfirmar() {
		return componenteSenhaConfirmar;
	}

	public void setComponenteSenhaConfirmar(UIComponent componenteSenhaConfirmar) {
		this.componenteSenhaConfirmar = componenteSenhaConfirmar;
	}

	public boolean isCheckboxAdmin() {
		return checkboxAdmin;
	}

	public void setCheckboxAdmin(boolean checkboxAdmin) {
		this.checkboxAdmin = checkboxAdmin;
	}

	public boolean isCheckboxPesquisador() {
		return checkboxPesquisador;
	}

	public void setCheckboxPesquisador(boolean checkboxPesquisador) {
		this.checkboxPesquisador = checkboxPesquisador;
	}

	public Pesquisador getPesquisador() {
		return pesquisador;
	}

	public void setPesquisador(Pesquisador pesquisador) {
		this.pesquisador = pesquisador;
	}

	private String msgOps;
	private List<Usuario> listaUsuarios;
	private List<Especialidade> listaEspecialidades;

	private ListDataModel<Usuario> usuarios;

	@PostConstruct
	public void init() {
		FabricaConexao fabrica = new FabricaConexao();
		Connection conexao = fabrica.fazerConexao();

		EspecialidadeDAO dao = new EspecialidadeDAO(conexao);
		try {
			listaEspecialidades = dao.listarTodos();
			conexao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Especialidade> getListaEspecialidades() {
		return listaEspecialidades;
	}

	public void setListaEspecialidades(List<Especialidade> listaEspecialidades) {
		this.listaEspecialidades = listaEspecialidades;
	}

	public UsuarioBean() {
		this.usuario = new Usuario();
		this.pesquisador = new Pesquisador();
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario _usuario) {
		this.usuario = _usuario;
	}

	public String getMsgOps() {
		return msgOps;
	}

	public void setMsgOps(String msgOps) {
		this.msgOps = msgOps;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public ListDataModel<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ListDataModel<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Deprecated
	public void CadastrarUsuario() {
		try {

			this.usuario.setDataCadastro(new Date());
			// this.usuario.setTipoUsuario(1);

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			UsuarioDAO dao = new UsuarioDAO(conexao);
			dao.Inserir(this.usuario);

			this.listaUsuarios = dao.listarTodos();

			this.usuarios = new ListDataModel<Usuario>(listaUsuarios);

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso("User registered successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void PrepararNovo() {
		this.usuario = new Usuario();

		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			UsuarioDAO dao = new UsuarioDAO(conexao);

			this.usuario.setId(dao.PegarProximoID());

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void ExcluirUsuario() {
		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			UsuarioDAO dao = new UsuarioDAO(conexao);
			dao.Excluir(this.usuario);

			this.listaUsuarios = dao.listarTodos();

			this.usuarios = new ListDataModel<Usuario>(listaUsuarios);

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso("User deleted successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void PrepararExcluir() {
		this.usuario = usuarios.getRowData();
	}

	public void EditarUsuario() {
		try {

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			UsuarioDAO dao = new UsuarioDAO(conexao);
			dao.Editar(this.usuario);

			this.listaUsuarios = dao.listarTodos();

			this.usuarios = new ListDataModel<Usuario>(listaUsuarios);

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso("User edited successfully!");

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public String EditarUsuarioSenha() {
		try {
			String outcome = "";
			String msgSucesso = "";

			if (!senhaValido()) {
				JSFUtil.adicionarMensagemErroComponente(componenteSenha.getClientId(),
						this.getMensagem("usuario.senha.invalida"));
				return outcome;
			}

			if (!senhasIguais()) {
				JSFUtil.adicionarMensagemErroComponente(componenteSenhaConfirmar.getClientId(),
						this.getMensagem("senha.confirmar.iguais"));
				return outcome;
			}

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			Usuario usuarioSessao = SessaoUtil.pegarUsuarioSessao();

			usuarioSessao.setSenha(usuario.getSenha());

			UsuarioDAO dao = new UsuarioDAO(conexao);
			dao.Editar(usuarioSessao);

			fabrica.fecharConexao();

			msgSucesso = getMensagem("senha.sucesso");
			JSFUtil.adicionarMensagemSucesso(msgSucesso);
			usuario = new Usuario();
			return outcome;

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
			return "";
		}
	}

	public void PrepararEditar() {
		this.usuario = usuarios.getRowData();
	}

	public String PrepararEditarMeuUsuario(Usuario usuario) {
		try {
			
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();
			
			pesquisador = new Pesquisador();

			PesquisadorDAO pesquisadorDao = new PesquisadorDAO(conexao);

			pesquisador = pesquisadorDao.PegarPeloID(usuario.getId());
			
			if (pesquisador==null) {
				pesquisador = new Pesquisador();
			}
			
			fabrica.fecharConexao();
			
			Map<String, Object> sessionMapObj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

			sessionMapObj.put("editarMeuUsuario", usuario);
			sessionMapObj.put("editarMeuPesquisador", pesquisador);

			return "/alteracao/alt_meu_usuario.jsf?faces-redirect=true";

		} catch (Exception e) {
			return null;
		}

	}
	
	public String EditarMeuUsuario(Usuario usuario, Pesquisador pesquisador) {
		try {
			
			String outcome = "";
			String msgSucesso = "";
			
			long Id;
			
			this.usuario = usuario;
			this.pesquisador = pesquisador;
			
			this.pesquisador.setIdEspecialidade(pesquisador.getEspecialidade().getId());
			
			Id = pesquisador.getId();
			
			if (existeOutroUsuarioEmail()) {
				JSFUtil.adicionarMensagemErroComponente(componenteEmail.getClientId(),
						this.getMensagem("usuario.email.ja.cadastrado"));
				return outcome;
			}

			if (!telefoneValido()) {
				JSFUtil.adicionarMensagemErroComponente(componenteTelefone.getClientId(),
						this.getMensagem("usuario.telefone.invalido"));
				return outcome;
			}

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			UsuarioDAO dao = new UsuarioDAO(conexao);
			dao.Editar(this.usuario);
			
			PesquisadorDAO pesquisadorDao = new PesquisadorDAO(conexao);
			if (Id==0) {
				pesquisador.setId(usuario.getId());
				pesquisadorDao.InserirExiste(pesquisador);
			}else {
				pesquisadorDao.EditarExiste(pesquisador);
			}

			fabrica.fecharConexao();

			msgSucesso = getMensagem("usuario.meu.editado");
			JSFUtil.adicionarMensagemSucesso(msgSucesso);
			
			return outcome;

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
			return "";
		}
	}

	public String cadastroUsuarioAdm() {
		try {
			String outcome = "";
			String msgSucesso = "";

			if (existeUsuarioEmail()) {
				JSFUtil.adicionarMensagemErroComponente(componenteEmail.getClientId(),
						this.getMensagem("usuario.email.ja.cadastrado"));
				return outcome;
			}

			if (!telefoneValido()) {
				JSFUtil.adicionarMensagemErroComponente(componenteTelefone.getClientId(),
						this.getMensagem("usuario.telefone.invalido"));
				return outcome;
			}

			if (!senhaValido()) {
				JSFUtil.adicionarMensagemErroComponente(componenteSenha.getClientId(),
						this.getMensagem("usuario.senha.invalida"));
				return outcome;
			}

			if (!senhasIguais()) {
				JSFUtil.adicionarMensagemErroComponente(componenteSenhaConfirmar.getClientId(),
						this.getMensagem("senha.confirmar.iguais"));
				return outcome;
			}

			this.usuario.setDataCadastro(new Date());

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			UsuarioDAO dao = new UsuarioDAO(conexao);

			// usuario ativado
			this.usuario.setStatus(1);

			msgSucesso = getMensagem("cadastro.sucesso");

			this.usuario.setDataStatus(new Date());

			Long id = dao.Inserir(this.usuario);
			
			pesquisador.setId(id);
			PesquisadorDAO pesquisadorDao = new PesquisadorDAO(conexao);
			pesquisadorDao.InserirExiste(pesquisador);

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso(msgSucesso);
			usuario = new Usuario();
			pesquisador = new Pesquisador();

			return outcome;

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(this.getMensagem("usuario.erro.cadastro"));
			return "";
		}
	}

	public String cadastroUsuario() {
		try {
			String outcome = "";
			String msgSucesso = "";

			if (existeUsuarioEmail()) {
				JSFUtil.adicionarMensagemErroComponente(componenteEmail.getClientId(),
						this.getMensagem("usuario.email.ja.cadastrado"));
				return outcome;
			}

			if (!telefoneValido()) {
				JSFUtil.adicionarMensagemErroComponente(componenteTelefone.getClientId(),
						this.getMensagem("usuario.telefone.invalido"));
				return outcome;
			}

			if (!senhaValido()) {
				JSFUtil.adicionarMensagemErroComponente(componenteSenha.getClientId(),
						this.getMensagem("usuario.senha.invalida"));
				return outcome;
			}

			if (!senhasIguais()) {
				JSFUtil.adicionarMensagemErroComponente(componenteSenhaConfirmar.getClientId(),
						this.getMensagem("senha.confirmar.iguais"));
				return outcome;
			}

			this.usuario.setDataCadastro(new Date());

			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			TipoUsuarioDAO tipoDao = new TipoUsuarioDAO(conexao);
			this.usuario.setTipoUsuario(tipoDao.PegarPeloID(2l));

			UsuarioDAO dao = new UsuarioDAO(conexao);

			if (emailFiocruz()) {
				this.usuario.setStatus(1);
				msgSucesso = getMensagem("usuario.fiocruz.cadastro.sucesso");
				outcome = "/index.jsf?faces-redirect=true";

				Usuario user = dao.VerificarUsuario(this.usuario.getEmail(), this.usuario.getSenha());

				if (user != null) {
					System.out.println("AUTENTICADO!!!");
					SessaoUtil.setParametroSessao("USUARIOLogado", user);
				}
			} else {
				this.usuario.setStatus(0);
				msgSucesso = getMensagem("usuario.cadastro.sucesso");
				// outcome="/default.jsf?faces-redirect=true";
			}
			this.usuario.setDataStatus(new Date());

			Long id = dao.Inserir(this.usuario);
			pesquisador.setId(id);
			PesquisadorDAO pesquisadorDao = new PesquisadorDAO(conexao);
			pesquisadorDao.InserirExiste(pesquisador);

			fabrica.fecharConexao();

			JSFUtil.adicionarMensagemSucesso(msgSucesso);
			usuario = new Usuario();
			pesquisador = new Pesquisador();

			return outcome;

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(this.getMensagem("usuario.erro.cadastro"));
			return "";
		}
	}

	private boolean senhaValido() {
		return usuario.getSenha().length() >= 5;
	}

	private boolean senhasIguais() {
		return usuario.getSenha().equals(usuario.getSenhaConfirmar());
	}

	private boolean telefoneValido() {
		String telefone = this.usuario.getTel();
		if (telefone.endsWith("_")) {
			telefone = telefone.substring(0, telefone.length() - 1);
		}
		if (telefone.endsWith("_")) {
			return false;
		}
		this.usuario.setTel(telefone);
		return true;
	}

	private boolean emailFiocruz() {
		String email[] = usuario.getEmail().split("@");
		String servidor = email[1];
		email = servidor.split("\\.");
		servidor = email[0];
		return servidor.equalsIgnoreCase("fiocruz");
	}

	private boolean existeUsuarioEmail() throws SQLException {
		FabricaConexao fabrica = new FabricaConexao();
		Connection conexao = fabrica.fazerConexao();
		UsuarioDAO dao = new UsuarioDAO(conexao);
		Usuario user = dao.PegarPeloEmail(this.usuario.getEmail());

		return user != null;
	}

	private boolean existeOutroUsuarioEmail() throws SQLException {
		FabricaConexao fabrica = new FabricaConexao();
		Connection conexao = fabrica.fazerConexao();
		UsuarioDAO dao = new UsuarioDAO(conexao);
		Usuario user = dao.PegarPeloEmail(this.usuario.getEmail());

		if (user != null) {
			if (user.getId().equals(this.usuario.getId())) {
				return false;
			}else {
				return true;
			}
		}else {	
			return false;
		}
	}	
	
	public void atualizaPesquisador() {
		if (!checkboxAdmin) {
			checkboxPesquisador = true;
		}
	}

	// @PostConstruct
	public void PesquisaUsuarios() {
		try {
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			UsuarioDAO dao = new UsuarioDAO(conexao);

			Usuario user = SessaoUtil.pegarUsuarioSessao();

			if (user.getTipoUsuario().getId() == 1) {
				this.listaUsuarios = dao.listarTodos();
			} else {

				Usuario usuario = dao.PegarPeloID(user.getId());

				this.listaUsuarios.add(usuario);
			}

			fabrica.fecharConexao();

			usuarios = new ListDataModel<Usuario>(listaUsuarios);

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.adicionarMensagemErro(e.getMessage());
		}
	}
}
