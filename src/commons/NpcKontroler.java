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
		wlaczElementyGUI();
	}

	@Override
	public void dodajCeche(String cPotworow) {
		widok.dodajPotworaDoGui(model.dodajCecheModel(cPotworow));
		
	}

	@Override
	public void usunCeche(String cecha) {
		widok.dodajPotworaDoGui(model.usunCecheModel(cecha));
		
	}

	@Override
	public void zmianaStatystyki(int wartosc, int ktora) {
		model.zmienStatystykeModel(wartosc, ktora);	
	}

	@Override
	public void wlaczElementyGUI() {
		widok.enableComponents();
		
	}
	


}
