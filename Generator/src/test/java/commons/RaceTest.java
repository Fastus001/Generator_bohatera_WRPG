package commons;

import enums.RaceType;
import factories.RaceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RaceTest {
    Race race;

    @BeforeEach
    void setUp() {
        race = RaceFactory.getInstance().createRace(RaceType.HALFLING);
    }

    @Test
    void getRaceEnum() {
        RaceType raceEnum = race.getRaceEnum();

        assertEquals( RaceType.HALFLING, raceEnum );
    }

    @Test
    void getSkillsFromRace() {
        Set<Skill> skillsFromRace = race.getSkillsFromRace();
        Integer skillLevelSum = skillsFromRace.stream()
                .map( Skill::getLevel )
                .reduce( Integer::sum )
                .orElse( -1 );

        assertEquals( 6, skillsFromRace.size() );
        assertEquals( 24, skillLevelSum );
    }

    @Test
    void addKnownTalentsFromRaceHalfling() {
        Set<Talent> talents = race.addKnownTalentsFromRace( RaceType.HALFLING );
        long sumOfLevels = talents.stream()
                .mapToInt( Talent::getLevel )
                .count();

        assertEquals( 6, sumOfLevels );
    }

    @Test
    void addKnownTalentsFromRaceHuman() {
        Race human = RaceFactory.getInstance().createRace( RaceType.HUMAN );
        List<Talent> availableTalents = human.getTalents();
        Talent savvy = availableTalents.get( 0 );
        Talent charismatic = availableTalents.get( 1 );


        Set<Talent> talents = human.addKnownTalentsFromRace( RaceType.HUMAN );

        assertEquals( 5, talents.size() );
        assertTrue( talents.contains( savvy ) || talents.contains( charismatic ) );
    }
}