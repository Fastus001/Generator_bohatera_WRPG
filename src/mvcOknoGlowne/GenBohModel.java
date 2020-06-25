package mvcOknoGlowne;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;
import javax.swing.plaf.ListUI;

import commons.Bohater;
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
	private ArrayList<Rasa> listaRas;
	private ArrayList<Talent> listaTalentow;	
	private ArrayList<Profesja> listaProfesji;
	private ArrayList<Profesja> profesjePierwszyPoziom;
	private ArrayList<Umiejetnosc> listaUm;
	private Bohater nowyBohater;
	private Profesja nowaProfesja;
	private ObserwatorModel obserwator;

	public GenBohModel() {
		listaRas = new ArrayList<Rasa>();
		listaTalentow = new ArrayList<Talent>();
		listaProfesji = new ArrayList<Profesja>();
		profesjePierwszyPoziom = new ArrayList<Profesja>();
		listaUm = new ArrayList<Umiejetnosc>();
	}
	/**
	 * wczytuje z zasobów rasy, profesje, talenty, umiejetnosci
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
			String urlProfesja = "../GeneratorBohatera/src/resources/profesje.txt";
			File plik = new File(urlProfesja);
			FileReader czytaj = new FileReader(plik);
			
			
			BufferedReader czytajB = new BufferedReader(czytaj);
			
			String wiersz = null;
			while((wiersz = czytajB.readLine()) != null){
				if(wiersz.length()==0)
					break;
				String nazwaProfesji = wiersz;
				String sciezkaProfesji = czytajB.readLine();
				//rasy
				wiersz = czytajB.readLine();
				String dostepneRasy[] = wiersz.split(",");
				
				//dostêpne umiejêtnoœci
				wiersz = czytajB.readLine();
				String dostepneUm[] = wiersz.split(",");
				
				ArrayList<Umiejetnosc> umiej = new ArrayList<Umiejetnosc>();
				for(String x:dostepneUm){
						String[] doZapisaniaUm = x.split("/");
						Umiejetnosc tempUm = new Umiejetnosc(doZapisaniaUm[0], Integer.parseInt(doZapisaniaUm[1]), doZapisaniaUm[2],0,false);
						umiej.add(tempUm);
					}
				
				//dostêpne talenty
				wiersz = czytajB.readLine();
				String dostepneTlnt[] = wiersz.split(",");
				
				ArrayList<Talent> listaZnTalentow = new ArrayList<Talent>();
					
					for(String x:dostepneTlnt){
						String[] doZapTalenty = x.split("/");
						Talent tempTlnt = new Talent(doZapTalenty[0],Integer.parseInt(doZapTalenty[1]),doZapTalenty[2]);
						tempTlnt.setOpis(dodajOpisDoTalentu(tempTlnt));
						listaZnTalentow.add(tempTlnt);
					}
				
				
				//cechy rozwoju
				wiersz = czytajB.readLine();
				String wynik3[] = wiersz.split(",");
				int [] cechyRozwoju = new int [wynik3.length];
				for (int i = 0; i<wynik3.length; i++){
					cechyRozwoju[i] = Integer.parseInt(wynik3[i]);
				}
				//poziom profesji
				int poziomProfesji = Integer.parseInt(czytajB.readLine());
				
				Profesja prof = new Profesja(nazwaProfesji, sciezkaProfesji, poziomProfesji, umiej,listaZnTalentow,dostepneRasy,cechyRozwoju,false );
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
	//zapisanie zaimportowanych z pliku txt opsiów w obiektach talent;
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
					//zamiana stringów na tablice intów -cechy bazowe
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
//szansa wylosowanie jak w podrêczniku 4ed
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
		
		//sprawdzenie profesji p¹d k¹tem poziomy (lvl1) oraz tego jaka rasa w danej profesji jest dostêpna
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
		//sprawdzenie wybopru rasy, je¿eli brak wyboru to losowanie, inaczej wybór z listy
		if(rasa == -1) {
			losowaRasa = listaRas.get(losowanieRasy());
			szukajProfesjiPierwszyPoziom(losowaRasa);
		}else {
			losowaRasa = (Rasa) listaRas.get(rasa);
			szukajProfesjiPierwszyPoziom(losowaRasa);
			System.out.println(losowaRasa);
		}
		//sprawdzenie wyboru profesji, je¿eli brak wyboru to losowanie, inaczej wybór z listy
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
		String sStackTrace = sw.toString(); // stack trace as a string
		//TODO textArea.append(sStackTrace);
	}
	
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
public void podniesPoziom(int pr, int exp, boolean talenty) {
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
		int potwierdznie = JOptionPane.showConfirmDialog(null, "Postaæ osi¹gne³a maksymalny poziom profesji,czy chcesz aby \"ukoñczy³a\" ten poziom?", "Koks", JOptionPane.YES_NO_OPTION);
			if(potwierdznie == JOptionPane.OK_OPTION)
				{
				nowyBohater.ukonczPoziomProfesji(5);
				obserwator.aktualizujPostac(wyswietlNowegoBohatera(talenty));
				}
			
		}else {
			JOptionPane.showMessageDialog(null, "Postaæ osi¹gnê³a maksymalny poziom, wybierz inn¹ profesjê jeœli dalej chcesz rozwijaæ postaæ!", "Maksymalny poziom", JOptionPane.INFORMATION_MESSAGE);
		}
	}else{
	//sprawdzenie czy s¹ obiekty z CB profesje, je¿eli s¹, to idziemy dalej
	if(pr != -1)
	{
		//wczytanie wybranej profesji (lvl1) do zmiennej
		profesjaNowyPoziom = (Profesja) listaProfesji.get(pr);
		
			//sprawdzenie historii bohatera czy ta profesja juz nie by³a wczesniej rozwijana
			int sprawdzenieHistoriiProfesji = nowyBohater.sprawdzHistorieProfesji(profesjaNowyPoziom);
			//System.out.println("Sprawdzenie poziomu profesji " + sprawdzenieHistoriiProfesji );
			if(sprawdzenieHistoriiProfesji != -1)
			{
				/*
				 * jeœli nie by³a to przypisujemy nowy poziom profesji jaka ma byæ wyszukana do rozwiniecia, plus nazwa tej profesji, przy za³o¿eniu
				 * ¿e poziom nie jest wy¿szy od 4 
				 */
				if((sprawdzenieHistoriiProfesji+1) <5)
				{
					nazwaProfesji = profesjaNowyPoziom.toString();
					poziomProfesji = sprawdzenieHistoriiProfesji+1;
				}else {
					JOptionPane.showMessageDialog(null, "Wybrana profesja posiada ju¿ maksymlany poziom, postaæ awansuje we wczeœniej wybranej profesji", "Maksymalny poziom wybranej profesjii!!", JOptionPane.INFORMATION_MESSAGE);
				}								
				System.out.println("Poziom tej samej profesji z najwy¿szym poziomem z historii to  = " + sprawdzenieHistoriiProfesji);				
			}
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
}//koniec metody podnieœ poziom
@Override
public void nowaProfesja(int exp, boolean talenty, boolean przycisk) {
	// TODO Auto-generated method stub
	
	//pytanie czy wczesniejsza sciezka ma byæ ukoñczona
	if(!nowyBohater.getProfesjaUkonczona())
	{
		int potwierdzenie = JOptionPane.showConfirmDialog(null, "Czy aktualny poziom profesji ma byæ ukoñczony przed zmian¹ profesji?", "Zmiana profesji!", JOptionPane.YES_NO_OPTION);
		if(potwierdzenie == JOptionPane.OK_OPTION) 
		{
			nowyBohater.ukonczPoziomProfesji(nowyBohater.getCurrentProfPoziom()+1);
		}
	}

	
	int sprawdzHistorieProfesji = nowyBohater.sprawdzHistorieProfesji(nowaProfesja);
	
	if(sprawdzHistorieProfesji == -1) {
		nowyBohater.nowaProfesja(nowaProfesja);
		nowyBohater.doswiadczenieBohatera(exp);
		//wyswietlenie nowego bohatera
		obserwator.aktualizujPostac(wyswietlNowegoBohatera(talenty));
		obserwator.wylaczbtnNowaProfesja();
	}
	
	if(!przycisk)
		obserwator.wlaczPrzyciskbtnPodniesPoziomPr();	
}//koniec metody nowaProfesja

}
