package util;

public enum TipoProjeto {

	REDE_METABOLICA(new Long(1)),
	REDE_REGULACAO(new Long(2)),
	ALL (new Long(3));
	
	private final Long valor;
	
	private TipoProjeto(final Long v) {
		this.valor = v;
	}
	
	@Override
	public String toString() {
		return this.valor.toString();
	}
}
