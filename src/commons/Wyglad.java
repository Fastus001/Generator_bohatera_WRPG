package commons;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Wyglad implements RzutKoscia{
	private int wiek;
	private int wzrost;
	private String kolorOczu;
	private String kolorWlosow;
	private ArrayList<String[]> listaWlosyOczy;
	
	public Wyglad(Rasa rs){
		//todo

		wczytajListe();
		setWiek(rs.getName());
		setKolorWlosow(rs.getName());
		setKolorOczu(rs.getName());
		setWzrost(rs.getName());
	}
	


	public Wyglad(Wyglad stary) {
		this.wiek = stary.wiek;
		this.kolorOczu = stary.kolorOczu;
		this.kolorWlosow = stary.kolorWlosow;
		this.wzrost = stary.wzrost;
	}
	
	
	public String toString() {
		String wszystko = "Wiek: " + wiek + ", Wzrost: " + wzrost + " cm, Kolor oczu: " + kolorOczu + ", Kolor włosów: " + kolorWlosow + ".\n";
		return wszystko;
	}
	




	public void setWzrost(String nazwaRasy) {
		switch(nazwaRasy) {
		case "Ludzie": this.wzrost = 150 + RzutKoscia.rzutK(4, 10);break;
		case "Krasnoludy":this.wzrost = 130 + RzutKoscia.rzutK(2, 10);break;
		case "Niziołki":this.wzrost = 95 + RzutKoscia.rzutK(2, 10);break;
		case "Wysokie elfy":this.wzrost = 180 + RzutKoscia.rzutK(3, 10);break;
		case "Leśne elfy":this.wzrost = 180 + RzutKoscia.rzutK(3, 10);break;
	}
	}

	
	public void setKolorOczu(String nazwaRasy) {
		int rand = 18 + ((int) (Math.random()*19));
		System.out.println("Rand = " + rand);
		String [] tab = listaWlosyOczy.get(rand);
		switch(nazwaRasy) {
		case "Ludzie": this.kolorOczu = tab[0];break;
		case "Krasnoludy":this.kolorOczu = tab[1];break;
		case "Niziołki":this.kolorOczu = tab[2];break;
		case "Wysokie elfy":this.kolorOczu = tab[3];break;
		case "Leśne elfy":this.kolorOczu = tab[4];break;
		}
	}
	
	/*
	 * w pliku txt najpierw jest 19 wierszy z kolorem wďż˝osďż˝w
	 */
	public void setKolorWlosow(String nazwaRasy) {
		int rand = (int) (Math.random()*19);
		String [] tab = listaWlosyOczy.get(rand);
		switch(nazwaRasy) {
		case "Ludzie": this.kolorWlosow = tab[0];break;
		case "Krasnoludy":this.kolorWlosow = tab[1];break;
		case "Niziołki":this.kolorWlosow = tab[2];break;
		case "Wysokie elfy":this.kolorWlosow = tab[3];break;
		case "Leśne elfy":this.kolorWlosow = tab[4];break;
		}
	}

	private void wczytajListe() {
		listaWlosyOczy = new ArrayList<String[]>();
		try {

			ClassLoader classLoader = getClass().getClassLoader();
			InputStream input = classLoader.getResourceAsStream("wlosy.txt");
			InputStreamReader czytaj = new InputStreamReader( input, StandardCharsets.UTF_8);
			
			BufferedReader bufor = new BufferedReader(czytaj);
			String wiersz;
			while ((wiersz = bufor.readLine())!= null) {
				if(wiersz=="")
					break;
				String[] tablica = wiersz.split(";");
				listaWlosyOczy.add(tablica);
			}
			bufor.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	/**
	 * ustalenie wieku postaci w oparciu od rasę
	 */
	public void setWiek(String nazwaRasy) {
		
		if(nazwaRasy.equals("Wysokie elfy") || nazwaRasy.equals("Leśne elfy"))
			nazwaRasy = "Elfy";
		
		switch(nazwaRasy) {
		case "Ludzie": this.wiek = 15 + RzutKoscia.rzutK(10);break;
		case "Krasnoludy":this.wiek = 15 + RzutKoscia.rzutK(10,10);break;
		case "Niziołki":this.wiek = 15 + RzutKoscia.rzutK(10,5);break;
		case "Elfy":this.wiek = 30 + RzutKoscia.rzutK(10,10);break;
		}
	}
	
	public void addWiekPoziomProf(String nazwaRasy) {
		if(nazwaRasy.equals("Wysokie elfy") || nazwaRasy.equals("Leśne elfy"))
			nazwaRasy = "Elfy";
		
		switch(nazwaRasy) {
		case "Ludzie": this.wiek += RzutKoscia.rzutK(6,2);break;
		case "Krasnoludy":this.wiek += RzutKoscia.rzutK(10,4);break;
		case "Niziołki":this.wiek += RzutKoscia.rzutK(10,2);break;
		case "Elfy":this.wiek += RzutKoscia.rzutK(10,8);break;
		}
	}
	
	/**
	 * @return the wiek
	 */
	public int getWiek() {
		return wiek;
	}

	/**
	 * @return the kolorOczu
	 */
	public String getKolorOczu() {
		return kolorOczu;
	}

	/**
	 * @return the kolorWlosow
	 */
	public String getKolorWlosow() {
		return kolorWlosow;
	}
	
	/**
	 * @return the wzrost
	 */
	public int getWzrost() {
		return wzrost;
	}
}
