package controllers;
/**
 * 
 * @author Tom
 *
 */

import services.HeroService;
import views.MainGui;

import java.awt.*;


public class MainGuiController {
	private MainGui view;
	private HeroService model;

	public MainGuiController(HeroService model) {
		this.model = model;
		EventQueue.invokeLater(()->{
			try {
				view = new MainGui( this, model);
				view.setVisible( true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		model.loadData();
	}

	public void selectRace(String race) {
		view.setComboBoxProfession( model.getProfessionsFirstLevel( race));
	}

	public void setRaceComboBox() {
		view.setComboBoxRace( model.raceNames());
	}

	public void activateLevelUpButton() {
		view.setButtonIncreaseLevelEnabled( true);
	}

	public void activateSaveHeroButton() {
		view.setButtonSaveHeroEnabled( true);
	}

	public void activateExportDoPdf() {
		view.setButtonExportToPdfActive();
	}

	public void deactivateExportDoPdf() {
		view.setButtonExportToPdfInactive();
	}

	public void activateExportDoExcel() {
		view.setButtonExportExcelEnabled();
	}


}
