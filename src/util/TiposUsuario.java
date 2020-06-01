package util;

public enum TiposUsuario {

	ADMINISTRADOR(new Long(1)),
	PESQUISADOR(new Long(2));
	
	private final Long valor;
	
	private TiposUsuario(final Long v) {
		this.valor = v;
	}
	
	@Override
	public String toString() {
		return this.valor.toString();
	}
}
