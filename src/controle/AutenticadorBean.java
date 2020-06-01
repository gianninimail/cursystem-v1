package controle;

import java.io.IOException;
import java.sql.Connection;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import dao.UsuarioDAO;
import modelo.Usuario;
import util.BeanBase;
import util.FabricaConexao;
import util.JSFUtil;
import util.SessaoUtil;

@ManagedBean
public class AutenticadorBean extends BeanBase {

	private static final long serialVersionUID = 1L;
	
	private String login;
	private String senha;
	
	public AutenticadorBean() {
		super();
	}
	
	public AutenticadorBean(String login, String senha) {
		super();
		this.login = login;
		this.senha = senha;
	}
	
	public void initialize() throws Exception {
		Usuario user = (Usuario)SessaoUtil.getParametroSessao("USUARIOLogado");
		if(user!=null)
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}
	
	public String autenticar() throws Exception {

		System.out.println("autenticando...");

		try {
			FabricaConexao fabrica = new FabricaConexao();
			Connection conexao = fabrica.fazerConexao();

			UsuarioDAO dao = new UsuarioDAO(conexao);

			Usuario user = dao.VerificarUsuario(this.login, this.senha);

			if (user != null) {
				
				if(user.getStatus()==0) {
					System.out.println("Conta desativada!!!");
					JSFUtil.adicionarMensagemErro(getMensagem("login.conta.desativada"));
					return null;
				} else {
					System.out.println("AUTENTICADO!!!");
					SessaoUtil.setParametroSessao("USUARIOLogado", user);
					return "/index.jsf?faces-redirect=true";
				}
			} else {
				
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, getMensagem("msg.login.falha"), getMensagem("msg.login.falha")));
				return null;
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Ocorreu um erro na conexão!", "Tente novamente mais tarde."));
			return null;
		}

	}
	
	public void checkAlreadyLoggedin() {
		try {
			if(usuarioLogado()) {
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.redirect(ec.getRequestContextPath()+"/index.jsf");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean usuarioLogado() throws Exception {
		Usuario user = (Usuario)SessaoUtil.getParametroSessao("USUARIOLogado");
		return user!=null;
	}

	public String registrarSaida() throws Exception {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/default.xhtml?faces-redirect=true";
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
