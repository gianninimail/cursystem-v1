package modelo;

import java.util.Date;

public class Pesquisador extends Usuario {

	private static final long serialVersionUID = 1L;
	private String instituto;
	private Especialidade especialidade;
	private Long idEspecialidade;
	//private int idEspecialidade;

	public Long getIdEspecialidade() {
		return idEspecialidade;
	}

	public void setIdEspecialidade(Long idEspecialidade) {
		this.idEspecialidade = idEspecialidade;
	}

	public Pesquisador() {
		super();
		this.especialidade = new Especialidade();
	}
	
	public Pesquisador(Long id, String nome, String login, String senha, String tel, String email, Date dtCadastro, String instituto, Especialidade especialidade, TipoUsuario tipoUsuario, Integer status, Date dtStatus) {
		super(id, nome, login, senha, tel, email, dtCadastro, tipoUsuario, status, dtStatus);
		this.instituto = instituto;
		this.especialidade = especialidade;
		//this.idEspecialidade = idEspecialidade;
	}
	
	public Pesquisador(Usuario _usuario) {
		super(_usuario.id, _usuario.nome, _usuario.login, _usuario.senha, _usuario.tel, _usuario.email, _usuario.dataCadastro, _usuario.tipoUsuario, _usuario.status, _usuario.dataStatus);
	}

	public String getInstituto() {
		return instituto;
	}

	public void setInstituto(String instituto) {
		if (!instituto.equals("")) {
			this.instituto = instituto;
		}	
	}
	
	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public void setUsuario(Usuario _usuario) {
		this.id = _usuario.id;
		this.nome = _usuario.nome;
		this.login = _usuario.login;
		this.senha = _usuario.senha;
		this.tel = _usuario.tel;
		this.email = _usuario.email;
		this.dataCadastro = _usuario.dataCadastro;
		this.tipoUsuario = _usuario.tipoUsuario;
	}

	@Override
	public String toString() {
		return "Pesquisador [instituto=" + instituto + ", especialidade=" + especialidade + "]";
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
        
        return super.equals(other);
	}
}
