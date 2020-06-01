package modelo;

import java.util.Date;

import util.ModeloBase;

public class Resposta extends ModeloBase {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String desc;
	private int validacoes;
	private Boolean status;
	private Long desafio;
	Pesquisador pesquisador;
	Date dtCadastro;
	private String novaEquacao;
	private String titulo;
	private String justificativa;
	
	public Resposta() {
		super();
		this.id = new Long(0);
	}

	public Resposta(Long id, String desc, int validacoes, Boolean status, Long desafio, Pesquisador pesquisador, Date dt_cadastro, String _novaEquacao, String _titulo, String _justificativa) {
		super();
		this.id = id;
		this.desc = desc;
		this.validacoes = validacoes;
		this.status = status;
		this.desafio = desafio;
		this.pesquisador = pesquisador;
		this.dtCadastro = dt_cadastro;
		this.novaEquacao = _novaEquacao;
		this.titulo = _titulo;
		this.justificativa = _justificativa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getValidacoes() {
		return validacoes;
	}

	public void setValidacoes(int validacoes) {
		this.validacoes = validacoes;
	}

	public Boolean getStatus() {
		System.out.println(this.status.toString());
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public Long getDesafio() {
		return desafio;
	}

	public void setDesafio(Long desafio) {
		this.desafio = desafio;
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
	
	public String getNovaEquacao() {
		return novaEquacao;
	}

	public void setNovaEquacao(String novaEquacao) {
		this.novaEquacao = novaEquacao;
	}
	
	public Date getDt_cadastro() {
		return dtCadastro;
	}

	public void setDt_cadastro(Date dt_cadastro) {
		this.dtCadastro = dt_cadastro;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (getClass() != obj.getClass())
			return false;
		
		Resposta other = (Resposta) obj;
		
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "Resposta [id=" + id + ", desc=" + desc + ", validacoes=" + validacoes + ", status=" + status
				+ ", desafio="+ desafio + ", pesquisador=" + pesquisador + "]";
	}
	
}
