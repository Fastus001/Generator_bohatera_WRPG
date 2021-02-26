package mvcOknoGlowne;

import domain.*;
import enums.Gender;
import enums.RaceType;
import export.ExportDoExcela;
import export.ExportToPdf;
import domain.Hero;
import hero.HeroDisplay;
import factories.HeroFactory;
import hero.HeroProgress;
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
public class GenBohModel implements HeroService {

	
	//rasa wybrana w comboBoxie w widoku
	private Race wybranaRasa;
	//profesja wybrana w CBoxie w widoku
	private Profession wybranaProfesja;
	private String urlSavaPdf = null;
	private ArrayList<Race> listaRas;
	private ArrayList<Talent> listaTalentow;	
	private ArrayList<Profession> listaProfesji;
	private ArrayList<Profession> profesjePierwszyPoziom;
	private HeroProgress heroProgress;
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
	public void loadData() {
		boolean czy = true;
		czy = WczytajTalenty();
		czy = WczytajRasy();
		czy = WczytajProfesje();
		
		System.out.println(czy);
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
								.isProfession( false ).build();
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
								.isProfession( false ).build();
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
					if(nazwaP.equals(p.getRaces()[i])){
					profesjePierwszyPoziom.add(p);							
					}
				}	
			}
		}
}

@Override
public void newHero(int raceNumber, int professionNumber, int experience, boolean gender, boolean showTalentDescription) {
	//System.out.println("Numer rasy " + rasa);
	try {
		Profession losowaProfesja;
		Race losowaRasa;
		//sprawdzenie wybopru rasy, jeżeli brak wyboru to losowanie, inaczej wybór z listy
		if( raceNumber == -1) {
			losowaRasa = listaRas.get(losowanieRasy());
			szukajProfesjiPierwszyPoziom(losowaRasa);
		}else {
			losowaRasa = ( Race ) listaRas.get( raceNumber );
			szukajProfesjiPierwszyPoziom(losowaRasa);
		}
		//sprawdzenie wyboru profesji, jeżeli brak wyboru to losowanie, inaczej wybór z listy
		if( professionNumber == -1) {
			losowaProfesja = profesjePierwszyPoziom.get( (int)(Math.random()*profesjePierwszyPoziom.size() )) ;
		}else {
			losowaProfesja = ( Profession ) profesjePierwszyPoziom.get( professionNumber );
		}
		RaceType randomRace = RaceType.getRaceEnumByName( losowaRasa.getName() );
		if( gender ){
//			newHero = new Hero( losowaRasa, losowaProfesja, Gender.MALE);
			heroProgress = new HeroProgress( HeroFactory.getInstance().create( randomRace, losowaProfesja.getName(), Gender.MALE));
		}else{
			heroProgress = new HeroProgress( HeroFactory.getInstance().create( randomRace, losowaProfesja.getName(), Gender.MALE));
//			newHero = new Hero( losowaRasa, losowaProfesja, Gender.FEMALE);
		}
//		newHero.experienceLevel(exp);
		obserwator.aktualizujPostac( showNewHero( showTalentDescription ));
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
public Object[] getProfessionsFirstLevel(Race rs) {
	szukajProfesjiPierwszyPoziom(rs);
	
	return profesjePierwszyPoziom.toArray();
}
@Override
public void subscribeObserver(ObserwatorModel observer) {
	this.obserwator = observer;
	
}

@Override
public String showNewHero(boolean showTalentDescription) {
	HeroDisplay heroDisplay = new HeroDisplay( heroProgress.getHero() );
	return heroDisplay.showHero( showTalentDescription );
}
@Override
public void levelUp(int experience, boolean showTalentDescription) {
	Profession profesjaNowyPoziom = null;
	/*
	 * wgranie akutalnej nazwy profesji do stringa
	 * wgranie aktualnego poziomu aktualnie wybranej klasy postaci
	 */
	String nazwaProfesji = heroProgress.getHero().getProfession().toString();
	int poziomProfesji = heroProgress.getHero().getProfession().getLevel()+1;
	
	if(poziomProfesji>4)
	{
		if(!heroProgress.getHero().getProfession().isFinished())
		{
		int potwierdznie = JOptionPane.showConfirmDialog(null, "Postać osiągneła maksymalny poziom profesji,czy chcesz aby \"ukończyła\" ten poziom?", "Koks", JOptionPane.YES_NO_OPTION);
			if(potwierdznie == JOptionPane.OK_OPTION)
				{
				heroProgress.finishProfession( 5);
				obserwator.aktualizujPostac( showNewHero( showTalentDescription ));
				}
			
		}else {
			JOptionPane.showMessageDialog(null, "Postać osiągnęła maksymalny poziom, wybierz inną profesję jeśli dalej chcesz rozwijać postać!", "Maksymalny poziom", JOptionPane.INFORMATION_MESSAGE);
		}
	}else{
		//sprawdzenie wybranej Profesji czy czasem nie jest inna niż aktualnie rozwijana
		if(wybranaProfesja!=null) {
			int testHistoriiProfesji = heroProgress.checkProfessionHistory( wybranaProfesja);
			if(testHistoriiProfesji>0) {
				profesjaNowyPoziom = wybranaProfesja;
			}
		}else {
			//wczytanie wybranej profesji (lvl1) do zmiennej
			profesjaNowyPoziom = heroProgress.getHero().getProfession();
		}
		
			int sprawdzenieHistoriiProfesji = heroProgress.checkProfessionHistory( profesjaNowyPoziom);
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
			heroProgress.newProfession( profesjaNowyPoziom);
			heroProgress.experienceLevel( experience );
			obserwator.aktualizujPostac( showNewHero( showTalentDescription ));
		}
	}//koniec else
}//koniec metody podnieś poziom
@Override
public void newProfession(int experience, boolean showTalentDescription, boolean btnPodniesPoziomWlaczony) {
	
	//pytanie czy wczesniejsza sciezka ma być ukończona
	if(!heroProgress.getHero().getProfession().isFinished())
	{
		int potwierdzenie = JOptionPane.showConfirmDialog(null, "Czy aktualny poziom profesji ma być ukończony przed zmianą profesji?", "Zmiana profesji!", JOptionPane.YES_NO_OPTION);
		if(potwierdzenie == JOptionPane.OK_OPTION) 
		{
			heroProgress.finishProfession( heroProgress.getHero().getProfession().getLevel()+1);
		}
	}

	
	int sprawdzHistorieProfesji = heroProgress.checkProfessionHistory( wybranaProfesja);
	
	if(sprawdzHistorieProfesji == -1) {
		heroProgress.newProfession( wybranaProfesja);
		heroProgress.experienceLevel( experience );
		//wyswietlenie nowego bohatera
		obserwator.aktualizujPostac( showNewHero( showTalentDescription ));
		obserwator.wylaczbtnNowaProfesja();
	}
	
	if(!btnPodniesPoziomWlaczony )
		obserwator.wlaczPrzyciskbtnPodniesPoziomPr();	
}//koniec metody newProfession
/**
 * @return the newHero
 */
public Hero getHeroProgress() {
	return heroProgress.getHero();
}

@Override
public void setRace(Race race) {
	if( heroProgress != null) {
		if( race.getName().equals( heroProgress.getHero().getRace().getName())) {
			obserwator.wlaczPrzyciskbtnPodniesPoziomPr();
		}
	}
	wybranaRasa = race;
	
}
@Override
public void setProfession(Profession profession) {
	if( heroProgress != null) {
		//jeżeli rasa nie została zmieniona to możemy działać
		if(wybranaRasa.getName().equals( heroProgress.getHero().getRace().getName()))
		{
			int test = heroProgress.checkProfessionHistory( profession );
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
	wybranaProfesja = profession;
}
@Override
public void showHeroTalents(boolean showTalentDescription) {
	if( heroProgress != null)
		obserwator.aktualizujPostac( showNewHero( showTalentDescription ));
}
@Override
public void saveHeroToList() {
	Hero nowy = heroProgress.getHero().toBuilder().build();
	obserwator.aktualizujListeBohaterow(nowy);
}
@Override
public void exportDoPdf(Hero hero) {
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
			new ExportToPdf( hero, urlSavaPdf);
			
		} catch (IOException e) {
			e.printStackTrace();
		}};
		Thread t = new Thread(runnable);
		t.start();

	
}
@Override
public void exportDoExcel(Object[] heroesAndNPC, int index) {
	ExportDoExcela exp = new ExportDoExcela();
	if( index ==0) {
		for(Object obiekt: heroesAndNPC) {
			if(obiekt instanceof Hero ) {
				Hero nBohater = (( Hero ) obiekt).toBuilder().build();
				exp.createBohaterSheet(nBohater);
			}
			if(obiekt instanceof Potwory) {
				Potwory nPotwor = new Potwory((Potwory) obiekt);
				exp.createNPCSheet(nPotwor);
			}
		}
	}else {
		if( heroesAndNPC[index] instanceof Hero ) {
			Hero nBohater = ( ( Hero ) heroesAndNPC[index]).toBuilder().build();
			exp.createBohaterSheet(nBohater);
		}
		if( heroesAndNPC[index] instanceof Potwory) {
			Potwory nPotwor = new Potwory((Potwory) heroesAndNPC[index]);
			exp.createNPCSheet(nPotwor);
		}
	}
	exp.saveWorkBook();	
}

}
