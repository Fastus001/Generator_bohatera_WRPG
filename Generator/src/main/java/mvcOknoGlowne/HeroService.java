/**
 * 
 */
package mvcOknoGlowne;

import domain.Hero;
import domain.Profession;
import domain.Race;

/**
 * @author Tom
 *
 */
public interface HeroService {

	void loadData();

	void newHero(int raceNumber, int professionNumber, int experience, boolean gender, boolean showTalentDescription);

	void levelUp(int experience, boolean showTalentDescription);

	void newProfession(int experience, boolean showTalentDescription, boolean btnPodniesPoziomWlaczony);

	String showNewHero(boolean showTalentDescription);

	Object [] getRasaArray();

	Object [] getProfessionsFirstLevel(Race rs);

	void subscribeObserver(ObserwatorModel observer);

	void setRace(Race race);

	void setProfession(Profession profession);

	void showHeroTalents(boolean showTalentDescription);

	void saveHeroToList();

	void exportDoPdf(Hero hero);

	/**
	 * @param index - która pozycja, jeżeli 0 to cała tablica
	 */
	void exportDoExcel(Object [] heroesAndNPC, int index);
	
	
}
