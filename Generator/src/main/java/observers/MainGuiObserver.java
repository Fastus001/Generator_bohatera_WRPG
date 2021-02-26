package observers;


import domain.Hero;

/**
 * @author Tom
 */
public interface MainGuiObserver {

	void updateHero(String description);

	void activateButtonNewHero();

	void activateNewProfessionButton();

	void activateNewLevelButton();

	void deactivateNewLevelButton();

	void updateListModelWithNewHero(Hero hero);
}
