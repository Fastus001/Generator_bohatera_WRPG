package commons;

import appearance.Appearance;
import appearance.AppearanceFactory;
import enums.Gender;
import enums.RaceType;
import lombok.*;
import utilities.NameGenerator;
import utilities.RandomTalent;

import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hero {
	public static final Random RANDOM = new Random();

	private String name;
	private Gender gender;
	private Appearance appearance;
	private RaceType race;
	private Profession profession;
	private Stats stats;
	public Set<Skill> knownSkills = new TreeSet<>();
	public Set<Talent> knownTalents = new TreeSet<>();
	private List<Profession> history;

	public Hero(Hero bh) {
		this.name = bh.name;
		this.gender = bh.gender;
		this.appearance = bh.appearance;
		this.race = bh.race;
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
	public void addSkill(Skill skill){
		if(knownSkills.contains( skill )){
			for (Skill s : knownSkills) {
				if(s.getName().equals( skill.getName() )){
					s.addToSkillLevel( skill.getLevel() );
					s.setProfessional( skill.isProfessional() );
				}
			}
		}else {
			knownSkills.add( skill );
		}

	}

	public void addTalent(Talent talent){
		if(knownTalents.add( talent )){
			Skill newTalent = TalentValidator.validate( talent, stats );
			if(newTalent!=null){
				knownSkills.add( newTalent );
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
	

	public void newProfession(Profession profession){
		history.add( profession);
		setUmiejetnosciProfesyjne();
		
		if( this.profession.getLevel() < profession.getLevel()){
			int minPoziomUm = 5* this.profession.getLevel();
			nowyPoziomUmiejetnosciNowyLvl(minPoziomUm);
			nowyPoziomCechyNowyLvl(minPoziomUm);
		}
		appearance.addAge();
		
		if(!this.profession.isFinished())
			this.profession.setFinished( true);
		
		if(!this.profession.toString().equals( profession.toString()))
		{
			for(Skill um: knownSkills)
				um.setProfessional( false);
		}
		this.profession = profession.toBuilder().build();
		getKnownSkillsFromProfession();
		setUmiejetnosciProfesyjne();
		
		if( this.profession.toString().equals( "CZARODZIEJ") && this.profession.getLevel()==2 ){
			Talent nowy = this.profession.getTalents().get( 0 );
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

	public Talent findTalent(Talent talent){
		return knownTalents.stream()
				.filter( t -> t.getName().equals( talent.getName() ) )
				.findFirst()
				.orElse( null );
	}

	private void dodajZnanyTalentZProfesji() {
		int iloscTalentow = profession.getTalents().size();
		Talent losowyTalent = profession.getTalents().get( RANDOM.nextInt( iloscTalentow));
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
