package npcGenerator;

public interface KontrolerInterface {
	void setPotwora(Potwory p, boolean czyPCS);
	
	void zmienNazweCechy(String stara, String nowa);
	
	void dodajCeche(String cPotworow);
	
	void usunCeche(String cecha);
	
	void zmianaStatystyki(int wartosc,int ktora);
	
	void wlaczElementyGUI();
	
	void zapiszPostac(String nazwaOpis);
}
