package util;

import java.io.Serializable;

public abstract class ModeloBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "ModeloBase []";
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}
