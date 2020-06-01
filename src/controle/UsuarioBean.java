package controle;


import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.model.ListDataModel;

import dao.UsuarioDAO;
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
	private String msgOps;
	private List<Usuario> listaUsuarios;
	
	private ListDataModel<Usuario> usuarios;

	public UsuarioBean() {
		this.usuario = new Usuario();
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
	
	public void CadastrarUsuario() {
		try {
			
			this.usuario.setDataCadastro(new Date());
			//this.usuario.setTipoUsuario(1);
			
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
	
	public void PrepararExcluir() 
	{
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
	
	public void PrepararEditar() 
	{
		this.usuario = usuarios.getRowData();
	}
	
	
	
	@PostConstruct
	public void PesquisaUsuarios() {
		try {
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			UsuarioDAO dao = new UsuarioDAO(conexao);
			
			Usuario user = SessaoUtil.pegarUsuarioSessao();
			
			if (user.getTipoUsuario().getId() == 1) {
				this.listaUsuarios = dao.listarTodos();
			}
			else {
				
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
