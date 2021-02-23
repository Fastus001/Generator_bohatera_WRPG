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

        assertEquals( "Krasnoludy",race.getName() );
    }

    @Test
    void createRaceHuman() {
        Race race = factory.createRace( RaceType.HUMAN );

        assertEquals( "Ludzie",race.getName() );
    }

    @Test
    void createRaceHalfling() {
        Race race = factory.createRace( RaceType.HALFLING );

       assertEquals( "Niziołki",race.getName() );
    }

    @Test
    void createRaceHighElf() {
        Race race = factory.createRace( RaceType.HIGH_ELF );

        assertEquals( "Wysokie elfy",race.getName() );
    }

    @Test
    void createRaceWoodElf() {
        Race race = factory.createRace( RaceType.WOOD_ELF );

        assertEquals( "Leśne elfy",race.getName() );
    }
}