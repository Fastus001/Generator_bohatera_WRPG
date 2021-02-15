package enums;

import lombok.Getter;

@Getter
public enum RaceEnum {
    HUMAN(4,"Ludzie"),
    DWARF(3,"Krasnoludy"),
    HIGH_ELF(5,"Wysokie elfy"),
    WOOD_ELF(5,"Leśne elfy"),
    HALFLING(3,"Niziołki");

    private final int speed;
    private final String name;

    RaceEnum(int speed, String name) {
        this.speed = speed;
        this.name = name;
    }

    public static RaceEnum getRaceEnumByName(String raceName){
        switch(raceName){
            case "Krasnoludy": return RaceEnum.DWARF;
            case "Niziołki": return RaceEnum.HALFLING;
            case "Wysokie elfy": return RaceEnum.HIGH_ELF;
            case "Leśne elfy": return RaceEnum.WOOD_ELF;
            default:  return RaceEnum.HUMAN;
        }
    }
}
