package controle;

import java.sql.Connection;

import javax.faces.bean.ManagedBean;

import dao.UsuarioDAO;
import modelo.Usuario;
import util.BeanBase;
import util.FabricaConexao;
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
	
	public String autenticar() throws Exception {
		
		System.out.println("autenticando...");

		FabricaConexao fabrica = new FabricaConexao();
		Connection conexao = fabrica.fazerConexao();

		UsuarioDAO dao = new UsuarioDAO(conexao);
		
		Usuario user = dao.VerificarUsuario(this.login, this.senha);
			
		if(user != null) {
			
			System.out.println("AUTENTICADO!!!");
			
			SessaoUtil.setParametroSessao("USUARIOLogado", user);
			
			return "/index.jsf?faces-redirect=true";
		}
		else {
			return "/login.jsf?erro=true";
		}
	}
	
	public String registrarSaida() throws Exception {
		
		SessaoUtil.invalidarSessao();
		return "/login.xhtml?faces-redirect=true";
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
