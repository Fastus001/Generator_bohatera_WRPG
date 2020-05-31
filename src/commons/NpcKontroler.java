package commons;

import views.NpcGUI;

public class NpcKontroler implements KontrolerInterface{
	NpcGUI widok;
	
	public NpcKontroler() {
		try {
			widok = new NpcGUI(this);
			widok.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setNpcList() {
		
	}

}
