package commons;

import java.util.ArrayList;

public interface NpcModelInterface {
	void wgrajCechyPotworow();
	Object[] wgrajPotwory();
	ArrayList<CechyPotworow> getCechyPotworow(String [] tab);
	Potwory setPotworAktualny(Potwory potw, boolean czyPCS);
	Potwory dodajCecheModel(String cPotw);
	Potwory usunCecheModel(String cPotw);
	void zmienStatystykeModel(int ile, int ktora);
	Potwory getNpc();

}
