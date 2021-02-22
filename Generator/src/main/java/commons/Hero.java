package commons;

import appearance.Appearance;
import appearance.AppearanceFactory;
import enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import utilities.NameGenerator;
import utilities.RandomTalent;

import java.util.*;

@Getter
@AllArgsConstructor
public class Hero {

	private final String name;
	private final Gender gender;
	private final Appearance appearance;
	private final Race race;
	private Profession profession;
	private final Stats stats;
	public Set<Skill> knownSkills;
	public Set<Talent> knownTalents;
	private final List<Profession> history;
	

	public Hero(Race race, Profession profession, Gender gender) {
		this.race = race.toBuilder().build();
		this.profession = profession.toBuilder().build();
		name = NameGenerator.getInstance().generateFullName( this.race.getRaceEnum(), gender);
		this.gender = gender;
		appearance = AppearanceFactory.create( this.race.getRaceEnum());
		stats = new Stats( race.baseStats, race.getRaceEnum());
		knownSkills = new TreeSet<>();
		knownTalents = new TreeSet<>();
		//todo - move race to HeroFactory and these two methods
		getKnownSkillsFromRace();
		addKnownTalentsFromRace();
		dodajZnanyTalentZProfesji();
		dodajZnaneUmiejetnosciZProfesjiLosowePoczatkowe();
		dodajLosowoPoczatkoweRozwiniecieCech();
		randomBonus( 1);
		
		history = new ArrayList<>();
		history.add( this.profession );
	}

	public Hero(Hero bh) {
		this.name = bh.name;
		this.gender = bh.gender;
		this.appearance = bh.appearance;
		this.race = bh.race.toBuilder().build();
		this.profession = bh.profession.toBuilder().build();
		this.stats = new Stats( bh.stats );
		this.knownSkills = new TreeSet<>();
		for(Skill um:bh.knownSkills) {
			Skill nowa = um.toBuilder().build();
			this.knownSkills.add( nowa);
		}
		this.knownTalents = new TreeSet<>();
		for(Talent tl:bh.knownTalents) {
			Talent nowyTl = tl.toBuilder().build();
			this.knownTalents.add( nowyTl);
		}
		this.history = new ArrayList<>();
		for(Profession pr:bh.history) {
			Profession nowaPr = pr.toBuilder().build();
			this.history.add( nowaPr);
		}
	}

	public void addTalent(Talent talent){
		if(knownTalents.add( talent )){
			Skill newSkill = TalentValidator.validate( talent, stats );
			if(newSkill!=null){
				knownSkills.add( newSkill );
			}
		}
	}

	@Override
	public String toString() {
		return name + ", Profesja: " + profession.getName( gender) + " poziom profesji: " + profession.getLevel();
	}
	
	/*
	 * metoda ktďż˝ra podnosi cechy aby moďż˝na byďż˝o ukoďż˝czyc dany poziom profesji (przewaďż˝nie 4 poziom)
	 */
	public void finishProfession(int level) {
		if( profession.getLevel() < level){
			int minPoziomUm = 5* profession.getLevel();
			nowyPoziomUmiejetnosciNowyLvl(minPoziomUm);
			nowyPoziomCechyNowyLvl(minPoziomUm);
		}
		stats.updateHp(getHardyLevel());
		profession.setFinished( true);
	}
	

	public void nowaProfesja(Profession nowaProfesja){
		history.add( nowaProfesja);
		setUmiejetnosciProfesyjne();
		
		if( profession.getLevel() < nowaProfesja.getLevel()){
			int minPoziomUm = 5* profession.getLevel();
			nowyPoziomUmiejetnosciNowyLvl(minPoziomUm);
			nowyPoziomCechyNowyLvl(minPoziomUm);
		}
		appearance.addAge();
		
		if(!profession.isFinished())
			profession.setFinished( true);
		
		if(!profession.toString().equals( nowaProfesja.toString()))
		{
			for(Skill um: knownSkills)
				um.setProfessional( false);
		}
		profession = nowaProfesja.toBuilder().build();
		getKnownSkillsFromProfession();
		setUmiejetnosciProfesyjne();
		
		if( profession.toString().equals( "CZARODZIEJ") && profession.getLevel()==2 ){
			Talent nowy = profession.getTalents().get( 0 );
			nowy.setTalentMax( stats );
			addTalent( nowy );
		}else{
			dodajZnanyTalentZProfesji();
		}
		stats.updateHp( this.getHardyLevel());
	}
	
	private void setUmiejetnosciProfesyjne() {
		List<Skill> tablicaUmiejetnosci = new ArrayList<>();
		for(Profession p: history) {
			if(p.toString().equals( profession.toString())) {
				tablicaUmiejetnosci.addAll( p.skills );
			}
		}

		for(Skill um:tablicaUmiejetnosci) {
			for(Skill znaneUm: knownSkills) {
				if(um.showSkill().equals( znaneUm.showSkill())) {
					znaneUm.setProfessional( true);
				}
			}
		}
	}

	public void nowyPoziomCechyNowyLvl(int minPoz){
		int[] klasoweAtrybuty = profession.getProfessionStats();
		for (int j : klasoweAtrybuty) {
			int advances = stats.getAdvancesAt( j );
			if ( advances < minPoz ) {
				System.out.println( "Roziwniecie cechy: " + advances );
				advances = minPoz - advances;
				System.out.println( "Roziwniecie cechy do dodania: " + advances );
				stats.increaseStatAt( advances, j, true );
			}
		} 
	}
	
	//sprawdzenie znanych umiejetnosci czy jest przynajmniej osiem na minimalnym poziomie rowiniecia do przejscia na nowďż˝ profesjďż˝

	public void nowyPoziomUmiejetnosciNowyLvl(int minPozUm) {
		int ile = 0;
		for(Skill um: knownSkills){
			if(um.isProfessional() && um.getLevel() >= minPozUm)
			{
				ile++;
			}
		}
		if(ile<8){
			System.out.println("Brak wystarczającej ilosci umiejetnosci na odpowiednim poziomie, jest tylko " + ile);
			increaseRandomSkillWithMinLevel( 3, minPozUm);
			nowyPoziomUmiejetnosciNowyLvl(minPozUm);
		}
	}

	//metoda tylko dla nowych postaci, wszelkie wyďż˝sze poziomy muszďż˝ juďż˝ sprawdzaďż˝ profesyjne umiejetnoďż˝ci i minimum dla przejscia na nowy poziom profesji
	private void dodajZnaneUmiejetnosciZProfesjiLosowePoczatkowe(){
		
		for(int i = 0; i < 40; i++) {
			int random = (int) (Math.random()* profession.getSkills().size());
			if( profession.skills.get( random).getLevel()<11){
				profession.skills.get( random).addToSkillLevel( 1);
			}else{
				i--;
			}
		}

		for (Skill um: profession.getSkills()) {
			for(Skill umZnane: knownSkills){
				//jezeli juz jest taka umiejetnosc to dodajemy do znanej +5, a nowďż˝ ustawiamy na -10
				if(um.getName().equals( umZnane.getName())){
					umZnane.addToSkillLevel( um.getLevel());
					umZnane.setProfessional( true);
					um.addToSkillLevel( -40);
					break;
				}	
			}
			if(um.getLevel() >= 0){
				um.setProfessional( true);
				knownSkills.add( um);
			}
		}
	}
	
	private void getKnownSkillsFromProfession(){
		for (Skill um: profession.getSkills()) {
			for(Skill umZnane: knownSkills){
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
				knownSkills.add( um);
			}
		}
	}

	
	private void getKnownSkillsFromRace() {
		List<Skill> temp = new ArrayList<>( race.getSkills() );
		
		for(int i = 0; i < 6; i++){	
			int index = (int) (Math.random()*temp.size());
			Skill newSkill = temp.get( index);
			if(i<3){
				newSkill.setLevel(5);
			}else{
				newSkill.setLevel(3);
			}
			knownSkills.add( newSkill);
			temp.remove(index);
		}
	}
	
	private void addKnownTalentsFromRace(){
		// ludzie wybďż˝r miďż˝dzy 0 a 1, w dostďż˝pnych talentach
		//krasnoludy majďż˝ do wyboru pomiďż˝dzy 0 a 1, oraz 2-3
		// wysokie elfy 0 lub 1  oraz 2 lub 3
		//lesne elfy, 0 lub 1, oraz 2 lub 3
		List<Talent> talents = race.getTalents();

		if( race.getName().equals( "Ludzie")){
			addTalent( talents.get( randomX( 2)) );
		}else{
			addTalent( talents.get( randomX( 2)) );
			addTalent( talents.get( randomX( 2)+2) );
			if( race.getSizeOfAvailableTalents() >3){
				for(int i = 4; i< race.getSizeOfAvailableTalents(); i++){
					addTalent( talents.get( i));
				}
			}
		}
		for(Talent t: knownTalents){
			t.setTalentMax( stats );
		}
		//dodanie losowych talentow, + sprawdzenie czy siďż˝ nie powtarzajďż˝, ewentualnie zwiďż˝kszenie o 1
		int noOfRandomTalents = race.getAvailableRandomTalents();
		if(noOfRandomTalents > 0 ) {
			for(int i= 0; i<noOfRandomTalents; i++){
				Talent newTalent = RandomTalent.getInstance().getTalent();
				Talent talent = findTalent( newTalent );
				if(talent == null){
					newTalent.setTalentMax( stats );
					addTalent( newTalent);
				}else{
						if( talent.getMaxLevel() == talent.getLevel()){
							i--;
						}else{
							talent.addOneToLevel();
						}
				}
			}
		}
	}

	private Talent findTalent(Talent talent){
		return knownTalents.stream()
				.filter( t -> t.getName().equals( talent.getName() ) )
				.findFirst()
				.orElse( null );
	}

	private void dodajZnanyTalentZProfesji() {
		int iloscTalentow = profession.getTalents().size();
		Talent losowyTalent = profession.getTalents().get( randomX( iloscTalentow));
		Talent talent = findTalent( losowyTalent );
		if(talent == null){
					losowyTalent.setTalentMax( stats );
					addTalent( losowyTalent);
		}else{
			if( talent.getMaxLevel() == talent.getLevel()){
				dodajZnanyTalentZProfesji();
			}else{
				talent.addOneToLevel();
			}
		}
	}

	private void dodajLosowoPoczatkoweRozwiniecieCech(){
		stats.increaseStatAt( 5, profession.getRandomProfessionStat(), true);
	}
	
	//w sumie moďż˝na teďż˝ uďż˝yďż˝ tej metody, do okreďż˝lenia, robienia bardziej zaawansowanej postaci
	public void randomBonus(int times) {
		for(int n = 0; n < times; n++){
			int opcja = (int) (Math.random()*10);
		if(opcja <3){
			opcja = 0;
		}else if( opcja < 7 ){
			opcja = 1;
		}else {
			opcja = 2;
		}
		switch(opcja){
			case 0: for(int i = 0; i < 4; i++){
				stats.increaseStatAt( 1, profession.getRandomProfessionStat(), true);
			} break;
			case 1: increaseRandomSkill( 6); ;break;
			case 2: dodajZnanyTalentZProfesji(); ;break;
			}
		}
	}
	
	public void increaseRandomSkill(int ileRazy) {
		List<Skill> temp = new ArrayList<>( knownSkills );
			for(int i = 0; i < ileRazy; i++){
				int index = (int) (Math.random()* knownSkills.size());
				temp.get( index ).addToSkillLevel( 1 );
			}
	}

	public void increaseRandomSkillWithMinLevel(int times, int level) {
		List<Skill> temp = new ArrayList<>( knownSkills );
			for(int i = 0; i < times; i++){
				int index = (int) (Math.random()* knownSkills.size());
				Skill skill = temp.get( index );
			if(skill.getLevel()<level){
				skill.addToSkillLevel( 1 );
			}
		}
	}
	
	
	private int randomX(int x){
		return (int) (Math.random() * x);
	}
	
	
	public void zmienOpisSciekiProfesji(String tekst) {
		profession.setProfessionPath( "("+tekst+")");
	}
	
	public void doswiadczenieBohatera(int experienceLevel) {
		int poziom = profession.getLevel();
		switch(experienceLevel){
				case 1:
					randomBonus( 1*poziom);
				zmienOpisSciekiProfesji("początkujący"); break;
				case 2: randomBonus( 3*poziom);
				zmienOpisSciekiProfesji("Średniozaawansowana"); break;
				case 3: randomBonus( 5*poziom);
				zmienOpisSciekiProfesji("doświadczona"); break;
			}
		stats.updateHp( getHardyLevel());
	}
	

	public int checkProfessionHistory(Profession newProfession) {
		int poziom = -1;
		for(Profession staraProf: history) {
			if(newProfession.toString().equals(staraProf.toString()))
				poziom = staraProf.getLevel();
		}
		return poziom;
	}
	
	public boolean getProfesjaUkonczona() {
		return profession.isFinished();
	}

	public String getProfesjaNameMain() {
		return this.profession.toString();
	}


	public String getCurrentProfesjaName(){
		String [] tablica = profession.getNameAndProfessionPath( gender).split( "/");
		if( gender.equals( Gender.MALE ))
			return tablica[0];
		else {
			return tablica[1];
		}
	}
	
	public int getCurrentProfPoziom() {
		return profession.getLevel();
	}
	
	public String getProfesjaSciezka() {
		String [] tablica = profession.getProfessionPath().split( " – ");
		return tablica[0];
	}
	
	public String getProfesjaStatus() {
		String [] tablica = profession.getProfessionPath().split( " – ");
		return tablica[1];
	}

	public int getHardyLevel() {
		return knownTalents.stream()
				.filter( t->t.getName().equals( "Twardziel" ) )
				.mapToInt( Talent::getLevel )
				.findFirst()
				.orElse( 0 );
	}
	
	public String getCechyHpString() {
		return Integer.toString( stats.getHp() );
	}

	public boolean czyJestCechaRozwojuProfesji(int x) {
		return this.profession.isProfessionStat( x);
	}
	
	public String getKlasaProfesji() {
		return this.profession.getCareer();
	}
	
}
