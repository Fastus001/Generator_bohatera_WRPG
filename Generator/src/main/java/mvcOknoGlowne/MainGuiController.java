package mvcOknoGlowne;
/**
 * 
 * @author Tom
 *
 */

import views.NewGui;

import java.awt.*;


public class MainGuiController {
	private NewGui view;
	private HeroService model;

	public MainGuiController(HeroService model) {
		this.model = model;
		EventQueue.invokeLater(()->{
			try {
				view = new NewGui( this, model);
				view.setVisible( true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		model.loadData();
	}

	public void selectRace(String rs) {
		view.setCbProfesja( model.getProfessionsFirstLevel( rs));
	}

	public void setRaceComboBox() {
		view.setCbRasa( model.raceNames());
	}

	public void activateLevelUpButton() {
		view.setBtnPodniesPoziomPrEnabled( true);
	}

	public void activateSaveHeroButton() {
		view.setBtsSaveHeroEnabled( true);
	}

	public void activateExportDoPdf() {
		view.setBtnExportToPdfActive();
	}

	public void deactivateExportDoPdf() {
		view.setBtnExportToPdfInactive();
	}

	public void activateExportDoExcel() {
		view.setBtnExportExcelEnabled();
	}


}
