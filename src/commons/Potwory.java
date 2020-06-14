package commons;

import java.util.ArrayList;
/**
 * 
 * @author Tom
 *klasa opisujaca potwora NPC
 */
public class Potwory implements RzutKoscia{
	private static final int IC = 12; //iloœæ cech
	private String nazwa;
	private int [] statyPotwora;
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
	public int [] getStatyPotwora() {

		return statyPotwora;
	}
	/**
	 * 
	 * @param x - enum, która pozycja cechy ma byc zwrócona 
	 * @return - zwraca wartoœæ wybranej statystki w postaci wartoœci int
	 */
	public int getStatyPotwora(StatyNPC x) {
		return statyPotwora[x.ordinal()];
	}
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
	 * @param ile wartoœæ o ile ma byæ podniesiona cecha, @param ktora - która cecha, @param plus - czy ma byæ podniesiona czy obni¿ona
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
	/**
	 * @param cechy the cechy to set
	 */
	public void setCechy(CechyPotworow cechy) {
		this.cechy.add(cechy);
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
		this.statyPotwora = new int[IC];
		for(int i=0; i < IC; i++) 
		{
			if(i==0)
				this.statyPotwora[i] = Integer.parseInt(staty[i]);
			else if(staty[i].equals("-")) {
				staty[i] = "0";
			}else if(i<IC-1){
				//wprowadzenie losowoœci do statystyk potwora
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
	@Override
	public String toString() {
		return nazwa;
	}
	/*
	 * kontruktor kopiujÄ…cy
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
