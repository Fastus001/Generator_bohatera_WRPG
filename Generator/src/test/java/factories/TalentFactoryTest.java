package factories;

import domain.Talent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TalentFactoryTest {
    TalentFactory talentFactory;

    @BeforeEach
    void setUp() {
        talentFactory = TalentFactory.getInstance();
    }

    @Test
    void getInstance() {
        TalentFactory newInstance = TalentFactory.getInstance();

        assertEquals( talentFactory,newInstance );
    }

    @Test
    void createTalent() {
        Talent createdTalent = talentFactory.createTalent( "Wytwórca" );

        assertAll( ()->assertEquals( "Wytwórca",createdTalent.getName() ),
                   ()->assertEquals( 6,createdTalent.getRelatedStat()),
                   ()->assertEquals( "Rzemiosło (Dowolne)",createdTalent.getTest()));
    }
}