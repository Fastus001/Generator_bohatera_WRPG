package domain;

import enums.RaceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatsTest {
    private static final int INDEX = 5;
    private static final int INCREASE_VALUE = 15;
    private static int BONUS_S;
    private static int BONUS_T;
    private static int BONUS_WP;

    Stats stats;
    int [] baseStats = {20,20,20,20,20,20,20,20,20,20};

    @BeforeEach
    void setUp() {
        stats = new Stats( RaceType.HUMAN );
        BONUS_S = stats.getStatAt( 2 ) / 10;
        BONUS_T = stats.getStatAt( 3 ) / 10;
        BONUS_WP = stats.getStatAt( 8 ) / 10;
    }

    @Test
    void updateHp() {
        int correctHP = BONUS_S + BONUS_WP + 2 * BONUS_T;

        stats.updateHp( 0 );

        assertEquals( correctHP, stats.getHp());
    }

    @Test
    void updateHpWithHard() {
        int hardy = 1;
        int correctHP = BONUS_S + BONUS_WP + 2 * BONUS_T + hardy*BONUS_T;

        stats.updateHp( 1 );

        assertEquals( correctHP, stats.getHp());
    }

    @Test
    void updateHpWithHardAndHalfling() {
        Stats halflingStats = new Stats( RaceType.HALFLING );
        int bonusT = halflingStats.getStatAt( 3 ) / 10;
        int bonusWP = halflingStats.getStatAt( 8 ) / 10;
        int hardy = 1;
        int correctHP = bonusWP + 2 * bonusT+ hardy*bonusT;

        halflingStats.updateHp( 1 );

        assertEquals( correctHP, halflingStats.getHp());
    }

    @Test
    void increaseStatAt() {
        int statBefore = stats.getStatAt( INDEX );

        stats.increaseStatAt( INCREASE_VALUE,INDEX,true );

        assertEquals(statBefore+INCREASE_VALUE, stats.getStatAt( INDEX ));
    }

    @Test
    void addOneToSpeed() {
        stats.addOneToSpeed();

        assertEquals( 5, stats.getSpeed() );
    }

    @Test
    void getStatAt() {
        int statAt = stats.getStatAt( INDEX );

        assertEquals( statAt,stats.getStatAt( INDEX ) );
    }

    @Test
    void getAdvancesAt() {
        stats.increaseStatAt( INCREASE_VALUE,INDEX,true );

        assertEquals( INCREASE_VALUE,  stats.getAdvancesAt( INDEX ));
    }

    @Test
    void getAdvancesAtNotIncludeAdvances() {
        stats.increaseStatAt( INCREASE_VALUE,INDEX,false );

        assertEquals( 0,  stats.getAdvancesAt( INDEX ));
    }
}