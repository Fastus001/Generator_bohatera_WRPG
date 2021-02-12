package commons;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
		 * Jeďż˝eli facet
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
			case "Niziołki": nazwa = wczytajImieNazwisko(Imie.NIZ_PIERWSZY_CZ.ordinal())+wczytajImieNazwisko(Imie.NIZ_DRUGI_ZENSK.ordinal());
			nazwa += " " + wczytajImieNazwisko(Imie.NIZIOLEK_NAZWISKO.ordinal());break;
			case "Elfy": nazwa = wczytajImieNazwisko(Imie.PIERWSZY_CZ_ELF.ordinal())+wczytajImieNazwisko(Imie.DRUGI_CZ_ELF.ordinal());
			nazwa +=wczytajImieNazwisko(Imie.TRZECI_CZ_ELF_ZENSKIE.ordinal());break;
			}
		}
		return nazwa;
	}
	

	/*
	 * zaczytanie pliku txt z wszystkimi moduďż˝ami do genrowania imion i nazwisk dla dostďż˝pnych ras z podstawowej wersji
	 */
	private void wczytajPlikTxt() 
	{
		
		try {

		ClassLoader classLoader = getClass().getClassLoader();
		InputStream input = classLoader.getResourceAsStream("imiona.txt");
		InputStreamReader czytaj = new InputStreamReader( input, StandardCharsets.UTF_8 );
		BufferedReader bufor = new BufferedReader(czytaj);
		String wiersz = null;
		while((wiersz = bufor.readLine()) != null ) {
			if(wiersz.length()==0)
				break;
			String[] tablica = wiersz.split(",");
			bazaImion.add(tablica);
		}
		//System.out.println("Zaczytany plik txt ma wierszy " +bazaImion.size());
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
	 * wygenerrowanie zaimka adekwatnego do płci
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
///2.ďż˝eďż˝skie imperium
///3.nazwiska
///4. Pierwszy czďż˝on krasnoludzkie
///5. Drugi czďż˝on krasnoludzkie mďż˝skie
///6. Drugi czďż˝on krasnoludzkie ďż˝eďż˝skie
///7. zaimki do nazwisk krasnoludzkich pierwsze dwa dla kobiet, pozostale dwa dla mďż˝czyzn
///8. nazwa klanu krasnoludzkiego
///9. Pierwszy czďż˝on imienia elfiego
///10. Drugi czďż˝on imienia elfiego
///11. Trzeci czďż˝on imienia elfiego - ďż˝eďż˝ski
///12. Tzeci czďż˝on imienia elfiego - mďż˝ski
///13. Imiona nizioďż˝kďż˝w pierwszy czďż˝on
///14. Imie nizioďż˝ka kobiety drugi czďż˝on
///15. Imie nizioďż˝ka faceta drugi czďż˝on
///16. Nazwisko nizioďż˝ka
///17. przydomek elfďż˝w

