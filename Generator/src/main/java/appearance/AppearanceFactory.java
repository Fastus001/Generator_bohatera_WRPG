package appearance;

import enums.RaceType;

public class AppearanceFactory {

    public static Appearance create(RaceType raceType){
        Appearance appearance;
        switch (raceType){
            case HUMAN:appearance =  new HumanAppearance();break;
            case DWARF:appearance =  new DwarfAppearance();break;
            case HALFLING: appearance = new HalflingAppearance();break;
            case HIGH_ELF: appearance = new HighElfAppearance();break;
            case WOOD_ELF: appearance = new WoodElfAppearance();break;
            default: throw new RuntimeException("Illegal race type for the appearance!");
        }
        return appearance;
    }
}
