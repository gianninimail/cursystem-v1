package modelo;

import java.io.Serializable;

import util.EntidadeBase;

public class TipoUsuario implements Serializable, EntidadeBase {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	
	public TipoUsuario() {
		this.id = new Long(0);
	}

	public TipoUsuario(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		if (id != null) {
			this.id = id;
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "TipoUsuario [id=" + id + ", nome=" + nome + "]";
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoUsuario other = (TipoUsuario) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
      
        return true;
	}
}
