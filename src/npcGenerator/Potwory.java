package npcGenerator;

import java.util.ArrayList;

import commons.RzutKoscia;
/**
 * 
 * @author Tom
 *klasa opisujaca potwora NPC
 */
public class Potwory implements RzutKoscia{
	private static final String[] CECHYNAZWA = {"Sz","WW", "US", "S", "Wt", "I", "Zw", "Zr", "Int", "SW", "Ogd","�yw"};


	private static final int IC = 12; //ilo�� cech
	private String nazwa;
	private int [] statyPotwora;
	private ArrayList<CechyPotworow> cechy;
	private ArrayList<CechyPotworow> cechyOpcjonalne;
	private String opisStwora;
	
	
	/////////////////////////////////////////
	//konstruktory
	/*
	 * konstruktor
	 */
	public Potwory(String n, String [] staty, ArrayList<CechyPotworow> cP, ArrayList<CechyPotworow> cOp, String opis) {
		this.nazwa = n;
		this.statyPotwora = new int[IC];
		for(int i=0; i < IC; i++) 
		{
			if(i==0)
				this.statyPotwora[i] = Integer.parseInt(staty[i]);
			else if(staty[i].equals("-")) {
				staty[i] = "0";
			}else if(i<IC-1){
				//wprowadzenie losowo�ci do statystyk potwora
				this.statyPotwora[i] = (Integer.parseInt(staty[i]) -10)+RzutKoscia.rzutK(10, 2);
			}else {
				this.statyPotwora[i] = Integer.parseInt(staty[i]);
			}
			
		}
		cechy = new ArrayList<CechyPotworow>();
		for(CechyPotworow cechyP:cP) {
			cechy.add(cechyP);
		}
		cechyOpcjonalne = new ArrayList<CechyPotworow>();
		for(CechyPotworow cechyP: cOp) {
			cechyOpcjonalne.add(cechyP);
		}
		this.opisStwora = opis;
	}
	
	/*
	 * kontruktor kopiujący
	 */
	public Potwory(Potwory potwory) {
		this.nazwa = potwory.nazwa;
		this.statyPotwora = new int[IC];
		for(int i=0; i < IC; i++) 
		{
			this.statyPotwora[i] = potwory.statyPotwora[i];
		}
		cechy = new ArrayList<CechyPotworow>();
		for(CechyPotworow cechyP:potwory.cechy) {
			cechy.add(cechyP);
		}
		cechyOpcjonalne = new ArrayList<CechyPotworow>();
		for(CechyPotworow cechyP: potwory.cechyOpcjonalne) {
			cechyOpcjonalne.add(cechyP);
		}
		this.opisStwora = potwory.opisStwora;
	}
	
	/////////////////////////////////////////////////////////
	//getters
	/**
	 * @return the nazwa
	 */
	public String getNazwa() {
		return nazwa;
	}
	/**
	 * @return the statyPotwora
	 */
	public int [] getStatyPotwora() {
		return statyPotwora;
	}
	/**
	 * 
	 * @param x - enum, kt�ra pozycja cechy ma byc zwr�cona 
	 * @return - zwraca warto�� wybranej statystki w postaci warto�ci int
	 */
	public int getStatyPotwora(StatyNPC x) {
		return statyPotwora[x.ordinal()];
	}
	
	/**
	 * @return the cechy
	 */
	public ArrayList<CechyPotworow> getCechy() {
		return cechy;
	}
	/**
	 * @return the cechyOpcjonalne
	 */
	public ArrayList<CechyPotworow> getCechyOpcjonalne() {
		return cechyOpcjonalne;
	}
	/**
	 * @return the opisStwora
	 */
	public String getOpisStwora() {
		return opisStwora;
	}
	
	/**
	 * zwraca opis STATYSTYK potwora w postaci tablicy string�w!!!
	 * @return the cechynazwa
	 */
	public static String[] getCechynazwa() {
		return CECHYNAZWA;
	}
	/////////////////////////////////////////////////////////
	//setters
	
	
	/**
	 * @param statyPotwora the statyPotwora to set
	 */
	public void setStatyPotwora(int[] statyPotwora) {
		this.statyPotwora = statyPotwora;
	}
	/**
	 * @param statyPotwora the statyPotwora to set, @param ktora
	 */
	public void setStatyPotwora(int statyPotwora, int ktora) {
		this.statyPotwora[ktora] = statyPotwora;
	}
	
	/**
	 * @param cechy the cechy to set
	 */
	public void setCechy(CechyPotworow cechy) {
		this.cechy.add(cechy);
	}
	
	/**
	 * @param nazwa the nazwa to set
	 */
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	/**
	 * @param ile warto�� o ile ma by� podniesiona cecha, @param ktora - kt�ra cecha, @param plus - czy ma by� podniesiona czy obni�ona
	 */
	public void addRemoveStatyPotwora(int ile, StatyNPC ktora, boolean plus) {
		if(plus)
			this.statyPotwora[ktora.ordinal()] += ile;
		else {
			if(this.statyPotwora[ktora.ordinal()]-ile <0) {
				this.statyPotwora[ktora.ordinal()] = 0;
			}else {
				this.statyPotwora[ktora.ordinal()] -= ile;
			}
			
		}
	}
	
	
	/**
	 * @param cechyOpcjonalne the cechyOpcjonalne to set
	 */
	public void addCechyOpcjonalne(CechyPotworow cechyOpcjonalne) {
		this.cechyOpcjonalne.add(cechyOpcjonalne);
	}




	@Override
	public String toString() {
		return nazwa;
	}
	
	/**
	 * 
	 * @return string z wszystkimi informacjami postaci NPC
	 */
	public String wyswietl() {
		StringBuilder textBuild = new StringBuilder();
		//nazwa
		textBuild.append(this.toString()+"\nStatystyki: ");
		//staty
		for(int i = 0; i < IC; i++)
		{
			textBuild.append(CECHYNAZWA[i]+": "+ Integer.toString(statyPotwora[i]) + ", ");
		}
		textBuild.append("\nCechy: ");
		//cechy
		for(CechyPotworow cp:cechy) {
			textBuild.append(cp.toString() + ", ");
		}
		textBuild.append("\nOpis:\n");
		//opis stwora
		textBuild.append(this.getOpisStwora() + "\n");
			
		
		return textBuild.toString();
	}

	
	

}
