package commons;

import java.util.ArrayList;

public class Potwory {
	private static final String[] CECHYNAZWA = {"Sz","WW", "US", "S", "Wt", "I", "Zw", "Zr", "Int", "SW", "Ogd","¯yw"};
	private static final int IC = 12; //iloœæ cech
	private String nazwa;
	private String [] statyPotwora;
	private ArrayList<CechyPotworow> cechy;
	private ArrayList<CechyPotworow> cechyOpcjonalne;
	private String opisStwora;
	
	/**
	 * @return the nazwa
	 */
	public String getNazwa() {
		return nazwa;
	}
	/**
	 * @return the statyPotwora
	 */
	public String[] getStatyPotwora() {
		String [] staty = statyPotwora;
		for(int i = 0; i < staty.length; i++)
		{
			if(staty[i].equals("-")) {
				staty[i] = "0";
			}
		}
	
		return staty;
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
	/*
	 * konstruktor
	 */
	public Potwory(String n, String [] staty, ArrayList<CechyPotworow> cP, ArrayList<CechyPotworow> cOp, String opis) {
		this.nazwa = n;
		this.statyPotwora = new String[IC];
		for(int i=0; i < IC; i++) 
		{
			this.statyPotwora[i] = staty[i];
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
	@Override
	public String toString() {
		return nazwa;
	}
	/*
	 * kontruktor kopiujÄ…cy
	 */
	public Potwory(Potwory potwory) {
		this.nazwa = potwory.nazwa;
		this.statyPotwora = new String[IC];
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
	
	

}
