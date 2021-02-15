package commons;

import npcGenerator.Cechy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TalentTest {
    Talent talent;
    int [] stats = {20,20,20,20,20,20,20,20,20,20};

    @BeforeEach
    void setUp() {
        talent = Talent.builder()
                .name( "sample" )
                .test( "test" )
                .relatedStat( 13 )
                .description( "description" ).build();
    }

    @Test
    void showTalentNameWithLevel() {
        talent.setLevel( 2 );

        String s = talent.showTalentNameWithLevel();

        assertEquals( "sample x2",s );
    }

    @Test
    void setTalentMax() {
        talent.setTalentMax( new Cechy( stats,"Nizio�ki" ) );

        assertEquals( 4, talent.getMaxLevel() );
    }

    @Test
    void setTalentMaxRelatedStatTen() {
        Talent talent1 = Talent.builder().relatedStat( 10 ).build();

        talent1.setTalentMax( new Cechy( stats,"Nizio�ki" ) );

        assertEquals( 10, talent1.getMaxLevel() );
    }

    @Test
    void setTalentMaxRelatedStatBelowTen() {
        Talent talent1 = Talent.builder().relatedStat( 9 ).build();
        Cechy cechy = new Cechy( stats, "Nizio�ki" );

        talent1.setTalentMax( cechy );
        int max = cechy.getCecha( 9 ) / 10;


        assertEquals( max, talent1.getMaxLevel() );
    }

    @Test
    void addOneToLevel() {
        talent.addOneToLevel();

        assertEquals( 2,talent.getLevel() );
    }

    @Test
    void showAll() {
        String s = talent.showAll();

        assertEquals( "sample Max: 4 Test: test\n" +
                              "Opis:\n" +
                              "description\n",s );
    }
}