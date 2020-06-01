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
	String senhaConfirmar;
	String tel;
	String email;
	Date dataCadastro;
	TipoUsuario tipoUsuario;
	Integer status;
	Date dataStatus;
	
	public Usuario() {
		this.id = new Long(0);
	}
	
	public Usuario (Long id, String nome, String login, String senha, String tel, String email, Date dtCadastro, TipoUsuario tipoUsuario
			, Integer status, Date dtStatus) {
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.tel = tel;
		this.email = email;
		this.dataCadastro = dtCadastro;
		this.tipoUsuario = tipoUsuario;
		this.dataCadastro = dtCadastro;
		this.status = status;
		this.dataStatus = dtStatus;
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

	public String getSenhaConfirmar() {
		return senhaConfirmar;
	}

	public void setSenhaConfirmar(String senhaConfirmar) {
		this.senhaConfirmar = senhaConfirmar;
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
	
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getDataStatus() {
		return this.dataStatus;
	}
	
	public void setDataStatus(Date dataStatus) {
		this.dataStatus = dataStatus;
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
