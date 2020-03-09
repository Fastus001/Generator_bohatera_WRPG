package commons;

public class Wyglad implements RzutKoscia{
	private int wiek;
	private String kolorOczu;
	private String kolorWlosow;
	
	Wyglad(Rasa rs){
		setWiek(rs.getName());
	}

	/**
	 * ustalenie wieku postaci w oparciu od ras�
	 */
	public void setWiek(String nazwaRasy) {
		
		if(nazwaRasy.equals("Wysokie elfy") || nazwaRasy.equals("Le�ne elfy"))
			nazwaRasy = "Elfy";
		
		switch(nazwaRasy) {
		case "Ludzie": this.wiek = 15 + RzutKoscia.rzutK(10);break;
		case "Krasnoludy":this.wiek = 15 + 10*RzutKoscia.rzutK(10);break;
		case "Nizio�ki":this.wiek = 15 + 5*RzutKoscia.rzutK(10);break;
		case "Elfy":this.wiek = 30 + 10*RzutKoscia.rzutK(10);break;
		}
	}
	

}
