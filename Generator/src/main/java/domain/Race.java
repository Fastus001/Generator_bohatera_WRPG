package domain;

import enums.RaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import utilities.RandomTalent;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Race {

	public String name;
	private final List<Skill> skills;
	private final List<Talent> talents;

	public RaceType getRaceEnum(){
		return RaceType.getRaceEnumByName( name );
	}

	public Set<Skill> getSkillsFromRace(){
		Set<Skill> skillsFromRace = new TreeSet<>();

		for(int i = 0; i < 6; i++){
			int index = (int) (Math.random()*skills.size());
			Skill newSkill = skills.get( index);
			if(i<3){
				newSkill.setLevel(5);
			}else{
				newSkill.setLevel(3);
			}
			skillsFromRace.add( newSkill );
			skills.remove( newSkill);
		}
		return skillsFromRace;
	}


	public Set<Talent> addKnownTalentsFromRace(RaceType raceType){
		Set<Talent> temp = new TreeSet<>();
		switch (raceType){
			case HUMAN: temp.add( talents.get( randomX( 2)) );break;
			case HALFLING:temp.addAll( talents );break;
			default:
				temp.add( talents.get( randomX( 2) ));
				temp.add( talents.get( randomX( 2)+2 ));
				temp.addAll( talents.subList( 4,talents.size() ) );
				return temp;
		}
		for (int i = 0; i < raceType.getAvailableTalents(); i++) {
			Talent talent = RandomTalent.getInstance().getTalent();
			if(!temp.contains( talent )){
				temp.add( talent );
			}else{
				Talent talentInSet = temp.stream().filter( t -> t.equals( talent ) ).findFirst().orElseThrow();
				if(talentInSet.isUpgradable()){
					talentInSet.addOneToLevel();
				}else{
					i--;
				}
			}
		}
		return temp;
	}

	private int randomX(int x){
		return (int) (Math.random() * x);
	}

	/*
	needed for the JComboBox<Object> proper name in GUI
	 */
	@Override
	public String toString() {
		return name;
	}
}