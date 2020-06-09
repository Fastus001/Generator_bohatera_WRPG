package commons;

import java.util.ArrayList;

public interface NpcModelInterface {
	void wgrajCechyPotworow();
	Object[] wgrajPotwory();
	ArrayList<CechyPotworow> getCechyPotworow(String [] tab);
	void setPotworAktualny(Potwory potw);

}
