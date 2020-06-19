/**
 * 
 * klasa opisuj¹ca podstawowy typ przedmiotów
 * @author Tom
 *
 */
package items;

public class Przedmiot {
	private String name;
	private String cena;
	private String obciazenie;
	private String dostepnosc;
	private String typ;
	
	
	//////////////////////////////////////////////////////////////////
	//konstruktory
	/**
	 * konstrutkor pusty
	 */
	public Przedmiot() {
		name = null;
		cena = null;
		obciazenie = null;
		dostepnosc = null;
		typ = null;	
	}
	/**
	 * 
	 * @param n - nazwa
	 * @param c - cena
	 * @param o - obciazenie
	 * @param d - dostepnosc
	 * @param t - typ
	 */
	public Przedmiot(String n, String c, String o, String d, String t) {
		this.name = n;
		this.cena = c;
		this.obciazenie = o;
		this.dostepnosc = d;
		this.typ = t;
	}
	
	/**
	 * 
	 * @param p - Przedmiot
	 */
	public Przedmiot(Przedmiot p) {
		this.name = p.name;
		this.cena = p.cena;
		this.obciazenie = p.obciazenie;
		this.dostepnosc = p.dostepnosc;
		this.typ = p.typ;
	}

	
	//////////////////////////////////////////////////////////////////
	//toString
	@Override
	public String toString() {
		return "Przedmiot [name=" + name + ", cena=" + cena + ", obciazenie=" + obciazenie + ", dostepnosc="
				+ dostepnosc + ", typ=" + typ + "]";
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the cena
	 */
	public String getCena() {
		return cena;
	}
	/**
	 * @param cena the cena to set
	 */
	public void setCena(String cena) {
		this.cena = cena;
	}
	/**
	 * @return the obciazenie
	 */
	public String getObciazenie() {
		return obciazenie;
	}
	/**
	 * @param obciazenie the obciazenie to set
	 */
	public void setObciazenie(String obciazenie) {
		this.obciazenie = obciazenie;
	}
	/**
	 * @return the dostepnosc
	 */
	public String getDostepnosc() {
		return dostepnosc;
	}
	/**
	 * @param dostepnosc the dostepnosc to set
	 */
	public void setDostepnosc(String dostepnosc) {
		this.dostepnosc = dostepnosc;
	}
	/**
	 * @return the typ
	 */
	public String getTyp() {
		return typ;
	}
	/**
	 * @param typ the typ to set
	 */
	public void setTyp(String typ) {
		this.typ = typ;
	}

	
	
}
