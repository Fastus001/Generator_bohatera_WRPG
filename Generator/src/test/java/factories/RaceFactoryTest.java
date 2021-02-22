package factories;

import commons.Race;
import enums.RaceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RaceFactoryTest {
    RaceFactory factory;

    @BeforeEach
    void setUp() {
        factory = RaceFactory.getInstance();
    }

    @Test
    void getInstance() {
        RaceFactory newInstance = RaceFactory.getInstance();

        assertEquals( factory,newInstance );
    }

    @Test
    void createRaceDwarf() {
        Race race = factory.createRace( RaceType.DWARF );

        assertAll( ()->assertEquals( "Krasnoludy",race.getName() ),
                   ()->assertEquals( 0,race.getAvailableRandomTalents() ));
    }

    @Test
    void createRaceHuman() {
        Race race = factory.createRace( RaceType.HUMAN );

        assertAll( ()->assertEquals( "Ludzie",race.getName() ),
                   ()->assertEquals( 4,race.getAvailableRandomTalents() ));
    }

    @Test
    void createRaceHalfling() {
        Race race = factory.createRace( RaceType.HALFLING );

        assertAll( ()->assertEquals( "Niziołki",race.getName() ),
                   ()->assertEquals( 2,race.getAvailableRandomTalents() ));
    }

    @Test
    void createRaceHighElf() {
        Race race = factory.createRace( RaceType.HIGH_ELF );

        assertAll( ()->assertEquals( "Wysokie elfy",race.getName() ),
                   ()->assertEquals( 0,race.getAvailableRandomTalents() ));
    }

    @Test
    void createRaceWoodElf() {
        Race race = factory.createRace( RaceType.WOOD_ELF );

        assertAll( ()->assertEquals( "Leśne elfy",race.getName() ),
                   ()->assertEquals( 0,race.getAvailableRandomTalents() ));
    }
}