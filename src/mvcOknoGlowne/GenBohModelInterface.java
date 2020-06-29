/**
 * 
 */
package mvcOknoGlowne;

import commons.Bohater;
import commons.Profesja;
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
	 * 
	 * @param t - wyswietl talent
	 * @param exp - opcja doœwiadczenia postaci
	 */
	void podniesPoziom(int exp, boolean t);
	/**
	 * 
	 * @param exp - doœwiadczenie postaci
	 * @param t - wyswietlanie talentów
	 * @param p - czy btnPodniesPoziomPr jest w³¹czony czy nie
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
	Bohater postacBohaterModel();
	void setRasa(Rasa r);
	void setProfesja(Profesja p);
	/**
	 * 
	 * @param talenty - czy maj¹ byæ wyswietlane czy nie
	 */
	void opisPostaciTalenty(boolean talenty);
	/**
	 * zapisuje postaæ do listy
	 */
	void zapiszPostac();
	/**
	 * 
	 * @param nBohater - postaæ wybra na zapisania do pliku pdf
	 */
	void exportDoPdf(Bohater nBohater);
	
	
}
