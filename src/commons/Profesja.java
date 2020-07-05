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
	private boolean czyUkonczona;
	// nowe dodane do Profesjiv2
	private String klasa;
	private String [] przedmiotyZProfesji;
	
	//utworzenie obiektu profesja 
	
	public Profesja() {
		nazwa = "brak";
		sciezkaProfesji = "brak";
		poziom = 0;
		czyUkonczona = false;
	}
	/**
	 * 
	 * @param n - nazwa Profesji
	 * @param sP - nazwa �cie�ki profesji
	 * @param p - poziom
	 * @param dU - dost�pne umiejetno�ci (z profesji)
	 * @param dT - dost�pne talenty (z profesji)
	 * @param dR - dost�pna rasa
	 * @param cR - cechy rozwoju (statysyki)
	 * @param koniec - czy dana profesja zosta�a uko�czona (gdy Bohater jest ju� utworzony)
	 * @param k - klasa Profesji
	 * @param pP - przedmioty z Profesji
	 */
	public Profesja(String n,  String sP, int p, ArrayList<Umiejetnosc> dU, ArrayList<Talent> dT, String[] dR, int[] cR, boolean koniec, String k, String [] pP){
		nazwa = new String(n);
		sciezkaProfesji = new String(sP);
		poziom = p;
		dostepneUmiejetnosci = dU;
		dostepneTalenty = dT;
		dostepnaRasa = dR;
		cechyRozwoju = cR;
		czyUkonczona = koniec;
		klasa = k;
		przedmiotyZProfesji = pP;
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
		this.czyUkonczona = pr.czyUkonczona;
		this.klasa = pr.klasa;
		this.przedmiotyZProfesji = pr.przedmiotyZProfesji;
	}
	
	


	
	/**
	 * Wy�wietla nazw� profesji
	 */
	public String toString() {
		return nazwa;
	}
	
	//wyswietlenie klasy w postaci skompresowanego stringa
	public String wyswietlProfesje(){
		String tekst = "Nazwa profesji: "+ nazwa + " �cie�ka profesji: " + sciezkaProfesji + " Poziom: " + Integer.toString(poziom) + "\nDost�pne Umiej�tno�ci: ";
		
		for(Umiejetnosc s: dostepneUmiejetnosci){
			tekst +=s.wyswietlWszystko() +",";
		}
		tekst +="\nDost�pne talenty: ";
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
	/**
	 * 
	 * @param plec - czy bohater to m�czycna TRUE, czy kobieta FALSE
	 * @return - zwraca strina z m�sk� lub �e�sk� wersj� nazw
	 */
	public String getNameProfesjaSciezka(boolean plec){
		String [] nazwaPr = nazwa.split("/");
		String [] sciezkaPr = sciezkaProfesji.split("/");
		if(plec) {
			return nazwaPr[0] + "\n" + sciezkaPr[0];
		}else {
			return nazwaPr[1] + "\n" + sciezkaPr[1];
		}
	}
	/**
	 * 
	 * @param plec
	 * @return nazwa
	 */
	public String getName(boolean plec){
		String [] nazwaPr = nazwa.split("/");
		if(plec) {
			return nazwaPr[0];
		}else {
			return nazwaPr[1];
		}
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
	/**
	 * @return the klasa
	 */
	public String getKlasa() {
		return klasa;
	}
	
	/**
	 * @return the sciezkaProfesji
	 */
	public String getSciezkaProfesji() {
		return sciezkaProfesji;
	}
	/**
	 * @param plec - p�e� bohatera
	 * @return the sciezkaProfesji
	 */
	public String getSciezkaProfesji(boolean plec) {
		String [] sciezkaPr = sciezkaProfesji.split("/");
		if(plec) {
			return sciezkaPr[0];
		}else {
			return sciezkaPr[1];
		}
	}
	/**
	 * @return the czyUkonczona
	 */
	public boolean isCzyUkonczona() {
		return czyUkonczona;
	}
	/**
	 * @param czyUkonczona the czyUkonczona to set
	 */
	public void setCzyUkonczona(boolean czyUkonczona) {
		this.czyUkonczona = czyUkonczona;
	}	
	
	public boolean czyJestCechaRozwoju(int x) {
		for(int i=0; i<this.cechyRozwoju.length; i++) {
			if(cechyRozwoju[i] == x)
				return true;
		}
		return false;
	}
	public String[] getPrzedmiotyZProfesji() {
		return przedmiotyZProfesji;
	}
	
}
