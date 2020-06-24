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
	 * @param prof - numer profesji, -1 nie wybrana ¿adna
	 * @param exp - poziom doœwiadczenia
	 * @param plec - facet (true)  czy kobieta (false)
	 * @param opisTalentow - czy talent ma byæ wyœwietlany czy nie
	 */
	void nowyBohater(int rasa, int prof,int exp, boolean plec, boolean opisTalentow);
	
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
