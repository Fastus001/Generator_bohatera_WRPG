package enums;

import lombok.Getter;

@Getter
public enum RaceType {
    HUMAN(4,"Ludzie"),
    DWARF(3,"Krasnoludy"),
    HIGH_ELF(5,"Wysokie elfy"),
    WOOD_ELF(5,"Leśne elfy"),
    HALFLING(3,"Niziołki");

    private final int speed;
    private final String name;

    RaceType(int speed, String name) {
        this.speed = speed;
        this.name = name;
    }

    public static RaceType getRaceEnumByName(String raceName){
        switch(raceName){
            case "Krasnoludy": return RaceType.DWARF;
            case "Niziołki": return RaceType.HALFLING;
            case "Wysokie elfy": return RaceType.HIGH_ELF;
            case "Leśne elfy": return RaceType.WOOD_ELF;
            default:  return RaceType.HUMAN;
        }
    }
}
