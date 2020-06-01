package modelo;

import java.io.Serializable;
import java.util.Date;

import util.EntidadeBase;

public class Usuario implements Serializable, EntidadeBase {

	private static final long serialVersionUID = 1L;
	Long id;
	String nome;
	String login;
	String senha;
	String tel;
	String email;
	Date dataCadastro;
	TipoUsuario tipoUsuario;
	
	public Usuario() {
		this.id = new Long(0);
	}
	
	public Usuario (Long id, String nome, String login, String senha, String tel, String email, Date dtCadastro, TipoUsuario tipoUsuario) {
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.tel = tel;
		this.email = email;
		this.dataCadastro = dtCadastro;
		this.tipoUsuario = tipoUsuario;
		this.dataCadastro = dtCadastro;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + "]";
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Usuario other = (Usuario) obj;
        
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        
        return true;
	}
}
