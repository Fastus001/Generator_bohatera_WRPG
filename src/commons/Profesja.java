package commons;
import java.util.*;

public class Profesja implements Comparable<Profesja>{
	private String nazwa;
	private String sciezkaProfesji;
	private int poziom;
	public ArrayList<Umiejetnosc> dostepneUmiejetnosci;
	public ArrayList<Talent> dostepneTalenty;
	public String [] dostepnaRasa;
	private int [] cechyRozwoju;
	
	//utworzenie obiektu profesja 
	public Profesja(String n,  String sP, int p, ArrayList<Umiejetnosc> dU, ArrayList<Talent> dT, String[] dR, int[] cR){
		nazwa = new String(n);
		sciezkaProfesji = new String(sP);
		poziom = p;
		//TODO
		dostepneUmiejetnosci = dU;
		dostepneTalenty = dT;
		dostepnaRasa = dR;
		cechyRozwoju = cR;
	}
	
	public Profesja(Profesja pr) {
		nazwa = pr.nazwa;
		sciezkaProfesji = pr.sciezkaProfesji;
		poziom = pr.poziom;
		dostepneUmiejetnosci = new ArrayList<Umiejetnosc>();
		for(Umiejetnosc temp : pr.dostepneUmiejetnosci) {
			Umiejetnosc nowa = new Umiejetnosc(temp);
			dostepneUmiejetnosci.add(nowa);
		}
		dostepneTalenty = new ArrayList<Talent>();
		for(Talent tempT : pr.dostepneTalenty){
			Talent nowyTalent = new Talent(tempT);
			dostepneTalenty.add(nowyTalent);
		}
		dostepnaRasa = pr.dostepnaRasa;
		cechyRozwoju = pr.cechyRozwoju;
	}
	
	
	public String toString() {
		return nazwa;
	}
	
	//wyswietlenie klasy w postaci skompresowanego stringa
	public String wyswietlProfesje(){
		String tekst = "Nazwa profesji: "+ nazwa + " Œcie¿ka profesji: " + sciezkaProfesji + " Poziom: " + Integer.toString(poziom) + "\nDostêpne Umiejêtnoœci: ";
		
		for(Umiejetnosc s: dostepneUmiejetnosci){
			tekst +=s.wyswietlWszystko() +",";
		}
		tekst +="\nDostêpne talenty: ";
		for(Talent x: dostepneTalenty){
			tekst +=x.wyswietlWszystkoTalent() +",";
		}
		tekst += "\nDozwolone rasy: ";
		for(String r:dostepnaRasa){
			tekst += r + ",";
		}
		tekst += "\nKlasowe cechy: ";
		for(int i:cechyRozwoju){
			tekst += Integer.toString(i) + ",";
		}
		
		return tekst;
	}
	
	public int compareTo(Profesja pr){
		return nazwa.compareTo(pr.toString());
	}
	
	
	public int getPoziom(){
		return poziom;
	}
	
	public String getPoziomUmiejetnosciString(){
		return Integer.toString(poziom);
	}
	
	public String getNameProfesjaSciezka(){
		return nazwa + "\n" +sciezkaProfesji;
	}
	
	public ArrayList<Umiejetnosc> getDostepneUmiejetnosciLista(){
		return dostepneUmiejetnosci;
	}
	
	public void addUmiejetnoscDoDostepneUmiejetnosci(Umiejetnosc um){
		dostepneUmiejetnosci.add(um);
	} 
	
	public int getLiczbaDostepnychRas(){
		return dostepnaRasa.length;
	}
	
	public String getName(){
		return nazwa + " " + sciezkaProfesji;
	}
	
	public Talent getLosowyTalent(int x) {
		return dostepneTalenty.get(x);
	}
	//losowy z cech profesyjnych
	public int getLosowyAtrybutCechy() {
		int losowaCecha = (int) (Math.random()*cechyRozwoju.length);
		return cechyRozwoju[losowaCecha];
	}
	
	public int getSizeOfDostepneTalenty() {
		return dostepneTalenty.size();
	}
	
	public int [] getTablicaCechyRozwoju() {
		return cechyRozwoju;
	}
	
	public int getSizeDostepneUm() {
		return dostepneUmiejetnosci.size();
	}
	
	public void zmienOpisSciezki(String tekst) {
		sciezkaProfesji +=  " (" + tekst + ")";
	}
	
	public int[] getCechyRozwoju(){
		return cechyRozwoju;
	}
	
			
	
}
