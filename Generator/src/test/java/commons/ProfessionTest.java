package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProfessionTest {
    private static final int [] PROFESSION_STATS = {3,6,7};
    private static final String PROF_PATH_NAME_FEMALE = "Uczennica Aptekarza – Brąz 3";
    private static final String PROF_NAME_FEMALE = "APTEKARKA";
    Profession profession;


    @BeforeEach
    void setUp() {
        profession = Profession.builder()
                .name( "APTEKARZ/APTEKARKA" )
                .professionPath( "Uczeń Aptekarza – Brąz 3/Uczennica Aptekarza – Brąz 3" )
                .career( "UCZONY" )
                .professionStats( PROFESSION_STATS )
                .level( 1 )
                .build();
    }

    @Test
    void getName() {
        String name = profession.getName( false );

        assertEquals( PROF_NAME_FEMALE,name );
    }

    @Test
    void getProfessionPath() {
        String professionPath = profession.getProfessionPath( false );

        assertEquals( PROF_PATH_NAME_FEMALE,professionPath );
    }

    @Test
    void getNameAndProfessionPath() {
        String nameAndProfessionPath = profession.getNameAndProfessionPath( false );

        assertEquals( PROF_NAME_FEMALE+"\n"+PROF_PATH_NAME_FEMALE,
                      nameAndProfessionPath );
    }

    @Test
    void getRandomProfessionStat() {
        int random = profession.getRandomProfessionStat();

        assertTrue(random == 3 || random==6 || random ==7);
    }

    @Test
    void isProfessionStat() {
        assertFalse( profession.isProfessionStat( 2 ));
    }
}