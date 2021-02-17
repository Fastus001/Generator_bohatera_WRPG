package utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomTalentTest {
    RandomTalent randomTalent;

    @Test
    void getInstance() {
        randomTalent = RandomTalent.getInstance();

        assertNotNull( randomTalent );
    }
}