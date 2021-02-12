/**
 * Rozszerzenie klasy Przedmiot opisuj�ca r�zne typy broni, bia�ej jak i dystansowej oraz amunicji
 */
package commons;

/**
 * @author Tom
 */
public class Bron extends Przedmiot {
	private String zasieg;
	private String obrazenia;
	private String zaletyWady;
	private String kategoria;

	//////////////////////////////////////////////////////////////////
	//konstruktory
	public Bron() {
		super();
		this.zasieg = null;
		this.obrazenia = null;
		this.zaletyWady = null;
		this.kategoria = null;
	}
	/**
	 * 
	 * @param n - nazwa
	 * @param c - cena
	 * @param o - obciazenie
	 * @param d - dostepno��
	 * @param t - typ
	 * @param z - zasieg/dystans
	 * @param ob - obrazenia
	 * @param zw - zalety i wady
	 * @param k - kategoria
	 */
	public Bron(String n, String c, String o, String d, String t, String z, String ob, String zw, String k) {
		super(n, c, o, d, t);
		this.zasieg = z;
		this.obrazenia = ob;
		this.zaletyWady = zw;
		this.kategoria = k;
	}
	/**
	 * 
	 * @param b - bro�
	 */
	public Bron(Bron b) {
		super((Przedmiot) b);
		this.zasieg = b.zasieg;
		this.obrazenia = b.obrazenia;
		this.zaletyWady = b.zaletyWady;
		this.kategoria = b.kategoria;
	}

	
	//////////////////////////////////////////////////////////////////
	//toString, getters, setters
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("Bron [zasieg=" + zasieg + ", obrazenia=" + obrazenia + ", zaletyWady=" + zaletyWady + ", kategoria="+ kategoria + "]");
		return sb.toString();
	}
	/**
	 * @return the zasieg
	 */
	public String getZasieg() {
		return zasieg;
	}
	/**
	 * @param zasieg the zasieg to set
	 */
	public void setZasieg(String zasieg) {
		this.zasieg = zasieg;
	}
	/**
	 * @return the obrazenia
	 */
	public String getObrazenia() {
		return obrazenia;
	}

	public void setObrazenia(String obrazenia) {
		this.obrazenia = obrazenia;
	}

	public String getZaletyWady() {
		return zaletyWady;
	}

	public void setZaletyWady(String zaletyWady) {
		this.zaletyWady = zaletyWady;
	}

	public String getKategoria() {
		return kategoria;
	}

	public void setKategoria(String kategoria) {
		this.kategoria = kategoria;
	}

}
