package util;

public enum Status {

	COMPLETED(1),
	OPEN(2),
	REOPENED(3),
	PAUSED(5),
	VALIDATED(6);
	
	private final int valor;
	
	private Status(final int v) {
		this.valor = v;
	}
	
	public int valor() {
		return this.valor;
	}
	
	@Override
	public String toString() {
		return String.valueOf(valor);
	}
}
