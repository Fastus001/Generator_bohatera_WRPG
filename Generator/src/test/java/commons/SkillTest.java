package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

;

class SkillTest {
    Skill skill;

    @BeforeEach
    void setUp() {
        skill = Skill.builder().name( "Test" )
                .statNumber( 2 )
                .level( 0 )
                .type( "zaawansowana" )
                .isProfessional( false ).build();
    }

    @Test
    void addToSkillLevel() {
        skill.addToSkillLevel( 5 );

        assertEquals(5, skill.getLevel());
    }
}