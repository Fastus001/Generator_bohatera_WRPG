/**
 * 
 */
package mvcOknoGlowne;

/**
 * @author Tom
 *
 */
public interface GenBohModelInterface {
	boolean wczytajDane();
	void nowyBohater(int exp);
	
	/**
	 * @param jak - czy z talentami czy bez
	 * @return
	 */
	String wyswietlNowegoBohatera(boolean jak);
}
