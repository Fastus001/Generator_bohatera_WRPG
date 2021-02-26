package mvcOknoGlowne;
/**
 * 
 * @author Tom
 *
 */

import domain.Race;
import export.ExportToPdf;
import views.NewGui;

import java.awt.*;

public class GenBohKontroler implements GenBohKontrolerInterface{
	private NewGui widok;
	private HeroService model;
	final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger( ExportToPdf.class );


	public GenBohKontroler(HeroService model) {
		this.model = model;
		EventQueue.invokeLater(()->{
			try {
				widok = new NewGui(this, model);
				widok.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		model.loadData();
		
	}
	@Override
	public void selectRasa(Race rs) {
		widok.setCbProfesja(model.getProfessionsFirstLevel( rs));
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
	@Override
	public void aktywujExportDoExcel() {
		widok.setBtnExportExcelEnabled();		
	}


}
