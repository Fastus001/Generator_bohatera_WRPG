package mvcOknoGlowne;
/**
 * 
 * @author Tom
 *
 */

import java.awt.EventQueue;

import javax.swing.UIManager;

import commons.Rasa;
import views.NewGui;

public class GenBohKontroler implements GenBohKontrolerInterface{
	private NewGui widok;
	private GenBohModelInterface model;
	/**
	 * 
	 * @param m - model interfejs
	 */
	public GenBohKontroler(GenBohModelInterface model) {
		this.model = model;
		try {
			widok = new NewGui(this, model);
			widok.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.wczytajDane();
		
	}
	@Override
	public String wyswietlBohatera() {
		return model.wyswietlNowegoBohatera(widok.getChckbxShowTalents());
	}
	@Override
	public void selectRasa(Rasa rs) {
		widok.setCbProfesja(model.getProfesjePierwszyPoziom(rs));
	}
	@Override
	public void setRacaCbBox() {
		widok.setCbRasa(model.getRasaArray());	
	}


}
