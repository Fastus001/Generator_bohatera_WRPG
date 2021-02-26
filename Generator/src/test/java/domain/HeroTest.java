package domain;

import enums.Gender;
import enums.RaceType;
import factories.HeroFactory;
import factories.SkillFactory;
import factories.TalentFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {
    Hero hero;

    @BeforeEach
    void setUp() {
        hero = HeroFactory.getInstance()
                .create( RaceType.HIGH_ELF, "SZARLATAN/SZARLATANKA", Gender.MALE);
    }

    @Test
    void addSkill() {
        Skill skillToAdd = SkillFactory.getInstance().createSkill( "Mocna Głowa" );
        skillToAdd.setLevel( 5 );
        Skill strongHead = hero.getKnownSkills()
                .stream()
                .filter( skill -> skill.equals( skillToAdd ) )
                .findFirst().orElseThrow();

        int level = strongHead.getLevel();

        hero.addSkill( skillToAdd );

        assertEquals( level+5, strongHead.getLevel() );
    }

    @Test
    void addTalent() {
        Talent talent = TalentFactory.getInstance().createTalent( "Słuch Absolutny" );
        Skill skill = SkillFactory.getInstance().createSkill( "Występy (Śpiewanie)" );

        hero.addTalent( talent );

        assertAll( ()->assertTrue( hero.getKnownTalents().contains( talent ) ),
                   ()->assertTrue( hero.getKnownSkills().contains( skill ) ));
    }

    @Test
    void addTalentFromProfession() {
        Talent talentA = TalentFactory.getInstance().createTalent( "Etykieta (Dowolna Grupa)" );
        Talent talentB = TalentFactory.getInstance().createTalent( "Szczęście" );
        Talent talentC = TalentFactory.getInstance().createTalent( "Szuler" );
        Talent talentD = TalentFactory.getInstance().createTalent( "Szuler Kościany" );

        hero.addTalentFromProfession();

        Set<Talent> knownTalents = hero.getKnownTalents();

        assertTrue( knownTalents.contains(talentA) || knownTalents.contains(talentB) ||
                            knownTalents.contains(talentC) || knownTalents.contains(talentD));
    }

    @Test
    void findTalent() {
        Talent talent = hero.getKnownTalents()
                .stream()
                .findFirst()
                .orElseThrow();

        assertEquals( talent,hero.findTalent(talent) );
    }

    @Test
    void getHardyLevel() {
        assertEquals( 0,hero.getHardyLevel() );
    }

    @Test
    void getCurrentProfessionName() {
        String currentProfessionName = hero.getCurrentProfessionName();

        assertEquals( "SZARLATAN\nKanciarz – Brąz 3",currentProfessionName );
    }

    @Test
    void professionPathName() {
        String pathName = hero.professionPathName();

        assertEquals( "Kanciarz",pathName );
    }

    @Test
    void professionStatusName() {
        String statusName = hero.professionStatusName();

        assertEquals( "Brąz 3",statusName );
    }
}