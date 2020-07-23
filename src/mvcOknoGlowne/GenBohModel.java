package mvcOknoGlowne;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import commons.Bohater;
import commons.ExportToPdf;
import commons.Profesja;
import commons.Rasa;
import commons.Talent;
import commons.Umiejetnosc;

/**
 * 
 * @author Tom
 *
 */
public class GenBohModel implements GenBohModelInterface{

	
	//rasa wybrana w comboBoxie w widoku
	private Rasa wybranaRasa;
	//profesja wybrana w CBoxie w widoku
	private Profesja wybranaProfesja;
	private String urlSavaPdf = null;
	private ArrayList<Rasa> listaRas;
	private ArrayList<Talent> listaTalentow;	
	private ArrayList<Profesja> listaProfesji;
	private ArrayList<Profesja> profesjePierwszyPoziom;
	private Bohater nowyBohater;
	private ObserwatorModel obserwator;

	public GenBohModel() {
		listaRas = new ArrayList<Rasa>();
		listaTalentow = new ArrayList<Talent>();
		listaProfesji = new ArrayList<Profesja>();
		profesjePierwszyPoziom = new ArrayList<Profesja>();

	}
	/**
	 * wczytuje z zasob�w rasy, profesje, talenty, umiejetnosci
	 */
	@Override
	public boolean wczytajDane() {
		// TODO Auto-generated method stub
		boolean czy = true;
		czy = WczytajTalenty();
		czy = WczytajRasy();
		czy = WczytajProfesje();
		
		System.out.println(czy);
		return czy;
	}
	
	public boolean WczytajProfesje(){
		try{
			listaProfesji = new ArrayList<Profesja>();
			/*
			ClassLoader classLoader2 = getClass().getClassLoader();
			InputStream inputStream2 = classLoader2.getResourceAsStream("resources/profesje.txt");
			InputStreamReader czytaj = new InputStreamReader(inputStream2);
			*/
			String urlProfesja = "../GeneratorBohatera/src/resources/profesje-v2.txt";
			File plik = new File(urlProfesja);
			FileReader czytaj = new FileReader(plik);
			
			
			BufferedReader czytajB = new BufferedReader(czytaj);
			
			String wiersz = null;
			while((wiersz = czytajB.readLine()) != null){
				if(wiersz.length()==0)
					break;
				String klasaProfesji = wiersz;
				String nazwaProfesji = czytajB.readLine();
				String sciezkaProfesji = czytajB.readLine();
				//rasy
				wiersz = czytajB.readLine();
				String dostepneRasy[] = wiersz.split(",");
				
				//dost�pne umiej�tno�ci
				wiersz = czytajB.readLine();
				String dostepneUm[] = wiersz.split(",");
				
				ArrayList<Umiejetnosc> umiej = new ArrayList<Umiejetnosc>();
				for(String x:dostepneUm){
						String[] doZapisaniaUm = x.split("/");
						Umiejetnosc tempUm = new Umiejetnosc(doZapisaniaUm[0], Integer.parseInt(doZapisaniaUm[1]), doZapisaniaUm[2],0,false);
						umiej.add(tempUm);
					}
				
				//dost�pne talenty
				wiersz = czytajB.readLine();
				String dostepneTlnt[] = wiersz.split(",");
				
				ArrayList<Talent> listaZnTalentow = new ArrayList<Talent>();
					
					for(String x:dostepneTlnt){
						String[] doZapTalenty = x.split("/");

						Talent tempTlnt = new Talent(doZapTalenty[0],Integer.parseInt(doZapTalenty[1]),doZapTalenty[2]);
						tempTlnt.setOpis(dodajOpisDoTalentu(tempTlnt));
						listaZnTalentow.add(tempTlnt);
					}
				//przedmioty
				wiersz = czytajB.readLine();
				String [] przedmiotyProf = wiersz.split(",");
				
				//cechy rozwoju
				wiersz = czytajB.readLine();
				String wynik3[] = wiersz.split(",");
				int [] cechyRozwoju = new int [wynik3.length];
				for (int i = 0; i<wynik3.length; i++){
					cechyRozwoju[i] = Integer.parseInt(wynik3[i]);
				}
				//poziom profesji
				int poziomProfesji = Integer.parseInt(czytajB.readLine());
				
				Profesja prof = new Profesja(nazwaProfesji, sciezkaProfesji, poziomProfesji, umiej,listaZnTalentow,dostepneRasy,cechyRozwoju,false,klasaProfesji,przedmiotyProf );
				listaProfesji.add(prof);

			}
			czytajB.close();
			//wyswietlProfesjeWszystkoPrzycisk();
			//TODO -1 btnNowyBohater.setEnabled(true);
			
			
		}catch(Exception ex){
			//TODO - 2 textArea.append(ex.toString());
			ex.printStackTrace();
			return false;
		}
		return true;
}//koniec metody
	
	public boolean WczytajTalenty(){
		try{
				listaTalentow = new ArrayList<Talent>();
				/*
				ClassLoader classLoader2 = getClass().getClassLoader();
				InputStream inputStream2 = classLoader2.getResourceAsStream("resources/talenty.txt");
				InputStreamReader strumien = new InputStreamReader(inputStream2);
				*/
				File plik = new File("../GeneratorBohatera/src/resources/talenty.txt");
				FileReader czytaj = new FileReader(plik);
				BufferedReader czytajBuf = new BufferedReader(czytaj);
				String wiersz = null;
				
				while((wiersz = czytajBuf.readLine()) !=null){
					if(wiersz.length()==0)
						break;
					tworzTalent(wiersz);
				}
				czytajBuf.close();
				//wyswietlTalentyWszystkie();
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("Talenty nie wczytane!!");
			return false;
		}
		return true;
}//koniec metody
	
	/////////////////////////////////////////////////////////////////////////
	//zapisanie zaimportowanych z pliku txt opsi�w w obiektach talent;
	/////////////////////////////////////////////////////////////////////////
	private void tworzTalent(String wierszDanych){
		String[] wynik = wierszDanych.split("/");
		Talent tl = new Talent(wynik[0],Integer.parseInt(wynik[1]),wynik[2], wynik[3]);
		listaTalentow.add(tl);
	}
	
	
public boolean WczytajRasy(){
		
		try{
			listaRas = new ArrayList<Rasa>();
			/*
			ClassLoader classLoader2 = getClass().getClassLoader();
			InputStream inputStream2 = classLoader2.getResourceAsStream("resources/rasy.txt");
			InputStreamReader czytaj = new InputStreamReader(inputStream2);
			/*/
			String urlRasy = "../GeneratorBohatera/src/resources/rasy.txt";
			File plik = new File(urlRasy);
			FileReader czytaj = new FileReader(plik);
			
			BufferedReader czytajBuf = new BufferedReader(czytaj);
			String wiersz = null;
			
			
			while((wiersz = czytajBuf.readLine()) !=null){
					if(wiersz.length()==0)
						break;
					String nazwa = wiersz;
					wiersz = czytajBuf.readLine();
					String wynik1[] = wiersz.split(",");
					wiersz = czytajBuf.readLine();
					String wynik2[] = wiersz.split(",");
					wiersz = czytajBuf.readLine();
					String talenty[] = wiersz.split(",");
					wiersz = czytajBuf.readLine();
					//zamiana string�w na tablice int�w -cechy bazowe
					int [] tablica = new int[10];
					for(int i = 0; i<10; i++){
						tablica[i] = Integer.parseInt(wynik1[i]);
					}
					//zapisanie umiejetnosci jako obiekty
					ArrayList<Umiejetnosc> umiej = new ArrayList<Umiejetnosc>();
					for(String x:wynik2){
						String[] doZapisaniaUm = x.split("/");
						Umiejetnosc tempUm = new Umiejetnosc(doZapisaniaUm[0], Integer.parseInt(doZapisaniaUm[1]), doZapisaniaUm[2],0,false);
						umiej.add(tempUm);
					}
					//konwersja talentow na obiekty
					ArrayList<Talent> listaZnTalnetow = new ArrayList<Talent>();
					
					for(String x:talenty){
						String[] doZapTalenty = x.split("/");
						Talent tempTlnt = new Talent(doZapTalenty[0],Integer.parseInt(doZapTalenty[1]),doZapTalenty[2]);
						//todo dodanie opisu do talentow
						tempTlnt.setOpis(dodajOpisDoTalentu(tempTlnt));
						listaZnTalnetow.add(tempTlnt);
					}
					
					
					Rasa rs = new Rasa(nazwa, tablica ,umiej,listaZnTalnetow, wiersz);
					listaRas.add(rs);	
				}	
			czytajBuf.close();
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
		return true;
		
}//koniec metody

public String dodajOpisDoTalentu(Talent tal) {
	if(listaTalentow.size()>0) {
		for(Talent temp:listaTalentow){
			if(temp.toString().equals(tal.toString())){
				return temp.getOpisString();
			}
		}
	}
	return "brak opis - ups";
}
/////////////////////////////////////////////////////////////////////////
//szansa wylosowanie jak w podr�czniku 4ed
/////////////////////////////////////////////////////////////////////////	
private int losowanieRasy() {
	int rzut = (int) (Math.random()*100)+1;
	if(rzut <91){
		return 0;
	}else if(rzut >90 && rzut <95){
		return 2;
	}else if(rzut>94 && rzut <99){
		return 1;
	}else if(rzut==99){
		return 3;
	}else{
		return 4;
	}
}
public void szukajProfesjiPierwszyPoziom(Rasa losowaRasa) {
		profesjePierwszyPoziom.clear();
		
		//sprawdzenie profesji p�d k�tem poziomy (lvl1) oraz tego jaka rasa w danej profesji jest dost�pna
		int poziomPierwszy = 1;
		for(Profesja p:listaProfesji){
			if(p.getPoziom()==poziomPierwszy){
				for(int i = 0; i < p.getLiczbaDostepnychRas(); i++){						
					String nazwaP = losowaRasa.getName();
					if(nazwaP.equals(p.dostepnaRasa[i])){
					profesjePierwszyPoziom.add(p);							
					}
				}	
			}
		}
}

@Override
public void nowyBohater(int rasa, int prof,int exp, boolean plec, boolean oT) {
	System.out.println("Numer rasy " + rasa);
	try {
		Profesja losowaProfesja;
		Rasa losowaRasa;
		//sprawdzenie wybopru rasy, je�eli brak wyboru to losowanie, inaczej wyb�r z listy
		if(rasa == -1) {
			losowaRasa = listaRas.get(losowanieRasy());
			szukajProfesjiPierwszyPoziom(losowaRasa);
		}else {
			losowaRasa = (Rasa) listaRas.get(rasa);
			szukajProfesjiPierwszyPoziom(losowaRasa);
			System.out.println(losowaRasa);
		}
		//sprawdzenie wyboru profesji, je�eli brak wyboru to losowanie, inaczej wyb�r z listy
		if(prof == -1) {
			losowaProfesja = profesjePierwszyPoziom.get( (int)(Math.random()*profesjePierwszyPoziom.size() )) ;
		}else {
			losowaProfesja = (Profesja) profesjePierwszyPoziom.get(prof);
		}

		if(plec)
			nowyBohater = new Bohater(losowaRasa,losowaProfesja, true);
		else
			nowyBohater = new Bohater(losowaRasa,losowaProfesja, false);
		nowyBohater.doswiadczenieBohatera(exp);
		obserwator.aktualizujPostac(wyswietlNowegoBohatera(oT));
		wybranaRasa = new Rasa(losowaRasa);
		wybranaProfesja = new Profesja(losowaProfesja);
		//wyswietlenie nowego bohatera
		//TODO textArea.setText(nowyBohater.wyswietlBohatera(chckbxShowTalents.isSelected()));
		//System.out.println(checkBox1.isSelected());
		//nowyBohater.closeBohater();
		//TODO btnPodniesPoziomPr.setEnabled(true);
		//TODO btsSaveHero.setEnabled(true);
		
		//TODO btnNowaProfesja.setEnabled(false);
	} catch (Exception e2) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e2.printStackTrace(pw);
	}
	
}
/**
 * @return the wybranaRasa
 */
public Rasa getWybranaRasa() {
	return wybranaRasa;
}
/**
 * @return the wybranaProfesja
 */
public Profesja getWybranaProfesja() {
	return wybranaProfesja;
}
/**
 * @param wybranaRasa the wybranaRasa to set
 */
public void setWybranaRasa(Rasa wybranaRasa) {
	this.wybranaRasa = wybranaRasa;
}
/**
 * @param wybranaProfesja the wybranaProfesja to set
 */
public void setWybranaProfesja(Profesja wybranaProfesja) {
	this.wybranaProfesja = wybranaProfesja;
}

@Override
public Object[] getRasaArray() {
	return listaRas.toArray();
}
@Override
public Object[] getProfesjePierwszyPoziom(Rasa rs) {
	szukajProfesjiPierwszyPoziom(rs);
	
	return profesjePierwszyPoziom.toArray();
}
@Override
public void zarejestrujObserwatora(ObserwatorModel o) {
	this.obserwator = o;
	
}
@Override
public void wyrejestrujObserwatora(ObserwatorModel o) {
	this.obserwator = null;	
}
@Override
public String wyswietlNowegoBohatera(boolean jak) {
	return nowyBohater.wyswietlBohatera(jak);
}
@Override
public void podniesPoziom(int exp, boolean talenty) {
	Profesja profesjaNowyPoziom = null;
	/*
	 * wgranie akutalnej nazwy profesji do stringa
	 * wgranie aktualnego poziomu aktualnie wybranej klasy postaci
	 */
	String nazwaProfesji = nowyBohater.getProfesjaNameMain();
	int poziomProfesji = nowyBohater.getCurrentProfPoziom()+1;
	
	if(poziomProfesji>4)
	{
		if(!nowyBohater.getProfesjaUkonczona())
		{
		int potwierdznie = JOptionPane.showConfirmDialog(null, "Posta� osi�gne�a maksymalny poziom profesji,czy chcesz aby \"uko�czy�a\" ten poziom?", "Koks", JOptionPane.YES_NO_OPTION);
			if(potwierdznie == JOptionPane.OK_OPTION)
				{
				nowyBohater.ukonczPoziomProfesji(5);
				obserwator.aktualizujPostac(wyswietlNowegoBohatera(talenty));
				}
			
		}else {
			JOptionPane.showMessageDialog(null, "Posta� osi�gn�a maksymalny poziom, wybierz inn� profesj� je�li dalej chcesz rozwija� posta�!", "Maksymalny poziom", JOptionPane.INFORMATION_MESSAGE);
		}
	}else{
		//sprawdzenie wybranej Profesji czy czasem nie jest inna ni� aktualnie rozwijana
		if(wybranaProfesja!=null) {
			int testHistoriiProfesji = nowyBohater.sprawdzHistorieProfesji(wybranaProfesja);
			if(testHistoriiProfesji>0) {
				profesjaNowyPoziom = wybranaProfesja;
			}
		}else {
			//wczytanie wybranej profesji (lvl1) do zmiennej
			profesjaNowyPoziom = nowyBohater.getCurrentProfesja();
		}
		
		

		
			//sprawdzenie historii bohatera czy ta profesja juz nie by�a wczesniej rozwijana
			int sprawdzenieHistoriiProfesji = nowyBohater.sprawdzHistorieProfesji(profesjaNowyPoziom);
			//System.out.println("Sprawdzenie poziomu profesji " + sprawdzenieHistoriiProfesji );
			if(sprawdzenieHistoriiProfesji != -1)
			{
				/*
				 * je�li nie by�a to przypisujemy nowy poziom profesji jaka ma by� wyszukana do rozwiniecia, plus nazwa tej profesji, przy za�o�eniu
				 * �e poziom nie jest wy�szy od 4 
				 */
				if((sprawdzenieHistoriiProfesji+1) <5)
				{
					nazwaProfesji = profesjaNowyPoziom.toString();
					poziomProfesji = sprawdzenieHistoriiProfesji+1;
				}else {
					JOptionPane.showMessageDialog(null, "Wybrana profesja posiada ju� maksymlany poziom, posta� awansuje we wcze�niej wybranej profesji", "Maksymalny poziom wybranej profesjii!!", JOptionPane.INFORMATION_MESSAGE);
				}								
				System.out.println("Poziom tej samej profesji z najwy�szym poziomem z historii to  = " + sprawdzenieHistoriiProfesji);				
			}
		
	for(Profesja p: listaProfesji)
	{
		if(p.toString().equals(nazwaProfesji) && (p.getPoziom() == poziomProfesji))
		{
			profesjaNowyPoziom = new Profesja(p);
		}
	}
	
			
	if(profesjaNowyPoziom != null)
		{
			nowyBohater.nowaProfesja(profesjaNowyPoziom);
			nowyBohater.doswiadczenieBohatera(exp);
			obserwator.aktualizujPostac(wyswietlNowegoBohatera(talenty));	
		}
	}//koniec else
}//koniec metody podnie� poziom
@Override
public void nowaProfesja(int exp, boolean talenty, boolean przycisk) {
	// TODO Auto-generated method stub
	
	//pytanie czy wczesniejsza sciezka ma by� uko�czona
	if(!nowyBohater.getProfesjaUkonczona())
	{
		int potwierdzenie = JOptionPane.showConfirmDialog(null, "Czy aktualny poziom profesji ma by� uko�czony przed zmian� profesji?", "Zmiana profesji!", JOptionPane.YES_NO_OPTION);
		if(potwierdzenie == JOptionPane.OK_OPTION) 
		{
			nowyBohater.ukonczPoziomProfesji(nowyBohater.getCurrentProfPoziom()+1);
		}
	}

	
	int sprawdzHistorieProfesji = nowyBohater.sprawdzHistorieProfesji(wybranaProfesja);
	
	if(sprawdzHistorieProfesji == -1) {
		nowyBohater.nowaProfesja(wybranaProfesja);
		nowyBohater.doswiadczenieBohatera(exp);
		//wyswietlenie nowego bohatera
		obserwator.aktualizujPostac(wyswietlNowegoBohatera(talenty));
		obserwator.wylaczbtnNowaProfesja();
	}
	
	if(!przycisk)
		obserwator.wlaczPrzyciskbtnPodniesPoziomPr();	
}//koniec metody nowaProfesja
/**
 * @return the nowyBohater
 */
public Bohater getNowyBohater() {
	return nowyBohater;
}
@Override
public Bohater postacBohaterModel() {
	return this.getNowyBohater();
}
@Override
public void setRasa(Rasa r) {
	if(nowyBohater != null) {
		if(r.getName().equals(nowyBohater.getRasaName())) {
			obserwator.wlaczPrzyciskbtnPodniesPoziomPr();
		}
	}

	wybranaRasa = r;
	
}
@Override
public void setProfesja(Profesja p) {
	if(nowyBohater != null) {
		//je�eli rasa nie zosta�a zmieniona to mo�emy dzia�a�
		if(wybranaRasa.getName().equals(nowyBohater.getRasaName()))
		{
			int test = nowyBohater.sprawdzHistorieProfesji(p);
			//posta� wcze�niej nie rozwija�a danej profesji, mo�na w��czyc opcj� nowa profesja
			if(test== -1) {
				obserwator.wlaczbtnNowaProfesja();
			}else {
				obserwator.wylaczbtnNowaProfesja();
			}
			
		}else {
			JOptionPane.showMessageDialog(null, "Zmieni�e� ras�, nie mo�esz modyfikowa� postaci do momentu gdy wyb�r rasy b�dzie zgodny z aktulanie towrzon� postaci�.");
			obserwator.wylaczbtnNowaProfesja();
			obserwator.wylaczPrzicskPodniesPoziomPr();
		}
	}
	wybranaProfesja = p;
}
@Override
public void opisPostaciTalenty(boolean talenty) {
	if(nowyBohater != null)
		obserwator.aktualizujPostac(wyswietlNowegoBohatera(talenty));
}
@Override
public void zapiszPostac() {
	Bohater nowy = new Bohater(nowyBohater);
	obserwator.aktualizujListeBohaterow(nowy);
}
@Override
public void exportDoPdf(Bohater nBohater) {
	if(urlSavaPdf==null)
	{
		JFileChooser dialogFolder = new JFileChooser();
		dialogFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = dialogFolder.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			urlSavaPdf = dialogFolder.getSelectedFile().getAbsolutePath()+"\\";
		}
	}
	

		Runnable runnable = ()->{try {
			new ExportToPdf(nBohater,urlSavaPdf);
			
		} catch (IOException e) {
			//TODO
			
		}};
		Thread t = new Thread(runnable);
		t.start();

	
}

}
