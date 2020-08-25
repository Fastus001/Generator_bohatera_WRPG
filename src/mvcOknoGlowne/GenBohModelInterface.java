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
	 * @param prof - numer profesji, -1 nie wybrana �adna
	 * @param exp - poziom do�wiadczenia
	 * @param plec - facet (true)  czy kobieta (false)
	 * @param opisTalentow - czy talent ma by� wy�wietlany czy nie
	 */
	void nowyBohater(int rasa, int prof,int exp, boolean plec, boolean opisTalentow);
	/**
	 * 
	 * @param t - wyswietl talent
	 * @param exp - opcja do�wiadczenia postaci
	 */
	void podniesPoziom(int exp, boolean t);
	/**
	 * 
	 * @param exp - do�wiadczenie postaci
	 * @param t - wyswietlanie talent�w
	 * @param p - czy btnPodniesPoziomPr jest w��czony czy nie
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
	 * @param talenty - czy maj� by� wyswietlane czy nie
	 */
	void opisPostaciTalenty(boolean talenty);
	/**
	 * zapisuje posta� do listy
	 */
	void zapiszPostac();
	/**
	 * 
	 * @param nBohater - posta� wybra na zapisania do pliku pdf
	 */
	void exportDoPdf(Bohater nBohater);
	/**
	 * 
	 * @param obj - tablica obiekt�w (bohater lub NPC do wgrania do arkusza Excel)
	 * @param ktora - kt�ra pozycja, je�eli 0 to ca�a tablica
	 */
	void exportDoExcel(Object [] obj,int ktora);
	
	
}
