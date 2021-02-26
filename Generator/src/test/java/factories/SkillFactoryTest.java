package factories;

import domain.Skill;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillFactoryTest {
    SkillFactory factory;

    @BeforeEach
    void setUp() {
        factory = SkillFactory.getInstance();
    }

    @Test
    void getInstance() {
        SkillFactory instance2 = SkillFactory.getInstance();

        assertEquals( factory,instance2 );
    }

    @Test
    void createSkill() {
        Skill skill = factory.createSkill( "Zwinne Palce" );

        assertEquals( "Zwinne Palce",skill.getName() );
        assertEquals( 6,skill.getStatNumber() );
    }
}