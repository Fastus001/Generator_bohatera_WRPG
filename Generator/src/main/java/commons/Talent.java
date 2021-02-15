package commons;

import lombok.*;
import npcGenerator.Cechy;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor()
@Builder(toBuilder = true)
public class Talent implements Comparable<Talent>{
	private static final String[] STAT_NAMES = {"WW", "US", "S", "Wt", "I", "Zw", "Zr", "Int", "SW", "Ogd","Brak", "1", "2","4"};

	private final String name;
	private final int relatedStat;
	private final String test;
	private String description;

	@Builder.Default
	private int maxLevel = 0;

	@Builder.Default
	private int level = 1;

	@Builder.Default
	private boolean show = true;

	public String showAll(){
		return name + " Max: "+ STAT_NAMES[relatedStat] + " Test: "+ test + "\nOpis:\n" + description +"\n";
	}

	public String showTalentNameWithLevel() {
		if( level >1){
			return name + " x" + level;
		}else{
			return name;
		}
	}
	
	public void setTalentMax(Cechy stats) {
		switch(relatedStat){
			case 10: maxLevel = 10;	break;
			case 11: maxLevel = 1;
				break;
			case 12: maxLevel = 2;
				break;
			case 13: maxLevel = 4;
				break;
			default: maxLevel = stats.getCecha( relatedStat ) / 10;
				break;
		}
	}

	public int compareTo(Talent talent){
		return name.compareTo( talent.getName());
	}

	public void addOneToLevel(){
		level +=1;
	}

}
