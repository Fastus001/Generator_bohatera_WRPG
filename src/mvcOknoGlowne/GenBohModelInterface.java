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
	void nowyBohater(int rasa, int prof,int exp, boolean plec);
	
	/**
	 * @param jak - czy z talentami czy bez
	 * @return
	 */
	String wyswietlNowegoBohatera(boolean jak);
	
	Object [] getRasaArray();
	Object [] getProfesjePierwszyPoziom(Rasa rs);
	
}
