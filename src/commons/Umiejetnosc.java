package commons;

public class Umiejetnosc  implements Comparable<Umiejetnosc>{
	 
	String nazwa;
	int tcecha; // numer cechy g��wnej, kt�ra jest testowana
	String typ;  //podstawowa (false) lub zawansowana (true)
	int poz;
	
	static String[] cechyNazwa = {"WW", "US", "S", "Wt", "I", "Zw", "Zr", "Int", "SW", "Ogd"};
	
	
	
	public Umiejetnosc(String a, int b,  String t, int p){
		
		nazwa = a;
		tcecha = b;
		typ = t;
		poz = p;
	}
	
	public Umiejetnosc(Umiejetnosc um) {
		nazwa = new String(um.nazwa);
		tcecha = um.tcecha;
		typ = new String(um.typ);
		poz = um.poz;
	}
	
	
	//przekazuje jako string wszystkie pozycje z klasy (opr�cz poziomu);
	public String wyswietlWszystko(){
		
		return nazwa + " ("+ cechyNazwa[tcecha] + ") " + 	typ;
	}
	
	public void setPoziom(int x) {
		poz = x;
	}
	
	public void addPoziom(int x) {
		poz +=x;
	}
	
	public String toString(){
		return nazwa +" +"+ Integer.toString(poz);
	}
	
	public String getName(){
		return nazwa;
	}
	
	public int getPoziom(){
		return poz;
	}
	
	public int getPozycjaCechy() {
		return tcecha;
	}
	
	public String getTyp() {
		return typ;
	}
	
	public int compareTo(Umiejetnosc um){
		return nazwa.compareTo(um.getName());
	}
	
}

