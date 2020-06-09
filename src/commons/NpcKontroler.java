package commons;

import java.awt.EventQueue;

import views.NewGui;
import views.NpcGUI;

public class NpcKontroler implements KontrolerInterface{
	NpcGUI widok;
	NpcModelInterface model;
	

	
	public NpcKontroler(NpcModelInterface model) {
		try {
			this.model = model;
			widok = new NpcGUI(this);
			widok.setVisible(true);
			model.wgrajCechyPotworow();
			widok.setComboBox(model.wgrajPotwory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setNpcList() {
		
	}

	@Override
	public void setPotwora(Potwory p, boolean czyPcs) {
		widok.dodajPotworaDoGui(model.setPotworAktualny(p,czyPcs));
	}
	


}
