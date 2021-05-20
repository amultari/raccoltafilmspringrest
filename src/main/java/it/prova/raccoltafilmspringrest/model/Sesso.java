package it.prova.raccoltafilmspringrest.model;

public enum Sesso {
	MASCHIO("M"), FEMMINA("S");

	private String abbreviazione;

	Sesso(String abbreviazione) {
		this.abbreviazione = abbreviazione;
	}

	public String getAbbreviazione() {
		return abbreviazione;
	}
	
//	@JsonCreator
//	public static Sesso getSessoFromCode(String value) {
//	 
//	    for (Sesso dep : Sesso.values()) {
//	 
//	        if (equals(value)) {
//	 
//	            return dep;
//	        }
//	    }
//	 
//	    return null;
//	}

}
