package commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Skill implements Comparable<Skill>{
	private static final String[] STAT_NAMES = {"WW", "US", "S", "Wt", "I", "Zw", "Zr", "Int", "SW", "Ogd"};

	private String name;
	private String type;
	private int statNumber;
	private int level;
	private boolean isProfessional;

	public void addToSkillLevel(int toAdd) {
		level+=toAdd;
	}

	public String showAll(){
		return name + " ("+ STAT_NAMES[statNumber] + ") " + type;
	}

	@Override
	public String toString(){
		if(isProfessional ){
			return name +" +"+  level +"*";
		}else{
			return name +" +"+ level;
		}
	}

	public int compareTo(Skill skill){
		return name.compareTo( skill.getName());
	}
}

