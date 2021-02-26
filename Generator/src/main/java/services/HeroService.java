/**
 * 
 */
package services;

import domain.Hero;
import observers.MainGuiObserver;

import java.util.List;

/**
 * @author Tom
 *
 */
public interface HeroService {

	void loadData();

	void newHero(int raceNumber, int professionNumber, int experience, boolean gender, boolean showTalentDescription);

	void levelUp(int experience, boolean showTalentDescription);

	void newProfession(int experience, boolean showTalentDescription, boolean isAddLevelButtonActivated);

	String showNewHero(boolean showTalentDescription);

	List<String> raceNames();

	List<String> getProfessionsFirstLevel(String rs);

	void subscribeObserver(MainGuiObserver observer);

	void setRace(String race);

	void setProfession(String profession);

	void showHeroTalents(boolean showTalentDescription);

	void saveHeroToList();

	void exportDoPdf(Hero hero);

	/**
	 * @param index - która pozycja, jeżeli 0 to cała tablica
	 */
	void exportDoExcel(Object [] heroesAndNPC, int index);
	
	
}
