package commons;

import enums.RaceType;
import lombok.Getter;
import utilities.DiceRoll;

@Getter
public class Stats{
	private static final String[] STATS_NAME = {"WW", "US", "S", "Wt", "I", "Zw", "Zr", "Int", "SW", "Ogd"};
	private static final int NO_OF_STATS = 10;

	private final int [] stats;
	private final int [] advances;
	private final RaceType race;
	private int hp;
	private int speed;

	public Stats(RaceType race){
		this.race = race;
		int [] baseStats = race.getBaseStats();
		stats = new int[NO_OF_STATS];
		advances = new int[NO_OF_STATS];

		for (int i = 0; i < NO_OF_STATS; i++){
				stats[i] = baseStats[i]+ DiceRoll.one( 10, 2);
				advances[i] = 0;
		}
		updateHp(0);
		setSpeedByRace();
	}

	public Stats(Stats stats) {
		this.stats = new int[NO_OF_STATS];
		this.advances = new int[NO_OF_STATS];
		for(int i = 0; i  < NO_OF_STATS; i++) {
			this.stats[i] = stats.stats[i];
			this.advances[i] = stats.advances[i];
		}
		this.race = stats.race;
		this.hp = stats.hp;
		this.speed = stats.speed;
	}

	public void updateHp(int hardy){
		int bonusT = stats[3] / 10;
		int bonusWP = stats[8] / 10;
		int bonusS = stats[2] / 10;

		hp = bonusS +2* (bonusT) + bonusWP;

		if(race.getName().equals( "Niziołki")) {
			hp = 2*(bonusT)+ bonusWP;
		}
		if(hardy>0) {
				hp += hardy* bonusT;
		}
	}

	public void increaseStatAt(int value, int index, boolean includeToAdvances) {
		stats[index] +=value;
		if(includeToAdvances){
			advances[index] +=value;
		}
	}

	public void addOneToSpeed(){
			speed +=1;
		}

	private void setSpeedByRace() {
		speed = race.getSpeed();
	}

	public int getStatAt(int index){
		return stats[index];
	}

	public int getAdvancesAt(int index){
			return advances[index];
		}

	public String showStats(int [] array){
		StringBuilder text = new StringBuilder();
		StringBuilder statsName;
		for (int i = 0; i < NO_OF_STATS; i++){
			statsName = new StringBuilder( STATS_NAME[i] );
			for (int j : array) {
				if ( i == j ) {
					statsName.append( "*" );
				}
			}
			text.append( statsName )
					.append( ": " )
					.append( stats[i] )
					.append( " (+" )
					.append( advances[i] )
					.append( ") " );
		}
		text.append( " \nPunkty życia: " )
				.append( hp )
				.append( "\nSzybkość: " )
				.append( speed )
				.append( "\n" );
		return text.toString();
	}
}
	
	
	


