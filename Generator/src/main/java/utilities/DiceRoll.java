package utilities;

/**
 * @author Tom
 */
public class DiceRoll {

	public static int one(int diceSize){
		return (int)(Math.random()*diceSize)+1;
	}
	
	public static int one(int diceSize, int numberOfDices){
		int a = 0;
		for(int i = 0; i < numberOfDices; i++){
			a += one( diceSize );
		}
		return a;
	}
}
