package commons;

import enums.RaceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RaceTest {
    Race race;

    @BeforeEach
    void setUp() {
        race = Race.builder().name( "Niziołki" ).build();
    }

    @Test
    void getRaceEnum() {
        RaceType raceEnum = race.getRaceEnum();

        assertEquals( RaceType.HALFLING, raceEnum );
    }
}