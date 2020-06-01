package util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JSFUtil {

	public static void adicionarMensagemSucesso(String _msg)
	{
		//severity - tipo da mensagem (info, warning, error, fatal)
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", _msg);
		
		//Pega a area de memoria temporaria
		FacesContext contexto = FacesContext.getCurrentInstance();
		
		contexto.addMessage(null, msg);
		
		//exibe mensagem mesmo após redirect
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	}
	
	public static void adicionarMensagemAlerta(String _msg)
	{
		//severity - tipo da mensagem (info, warning, error, fatal)
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "", _msg);
		
		//Pega a �rea de memoria temporaria
		FacesContext contexto = FacesContext.getCurrentInstance();
		
		contexto.addMessage(null, msg);
		
		//exibe mensagem mesmo após redirect
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	}
	
	
	public static void adicionarMensagemErro(String _msg)
	{
		//severity - tipo da mensagem (info, warning, error, fatal)
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", _msg);
		
		//Pega a �rea de memoria temporaria
		FacesContext contexto = FacesContext.getCurrentInstance();
		
		contexto.addMessage(null, msg);
		
		//exibe mensagem mesmo após redirect
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	}
	
	public static void adicionarMensagemErroComponente(String _id ,String _msg)
	{
		//severity - tipo da mensagem (info, warning, error, fatal)
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", _msg);
		
		//Pega a �rea de memoria temporaria
		FacesContext contexto = FacesContext.getCurrentInstance();
		
		contexto.addMessage(_id, msg);
		
	}
}
