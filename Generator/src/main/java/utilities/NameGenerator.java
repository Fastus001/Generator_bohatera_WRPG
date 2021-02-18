package utilities;

import enums.Gender;
import enums.RaceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class NameGenerator {
	private static NameGenerator instance;
    private static final String NAMES = "names.txt";
    private final List<String[]> namesList;

    private NameGenerator() {
        namesList = Reader.getListOfArraysFrom( NAMES );
    }

    public static NameGenerator getInstance(){
    	if(instance == null){
    		instance = new NameGenerator();
		}
    	return instance;
	}

    public String generateFullName(RaceType raceType, Gender gender) {
        return gender.equals( Gender.MALE ) ? maleFullName( raceType ):femaleFullName( raceType );
    }

	private String maleFullName(RaceType raceType) {
		String name = "";
		switch (raceType) {
			case HUMAN:
				name = getName( NameTypes.MALE_IMPERIAL.getIndex() );
				name += " " + getName( NameTypes.LAST_NAME_IMPERIAL.getIndex() );
				break;
			case DWARF:
				name = getName( NameTypes.FIRST_PART_DWARF.getIndex() );
				name += getName( NameTypes.SECOND_PART_DWARF.getIndex() );
				name += " " + getName( NameTypes.FIRST_PART_DWARF.getIndex() );
				name += getName( NameTypes.SECOND_PART_DWARF.getIndex() );
				name += generatePronounDwarf( true );
				name += " z klanu " + getName( NameTypes.DWARF_CLAN_NAME.getIndex() );
				break;
			case HALFLING:
				name = getName( NameTypes.FIRST_PART_HALFLING.getIndex() ) +
						getName( NameTypes.SECOND_MALE_HALFLING.getIndex() );
				name += " " + getName( NameTypes.HALFLING_LAST_NAME.getIndex() );
				break;
			case HIGH_ELF:
			case WOOD_ELF:
				name = getName( NameTypes.FIRST_PART_ELF.getIndex() ) +
						getName( NameTypes.SECOND_PART_ELF.getIndex() );
				name += getName( NameTypes.THIRD_PART_MALE_ELF.getIndex() );
				break;
		}
		return name;
	}

	private String femaleFullName(RaceType raceType) {
		String name = "";
		switch (raceType) {
			case HUMAN:
				name = getName( NameTypes.FEMALE_IMPERIAL.getIndex() );
				name += " " + getName( NameTypes.LAST_NAME_IMPERIAL.getIndex() );
				break;
			case DWARF:
				name = getName( NameTypes.FIRST_PART_DWARF.getIndex() );
				name += getName( NameTypes.SECOND_PART_FEMALE_DWARF.getIndex() );
				name += " " + getName( NameTypes.FIRST_PART_DWARF.getIndex() );
				name += getName( NameTypes.SECOND_PART_FEMALE_DWARF.getIndex() );
				name += generatePronounDwarf( false );
				name += " z klanu " + getName( NameTypes.DWARF_CLAN_NAME.getIndex() );
				break;
			case HALFLING:
				name = getName( NameTypes.FIRST_PART_HALFLING.getIndex()) +
						getName( NameTypes.HALFLING_SECOND_FEMALE.getIndex() );
				name += " " + getName( NameTypes.HALFLING_LAST_NAME.getIndex() );
				break;
			case HIGH_ELF:
			case WOOD_ELF:
				name = getName( NameTypes.FIRST_PART_ELF.getIndex() ) +
						getName( NameTypes.SECOND_PART_ELF.getIndex() );
				name += getName( NameTypes.THIRD_PART_FEMALE_ELF.getIndex() );
				break;
		}
		return name;
	}

	private String getName(int x) {
        String[] array = namesList.get( x );
        int size = array.length;

        return array[( int ) (Math.random() * size)];
    }

    private String generatePronounDwarf(boolean male) {
        String[] array = namesList.get( NameTypes.DWARF_PRONOUN.getIndex() );
        if ( male ) {
            return array[( int ) (Math.random() * 2) + 2];
        }
        else {
            return array[( int ) (Math.random() * 2)];
        }
    }

	@Getter
	@AllArgsConstructor
    private enum NameTypes {
        MALE_IMPERIAL(0),
		FEMALE_IMPERIAL(1),
		LAST_NAME_IMPERIAL(2),
		FIRST_PART_DWARF(3),
		SECOND_PART_DWARF(4),
		SECOND_PART_FEMALE_DWARF(5),
        DWARF_PRONOUN(6),
		DWARF_CLAN_NAME(7),
		FIRST_PART_ELF(8),
		SECOND_PART_ELF(9),
		THIRD_PART_FEMALE_ELF(10),
		THIRD_PART_MALE_ELF(11),
        FIRST_PART_HALFLING(12),
		HALFLING_SECOND_FEMALE(13),
		SECOND_MALE_HALFLING(14),
		HALFLING_LAST_NAME(15);

        private final int index;
	}


}//koniec klasy

//////////////////////////////////////
///kolejnosc w pliku txt
///1.meskie imperium
///2.ďż˝eďż˝skie imperium
///3.nazwiska
///4. Pierwszy czďż˝on krasnoludzkie
///5. Drugi czďż˝on krasnoludzkie mďż˝skie
///6. Drugi czďż˝on krasnoludzkie ďż˝eďż˝skie
///7. zaimki do nazwisk krasnoludzkich pierwsze dwa dla kobiet, pozostale dwa dla mďż˝czyzn
///8. nazwa klanu krasnoludzkiego
///9. Pierwszy czďż˝on imienia elfiego
///10. Drugi czďż˝on imienia elfiego
///11. Trzeci czďż˝on imienia elfiego - ďż˝eďż˝ski
///12. Tzeci czďż˝on imienia elfiego - mďż˝ski
///13. Imiona nizioďż˝kďż˝w pierwszy czďż˝on
///14. Imie nizioďż˝ka kobiety drugi czďż˝on
///15. Imie nizioďż˝ka faceta drugi czďż˝on
///16. Nazwisko nizioďż˝ka

