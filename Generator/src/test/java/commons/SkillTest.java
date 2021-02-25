package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

;

class SkillTest {
    private static final String EXPECTED = "Test +0*";
    private static final String EXPECTED_ALL = "Test (S) zaawansowana";
    Skill skill;

    @BeforeEach
    void setUp() {
        skill = Skill.builder().name( "Test" )
                .statNumber( 2 )
                .level( 0 )
                .type( "zaawansowana" )
                .isProfession( true ).build();
    }

    @Test
    void addToSkillLevel() {
        skill.addToSkillLevel( 5 );

        assertEquals(5, skill.getLevel());
    }


    @Test
    void showSkill() {
        String result = skill.showSkill();

        assertEquals( EXPECTED,result );
    }

}