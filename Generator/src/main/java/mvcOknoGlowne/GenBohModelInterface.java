/**
 * 
 */
package mvcOknoGlowne;

import commons.Bohater;
import commons.Profession;
import commons.Race;

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
	 * @param exp - opcja doświadczenia postaci
	 */
	void podniesPoziom(int exp, boolean t);
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
	Object [] getProfesjePierwszyPoziom(Race rs);
	void zarejestrujObserwatora(ObserwatorModel o);
	void wyrejestrujObserwatora(ObserwatorModel o);
	Bohater postacBohaterModel();
	void setRasa(Race r);
	void setProfesja(Profession p);
	/**
	 * 
	 * @param talenty - czy mają być wyswietlane czy nie
	 */
	void opisPostaciTalenty(boolean talenty);
	/**
	 * zapisuje postać do listy
	 */
	void zapiszPostac();
	/**
	 * 
	 * @param nBohater - postać wybra na zapisania do pliku pdf
	 */
	void exportDoPdf(Bohater nBohater);
	/**
	 * 
	 * @param obj - tablica obiektów (bohater lub NPC do wgrania do arkusza Excel)
	 * @param ktora - która pozycja, jeżeli 0 to cała tablica
	 */
	void exportDoExcel(Object [] obj,int ktora);
	
	
}
