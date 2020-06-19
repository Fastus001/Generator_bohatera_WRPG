package npcGenerator;

public class CechyPotworow {
	@Override
	public String toString() {
		return nazwa;
	}

	private String nazwa;
	private String opis;
	
	public CechyPotworow() {
		this.nazwa = "brak";
		this.opis = "brak opisu";
	}
	
	public CechyPotworow(String nazwa, String opis) {
		this.nazwa = nazwa;
		this.opis = opis;
	}
	
	public CechyPotworow(CechyPotworow cp) {
		this.nazwa = cp.nazwa;
		this.opis = cp.opis;
	}

	/**
	 * @return the nazwa
	 */
	public String getNazwa() {
		return nazwa;
	}

	/**
	 * @param nazwa the nazwa to set
	 */
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	/**
	 * @return the opis
	 */
	public String getOpis() {
		return opis;
	}

	/**
	 * @param opis the opis to set
	 */
	public void setOpis(String opis) {
		this.opis = opis;
	}

}
