package factories;

import domain.Profession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfessionFactoryTest {
    private static final int LEVEL_ONE = 1;
    ProfessionFactory factory;

    @BeforeEach
    void setUp() {
        factory = ProfessionFactory.getInstance();
    }

    @Test
    void getInstance() {
        ProfessionFactory newFactory = ProfessionFactory.getInstance();

        assertEquals( factory, newFactory );
    }

    @Test
    void createFirstLevelMale() {
        Profession riverGuard = factory.create( "STRAŻNIK RZECZNY/STRAŻNICZKA RZECZNA",1 );

        assertAll( ()->assertEquals( "STRAŻNIK RZECZNY/STRAŻNICZKA RZECZNA",riverGuard.getName()),
                   ()->assertEquals( "WODNIACY", riverGuard.getCareer() ),
                   ()->assertEquals( 8,riverGuard.getSkills().size() ),
                   ()->assertEquals( 4,riverGuard.getTalents().size() ));
    }

    @Test
    void createAll() {
        List<Profession> allFistLevel = factory.createAll( );


        assertEquals( 256,allFistLevel.size() );
    }
}