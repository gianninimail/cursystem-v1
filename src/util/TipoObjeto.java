package util;

public enum TipoObjeto {

	DESAFIO("Challenge"),
	RESPOSTA("Answer");
	
	private final String text;
	
	TipoObjeto(final String v) {
		this.text = v;
	}
	
	@Override
    public String toString() {
        return text;
    }
}
