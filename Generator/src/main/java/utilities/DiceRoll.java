package utilities;

import java.util.Random;

/**
 * @author Tom
 */
public class DiceRoll {
	private static final Random RAND = new Random();

	public static int one(int diceSize){
		return RAND.nextInt(diceSize)+1;
	}
	
	public static int one(int diceSize, int numberOfDices){
		int a = 0;
		for(int i = 0; i < numberOfDices; i++){
			a += one( diceSize );
		}
		return a;
	}

	public static int randomRace() {
		int roll = RAND.nextInt(100)+1;
		if(roll <91){
			return 0;
		}else if( roll <95 ){
			return 2;
		}else if( roll <99 ){
			return 1;
		}else if(roll==99){
			return 3;
		}else{
			return 4;
		}
	}
}
