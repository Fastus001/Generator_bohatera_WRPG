/**
 * 
 */
package commons;

/**
 * @author Tom
 * rzut K10 w przypadku cech
 */
public interface RzutKoscia {
	public static int rzutK(int x){
		return (int)(Math.random()*x)+1;
	}
	
	//podanie typu ko�ci i ilo�ci rzut�w
	public static int rzutK(int x, int y){
		int a = 0;
		for(int i = 0; i < y; i++){
			a += (int)(Math.random()*x)+1;
		}
		return a;
	}
}
