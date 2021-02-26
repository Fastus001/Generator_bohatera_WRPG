package mvcOknoGlowne;

public class GeneratorBogatera {

	public static void main(String[] args) {
		HeroServiceImpl model = new HeroServiceImpl();
		new GenBohKontroler(model);
		}
	}


