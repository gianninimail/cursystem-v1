package modelo;

import java.io.Serializable;

public class Reacao implements Serializable {

	private static final long serialVersionUID = 1L;
	Long id;
	String abbreviation;
	String name;
	String equation;
	String subsystem;
	String function_subsystem;
	String protein;
	String protein_classification;
	String genePAO;
	String geneCCBH;
	String image;//atributo implementado para garantir compatibilidade com modelo de elemento do Primefaces
	
	public Reacao() {
		this.id = new Long(0);
		//this.abbreviation = "";
	}
	
	public Reacao(String name) {
		this.name = name;
		this.image = "";
	}
	
	public Reacao(String name, String image) {
		this.name = name;
		this.image = image;
	}
	
	public Reacao(Long id, String abbreviation, String name, String equation, String subsystem, String function_subsystem,
			String protein, String protein_classification, String genePAO, String geneCCBH) {

		this.id = id;
		this.abbreviation = abbreviation;
		this.name = name;
		this.equation = equation;
		this.subsystem = subsystem;
		this.function_subsystem = function_subsystem;
		this.protein = protein;
		this.protein_classification = protein_classification;
		this.genePAO = genePAO;
		this.geneCCBH = geneCCBH;
		this.image = "";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
	
	public String getEquation() {
		return equation;
	}
	
	public void setEquation(String equation) {
		this.equation = equation;
	}
	
	public String getSubsystem() {
		return subsystem;
	}
	
	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}
	
	public String getFunction_subsystem() {
		return function_subsystem;
	}
	
	public void setFunction_subsystem(String function_subsystem) {
		this.function_subsystem = function_subsystem;
	}
	
	public String getProtein() {
		return protein;
	}
	
	public void setProtein(String protein) {
		this.protein = protein;
	}
	
	public String getProtein_classification() {
		return protein_classification;
	}
	
	public void setProtein_classification(String protein_classification) {
		this.protein_classification = protein_classification;
	}

	public String getGenePAO() {
		return genePAO;
	}
	
	public void setGenePAO(String genePAO) {
		this.genePAO = genePAO;
	}
	
	public String getGeneCCBH() {
		return geneCCBH;
	}
	
	public void setGeneCCBH(String geneCCBH) {
		this.geneCCBH = geneCCBH;
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

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reacao other = (Reacao) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
      
        return true;
	}
}
