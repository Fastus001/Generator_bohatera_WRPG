package commons;

import enums.RaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Race {

	public String name;
	public int [] baseStats;
	private final List<Skill> skills;
	private final List<Talent> talents;
	private final int randomTalents;

	public int getSizeOfAvailableTalents(){
			return talents.size();
		}

	public RaceType getRaceEnum(){
		return RaceType.getRaceEnumByName( name );
	}

	/*
	needed for the JComboBox<Object> proper name in GUI
	 */
	@Override
	public String toString() {
		return name;
	}
}