package modelo;

import java.util.Date;

import util.ModeloBase;

public class Comentario extends ModeloBase {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String texto;
	private Long resposta;
	private Pesquisador pesquisador;
	private Date dtCadastro; 
	
	public Comentario() {
		super();
		this.id = new Long(0);
	}
	
	public Comentario(Long _id, String _texto, Long _resposta, Pesquisador _pesquisador, Date _dtCadastro) {
		super();
		this.id = _id;
		this.texto = _texto;
		this.resposta = _resposta;
		this.pesquisador = _pesquisador;
		this.dtCadastro = _dtCadastro;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public Long getResposta() {
		return resposta;
	}
	
	public void setResposta(Long resposta) {
		this.resposta = resposta;
	}
	
	public Pesquisador getPesquisador() {
		return pesquisador;
	}
	
	public void setPesquisador(Pesquisador pesquisador) {
		this.pesquisador = pesquisador;
	}

	public Date getDataCadastro() {
		return dtCadastro;
	}

	public void setDataCadastro(Date dt_cadastro) {
		this.dtCadastro = dt_cadastro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pesquisador == null) ? 0 : pesquisador.hashCode());
		result = prime * result + ((resposta == null) ? 0 : resposta.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comentario other = (Comentario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pesquisador == null) {
			if (other.pesquisador != null)
				return false;
		} else if (!pesquisador.equals(other.pesquisador))
			return false;
		if (resposta == null) {
			if (other.resposta != null)
				return false;
		} else if (!resposta.equals(other.resposta))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comentario [id=" + id + ", texto=" + texto + ", resposta=" + resposta + ", pesquisador=" + pesquisador
				+ "]";
	}
}
