package hero;

import domain.Hero;
import domain.Profession;
import domain.Skill;
import enums.Gender;
import enums.RaceType;
import factories.HeroFactory;
import factories.ProfessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroProgressTest {

    public static final String GLADIATOR_GLADIATORKA = "GLADIATOR/GLADIATORKA";
    public static final int MIN_LEVEL_ONE = 5;
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
        progress.newProfession( ProfessionFactory
                                        .getInstance()
                                        .create(GLADIATOR_GLADIATORKA,2) );

        long numberOfProfSkills = hero.getKnownSkills().stream()
                .filter( Skill::isProfession )
                .count();

        assertAll( ()->assertEquals( 2,hero.getProfession().getLevel() ),
                   ()->assertEquals( 14,numberOfProfSkills ),
                   ()->assertEquals( "Gladiatorka – Srebro 2",
                                     hero.getProfession().getProfessionPath(Gender.FEMALE) ));
    }

    @Test
    void finishProfession() {

        progress.finishProfession( 2 );

        int[] professionStats = hero.getProfession().getProfessionStats();
        long numberOfSkills = hero.getKnownSkills()
                .stream()
                .filter( Skill::isProfession )
                .filter( skill -> skill.getLevel() >= MIN_LEVEL_ONE )
                .count();

        assertEquals( 8,numberOfSkills );
        assertAll( ()->assertTrue( hero.getStats().getStatAt( professionStats[0] )>=MIN_LEVEL_ONE ),
                   ()->assertTrue( hero.getStats().getStatAt( professionStats[1] )>=MIN_LEVEL_ONE ),
                   ()->assertTrue( hero.getStats().getStatAt( professionStats[2] )>=MIN_LEVEL_ONE ));
    }

    @Test
    void checkProfessionHistory() {
        progress.newProfession( ProfessionFactory.getInstance()
                                        .create("SZARLATAN/SZARLATANKA",1) );
        progress.newProfession( ProfessionFactory.getInstance().create("SZARLATAN/SZARLATANKA",2) );

        int level = progress.checkProfessionHistory(
                ProfessionFactory.getInstance().create( GLADIATOR_GLADIATORKA, 2 ) );

        assertEquals( 1,level );
    }

    @DisplayName( "set Profession skills - same profession" )
    @Test
    void setProfessionSkills() {
        Profession levelTwo = ProfessionFactory
                .getInstance()
                .create( GLADIATOR_GLADIATORKA, 2 );

        progress.newProfession( levelTwo );
//        progress.setProfessionSkills();

        long count = hero.getKnownSkills()
                .stream()
                .filter( Skill::isProfession )
                .count();

        assertEquals( 14, count );
    }

    @DisplayName( "set Profession skills - new profession on level 1" )
    @Test
    void setProfessionSkillsForNew() {
        Profession thief = ProfessionFactory.getInstance().create( "ZŁODZIEJ/ZŁODZIEJKA", 1 );


        progress.newProfession( thief );
//        progress.setProfessionSkills();

        long count = hero.getKnownSkills()
                .stream()
                .filter( Skill::isProfession )
                .count();

        assertEquals( 8, count );
    }
}