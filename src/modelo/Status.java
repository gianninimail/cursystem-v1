package modelo;

import util.ModeloBase;

public class Status extends ModeloBase {

	private static final long serialVersionUID = 1L;
	int id;
	String nome;
	
	public Status() {
		
	}
	
	public Status(int id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Status [id=" + id + ", nome=" + nome + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	
}
