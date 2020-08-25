package commons;

import java.io.BufferedReader;
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
		
		String nazwa = "Kosza³ek Opa³ek";
		if(nazwaRasy.equals("Wysokie elfy") || nazwaRasy.equals("Leœne elfy"))
			nazwaRasy = "Elfy";
		
		/*
		 * Jeï¿½eli facet
		 */
		if(plec) {
			switch(nazwaRasy) {
			case "Ludzie":  nazwa = wczytajImieNazwisko(Imie.MESKIE_IMP.ordinal()); nazwa += " " + wczytajImieNazwisko(Imie.NAZWISKO_IMP.ordinal());break;
			case "Krasnoludy": nazwa = wczytajImieNazwisko(Imie.PIERWSZ_CZ_KR.ordinal()); nazwa += wczytajImieNazwisko(Imie.DRUGI_CZ_KR_MESKIE.ordinal()); 
			nazwa += " " + wczytajImieNazwisko(Imie.PIERWSZ_CZ_KR.ordinal()); nazwa += wczytajImieNazwisko(Imie.DRUGI_CZ_KR_MESKIE.ordinal());
			nazwa += zaimekKrasnoludy(plec); nazwa += " z klanu " + wczytajImieNazwisko(Imie.NAZWA_KLANU_KR.ordinal());break;
			case "Nizio³ki": nazwa = wczytajImieNazwisko(Imie.NIZ_PIERWSZY_CZ.ordinal())+wczytajImieNazwisko(Imie.NIZ_DRUGI_MESKIE.ordinal());
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
			case "Nizio³ki": nazwa = wczytajImieNazwisko(Imie.NIZ_PIERWSZY_CZ.ordinal())+wczytajImieNazwisko(Imie.NIZ_DRUGI_ZENSK.ordinal());
			nazwa += " " + wczytajImieNazwisko(Imie.NIZIOLEK_NAZWISKO.ordinal());break;
			case "Elfy": nazwa = wczytajImieNazwisko(Imie.PIERWSZY_CZ_ELF.ordinal())+wczytajImieNazwisko(Imie.DRUGI_CZ_ELF.ordinal());
			nazwa +=wczytajImieNazwisko(Imie.TRZECI_CZ_ELF_ZENSKIE.ordinal());break;
			}
		}
		return nazwa;
	}
	

	/*
	 * zaczytanie pliku txt z wszystkimi moduï¿½ami do genrowania imion i nazwisk dla dostï¿½pnych ras z podstawowej wersji
	 */
	private void wczytajPlikTxt() 
	{
		
		try {

		ClassLoader classLoader = getClass().getClassLoader();
		InputStream input = classLoader.getResourceAsStream("imiona.txt");
		InputStreamReader czytaj = new InputStreamReader(input);
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
	 * wygenerrowanie zaimka adekwatnego do p³ci
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
///2.ï¿½eï¿½skie imperium
///3.nazwiska
///4. Pierwszy czï¿½on krasnoludzkie
///5. Drugi czï¿½on krasnoludzkie mï¿½skie
///6. Drugi czï¿½on krasnoludzkie ï¿½eï¿½skie
///7. zaimki do nazwisk krasnoludzkich pierwsze dwa dla kobiet, pozostale dwa dla mï¿½czyzn
///8. nazwa klanu krasnoludzkiego
///9. Pierwszy czï¿½on imienia elfiego
///10. Drugi czï¿½on imienia elfiego
///11. Trzeci czï¿½on imienia elfiego - ï¿½eï¿½ski
///12. Tzeci czï¿½on imienia elfiego - mï¿½ski
///13. Imiona nizioï¿½kï¿½w pierwszy czï¿½on
///14. Imie nizioï¿½ka kobiety drugi czï¿½on
///15. Imie nizioï¿½ka faceta drugi czï¿½on
///16. Nazwisko nizioï¿½ka
///17. przydomek elfï¿½w

