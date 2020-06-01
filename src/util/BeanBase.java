package util;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public abstract class BeanBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ResourceBundle bundle;
	private Locale locale;
	private FacesContext facesContext;
	
	public String getMsg(String key) {
		facesContext = FacesContext.getCurrentInstance();
		locale = facesContext.getViewRoot().getLocale();
		bundle = ResourceBundle.getBundle("textos.properties",locale);
		return bundle.getString(key);
	}

	@Override
	public String toString() {
		return "ModeloBase []";
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	public String getMensagem(String key) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle textos = ResourceBundle.getBundle("com.cursystem.textos", context.getViewRoot().getLocale());
		String msg;
		
		try {
			msg = textos.getString(key);
		}catch (Exception e) {
			msg = "";
		}
		
		return msg;
	}

}
