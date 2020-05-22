package commons;

public class Talent implements Comparable<Talent>{
	
	static String[] CECHYNAZWA = {"WW", "US", "S", "Wt", "I", "Zw", "Zr", "Int", "SW", "Ogd","Brak", "1", "2","4"};
	private String nazwa;
	//maksymalny bonus jaki mo¿e mieæ dany talent, obliczenie tego bêdzie dopiero w klasie Bohater. A tutaj jest tylko podany numer cechy z której jest obliczny bonus (czyli maksimum)
	private int max;
	private String test;
	private String opis;
	private int maksimum;
	private int poziom;
	private boolean pokaz;
	
	public Talent(String n, int m, String t, String op){
		nazwa = n;
		max = m;
		test = t;
		opis = op;
		maksimum = 0;
		poziom = 1;
		pokaz =  true;
	}
	
	
	public Talent(String n, int m, String t){
		nazwa = n;
		max = m;
		test = t;
		opis = "";
		maksimum = 0;
		poziom = 1;
		pokaz = true;
	}
	
	public Talent(Talent talent) {
		nazwa = talent.nazwa;
		max = talent.max;
		test = talent.test;
		opis = talent.opis;
		maksimum = talent.maksimum;
		poziom = talent.poziom;
		pokaz = talent.pokaz;
	}
	
	public String wyswietlWszystkoTalent(){
		return nazwa + " Max: "+CECHYNAZWA[max] + " Test: "+ test + "\nOpis:\n" + opis +"\n";
	}
	
	public String nazwaTalentu(){
		return nazwa;
	}
	
	public String getName() {
		//opcja z wyswietlenie nazwy, maksa oraz poziomu talentu (testowe)
		//return nazwa + " Max: " + Integer.toString(maksimum) + "  Poziom talentu:" + Integer.toString(poziom);
		if(poziom>1){
			return nazwa + " x" + Integer.toString(poziom);
		}else{
			return nazwa;
		}
			
	}
	
	public String toString() {
		return nazwa;
	}
	
	public void setTalentMax(Cechy cechyPostaci) {
			
		int maxt = max;
		if(maxt <10){
			maxt = 1;
		}
		switch(maxt){
			case  1:
				maksimum = cechyPostaci.getCecha(max) / 10;
				break;
			case 10:
				maksimum = 10;
				break;
			case 11:
				maksimum = 1;
				break;
			case 12:
				maksimum = 2;
				break;
			case 13:
				maksimum = 4;
				break;
				
		}//koniec switch
	}
	
	public int getMaksimumValue() {
		return maksimum;
	}
	
	public int getPoziomValue(){
		return poziom;
	}
	
	public String getTest() {
		return test;
	}
	
	public void addPoziom(){
		poziom +=1;
	}
	
	public String getOpisString(){
		return opis;
	}
	
	public void setOpis(String op) {
		opis = op;
	}
	
	public void niePokazujOpisu(){
		pokaz = false;
	}
	
	public boolean getOpcjeWyswietlania(){
		return pokaz;
	}
	
	public int compareTo(Talent tl){
		return nazwa.compareTo(tl.toString());
	}
	
}
