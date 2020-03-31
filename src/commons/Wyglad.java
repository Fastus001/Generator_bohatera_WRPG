package commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Wyglad implements RzutKoscia{
	private int wiek;
	private String kolorOczu;
	private String kolorWlosow;
	private ArrayList<String[]> listaWlosyOczy;
	
	public Wyglad(Rasa rs){
		//todo

		wczytajListe();
		setWiek(rs.getName());
		setKolorWlosow(rs.getName());
		setKolorOczu(rs.getName());
	}
	
	public Wyglad(Wyglad stary) {
		this.wiek = stary.wiek;
		this.kolorOczu = stary.kolorOczu;
		this.kolorWlosow = stary.kolorWlosow;
	}
	
	
	public String toString() {
		String wszystko = "Wiek: " + wiek + ", Kolor oczu: " + kolorOczu + ", Kolor w³osów: " + kolorWlosow + ".\n";
		return wszystko;
	}
	
	public void setKolorOczu(String nazwaRasy) {
		int rand = 18 + ((int) (Math.random()*19));
		System.out.println("Rand = " + rand);
		String [] tab = listaWlosyOczy.get(rand);
		switch(nazwaRasy) {
		case "Ludzie": this.kolorOczu = tab[0];break;
		case "Krasnoludy":this.kolorOczu = tab[1];break;
		case "Nizio³ki":this.kolorOczu = tab[2];break;
		case "Wysokie elfy":this.kolorOczu = tab[3];break;
		case "Leœne elfy":this.kolorOczu = tab[4];break;
		}
	}
	
	/*
	 * w pliku txt najpierw jest 19 wierszy z kolorem w³osów
	 */
	public void setKolorWlosow(String nazwaRasy) {
		int rand = (int) (Math.random()*19);
		String [] tab = listaWlosyOczy.get(rand);
		switch(nazwaRasy) {
		case "Ludzie": this.kolorWlosow = tab[0];break;
		case "Krasnoludy":this.kolorWlosow = tab[1];break;
		case "Nizio³ki":this.kolorWlosow = tab[2];break;
		case "Wysokie elfy":this.kolorWlosow = tab[3];break;
		case "Leœne elfy":this.kolorWlosow = tab[4];break;
		}
	}

	private void wczytajListe() {
		listaWlosyOczy = new ArrayList<String[]>();
		try {
			File file = new File("../GeneratorBohatera/src/resources/wlosy.txt");
			FileReader czytaj = new FileReader(file);
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
	 * ustalenie wieku postaci w oparciu od rasê
	 */
	public void setWiek(String nazwaRasy) {
		
		if(nazwaRasy.equals("Wysokie elfy") || nazwaRasy.equals("Leœne elfy"))
			nazwaRasy = "Elfy";
		
		switch(nazwaRasy) {
		case "Ludzie": this.wiek = 15 + RzutKoscia.rzutK(10);break;
		case "Krasnoludy":this.wiek = 15 + RzutKoscia.rzutK(10,10);break;
		case "Nizio³ki":this.wiek = 15 + RzutKoscia.rzutK(10,5);break;
		case "Elfy":this.wiek = 30 + RzutKoscia.rzutK(10,10);break;
		}
	}
	
	public void addWiekPoziomProf(String nazwaRasy) {
		if(nazwaRasy.equals("Wysokie elfy") || nazwaRasy.equals("Leœne elfy"))
			nazwaRasy = "Elfy";
		
		switch(nazwaRasy) {
		case "Ludzie": this.wiek += RzutKoscia.rzutK(6,2);break;
		case "Krasnoludy":this.wiek += RzutKoscia.rzutK(10,4);break;
		case "Nizio³ki":this.wiek += RzutKoscia.rzutK(10,2);break;
		case "Elfy":this.wiek += RzutKoscia.rzutK(10,8);break;
		}
	}
}
