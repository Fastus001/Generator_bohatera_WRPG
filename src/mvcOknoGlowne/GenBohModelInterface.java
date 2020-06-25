/**
 * 
 */
package mvcOknoGlowne;

import commons.Rasa;

/**
 * @author Tom
 *
 */
public interface GenBohModelInterface {
	boolean wczytajDane();
	/**
	 * 
	 * @param rasa - jaka rasa, -1 nie wybrana rasa
	 * @param prof - numer profesji, -1 nie wybrana żadna
	 * @param exp - poziom doświadczenia
	 * @param plec - facet (true)  czy kobieta (false)
	 * @param opisTalentow - czy talent ma być wyświetlany czy nie
	 */
	void nowyBohater(int rasa, int prof,int exp, boolean plec, boolean opisTalentow);
	/**
	 * 
	 * @param t - wyswietl talent
	 * @param p - numer profesji
	 * @param exp - opcja doświadczenia postaci
	 */
	void podniesPoziom(int p, int exp, boolean t);
	/**
	 * 
	 * @param exp - doświadczenie postaci
	 * @param t - wyswietlanie talentów
	 * @param p - czy btnPodniesPoziomPr jest włączony czy nie
	 */
	void nowaProfesja(int exp, boolean t, boolean p);
	
	/**
	 * @param jak - czy z talentami czy bez
	 * @return
	 */
	String wyswietlNowegoBohatera(boolean jak);
	Object [] getRasaArray();
	Object [] getProfesjePierwszyPoziom(Rasa rs);
	void zarejestrujObserwatora(ObserwatorModel o);
	void wyrejestrujObserwatora(ObserwatorModel o);
	
	
}
