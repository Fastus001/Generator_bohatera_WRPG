package commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GeneratorImion {
	private ArrayList<String[]> bazaImion;
	
	/*
	 * kontruktor klasy, 
	 */
	public GeneratorImion() {
		bazaImion = new ArrayList<String[]>();
		wczytajPlikTxt();
	}
		
	public String getFullName(String nazwaRasy, boolean plec) {
		
		String nazwa = "Koszałek Opałek";
		if(nazwaRasy.equals("Wysokie elfy") || nazwaRasy.equals("Leśne elfy"))
			nazwaRasy = "Elfy";
		
		/*
		 * Je�eli facet
		 */
		if(plec) {
			switch(nazwaRasy) {
			case "Ludzie":  nazwa = wczytajImieNazwisko(Imie.MESKIE_IMP.ordinal()); nazwa += " " + wczytajImieNazwisko(Imie.NAZWISKO_IMP.ordinal());break;
			case "Krasnoludy": nazwa = wczytajImieNazwisko(Imie.PIERWSZ_CZ_KR.ordinal()); nazwa += wczytajImieNazwisko(Imie.DRUGI_CZ_KR_MESKIE.ordinal()); 
			nazwa += " " + wczytajImieNazwisko(Imie.PIERWSZ_CZ_KR.ordinal()); nazwa += wczytajImieNazwisko(Imie.DRUGI_CZ_KR_MESKIE.ordinal());
			nazwa += zaimekKrasnoludy(plec); nazwa += " z klanu " + wczytajImieNazwisko(Imie.NAZWA_KLANU_KR.ordinal());break;
			case "Niziołki": nazwa = wczytajImieNazwisko(Imie.NIZ_PIERWSZY_CZ.ordinal())+wczytajImieNazwisko(Imie.NIZ_DRUGI_MESKIE.ordinal());
			nazwa += " " + wczytajImieNazwisko(Imie.NIZIOLEK_NAZWISKO.ordinal());break;
			case "Elfy": nazwa = wczytajImieNazwisko(Imie.PIERWSZY_CZ_ELF.ordinal())+wczytajImieNazwisko(Imie.DRUGI_CZ_ELF.ordinal());
			nazwa +=wczytajImieNazwisko(Imie.TRZECI_CZ_ELF_MESKIE.ordinal());break;
			}
		}else {
			switch(nazwaRasy) {
			case "Ludzie":  nazwa = wczytajImieNazwisko(Imie.ZENSKIE_IMP.ordinal()); nazwa += " " + wczytajImieNazwisko(Imie.NAZWISKO_IMP.ordinal());break;
			case "Krasnoludy": nazwa = wczytajImieNazwisko(Imie.PIERWSZ_CZ_KR.ordinal()); nazwa += wczytajImieNazwisko(Imie.DRUGI_CZ_KR_ZENSKIE.ordinal()); 
			nazwa += " " + wczytajImieNazwisko(Imie.PIERWSZ_CZ_KR.ordinal()); nazwa += wczytajImieNazwisko(Imie.DRUGI_CZ_KR_ZENSKIE.ordinal());
			nazwa += zaimekKrasnoludy(plec); nazwa += " z klanu " + wczytajImieNazwisko(Imie.NAZWA_KLANU_KR.ordinal());break;
			case "Nizio�ki": nazwa = wczytajImieNazwisko(Imie.NIZ_PIERWSZY_CZ.ordinal())+wczytajImieNazwisko(Imie.NIZ_DRUGI_ZENSK.ordinal());
			nazwa += " " + wczytajImieNazwisko(Imie.NIZIOLEK_NAZWISKO.ordinal());break;
			case "Elfy": nazwa = wczytajImieNazwisko(Imie.PIERWSZY_CZ_ELF.ordinal())+wczytajImieNazwisko(Imie.DRUGI_CZ_ELF.ordinal());
			nazwa +=wczytajImieNazwisko(Imie.TRZECI_CZ_ELF_ZENSKIE.ordinal());break;
			}
		}
		return nazwa;
	}
	

	/*
	 * zaczytanie pliku txt z wszystkimi modu�ami do genrowania imion i nazwisk dla dost�pnych ras z podstawowej wersji
	 */
	private void wczytajPlikTxt() 
	{
		
		try {
		/*	
			ClassLoader classLoader2 = getClass().getClassLoader();
			InputStream inputStream2 = classLoader2.getResourceAsStream("resources/imiona.txt");
			InputStreamReader czytaj = new InputStreamReader(inputStream2);
			*/
		File plik = new File("../GeneratorBohatera/src/resources/imiona.txt");
		FileReader czytaj = new FileReader(plik);
		
		
		BufferedReader bufor = new BufferedReader(czytaj);
		String wiersz = null;
		while((wiersz = bufor.readLine()) != null ) {
			if(wiersz.length()==0)
				break;
			String[] tablica = wiersz.split(",");
			bazaImion.add(tablica);
		}
		System.out.println("Zaczytany plik txt ma wierszy " +bazaImion.size());
		bufor.close();
		}catch(Exception e) 
		{
			e.printStackTrace();
		}

	}//koniec metody wczytajpliktxt
	
	private String wczytajImieNazwisko(int x) {
		String[] tablica;
		int size;
		
		tablica = bazaImion.get(x); 
		size = tablica.length; 
		return tablica[(int) (Math.random()*size)];
	}
	
	/*
	 * wygenerrowanie zaimka adekwatnego do p�ci
	 */
	private String zaimekKrasnoludy(boolean facet) {
		String[] tablica;
		tablica = bazaImion.get(Imie.ZAIMKI_KR.ordinal()); 
		if(facet) {
			return tablica[(int) (Math.random()*2)+2];
		}else {
			return tablica[(int) (Math.random()*2)];
		}
	}
	
	
	private enum Imie {
		MESKIE_IMP, ZENSKIE_IMP, NAZWISKO_IMP, PIERWSZ_CZ_KR, DRUGI_CZ_KR_MESKIE, DRUGI_CZ_KR_ZENSKIE, ZAIMKI_KR, NAZWA_KLANU_KR, PIERWSZY_CZ_ELF, DRUGI_CZ_ELF, 
		TRZECI_CZ_ELF_ZENSKIE, TRZECI_CZ_ELF_MESKIE, NIZ_PIERWSZY_CZ, NIZ_DRUGI_ZENSK, NIZ_DRUGI_MESKIE, NIZIOLEK_NAZWISKO, ELF_PRZYDOMEK
	}
	
	
}//koniec klasy

//////////////////////////////////////
///kolejnosc w pliku txt
///1.meskie imperium
///2.�e�skie imperium
///3.nazwiska
///4. Pierwszy cz�on krasnoludzkie
///5. Drugi cz�on krasnoludzkie m�skie
///6. Drugi cz�on krasnoludzkie �e�skie
///7. zaimki do nazwisk krasnoludzkich pierwsze dwa dla kobiet, pozostale dwa dla m�czyzn
///8. nazwa klanu krasnoludzkiego
///9. Pierwszy cz�on imienia elfiego
///10. Drugi cz�on imienia elfiego
///11. Trzeci cz�on imienia elfiego - �e�ski
///12. Tzeci cz�on imienia elfiego - m�ski
///13. Imiona nizio�k�w pierwszy cz�on
///14. Imie nizio�ka kobiety drugi cz�on
///15. Imie nizio�ka faceta drugi cz�on
///16. Nazwisko nizio�ka
///17. przydomek elf�w

