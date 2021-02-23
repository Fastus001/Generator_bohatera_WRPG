package enums;

import lombok.Getter;

@Getter
public enum RaceType {
    HUMAN( 4, "Ludzie", new int[]{20, 20, 20, 20, 20, 20, 20, 20, 20, 20},4 ),
    DWARF(3,"Krasnoludy",new int[]{30,20,20,30,20,10,30,20,40,10},0),
    HIGH_ELF(5,"Wysokie elfy", new int[]{30,30,20,20,40,30,30,30,30,20},0),
    WOOD_ELF(5,"Leśne elfy", new int[]{30,30,20,20,40,30,30,30,30,20},0),
    HALFLING(3,"Niziołki", new int[]{10,30,10,20,20,20,30,20,30,30},2);

    private final int speed;
    private final String name;
    private final int[] baseStats;
    private final int availableTalents;

    RaceType(int speed, String name, int[] baseStats, int availableTalents) {
        this.speed = speed;
        this.name = name;
        this.baseStats = baseStats;
        this.availableTalents = availableTalents;
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
