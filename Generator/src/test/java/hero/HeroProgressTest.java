package hero;

import commons.Hero;
import commons.Profession;
import commons.Skill;
import enums.Gender;
import enums.RaceType;
import factories.HeroFactory;
import factories.ProfessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HeroProgressTest {

    public static final String GLADIATOR_GLADIATORKA = "GLADIATOR/GLADIATORKA";
    HeroProgress progress;
    Hero hero;

    @BeforeEach
    void setUp() {
        hero = HeroFactory.getInstance()
                .create( RaceType.HUMAN, GLADIATOR_GLADIATORKA, Gender.FEMALE);
        progress = new HeroProgress( hero );
    }

    @Test
    void newProfession() {
    }

    @Test
    void finishProfession() {
    }

    @Test
    void setSkillsLevelOnNewProfessionLevel() {
    }

    @Test
    void increaseRandomSkillWithMinLevel() {
    }

    @Test
    void setStatsDueToNewLevel() {
    }

    @Test
    void checkProfessionHistory() {
    }

    @Test
    void experienceLevel() {
    }

    @Test
    void randomBonus() {
    }

    @Test
    void increaseRandomSkill() {
    }

    @DisplayName( "set Profession skills - same profession" )
    @Test
    void setProfessionSkills() {
        Profession levelTwo = ProfessionFactory
                .getInstance()
                .create( GLADIATOR_GLADIATORKA, 2 );

        progress.newProfession( levelTwo );
        progress.setProfessionSkills();

        long count = hero.getKnownSkills()
                .stream()
                .filter( Skill::isProfession )
                .count();

        assertEquals( 14, count );
    }

    @DisplayName( "set Profession skills - new profession on level 1" )
    @Test
    void setProfessionSkillsForNew() {
        Profession thiefLevelOne = ProfessionFactory.getInstance().create( "ZŁODZIEJ/ZŁODZIEJKA", 1 );


        progress.newProfession( thiefLevelOne );
        progress.setProfessionSkills();

        long count = hero.getKnownSkills()
                .stream()
                .filter( Skill::isProfession )
                .count();

        assertEquals( 8, count );
    }
}