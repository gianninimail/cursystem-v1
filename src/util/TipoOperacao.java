package util;

public enum TipoOperacao {

	CREATE("c"),
	READ("r"),
	UPDATE("u"),
	DELETE("d"),
	VALIDACAO("v"),
	CANCELAR("a");
	
	private final String text;
	
	TipoOperacao(final String v) {
		this.text = v;
	}
	
	@Override
    public String toString() {
        return text;
    }
}
