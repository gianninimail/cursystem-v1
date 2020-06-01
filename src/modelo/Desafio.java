package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.ModeloBase;

public class Desafio extends ModeloBase {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String titulo;
	private String desc;
	private Status status;
	private Especialidade especialidade;
	private Reacao reacao;
	private Usuario usuario;
	private Long idRespostaAceita;
	private Date dt_cadastro;
	private List<Resposta> respostas;
	
	public Desafio() {
		this.id = new Long(0);
		this.respostas = new ArrayList<Resposta>();
		this.especialidade = new Especialidade();
		this.usuario = new Usuario();
		this.reacao = new Reacao();
		this.status = new Status();
	}
	
	public Desafio(Long id, String titulo, String desc, Status status, Especialidade especialidade, Reacao reacao, Usuario usuario, Long idRespostaAceita, Date dt_cadastro, List<Resposta> respostas) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.desc = desc;
		this.status = status;	
		this.especialidade = especialidade;
		this.reacao = reacao;
		this.usuario = usuario;
		this.idRespostaAceita = idRespostaAceita;
		this.dt_cadastro = dt_cadastro;
		this.respostas = respostas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
		
	public Date getDataCadastro() {
		return dt_cadastro;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public Reacao getReacao() {
		return reacao;
	}

	public void setReacao(Reacao reacao) {
		this.reacao = reacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Long getIdRespostaAceita() {
		return idRespostaAceita;
	}

	public void setIdRespostaAceita(Long idRespostaAceita) {
		this.idRespostaAceita = idRespostaAceita;
	}

	public Date getDt_cadastro() {
		return dt_cadastro;
	}

	public void setDt_cadastro(Date dt_cadastro) {
		this.dt_cadastro = dt_cadastro;
	}

	public void setDataCadastro(Date dt_cadastro) {
		this.dt_cadastro = dt_cadastro;
	}
	
	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	@Override
	public String toString() {
		return "Desafio [id=" + id + ", titulo=" + titulo + ", desc=" + desc + ", status=" + status + ", especialidade="
				+ especialidade + ", reacao=" + reacao + ", usuario=" + usuario + ", id_resposta_aceita="
				+ idRespostaAceita + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Desafio other = (Desafio) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        
        return true;
	}
}
