package mvcOknoGlowne;

import commons.*;
import enums.Gender;
import enums.RaceType;
import export.ExportDoExcela;
import export.ExportToPdf;
import commons.Hero;
import hero.HeroDisplay;
import hero.HeroFactory;
import npcGenerator.Potwory;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * 
 * @author Tom
 *
 */
public class GenBohModel implements GenBohModelInterface{

	
	//rasa wybrana w comboBoxie w widoku
	private Race wybranaRasa;
	//profesja wybrana w CBoxie w widoku
	private Profession wybranaProfesja;
	private String urlSavaPdf = null;
	private ArrayList<Race> listaRas;
	private ArrayList<Talent> listaTalentow;	
	private ArrayList<Profession> listaProfesji;
	private ArrayList<Profession> profesjePierwszyPoziom;
	private Hero nowyBohater;
	private ObserwatorModel obserwator;

	public GenBohModel() {
		listaRas = new ArrayList<Race>();
		listaTalentow = new ArrayList<Talent>();
		listaProfesji = new ArrayList<Profession>();
		profesjePierwszyPoziom = new ArrayList<Profession>();

	}
	/**
	 * wczytuje z zasobów rasy, profesje, talenty, umiejetnosci
	 */
	@Override
	public boolean wczytajDane() {
		boolean czy = true;
		czy = WczytajTalenty();
		czy = WczytajRasy();
		czy = WczytajProfesje();
		
		System.out.println(czy);
		return czy;
	}
	
	public boolean WczytajProfesje(){
		try{
			listaProfesji = new ArrayList<Profession>();

			ClassLoader classLoader = getClass().getClassLoader();
			InputStream input = classLoader.getResourceAsStream("profesje-v2.txt");
			InputStreamReader czytaj = new InputStreamReader( input, StandardCharsets.UTF_8);
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
				
				//dostępne umiejętności
				wiersz = czytajB.readLine();
				String dostepneUm[] = wiersz.split(",");
				
				ArrayList<Skill> umiej = new ArrayList<Skill>();
				for(String x:dostepneUm){
						String[] doZapisaniaUm = x.split("/");
						Skill tempUm = Skill.builder()
								.name( doZapisaniaUm[0] )
								.statNumber( Integer.parseInt( doZapisaniaUm[1]) )
								.type( doZapisaniaUm[2] )
								.level( 0 )
								.isProfessional( false ).build();
						umiej.add(tempUm);
					}
				
				//dostępne talenty
				wiersz = czytajB.readLine();
				String dostepneTlnt[] = wiersz.split(",");
				
				ArrayList<Talent> listaZnTalentow = new ArrayList<Talent>();
					
					for(String x:dostepneTlnt){
						String[] doZapTalenty = x.split("/");

//						Talent tempTlnt = new Talent(doZapTalenty[0],Integer.parseInt(doZapTalenty[1]),doZapTalenty[2]);
						Talent tempTlnt = Talent.builder()
								.name(doZapTalenty[0])
								.relatedStat( parseInt(doZapTalenty[1]))
								.test( doZapTalenty[2]).build();
						tempTlnt.setDescription( dodajOpisDoTalentu( tempTlnt));
						listaZnTalentow.add(tempTlnt);
					}
				//przedmioty
				wiersz = czytajB.readLine();
				String przedmiotyProf = wiersz;
				
				//cechy rozwoju
				wiersz = czytajB.readLine();
				String wynik3[] = wiersz.split(",");
				int [] cechyRozwoju = new int [wynik3.length];
				for (int i = 0; i<wynik3.length; i++){
					cechyRozwoju[i] = Integer.parseInt(wynik3[i]);
				}
				//poziom profesji
				int poziomProfesji = Integer.parseInt(czytajB.readLine());
				
				Profession prof = new Profession( nazwaProfesji, sciezkaProfesji, poziomProfesji, umiej, listaZnTalentow, dostepneRasy, cechyRozwoju, false, klasaProfesji, przedmiotyProf );
				listaProfesji.add(prof);

			}
			czytajB.close();
		
			
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
}//koniec metody
	
	public boolean WczytajTalenty(){
		try{
				listaTalentow = new ArrayList<Talent>();

				ClassLoader classLoader = getClass().getClassLoader();
				InputStream input = classLoader.getResourceAsStream("talenty.txt");
				InputStreamReader czytaj = new InputStreamReader(input,StandardCharsets.UTF_8);
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
		String[] wynik = wierszDanych.split(";");
		Talent tl = Talent.builder()
				.name(wynik[0])
				.relatedStat(parseInt( wynik[1] ))
				.test( wynik[2])
				.description(wynik[3] ).build();
		listaTalentow.add(tl);
	}
	
	
public boolean WczytajRasy(){
		
		try{
			listaRas = new ArrayList<Race>();

			ClassLoader classLoader = getClass().getClassLoader();
			InputStream input = classLoader.getResourceAsStream("rasy.txt");
			InputStreamReader czytaj = new InputStreamReader(input,StandardCharsets.UTF_8);
			
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
					ArrayList<Skill> umiej = new ArrayList<Skill>();
					for(String x:wynik2){
						String[] doZapisaniaUm = x.split("/");
						Skill tempUm = Skill.builder()
								.name( doZapisaniaUm[0] )
								.statNumber( Integer.parseInt( doZapisaniaUm[1]) )
								.type( doZapisaniaUm[2] )
								.level( 0 )
								.isProfessional( false ).build();
						umiej.add(tempUm);
					}
					//konwersja talentow na obiekty
					ArrayList<Talent> listaZnTalnetow = new ArrayList<Talent>();
					
					for(String x:talenty){
						String[] doZapTalenty = x.split("/");
						Talent tempTlnt = Talent.builder()
								.name(doZapTalenty[0])
								.relatedStat( parseInt(doZapTalenty[1]))
								.test( doZapTalenty[2]).build();
						//todo dodanie opisu do talentow
						tempTlnt.setDescription( dodajOpisDoTalentu( tempTlnt));
						listaZnTalnetow.add(tempTlnt);
					}
					
					
//					Race rs = new Race( nazwa, tablica , umiej, listaZnTalnetow, Integer.parseInt( wiersz));
					Race rs = Race.builder().name( nazwa ).skills( umiej ).talents( listaZnTalnetow ).build();
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
			if(temp.getName().equals( tal.getName())){
				return temp.getDescription();
			}
		}
	}
	return "brak opis - ups";
}
/////////////////////////////////////////////////////////////////////////
//szansa wylosowanie jak w podręczniku 4ed
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
public void szukajProfesjiPierwszyPoziom(Race losowaRasa) {
		profesjePierwszyPoziom.clear();
		
		//sprawdzenie profesji pąd kątem poziomy (lvl1) oraz tego jaka rasa w danej profesji jest dostępna
		int poziomPierwszy = 1;
		for(Profession p:listaProfesji){
			if(p.getLevel()==poziomPierwszy){
				for(int i = 0; i < p.getRaces().length; i++){
					String nazwaP = losowaRasa.getName();
					if(nazwaP.equals(p.races[i])){
					profesjePierwszyPoziom.add(p);							
					}
				}	
			}
		}
}

@Override
public void nowyBohater(int rasa, int prof,int exp, boolean plec, boolean oT) {
	//System.out.println("Numer rasy " + rasa);
	try {
		Profession losowaProfesja;
		Race losowaRasa;
		//sprawdzenie wybopru rasy, jeżeli brak wyboru to losowanie, inaczej wybór z listy
		if(rasa == -1) {
			losowaRasa = listaRas.get(losowanieRasy());
			szukajProfesjiPierwszyPoziom(losowaRasa);
		}else {
			losowaRasa = ( Race ) listaRas.get( rasa);
			szukajProfesjiPierwszyPoziom(losowaRasa);
		}
		//sprawdzenie wyboru profesji, jeżeli brak wyboru to losowanie, inaczej wybór z listy
		if(prof == -1) {
			losowaProfesja = profesjePierwszyPoziom.get( (int)(Math.random()*profesjePierwszyPoziom.size() )) ;
		}else {
			losowaProfesja = ( Profession ) profesjePierwszyPoziom.get( prof);
		}
		RaceType randomRace = RaceType.getRaceEnumByName( losowaRasa.getName() );
		if(plec){
//			nowyBohater = new Hero( losowaRasa, losowaProfesja, Gender.MALE);
			nowyBohater = HeroFactory.getInstance().create( randomRace, losowaProfesja.getName(), Gender.MALE);
		}else{
			nowyBohater = HeroFactory.getInstance().create( randomRace, losowaProfesja.getName(), Gender.MALE);
//			nowyBohater = new Hero( losowaRasa, losowaProfesja, Gender.FEMALE);
		}
//		nowyBohater.doswiadczenieBohatera(exp);
		obserwator.aktualizujPostac(wyswietlNowegoBohatera(oT));
		wybranaRasa = losowaRasa.toBuilder().build();
		wybranaProfesja = losowaProfesja.toBuilder().build();
	} catch (Exception e2) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e2.printStackTrace(pw);
	}
	
}
/**
 * @return the wybranaRasa
 */
public Race getWybranaRasa() {
	return wybranaRasa;
}
/**
 * @return the wybranaProfesja
 */
public Profession getWybranaProfesja() {
	return wybranaProfesja;
}
/**
 * @param wybranaRasa the wybranaRasa to set
 */
public void setWybranaRasa(Race wybranaRasa) {
	this.wybranaRasa = wybranaRasa;
}
/**
 * @param wybranaProfesja the wybranaProfesja to set
 */
public void setWybranaProfesja(Profession wybranaProfesja) {
	this.wybranaProfesja = wybranaProfesja;
}

@Override
public Object[] getRasaArray() {
	return listaRas.toArray();
}
@Override
public Object[] getProfesjePierwszyPoziom(Race rs) {
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
	HeroDisplay heroDisplay = new HeroDisplay( nowyBohater );
	return heroDisplay.showHero(jak);
}
@Override
public void podniesPoziom(int exp, boolean talenty) {
	Profession profesjaNowyPoziom = null;
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
		int potwierdznie = JOptionPane.showConfirmDialog(null, "Postać osiągneła maksymalny poziom profesji,czy chcesz aby \"ukończyła\" ten poziom?", "Koks", JOptionPane.YES_NO_OPTION);
			if(potwierdznie == JOptionPane.OK_OPTION)
				{
				nowyBohater.finishProfession( 5);
				obserwator.aktualizujPostac(wyswietlNowegoBohatera(talenty));
				}
			
		}else {
			JOptionPane.showMessageDialog(null, "Postać osiągnęła maksymalny poziom, wybierz inną profesję jeśli dalej chcesz rozwijać postać!", "Maksymalny poziom", JOptionPane.INFORMATION_MESSAGE);
		}
	}else{
		//sprawdzenie wybranej Profesji czy czasem nie jest inna niż aktualnie rozwijana
		if(wybranaProfesja!=null) {
			int testHistoriiProfesji = nowyBohater.checkProfessionHistory( wybranaProfesja);
			if(testHistoriiProfesji>0) {
				profesjaNowyPoziom = wybranaProfesja;
			}
		}else {
			//wczytanie wybranej profesji (lvl1) do zmiennej
			profesjaNowyPoziom = nowyBohater.getProfession();
		}
		
			int sprawdzenieHistoriiProfesji = nowyBohater.checkProfessionHistory( profesjaNowyPoziom);
			if(sprawdzenieHistoriiProfesji != -1)
			{
				/*
				 * jeśli nie była to przypisujemy nowy poziom profesji jaka ma być wyszukana do rozwiniecia, plus nazwa tej profesji, przy założeniu
				 * że poziom nie jest wyższy od 4 
				 */
				if((sprawdzenieHistoriiProfesji+1) <5)
				{
					nazwaProfesji = profesjaNowyPoziom.toString();
					poziomProfesji = sprawdzenieHistoriiProfesji+1;
				}else {
					JOptionPane.showMessageDialog(null, "Wybrana profesja posiada już maksymlany poziom, postać awansuje we wcześniej wybranej profesji", "Maksymalny poziom wybranej profesjii!!", JOptionPane.INFORMATION_MESSAGE);
				}								
				//System.out.println("Poziom tej samej profesji z najwyższym poziomem z historii to  = " + sprawdzenieHistoriiProfesji);				
			}
		
	for(Profession p: listaProfesji)
	{
		if(p.toString().equals(nazwaProfesji) && (p.getLevel() == poziomProfesji))
		{
			profesjaNowyPoziom = p.toBuilder().build();
		}
	}
	
			
	if(profesjaNowyPoziom != null)
		{
			nowyBohater.newProfession( profesjaNowyPoziom);
			nowyBohater.doswiadczenieBohatera(exp);
			obserwator.aktualizujPostac(wyswietlNowegoBohatera(talenty));	
		}
	}//koniec else
}//koniec metody podnieś poziom
@Override
public void nowaProfesja(int exp, boolean talenty, boolean przycisk) {
	
	//pytanie czy wczesniejsza sciezka ma być ukończona
	if(!nowyBohater.getProfesjaUkonczona())
	{
		int potwierdzenie = JOptionPane.showConfirmDialog(null, "Czy aktualny poziom profesji ma być ukończony przed zmianą profesji?", "Zmiana profesji!", JOptionPane.YES_NO_OPTION);
		if(potwierdzenie == JOptionPane.OK_OPTION) 
		{
			nowyBohater.finishProfession( nowyBohater.getCurrentProfPoziom()+1);
		}
	}

	
	int sprawdzHistorieProfesji = nowyBohater.checkProfessionHistory( wybranaProfesja);
	
	if(sprawdzHistorieProfesji == -1) {
		nowyBohater.newProfession( wybranaProfesja);
		nowyBohater.doswiadczenieBohatera(exp);
		//wyswietlenie nowego bohatera
		obserwator.aktualizujPostac(wyswietlNowegoBohatera(talenty));
		obserwator.wylaczbtnNowaProfesja();
	}
	
	if(!przycisk)
		obserwator.wlaczPrzyciskbtnPodniesPoziomPr();	
}//koniec metody newProfession
/**
 * @return the nowyBohater
 */
public Hero getNowyBohater() {
	return nowyBohater;
}
@Override
public Hero postacBohaterModel() {
	return this.getNowyBohater();
}
@Override
public void setRasa(Race r) {
	if(nowyBohater != null) {
		if(r.getName().equals(nowyBohater.getRace().getName())) {
			obserwator.wlaczPrzyciskbtnPodniesPoziomPr();
		}
	}
	wybranaRasa = r;
	
}
@Override
public void setProfesja(Profession p) {
	if(nowyBohater != null) {
		//jeżeli rasa nie została zmieniona to możemy działać
		if(wybranaRasa.getName().equals(nowyBohater.getRace().getName()))
		{
			int test = nowyBohater.checkProfessionHistory( p);
			//postać wcześniej nie rozwijała danej profesji, można włączyc opcję nowa profesja
			if(test== -1) {
				obserwator.wlaczbtnNowaProfesja();
			}else {
				obserwator.wylaczbtnNowaProfesja();
			}
			
		}else {
			JOptionPane.showMessageDialog(null, "Zmieniłeś rasę, nie możesz modyfikować postaci do momentu gdy wybór rasy będzie zgodny z aktulanie towrzoną postacią.");
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
	Hero nowy = new Hero( nowyBohater);
	obserwator.aktualizujListeBohaterow(nowy);
}
@Override
public void exportDoPdf(Hero nBohater) {
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
			new ExportToPdf( nBohater, urlSavaPdf);
			
		} catch (IOException e) {
			e.printStackTrace();
		}};
		Thread t = new Thread(runnable);
		t.start();

	
}
@Override
public void exportDoExcel(Object[] obj,int ktora) {
	ExportDoExcela exp = new ExportDoExcela();
	if(ktora ==0) {
		for(Object obiekt:obj) {
			if(obiekt instanceof Hero ) {
				Hero nBohater = new Hero( ( Hero ) obiekt);
				exp.createBohaterSheet(nBohater);
			}
			if(obiekt instanceof Potwory) {
				Potwory nPotwor = new Potwory((Potwory) obiekt);
				exp.createNPCSheet(nPotwor);
			}
		}
	}else {
		if(obj[ktora] instanceof Hero ) {
			Hero nBohater = new Hero( ( Hero ) obj[ktora]);
			exp.createBohaterSheet(nBohater);
		}
		if(obj[ktora] instanceof Potwory) {
			Potwory nPotwor = new Potwory((Potwory) obj[ktora]);
			exp.createNPCSheet(nPotwor);
		}
	}
	exp.saveWorkBook();	
}

}
