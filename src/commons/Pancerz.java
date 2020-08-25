/**
 * 
 */
package commons;

/**
 * @author Tom
 *
 */
public class Pancerz extends Przedmiot {
	private String kara;
	private String lokacja;
	private String zaletyWady;
	private String kategoria;
	private int punktyPancerza;
	
	//////////////////////////////////////////////////////////////////
	//konstruktory
	/**
	 * 
	 */
	public Pancerz() {
		super();
		this.kara = null;
		this.lokacja = null;
		this.zaletyWady = null;
		this.kategoria = null;
		this.punktyPancerza = 0;
	}

	/**
	 * 
	 * @param n - nazwa
	 * @param c - cena
	 * @param o - obci¹zenie
	 * @param d - dostêpnoœæ
	 * @param t - typ
	 * @param k - kara
	 * @param l - lokacja
	 * @param zw - zakety i wady
	 * @param kt - kategoria
	 * @param pp - punkty pancerza
	 */
	public Pancerz(String n, String c, String o, String d, String t, String k, String l, String zw, String kt, int pp) {
		super(n, c, o, d, t);
		this.kara = k;
		this.lokacja = l;
		this.zaletyWady = zw;
		this.kategoria = kt;
		this.punktyPancerza = pp;
	}

	/**
	 * @param p - Pancerz
 	 */
	public Pancerz(Pancerz p) {
		super((Przedmiot) p);
		this.kara = p.kara;
		this.lokacja = p.lokacja;
		this.zaletyWady = p.zaletyWady;
		this.kategoria = p.kategoria;
		this.punktyPancerza = p.punktyPancerza;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("Pancerz [kara=");
		builder.append(kara);
		builder.append(", lokacja=");
		builder.append(lokacja);
		builder.append(", zaletyWady=");
		builder.append(zaletyWady);
		builder.append(", kategoria=");
		builder.append(kategoria);
		builder.append(", punktyPancerza=");
		builder.append(punktyPancerza);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the kara
	 */
	public String getKara() {
		return kara;
	}

	/**
	 * @return the lokacja
	 */
	public String getLokacja() {
		return lokacja;
	}

	/**
	 * @return the zaletyWady
	 */
	public String getZaletyWady() {
		return zaletyWady;
	}

	/**
	 * @return the kategoria
	 */
	public String getKategoria() {
		return kategoria;
	}

	/**
	 * @return the punktyPancerza
	 */
	public int getPunktyPancerza() {
		return punktyPancerza;
	}

	/**
	 * @param kara the kara to set
	 */
	public void setKara(String kara) {
		this.kara = kara;
	}

	/**
	 * @param lokacja the lokacja to set
	 */
	public void setLokacja(String lokacja) {
		this.lokacja = lokacja;
	}

	/**
	 * @param zaletyWady the zaletyWady to set
	 */
	public void setZaletyWady(String zaletyWady) {
		this.zaletyWady = zaletyWady;
	}

	/**
	 * @param kategoria the kategoria to set
	 */
	public void setKategoria(String kategoria) {
		this.kategoria = kategoria;
	}

	/**
	 * @param punktyPancerza the punktyPancerza to set
	 */
	public void setPunktyPancerza(int punktyPancerza) {
		this.punktyPancerza = punktyPancerza;
	}
	
	
	

}
