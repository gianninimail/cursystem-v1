package modelo;

import java.io.Serializable;

public class Metabolito implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Metabolito() {
		
	}
	
	public Metabolito(String name) {
		this.name = name;
		this.image = "";
	}
	
	public Metabolito(String name, String image) {
		this.name = name;
		this.image = image;
	}
	
	public Metabolito(String abbreviation, String name, String formula, String formulaAlt, String compAlternativos,
			String keggId) {
		super();
		this.abbreviation = abbreviation;
		this.name = name;
		this.formula = formula;
		this.formulaAlt = formulaAlt;
		this.compAlternativos = compAlternativos;
		this.keggId = keggId;
		this.image = "";
	}

	String abbreviation;
	String name;
	String formula;
	String formulaAlt;
	String compAlternativos;
	String keggId;
	String image;//Implementação de compatibilidade do modelo de componente PrimeFaces
	
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getFormulaAlt() {
		return formulaAlt;
	}
	public void setFormulaAlt(String formulaAlt) {
		this.formulaAlt = formulaAlt;
	}
	public String getCompAlternativos() {
		return compAlternativos;
	}
	public void setCompAlternativos(String compAlternativos) {
		this.compAlternativos = compAlternativos;
	}
	public String getKeggId() {
		return keggId;
	}
	public void setKeggId(String keggId) {
		this.keggId = keggId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
    public String toString() {
        return name;
    }
}
