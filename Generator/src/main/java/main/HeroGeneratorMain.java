package main;

import controllers.MainGuiController;
import services.HeroServiceImpl;

public class HeroGeneratorMain {

	public static void main(String[] args) {
		HeroServiceImpl model = new HeroServiceImpl();
		new MainGuiController( model);
		}
	}


