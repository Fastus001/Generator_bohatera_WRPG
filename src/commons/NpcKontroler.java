package commons;

import views.NpcGUI;

public class NpcKontroler implements KontrolerInterface{
	NpcGUI widok;
	NpcModelInterface model;
	
	public NpcKontroler(NpcModelInterface model) {
		try {
			this.model = model;
			widok = new NpcGUI(this);
			widok.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setNpcList() {
		
	}

}
