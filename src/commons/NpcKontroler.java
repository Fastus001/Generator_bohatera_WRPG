package commons;

import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import views.NewGui;
import views.NpcGUI;

public class NpcKontroler implements KontrolerInterface{
	NpcGUI widok;
	NpcModelInterface model;
	DefaultListModel<Object> listaZapisanychProfesji;

	
	public NpcKontroler(NpcModelInterface model,DefaultListModel<Object> listaBohaterow) {
		try {
			this.model = model;
			this.listaZapisanychProfesji = listaBohaterow;
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

	@Override
	public void zapiszPostac(String nazwaOpis) {
		// TODO Auto-generated method stub
		listaZapisanychProfesji.addElement(model.getNpc(nazwaOpis));
		widok.dispose();
		
	}

	@Override
	public void zmienNazweCechy(String stara, String nowa) {
		widok.dodajPotworaDoGui(model.zmienNazweCechyModel(stara, nowa));
		
	}
	


}
