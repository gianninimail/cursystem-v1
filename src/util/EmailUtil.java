package util;

import java.util.List;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {

	public void enviaEmail() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthentication("cursystem@gmail.com", "ccbh4851");
		//email.setAuthenticator(new DefaultAuthenticator("username", "password"));
		email.setSSLOnConnect(true);
		email.setFrom("cursystem@gmail.com");
		email.setSubject("TestMail");
		email.setMsg("E-mail teste do sistema ... :-)");
		email.addTo("gianninimail@gmail.com");
		email.send();
	}
	
	public void enviaEmail(String _msg, String _destino) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthentication("cursystem@gmail.com", "ccbh4851");
		//email.setAuthenticator(new DefaultAuthenticator("username", "password"));
		email.setSSLOnConnect(true);
		email.setFrom("cursystem@gmail.com");
		email.setSubject("System Default e-mail.");
		email.setMsg("_msg");
		email.addTo(_destino);
		email.send();
	}
	
	public static void enviaEmail(String _titulo, String _msg, String _destino) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthentication("cursystem@gmail.com", "ccbh4851");
		//email.setAuthenticator(new DefaultAuthenticator("username", "password"));
		email.setSSLOnConnect(true);
		email.setFrom("cursystem@gmail.com");
		email.setSubject(_titulo);
		email.setMsg(_msg);
		email.addTo(_destino);
		email.send();
	}
	
	public static void enviaEmail(String _titulo, String _msg, List<String> _destinos) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthentication("cursystem@gmail.com", "ccbh4851");
		//email.setAuthenticator(new DefaultAuthenticator("username", "password"));
		email.setSSLOnConnect(true);
		email.setFrom("cursystem@gmail.com");
		email.setSubject(_titulo);
		email.setMsg(_msg);
		for (String dest : _destinos) {
			email.addTo(dest);
		}
		email.send();
	}
}
