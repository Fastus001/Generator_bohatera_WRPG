package hero;

import commons.Hero;
import enums.Gender;
import enums.RaceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroFactoryTest {
    HeroFactory factory;

    @BeforeEach
    void setUp() {
        factory = HeroFactory.getInstance();
    }

    @Test
    void getInstance() {
        HeroFactory instance = HeroFactory.getInstance();

        assertEquals( factory, instance );
    }

    @Test
    void create() {
        Hero hero = factory.create( RaceType.HUMAN, "PRZEWOŹNIK/PRZEWOŹNICZKA", Gender.FEMALE );

        assertAll( ()->assertNotNull( hero ),
                   ()->assertEquals( "PRZEWOŹNIK/PRZEWOŹNICZKA",hero.getProfession().getName() ) );
    }

    @Test
    void randomBonus() {
    }
}