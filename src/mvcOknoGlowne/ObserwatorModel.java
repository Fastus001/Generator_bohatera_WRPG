/**
 * 
 */
package mvcOknoGlowne;


import commons.Bohater;

/**
 * @author Tom
 *
 */
public interface ObserwatorModel {
	void aktualizujPostac(String opis);
	void wylaczbtnNowaProfesja();
	void wlaczbtnNowaProfesja();
	void wlaczPrzyciskbtnPodniesPoziomPr();
	void wylaczPrzicskPodniesPoziomPr();
	void aktualizujListeBohaterow(Bohater nowy);
}
