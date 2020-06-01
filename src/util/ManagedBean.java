package util;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

public class ManagedBean {

	@SuppressWarnings("deprecation")
	public static Object getContextInfo(String info) {
		  FacesContext context = FacesContext.getCurrentInstance();
		  Application app = context.getApplication();
		  //app.getExpressionFactory().createValueExpression(context, arg1);
		  return app.createValueBinding(info).getValue(context);
	}
}