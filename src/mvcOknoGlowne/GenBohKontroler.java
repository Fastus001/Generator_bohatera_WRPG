package mvcOknoGlowne;
/**
 * 
 * @author Tom
 *
 */

import java.awt.EventQueue;



import commons.ExportToPdf;
import commons.Rasa;
import views.NewGui;

public class GenBohKontroler implements GenBohKontrolerInterface{
	private NewGui widok;
	private GenBohModelInterface model;
	final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger( ExportToPdf.class );
	/**
	 * 
	 * @param m - model interfejs
	 */
	public GenBohKontroler(GenBohModelInterface model) {
		this.model = model;
		EventQueue.invokeLater(()->{
			try {
				widok = new NewGui(this, model);
				widok.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		model.wczytajDane();
		
	}
	@Override
	public void selectRasa(Rasa rs) {
		widok.setCbProfesja(model.getProfesjePierwszyPoziom(rs));
	}
	@Override
	public void setRacaCbBox() {
		widok.setCbRasa(model.getRasaArray());	
	}
	@Override
	public void aktywujPodniesPoziom() {
		widok.setBtnPodniesPoziomPrEnabled(true);	
	}
	@Override
	public void aktywujZapiszPostac() {
		widok.setBtsSaveHeroEnabled(true);
	}
	@Override
	public void aktywujNowaProfesja() {
		widok.setBtnNowaProfesjaEnabled(true);
	}
	@Override
	public void aktywujExportDoPdf() {
		widok.setBtnExportToPdfActive();
	}
	@Override
	public void wylaczExportDoPdf() {
		widok.setBtnExportToPdfInactive();
	}


}
