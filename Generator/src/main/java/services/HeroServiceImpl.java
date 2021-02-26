package services;

import domain.Hero;
import domain.Profession;
import domain.Race;
import enums.Gender;
import enums.RaceType;
import export.ExportDoExcela;
import export.ExportToPdf;
import factories.HeroFactory;
import factories.ProfessionFactory;
import factories.RaceFactory;
import hero.HeroDisplay;
import hero.HeroProgress;
import observers.MainGuiObserver;
import npcGenerator.Potwory;
import utilities.DiceRoll;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tom
 */
public class HeroServiceImpl implements HeroService {
    //rasa wybrana w comboBoxie w widoku
    private Race chosenRace;
    //profesja wybrana w CBoxie w widoku
    private String chosenProfession;
    private String urlSavaPdf = null;
    private List<Race> races = new ArrayList<>();
    private final List<Profession> professions = new ArrayList<>();
    private List<Profession> professionsFirstLevel = new ArrayList<>();
    private HeroProgress heroProgress;

    private MainGuiObserver observer;

    @Override
    public void loadData() {
        races = RaceFactory.getInstance().createAll();
        professions.addAll( ProfessionFactory.getInstance().createAll() );
    }

    @Override
    public void newHero(int raceNumber, int professionNumber, int experience, boolean gender, boolean showTalentDescription) {
        Race race = getRace( raceNumber );
        searchForFirstLevelProfession( race.getName() );

        Profession profession = getProfession( professionNumber );
        RaceType randomRace = RaceType.getRaceEnumByName( race.getName() );
        createHeroProgress( profession, randomRace, gender );

        heroProgress.experienceLevel( experience );
        observer.updateHero( showNewHero( showTalentDescription ) );
        chosenRace = race.toBuilder().build();
        chosenProfession = profession.getName();
    }

    private Race getRace(int raceNumber) {
        if ( raceNumber == -1 ) {
            return races.get( DiceRoll.randomRace() );
        }
        return races.get( raceNumber );
    }

    private void searchForFirstLevelProfession(String randomRace) {
        professionsFirstLevel.clear();
        professionsFirstLevel = professions.stream()
                .filter( p -> p.getLevel() == 1 && p.getRaces().contains( randomRace ) )
                .collect( Collectors.toList() );
    }

    private Profession getProfession(int professionNumber) {
        if ( professionNumber == -1 ) {
            return professionsFirstLevel.get( ( int ) (Math.random() * professionsFirstLevel.size()) );
        }
        return professionsFirstLevel.get( professionNumber );
    }

    private void createHeroProgress(Profession profession, RaceType raceType, boolean gender) {
        if ( gender ) {
            heroProgress = new HeroProgress( HeroFactory.getInstance().create( raceType, profession.getName(), Gender.MALE ) );
        }
        else {
            heroProgress = new HeroProgress( HeroFactory.getInstance().create( raceType, profession.getName(), Gender.FEMALE ) );
        }
    }

    @Override
    public List<String> raceNames() {
        return races.stream()
                .map( Race::getName )
                .collect( Collectors.toList() );
    }

    @Override
    public List<String> getProfessionsFirstLevel(String rs) {
        searchForFirstLevelProfession( rs );
        return professionsFirstLevel.stream()
                .map( Profession::getName )
                .collect( Collectors.toList() );
    }

    @Override
    public void subscribeObserver(MainGuiObserver observer) {
        this.observer = observer;
    }

    @Override
    public String showNewHero(boolean showTalentDescription) {
        HeroDisplay heroDisplay = new HeroDisplay( heroProgress.getHero() );
        return heroDisplay.showHero( showTalentDescription );
    }

    @Override
    public void levelUp(int experience, boolean showTalentDescription) {
        Profession profession = heroProgress.getHero().getProfession();

        int level = profession.getLevel() + 1;
        if ( level > 4 && profession.isNotFinished() ) {
            professionFinishConfirmation( showTalentDescription );
        }
        else {
            int levelHistory = heroProgress.checkProfessionHistory( chosenProfession );
            if ( levelHistory != -1 ) {
                if ( (levelHistory + 1) < 5 ) {
                    level = levelHistory + 1;
                }
                else {
                    maxLevelReachedMessage(
                    );
                }
            }
            profession = ProfessionFactory.getInstance()
                    .create( profession.getName(), level );

            if(profession !=null){
                heroProgress.newProfession( profession );
                heroProgress.experienceLevel( experience );
                observer.updateHero( showNewHero( showTalentDescription ) );
            }
        }
    }

    private void professionFinishConfirmation(boolean showTalentDescription) {
        int confirmation = JOptionPane.showConfirmDialog( null,
                                                          "Postać osiągneła maksymalny poziom profesji,czy chcesz aby \"ukończyła\" ten poziom?", "Koks",
                                                          JOptionPane.YES_NO_OPTION );
        if ( confirmation == JOptionPane.OK_OPTION ) {
            heroProgress.finishProfession( 5 );
            observer.updateHero( showNewHero( showTalentDescription ) );
        }
    }

    private void maxLevelReachedMessage() {
        JOptionPane.showMessageDialog( null,
                                       "Wybrana profesja posiada już maksymlany poziom, postać awansuje we wcześniej wybranej profesji",
                                       "Maksymalny poziom wybranej profesjii!!", JOptionPane.INFORMATION_MESSAGE );
    }

    @Override
    public void newProfession(int experience, boolean showTalentDescription, boolean isAddLevelButtonActivated) {

        if ( heroProgress.getHero().getProfession().isNotFinished() ) {
            int confirmDialog = JOptionPane.showConfirmDialog( null,
            "Czy aktualny poziom profesji ma być ukończony przed zmianą profesji?",
             "Zmiana profesji!", JOptionPane.YES_NO_OPTION );

            if ( confirmDialog == JOptionPane.OK_OPTION ) {
                heroProgress.finishProfession( heroProgress.getHero().getProfession().getLevel() + 1 );
            }
        }

        int checkProfessionHistoryLevel = heroProgress.checkProfessionHistory( chosenProfession );

        if ( checkProfessionHistoryLevel == -1 ) {
            heroProgress.newProfession( ProfessionFactory.getInstance().create( chosenProfession, 1 ) );
            heroProgress.experienceLevel( experience );
            //wyswietlenie nowego bohatera
            observer.updateHero( showNewHero( showTalentDescription ) );
            observer.activateButtonNewHero();
        }

        if ( !isAddLevelButtonActivated )
            observer.activateNewLevelButton();
    }


    @Override
    public void setRace(String race) {
        if ( heroProgress != null ) {
            if ( race.equals( heroProgress.getHero().getRace().getName() ) ) {
                observer.activateNewLevelButton();
            }
        }
        chosenRace = RaceFactory.getInstance().createRace( RaceType.getRaceEnumByName( race ) );
    }

    @Override
    public void setProfession(String profession) {
        if ( heroProgress != null ) {
            //jeżeli rasa nie została zmieniona to możemy działać
            if ( chosenRace.getName().equals( heroProgress.getHero().getRace().getName() ) ) {
                int test = heroProgress.checkProfessionHistory( profession );
                //postać wcześniej nie rozwijała danej profesji, można włączyc opcję nowa profesja
                if ( test == -1 ) {
                    observer.activateNewProfessionButton();
                }else {
                    observer.activateButtonNewHero();
                }
            }
            else {
                JOptionPane.showMessageDialog( null, "Zmieniłeś rasę, nie możesz modyfikować postaci do momentu gdy wybór rasy będzie zgodny z aktulanie towrzoną postacią." );
                observer.activateButtonNewHero();
                observer.deactivateNewLevelButton();
            }
        }
        chosenProfession = profession;
    }

    @Override
    public void showHeroTalents(boolean showTalentDescription) {
        if ( heroProgress != null ) {
            observer.updateHero( showNewHero( showTalentDescription ) );
        }
    }

    @Override
    public void saveHeroToList() {
        observer.updateListModelWithNewHero( heroProgress.getHero()
                                                   .toBuilder()
                                                   .build() );
    }

    @Override
    public void exportDoPdf(Hero hero) {
        if ( urlSavaPdf == null ) {
            JFileChooser dialogFolder = new JFileChooser();
            dialogFolder.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
            int returnVal = dialogFolder.showOpenDialog( null );
            if ( returnVal == JFileChooser.APPROVE_OPTION ) {
                urlSavaPdf = dialogFolder.getSelectedFile().getAbsolutePath() + "\\";
            }
        }

        Thread thread = new Thread( () -> {
            try {
                new ExportToPdf( hero, urlSavaPdf );

            } catch (IOException e) {
                e.printStackTrace();
            }
        } );
        thread.start();
    }

    @Override
    public void exportDoExcel(Object[] heroesAndNPC, int index) {
        ExportDoExcela exp = new ExportDoExcela();
        if ( index == 0 ) {
            for (Object obiekt : heroesAndNPC) {
                if ( obiekt instanceof Hero ) {
                    Hero nBohater = (( Hero ) obiekt).toBuilder().build();
                    exp.createBohaterSheet( nBohater );
                }
                if ( obiekt instanceof Potwory ) {
                    Potwory nPotwor = new Potwory( ( Potwory ) obiekt );
                    exp.createNPCSheet( nPotwor );
                }
            }
        }
        else {
            if ( heroesAndNPC[index] instanceof Hero ) {
                Hero nBohater = (( Hero ) heroesAndNPC[index]).toBuilder().build();
                exp.createBohaterSheet( nBohater );
            }
            if ( heroesAndNPC[index] instanceof Potwory ) {
                Potwory nPotwor = new Potwory( ( Potwory ) heroesAndNPC[index] );
                exp.createNPCSheet( nPotwor );
            }
        }
        exp.saveWorkBook();
    }

}
