package commons;

import appearance.Appearance;
import enums.Gender;
import enums.RaceType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Hero {
	public static final Random RANDOM = new Random();

	private String name;
	private Gender gender;
	private Appearance appearance;
	private RaceType race;
	private Profession profession;
	private Stats stats;
	private Set<Skill> knownSkills = new TreeSet<>();
	private Set<Talent> knownTalents = new TreeSet<>();
	private List<Profession> history;

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
			setSkillsLevelOnNewProfessionLevel( minPoziomUm);
			setStatsDueToNewLevel( minPoziomUm);
		}
		stats.updateHp(getHardyLevel());
		profession.setFinished( true);
	}
	

	public void newProfession(Profession profession){
		history.add( profession);
		setProfessionSkills();
		
		if( this.profession.getLevel() < profession.getLevel()){
			int minPoziomUm = 5* this.profession.getLevel();
			setSkillsLevelOnNewProfessionLevel( minPoziomUm);
			setStatsDueToNewLevel( minPoziomUm);
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
		setProfessionSkills();
		
		if( this.profession.toString().equals( "CZARODZIEJ") && this.profession.getLevel()==2 ){
			Talent nowy = this.profession.getTalents().get( 0 );
			nowy.setTalentMax( stats );
			addTalent( nowy );
		}else{
			getTalentFromProfession();
		}
		stats.updateHp( this.getHardyLevel());
	}
	
	private void setProfessionSkills() {
		List<Skill> skills = new ArrayList<>();
		for(Profession p: history) {
			if(p.toString().equals( profession.toString())) {
				skills.addAll( p.skills );
			}
		}

		for(Skill skill:skills) {
			for(Skill knownSkill: knownSkills) {
				if(skill.showSkill().equals( knownSkill.showSkill())) {
					knownSkill.setProfessional( true);
				}
			}
		}
	}

	public void setStatsDueToNewLevel(int minLevel){
		int[] professionStats = profession.getProfessionStats();
		for (int stat : professionStats) {
			int advances = stats.getAdvancesAt( stat );
			if ( advances < minLevel ) {
				advances = minLevel - advances;
				stats.increaseStatAt( advances, stat, true );
			}
		} 
	}
	
	//sprawdzenie znanych umiejetnosci czy jest przynajmniej osiem na minimalnym poziomie rowiniecia do przejscia na nowďż˝ profesjďż˝

	public void setSkillsLevelOnNewProfessionLevel(int minStatLevel) {
		int numberOfSkills = 0;
		for(Skill skill: knownSkills){
			if(skill.isProfessional() && skill.getLevel() >= minStatLevel) {
				numberOfSkills++;
			}
		}
		if(numberOfSkills<8){
			log.debug( "not enough skills to get eight to correct level!"  + numberOfSkills);
			increaseRandomSkillWithMinLevel( 3, minStatLevel);
			setSkillsLevelOnNewProfessionLevel( minStatLevel);
		}
	}

	private void getKnownSkillsFromProfession(){
		for (Skill professionSkill: profession.getSkills()) {
			for(Skill knownSkill: knownSkills){
				if(professionSkill.getName().equals( knownSkill.getName())){
					knownSkill.setProfessional( true);
					professionSkill.addToSkillLevel( -40);
					break;
				}	
			}
			//jezeli poziom umiejetnosci jest na minus, to nie dodawaj jej do znanych umiejetnosci
			if(professionSkill.getLevel() >= 0){
				professionSkill.setProfessional( true);
				knownSkills.add( professionSkill);
			}
		}
	}

	public Talent findTalent(Talent talent){
		return knownTalents.stream()
				.filter( t -> t.getName().equals( talent.getName() ) )
				.findFirst()
				.orElse( null );
	}

	private void getTalentFromProfession() {
		int size = profession.getTalents().size();
		Talent randomTalent = profession.getTalents().get( RANDOM.nextInt( size));
		Talent talent = findTalent( randomTalent );
		if(talent == null){
					randomTalent.setTalentMax( stats );
					addTalent( randomTalent);
		}else{
			if( talent.getMaxLevel() == talent.getLevel()){
				getTalentFromProfession();
			}else{
				talent.addOneToLevel();
			}
		}
	}

	public void randomBonus(int times) {
		for(int n = 0; n < times; n++){
			int random = (int) (Math.random()*5);
			switch(random){
				case 0:
				case 1:
				case 2: for(int i = 0; i < 4; i++){
					stats.increaseStatAt( 1, profession.getRandomProfessionStat(), true);
				} break;
				case 3:
				case 4: increaseRandomSkill( 6); break;
				case 5: getTalentFromProfession(); break;
			}
		}
	}
	
	public void increaseRandomSkill(int times) {
		List<Skill> temp = new ArrayList<>( knownSkills );
			for(int i = 0; i < times; i++){
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

	public void changeProfessionPathDescription(String text) {
		profession.setProfessionPath( "("+text+")");
	}
	
	public void experienceLevel(int experienceLevel) {
		int poziom = profession.getLevel();
		switch(experienceLevel){
				case 1:
					randomBonus( 1*poziom);
				changeProfessionPathDescription( "początkujący"); break;
				case 2: randomBonus( 3*poziom);
				changeProfessionPathDescription( "Średniozaawansowana"); break;
				case 3: randomBonus( 5*poziom);
				changeProfessionPathDescription( "doświadczona"); break;
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

	public String getCurrentProfessionName(){
		String [] split = profession.getNameAndProfessionPath( gender).split( "/");
		if( gender.equals( Gender.MALE ))
			return split[0];
		else {
			return split[1];
		}
	}

	public String professionPathName() {
		String [] tablica = profession.getProfessionPath().split( " – ");
		return tablica[0];
	}
	
	public String professionStatusName() {
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

}
