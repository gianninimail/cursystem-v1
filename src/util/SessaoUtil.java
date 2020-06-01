package util;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import modelo.Usuario;

public class SessaoUtil implements Serializable {


	private static final long serialVersionUID = 1L;

	public static HttpSession getSessao() throws Exception {
		
		FacesContext contexto = FacesContext.getCurrentInstance();
		
		HttpSession sessao = (HttpSession)contexto.getExternalContext().getSession(false);
		
		return sessao;
	}
	
	public static void setParametroSessao(String _chave, Object _valor) throws Exception {
		getSessao().setAttribute(_chave, _valor);
	}
	
	public static Object getParametroSessao(String _chave) throws Exception {
		return getSessao().getAttribute(_chave);
	}
	
	public static void removerParametro(String _chave) throws Exception {
		getSessao().removeAttribute(_chave);
	}
	
	public static Usuario pegarUsuarioSessao() throws Exception {
		Object o = getSessao().getAttribute("USUARIOLogado");
		
		Usuario user = (Usuario)o;
		
		return user;
	}
	
	public static void invalidarSessao() throws Exception {
		getSessao().invalidate();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
