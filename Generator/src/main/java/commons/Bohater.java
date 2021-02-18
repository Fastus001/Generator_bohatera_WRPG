package commons;

import appearance.Appearance;
import appearance.AppearanceFactory;
import utilities.NameGenerator;
import utilities.RandomTalent;

import java.util.ArrayList;
import java.util.Collections;

public class Bohater {


	private String imieNazwisko;
	private String plecBohatera;
	private Appearance appearance;
	private Race race;
	private Profession prof;
	private Stats stats;
	public ArrayList <Skill> znaneUmiejetnosci;
	public ArrayList<Talent> znaneTalenty;
	private ArrayList<Profession> historiaProfesji;
	

	public Bohater(Race rs, Profession pr, boolean plec) {
		race = rs.toBuilder().build();
		prof = pr.toBuilder().build();
		
		NameGenerator genImion = new NameGenerator();
		imieNazwisko = genImion.generateFullName( race.getRaceEnum(), plec);
		
		appearance = AppearanceFactory.create( race.getRaceEnum());
		
		if(plec)
			plecBohatera = "Mężczyzna";
		else
			plecBohatera = "Kobieta";

		
		stats = new Stats( rs.baseStats, rs.getRaceEnum());
		znaneUmiejetnosci = new ArrayList<Skill>();
		dodajZnaneUmiejetnosciZRasy();
		znaneTalenty = new ArrayList<Talent>();
		dodajZnaneTalentyZRasy();
		dodajZnanyTalentZProfesji();
		for(Talent t:znaneTalenty){
			sprawdzTalenty(t);
		}
		
		dodajZnaneUmiejetnosciZProfesjiLosowePoczatkowe();
		//dodajZnaneUmiejetnosciZProfesji(prof.getPoziom());
		dodajLosowoPoczatkoweRozwiniecieCech();
		postacLosowyBonus(1);
		
		historiaProfesji = new ArrayList<Profession>();
		historiaProfesji.add(prof);
	}
	
/**
 * Tworzy nową kopię bohatera
 * @param bh - bohater 
 */
	public Bohater(Bohater bh) {
		this.imieNazwisko = bh.imieNazwisko;
		this.plecBohatera = bh.plecBohatera;
		this.appearance = bh.appearance;
		this.race = bh.race.toBuilder().build();
		this.prof = bh.prof.toBuilder().build();
		this.stats = new Stats( bh.stats );
		this.znaneUmiejetnosci = new ArrayList<Skill>();
		for(Skill um:bh.znaneUmiejetnosci) {
			Skill nowa = um.toBuilder().build();
			this.znaneUmiejetnosci.add(nowa);
		}
		this.znaneTalenty = new ArrayList<Talent>();
		for(Talent tl:bh.znaneTalenty) {
			Talent nowyTl = tl.toBuilder().build();
			this.znaneTalenty.add(nowyTl);
		}
		this.historiaProfesji = new ArrayList<Profession>();
		for(Profession pr:bh.historiaProfesji) {
			Profession nowaPr = pr.toBuilder().build();
			this.historiaProfesji.add(nowaPr);
		}
	}
	/**
	 * Wyświetla imię nazwizko, nazwę profesji oraz ścieżkę profesji
	 */
	@Override
	public String toString() {
		return imieNazwisko + ", Profesja: " + prof.getName(getPlecBohatera()) + " poziom profesji: " + prof.getLevel();
	}
	
	/*
	 * metoda ktďż˝ra podnosi cechy aby moďż˝na byďż˝o ukoďż˝czyc dany poziom profesji (przewaďż˝nie 4 poziom)
	 */
	public void ukonczPoziomProfesji(int poziom) {
		if(prof.getLevel() < poziom){
			int minPoziomUm = 5*prof.getLevel();
			//sprawdzenie umiejetnosci
			nowyPoziomUmiejetnosciNowyLvl(minPoziomUm);
			//sprawdzenie cech
			nowyPoziomCechyNowyLvl(minPoziomUm);
		}
		for(Talent t:znaneTalenty){
			sprawdzTalenty(t);
		}
		
		//uaktualnienie poziomu zycia
		stats.updateHp(this.getCzyJestTwardziel());
		prof.setFinished( true);
	}
	
		
	public void nowaProfesja(Profession nowaProfesja){
		historiaProfesji.add(nowaProfesja);
		setUmiejetnosciProfesyjne();
		
		if(prof.getLevel() < nowaProfesja.getLevel()){
			int minPoziomUm = 5*prof.getLevel();
			//sprawdzenie umiejetnosci
			nowyPoziomUmiejetnosciNowyLvl(minPoziomUm);
			//sprawdzenie cech
			nowyPoziomCechyNowyLvl(minPoziomUm);
		}
		appearance.addAge();
		
		if(!prof.isFinished())
			prof.setFinished( true);
		
		if(!prof.toString().equals(nowaProfesja.toString()))
		{
			System.out.println("Kompletnie nowa profesja, zmieniam umiejętności profesyjne");
			for(Skill um:znaneUmiejetnosci)
				um.setProfessional( false);
		}
		prof = nowaProfesja.toBuilder().build();
		dodajZnaneUmiejetnosciZProfesji();
		setUmiejetnosciProfesyjne();
		
		if(prof.toString().equals("CZARODZIEJ") && prof.getLevel()==2 ){
			Talent nowy = prof.getTalents().get( 0 );
			nowy.setTalentMax( stats );
			znaneTalenty.add(nowy);
		}else{
			dodajZnanyTalentZProfesji();
		}
		
		for(Talent t:znaneTalenty){
			sprawdzTalenty(t);
		}
		
		//uaktualnienie poziomu zycia
		stats.updateHp( this.getCzyJestTwardziel());
	}
	
	private void setUmiejetnosciProfesyjne() {
		ArrayList<Skill> tablicaUmiejetnosci = new ArrayList<Skill>();
		//zapisanie całej historii w tablicy
		for(Profession p:historiaProfesji) {
			if(p.toString().equals(prof.toString())) {
				for(Skill umP: p.skills) {
					tablicaUmiejetnosci.add(umP);
				}
			}
		}

		//ustawienie z całej histrii umiejetnosci jako profesyjnych
		for(Skill um:tablicaUmiejetnosci) {
			for(Skill znaneUm:znaneUmiejetnosci) {
				if(um.showSkill().equals( znaneUm.showSkill())) {
					znaneUm.setProfessional( true);
				}
			}
		}
	}

	//sprawdzenie czy atrybuty klasowe majďż˝ odpowiedniu poziom do przejscia na nowy poziom
	public void nowyPoziomCechyNowyLvl(int minPoz){
		int[] klasoweAtrybuty = prof.getProfessionStats();
		for(int i = 0; i < klasoweAtrybuty.length; i++){
			int x = stats.getAdvancesAt( klasoweAtrybuty[i]);
				if(x < minPoz){
				System.out.println("Roziwniecie cechy: " + x);
				x = minPoz - x;
				System.out.println("Roziwniecie cechy do dodania: " + x);
				stats.increaseStatAt( x, klasoweAtrybuty[i], true);
			}
		} 
	}
	
	//sprawdzenie znanych umiejetnosci czy jest przynajmniej osiem na minimalnym poziomie rowiniecia do przejscia na nowďż˝ profesjďż˝

	public void nowyPoziomUmiejetnosciNowyLvl(int minPozUm) {
		int ile = 0;
		for(Skill um:znaneUmiejetnosci){
			if(um.isProfessional() && um.getLevel() >= minPozUm)
			{
				ile++;
			}
		}
		if(ile<8){
			System.out.println("Brak wystarczającej ilosci umiejetnosci na odpowiednim poziomie, jest tylko " + ile);
			/*
			 * metoda podnosi o jeden umiejętności ale tylko takiej, która ma poziom niższy od wymaganego, czyli efektywnie nie będzie 
			 * podnosic poziomu ponad 20
			 */
			podniesUmiejRandomMinPoz(3,minPozUm);
			nowyPoziomUmiejetnosciNowyLvl(minPozUm);
		}
	}
	
		
	//pewnie siďż˝ zmieni nazwe
	public String wyswietlBohatera(boolean czyWyswietlicTalent){
		
		StringBuilder stringBuilder = new StringBuilder( race.getName()+"\n" +imieNazwisko +" ("+ plecBohatera + ")\n");
		stringBuilder.append( appearance.showAll());
		stringBuilder.append("Klasa postaci: " + prof.getCareer()+"\n");
		stringBuilder.append(prof.getNameAndProfessionPath( getPlecBohatera()));
		stringBuilder.append(" (Poziom profesji: " + prof.getLevel()+")\n");
		stringBuilder.append("Historia rozwoju bohatera: ");
		for(int i = 0; i < (historiaProfesji.size()-1); i++)
		{
			if(i>=0)
			{
				String[] nazwaSciezkiProfesji = historiaProfesji.get(i).getProfessionPath( getPlecBohatera()).split( "-");
				stringBuilder.append("ex-"+nazwaSciezkiProfesji[0] + "" + "(" + historiaProfesji.get(i).getName(getPlecBohatera())+"),");
			}
		}
		stringBuilder.append("\n");
		stringBuilder.append( stats.showStats( prof.getProfessionStats()));
		stringBuilder.append("\nZnane Umiejętności:\n");
		
		Collections.sort(znaneUmiejetnosci);
		for(Skill u:znaneUmiejetnosci){
			
			int poziomTestowanejUmiejetn = stats.getStatAt( u.getStatNumber()) + u.getLevel();
			stringBuilder.append(u.showSkill() + " (" + Integer.toString( poziomTestowanejUmiejetn) + "), ");
		}
		stringBuilder.append("\n\nZnane talenty:\n");
		
		Collections.sort(znaneTalenty);
		for(Talent t: znaneTalenty){
			stringBuilder.append(t.showTalentNameWithLevel()+", ");
		}
		stringBuilder.append("\n\nPrzedmioty dostępne z profesji:\n");
		for(Profession p:historiaProfesji) {
			String [] tab = p.getItems();
			for(String s:tab) {
				stringBuilder.append(s.toLowerCase()+", ");
			}
		}
		stringBuilder.append("\n");
		
		
		if(czyWyswietlicTalent){
			for(Talent st: znaneTalenty){
			if(st.isShow()){
				stringBuilder.append(st.getName() + " - " +st.getDescription() +"\nTesty: " + st.getTest() +"\nMaksymalny poziom Talentu " + st.getMaxLevel() + "\n\n");
				}		
			}
		}

		return stringBuilder.toString();
	}
	
	//metoda tylko dla nowych postaci, wszelkie wyďż˝sze poziomy muszďż˝ juďż˝ sprawdzaďż˝ profesyjne umiejetnoďż˝ci i minimum dla przejscia na nowy poziom profesji
	private void dodajZnaneUmiejetnosciZProfesjiLosowePoczatkowe(){
		
		for(int i = 0; i < 40; i++) {
			int random = (int) (Math.random()*prof.getSkills().size());
			if(prof.skills.get( random).getLevel()<11){
				prof.skills.get( random).addToSkillLevel( 1);
			}else{
				i--;
			}
		}

		for (Skill um:prof.getSkills()) {
			for(Skill umZnane: znaneUmiejetnosci){
				//jezeli juz jest taka umiejetnosc to dodajemy do znanej +5, a nowďż˝ ustawiamy na -10
				if(um.getName().equals( umZnane.getName())){
					umZnane.addToSkillLevel( um.getLevel());
					umZnane.setProfessional( true);
					um.addToSkillLevel( -40);
					break;
				}	
			}
			//jezeli poziom umiejetnosci jest na minus, to nie dodawaj jej do znanych umiejetnosci
			if(um.getLevel() >= 0){
				um.setProfessional( true);
				znaneUmiejetnosci.add(um);
			}
		}
	}
	
	private void dodajZnaneUmiejetnosciZProfesji(){
		for (Skill um:prof.getSkills()) {
			for(Skill umZnane: znaneUmiejetnosci){
				//jezeli juz jest taka umiejetnosc to ignorujemy
				if(um.getName().equals( umZnane.getName())){
					//umZnane.addToSkillLevel(um.getPoziom());
					umZnane.setProfessional( true);
					um.addToSkillLevel( -40);
					break;
				}	
			}
			//jezeli poziom umiejetnosci jest na minus, to nie dodawaj jej do znanych umiejetnosci
			if(um.getLevel() >= 0){
				System.out.println("Dodajemy nowś umiejętność z nowej profesji " + um.showSkill());
				um.setProfessional( true);
				znaneUmiejetnosci.add(um);
			}
		}
	}
		
	
	//zaleca siďż˝ najpierw zaczytac dostepne umiejetnoďż˝ci z rasy a dopiero potem z profesji
	//
	@SuppressWarnings("unused")
	private void dodajZnaneUmiejetnosciZProfesji(int x){
		for (Skill um:prof.getSkills()) {
			for(Skill umZnane: znaneUmiejetnosci){
				//jezeli juz jest taka umiejetnosc to dodajemy do znanej +5, a nowďż˝ ustawiamy na -10
				if(um.getName().equals( umZnane.getName())){
					umZnane.addToSkillLevel( 5*x);
					um.addToSkillLevel( -40);
					break;
				}	
			}
			um.addToSkillLevel( 5*x);
			//jezeli poziom umiejetnosci jest na minus, to nie dodawaj jej do znanych umiejetnosci
			if(um.getLevel() > 0){
				znaneUmiejetnosci.add(um);
			}
		}
	}
	
	
	private void dodajZnaneUmiejetnosciZRasy() {
		
		ArrayList<Skill> tempRasaZnaneUmiejetnosci = new ArrayList<Skill>();
		
		//skopiowanie listy dostďż˝pnych umiejetnoďż˝ci.
		for(Skill m: race.getSkills()){
			tempRasaZnaneUmiejetnosci.add(m);
		}
		
		for(int i = 0; i < 6; i++){	
			int numer = (int) (Math.random()*tempRasaZnaneUmiejetnosci.size());
					
			Skill nowaUmiejetnosc = tempRasaZnaneUmiejetnosci .get( numer);
			if(i<3){
				nowaUmiejetnosc.setLevel(5);
				}else{
					nowaUmiejetnosc.setLevel(3);
				}
			znaneUmiejetnosci.add(nowaUmiejetnosc);
			tempRasaZnaneUmiejetnosci.remove(numer);
		}
	}
	
	private void dodajZnaneTalentyZRasy(){
		// ludzie wybďż˝r miďż˝dzy 0 a 1, w dostďż˝pnych talentach
		//krasnoludy majďż˝ do wyboru pomiďż˝dzy 0 a 1, oraz 2-3
		// wysokie elfy 0 lub 1  oraz 2 lub 3
		//lesne elfy, 0 lub 1, oraz 2 lub 3
		if( race.getName().equals( "Ludzie")){
			znaneTalenty.add( race.getTalents().get( randomX( 2)));
		}else{
			znaneTalenty.add( race.getTalents().get( randomX( 2)));
			znaneTalenty.add( race.getTalents().get( randomX( 2)+2));
			if( race.getSizeOfAvailableTalents() >3){
				for(int i = 4; i< race.getSizeOfAvailableTalents(); i++){
					znaneTalenty.add( race.getTalents().get( i));
				}
			}
		}
		for(Talent t:znaneTalenty){
			t.setTalentMax( stats );
		}
		//dodanie losowych talentow, + sprawdzenie czy siďż˝ nie powtarzajďż˝, ewentualnie zwiďż˝kszenie o 1
		int losoweTalenty = race.getRandomTalents();
		if(losoweTalenty > 0 ) {
			for(int i= 0; i<losoweTalenty; i++){
//				Talent nowyTalent = race.getRandomTalent();
				Talent nowyTalent = RandomTalent.getInstance().getTalent();
				int test = sprawdzCzyTalentJest(nowyTalent);
				if(test == -1){
					nowyTalent.setTalentMax( stats );
					znaneTalenty.add(nowyTalent);
					System.out.println("Nowy losowy talent z rasy= " + nowyTalent.getName() + " numer i = " + i);
				}else{
						if(znaneTalenty.get(test).getMaxLevel() == znaneTalenty.get( test).getLevel()){
							System.out.println("Znany talent z rasy, który ma juz maks = " + znaneTalenty.get(test).getName()+ " numer i = " + i);
							i--;
						}else{
							
							znaneTalenty.get(test).addOneToLevel();
							System.out.println("Znany talent z rasy, podniesienie poziomu o 1 = " + znaneTalenty.get(test).getName()+ " numer i = " + i);
						}
						
				}
				
			}
		}
		
		
	}
	//dodaje jeden losowy talent z profesjii
	//ustalamy maksimum dla talentu oraz sprawdza wczeďż˝niej czy juz nie jest dodany
	
	//uwaga!! - moďż˝e siďż˝ zdarzyďż˝ ďż˝e siďż˝ ta metoda zapďż˝tli jak wszystkie talenty bďż˝dďż˝ miaďż˝y maksymalny poziom
	private void dodajZnanyTalentZProfesji() {
		int iloscTalentow = prof.getTalents().size();
		Talent losowyTalent = prof.getTalents().get( randomX( iloscTalentow));
		
		// sprawdzanie czy talenty siďż˝ powtarzajďż˝
		int test = sprawdzCzyTalentJest(losowyTalent);
		System.out.println(test);
		if(test == -1){
					losowyTalent.setTalentMax( stats );
					znaneTalenty.add(losowyTalent);
					System.out.println("Nowy losowy talent z profesji= " + losowyTalent.getName());
				}else{
						if(znaneTalenty.get(test).getMaxLevel() == znaneTalenty.get( test).getLevel()){
							System.out.println("Znany talent, który ma juz maks z profesji= " + znaneTalenty.get(test).getName());
							dodajZnanyTalentZProfesji();
						}else{
							znaneTalenty.get(test).addOneToLevel();
							System.out.println("Znany talent, podniesienie poziomu o 1 = " + znaneTalenty.get(test).getName());
						}	
				}	
	}
	
	private int sprawdzCzyTalentJest(Talent nowyTalent){
		for(Talent t : znaneTalenty){
			if(t.getName().equals( nowyTalent.getName())){
				return znaneTalenty.indexOf(t);
			}
		}
		return -1;
	}
	
	private void dodajLosowoPoczatkoweRozwiniecieCech(){
		
		stats.increaseStatAt( 5, prof.getRandomProfessionStat(), true);
	}
	
	//w sumie moďż˝na teďż˝ uďż˝yďż˝ tej metody, do okreďż˝lenia, robienia bardziej zaawansowanej postaci
	public void postacLosowyBonus(int ileRazy) {
		for(int n = 0; n < ileRazy; n++){
			int opcja = (int) (Math.random()*10);
		if(opcja <3){
			opcja = 0;
		}else if(opcja>2 && opcja < 7){
			opcja = 1;
		}else {
			opcja = 2;
		}
		switch(opcja){
			case 0: for(int i = 0; i < 4; i++){
				stats.increaseStatAt( 1, prof.getRandomProfessionStat(), true);
			};System.out.println("losowa cecha dodatkowa"); break;
			case 1: podniesUmiejRandom(6); System.out.println("losowe umiejetnosci");break;
			case 2: dodajZnanyTalentZProfesji(); System.out.println("losowey talent");break;
			}//koniec switch
		}//koniec pďż˝li fo
	}
	
	//podniesienie umiejetnosci losowe
		public void podniesUmiejRandom(int ileRazy) {
			for(int i = 0; i < ileRazy; i++){
				int ktora = (int) (Math.random()*znaneUmiejetnosci.size());
				znaneUmiejetnosci.get(ktora).addToSkillLevel( 1);
			}
		}
		// podniesienie umiejetnosci, ale z uwzgledniemie tylko tych ponizej konkretnego poziomu
		public void podniesUmiejRandomMinPoz(int ileRazy, int poziomUm) {
			for(int i = 0; i < ileRazy; i++){
				int ktora = (int) (Math.random()*znaneUmiejetnosci.size());
				if(znaneUmiejetnosci.get(ktora).getLevel() <poziomUm){
					znaneUmiejetnosci.get(ktora).addToSkillLevel( 1);
				}
				
			}
		}
	
	
	//moloch odpowiadajďż˝cy za dodanie lub opisanie wszystkich cech, bonusďż˝w z talentďż˝w
	private void sprawdzTalenty(Talent talent) {
		Skill nowa;
		switch(talent.getName()) {
			case "Urodzony Wojownik": if(talent.isShow()){
				stats.increaseStatAt( 5, 0, false); talent.setShow( false);
				}break;
			case "Strzelec Wyborowy": if(talent.isShow()){
				stats.increaseStatAt( 5, 1, false); talent.setShow( false);
				}break;
			case "Bardzo Silny": if(talent.isShow()){
			stats.increaseStatAt( 5, 2, false);talent.setShow( false);
				}break;
			case "Niezwykle Odporny": if(talent.isShow()){
			stats.increaseStatAt( 5, 3, false); talent.setShow( false);
				}break;
			case "Czujny": if(talent.isShow()){
			stats.increaseStatAt( 5, 4, false); talent.setShow( false);
				}break;
			case "Szybki Refleks": if(talent.isShow()){
			stats.increaseStatAt( 5, 5, false); talent.setShow( false);
				}break;
			case "Zręczny": if(talent.isShow()){
			stats.increaseStatAt( 5, 6, false); talent.setShow( false);
				}break;
			case "Błyskotliwość":  if(talent.isShow()){
			stats.increaseStatAt( 5, 7, false); talent.setShow( false); System.out.println( "Błyskotliwość, int podniesiony!");
				}break;
			case "Zimna krew": if(talent.isShow()){
			stats.increaseStatAt( 5, 8, false); talent.setShow( false);
				}break;
			case "Charyzmatyczny": if(talent.isShow()){
				stats.increaseStatAt( 5, 9, false); talent.setShow( false);System.out.println( "Charyzmatyczny, ogd podniesiona!");
				}break;
			case "Bardzo Szybki": if(talent.isShow()){
				stats.addOneToSpeed(); talent.setShow( false);
				}break;

			case "Słuch Absolutny":  nowa = Skill.builder().name( "Występy (Śpiewanie)").statNumber(9).type( "podstawowa").level( 0).isProfessional( false ).build(); prof.getSkills().add( nowa); break;
			case "Obieżyświat":  nowa = Skill.builder().name( "Wiedza (Lokalna)").statNumber(7).type("zaawansowana").level(0).isProfessional(false).build(); prof.getSkills().add( nowa); break;
			case "Czarownica!":  nowa = Skill.builder().name( "Język (Magiczny)").statNumber(7).type("zaawansowana").level(0).isProfessional(false).build(); prof.getSkills().add( nowa); break;
			case "Wytwórca (Dowolny)":  nowa = Skill.builder().name("Rzemiosło (Dowolny)").statNumber(6).type("zaawansowana").level(0).isProfessional(false).build(); prof.getSkills().add( nowa); break;
			case "Wytwórca (Materiały Wybuchowe)":  nowa = Skill.builder().name( "Rzemiosło (Materiały Wybuchowe)").statNumber(6).type("zaawansowana").level(0).isProfessional(false).build(); prof.getSkills().add( nowa); break;
			case "Wytwórca (Zielarz)":  nowa = Skill.builder().name( "Rzemiosło (Zielarz)").statNumber(6).type("zaawansowana").level(0).isProfessional(false).build(); prof.getSkills().add( nowa); break;
			case "Wytwórca (Dowolne Rzemiosło)":  nowa = Skill.builder().name( "Rzemiosło (Dowolne Rzemiosło)").statNumber(6).type("zaawansowana").level(0).isProfessional(false).build(); prof.getSkills().add( nowa); break;
			case "Wytwórca (Szkutnik)":  nowa = Skill.builder().name( "Rzemiosło (Szkutnik)").statNumber(6).type("zaawansowana").level(0).isProfessional(false).build(); prof.getSkills().add( nowa); break;
			case "Wytwórca (Aptekarz)":  nowa = Skill.builder().name( "Rzemiosło (Aptekarz)").statNumber(6).type("zaawansowana").level(0).isProfessional(false).build(); prof.getSkills().add( nowa); break;
			case "Talent Artystyczny":  nowa = Skill.builder().name( "Sztuka (Dowolna)").statNumber(6).type("podstawowa").level(0).isProfessional(false).build(); prof.getSkills().add( nowa); break;
		}
	}
	
	
	private int randomX(int x){
		return (int) (Math.random() * x);
	}
	
	
	public void zmienOpisSciekiProfesji(String tekst) {
		prof.setProfessionPath("("+tekst+")");
	}
	
	public void doswiadczenieBohatera(int opcjaDoswiadczenia) {
		int poziom = prof.getLevel();
		System.out.println(poziom); 
		switch(opcjaDoswiadczenia){
				case 1:postacLosowyBonus(1*poziom);
				zmienOpisSciekiProfesji("początkujący");
				System.out.println("profesja początkująca"); break;
				case 2: postacLosowyBonus(3*poziom);
				zmienOpisSciekiProfesji("Średniozaawansowana");
				System.out.println("profesja Średniozaawansowana"); break;
				case 3: postacLosowyBonus(5*poziom);
				zmienOpisSciekiProfesji("doświadczona");
				System.out.println("profesja zaawansowana"); break;
			}
		stats.updateHp( this.getCzyJestTwardziel());
	}
	

	public int sprawdzHistorieProfesji(Profession nowaProfesja) {
		int poziom = -1;
		for(Profession staraProf:historiaProfesji) {
			if(nowaProfesja.toString().equals(staraProf.toString()))
				poziom = staraProf.getLevel();
		}
		return poziom;
	}
	
	public boolean getProfesjaUkonczona() {
		return prof.isFinished();
	}
	//getters
	/**
	 * @return the imieNazwisko
	 */
	public String getImieNazwisko() {
		return imieNazwisko;
	}
	public String getRasaName() {
		return race.getName();
	}
	
	public String getProfesjaNameMain() {
		return this.prof.toString();
	}
	
	public Profession getCurrentProfesja(){
		return prof;
	}
	

	public String getCurrentProfesjaName(){
		String [] tablica =prof.getNameAndProfessionPath(getPlecBohatera()).split("/");
		if(plecBohatera == "Mężczyzna")
			return tablica[0];
		else {
			return tablica[1];
		}
	}
	
	public int getCurrentProfPoziom() {
		return prof.getLevel();
	}
	
	public String getProfesjaSciezka() {
		String [] tablica =prof.getProfessionPath().split( " – ");
		return tablica[0];
	}
	
	public String getProfesjaStatus() {
		String [] tablica =prof.getProfessionPath().split( " – ");
		return tablica[1];
	}
	
	public String getWygladWiek() {
		Integer wiek = this.appearance.getAge();
		return wiek.toString();
	}
	
	public String getWygladWzrost() {
		Integer wzrost = this.appearance.getHeight();
		return wzrost.toString();
	}
	
	public String getWygladWlosy() {
		return this.appearance.getHairColor();
	}
	
	public String getWygladOczy() {
		return this.appearance.getEyesColor();
	}
	
	public String [] getCechyAktualne() {
		String [] tab = new String[10];
		for(int x =0; x<10; x++) {
			Integer aktualne = this.stats.getStatAt( x);
			tab[x] = aktualne.toString();
		}
		return tab;
	}
	
	public int [] getCechyAktualneInt() {
		int [] tab = new int[10];
		for(int x =0; x<10; x++) {
			tab[x] = this.stats.getStatAt( x);
		}
		return tab;
	}
	
	public String [] getCechyRozwiniecia() {
		String [] tabRozw = new String[10];
		for(int x =0; x<10; x++) {
			Integer aktualne = this.stats.getAdvancesAt( x);
			if(aktualne >0)
				tabRozw[x] = aktualne.toString();
			else {
				tabRozw[x] = null;
			}
		}
		return tabRozw;
	}
	
	public int [] getCechyRozwinieciaInt() {
		int [] tabRozw = new int[10];
		for(int x =0; x<10; x++) {
			tabRozw[x] = this.stats.getAdvancesAt( x);
		}
		return tabRozw;
	}
	
	public int getCechySzybkosc() {
		return this.stats.getSpeed();
	}
	
	public int getCzyJestTwardziel() {
		int liczba = 0;
		for(Talent tl:znaneTalenty) {
			if(tl.getName().equals("Twardziel"))
			{
				liczba = tl.getLevel();
			}
		}
		return liczba;
	}
	
	public String getCechyHpString() {
		Integer hp = this.stats.getHp();
		return hp.toString();
	}
	
	/**
	 * @return - jeżeli mężczyzna to true, inacze false
	 */
	public boolean getPlecBohatera() {
		if(plecBohatera.equals("Mężczyzna"))
			return true;
		else
			return false;
	}

	public boolean czyJestCechaRozwojuProfesji(int x) {
		return this.prof.isProfessionStat( x);
	}
	
	public String getKlasaProfesji() {
		return this.prof.getCareer();
	}
	
}
