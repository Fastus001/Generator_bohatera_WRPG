package commons;

import java.util.ArrayList;

public class Potwory {
	private static final String[] CECHYNAZWA = {"Sz","WW", "US", "S", "Wt", "I", "Zw", "Zr", "Int", "SW", "Ogd","¯yw"};
	private static final int IC = 12; //iloœæ cech
	private String nazwa;
	private int [] statyPotwora;
	private ArrayList<CechyPotworow> cechy;
	private ArrayList<CechyPotworow> cechyOpcjonalne;
	private String opisStwora;
	
	/*
	 * konstruktor
	 */
	public Potwory(String n, int [] staty, CechyPotworow[] cP, CechyPotworow [] cOp, String opis) {
		this.nazwa = n;
		this.statyPotwora = new int[IC];
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
	/*
	 * kontruktor kopiuj¹cy
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
	
	

}
