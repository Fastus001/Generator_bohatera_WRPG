package commons;

public interface KontrolerInterface {
	//PCS - Powrzechne Cechy Stworze�
	void setPotwora(Potwory p, boolean czyPCS);
	
	void dodajCeche(String cPotworow);
	
	void usunCeche(String cecha);
	
	void zmianaStatystyki(int wartosc,int ktora);
	
	void wlaczElementyGUI();
	
	void zapiszPostac();
}
