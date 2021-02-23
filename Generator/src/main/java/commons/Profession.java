package commons;

import enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Profession implements Comparable<Profession> {
    private static final String SPLIT = "/";
    
    private String name;
    private String professionPath;
    private int level;
    public List<Skill> skills;
    public List<Talent> talents;
    public String[] races;
    private int[] professionStats;
    private boolean finished;
    private String career;
    private String items;

    public String getName(Gender gender) {
        String[] split = name.split( SPLIT );

        return gender.equals( Gender.MALE ) ? split[0] : split [1];
    }

    public String getProfessionPath(Gender gender) {
        String[] split = professionPath.split( SPLIT );

        return gender.equals( Gender.MALE ) ? split[0] : split [1];
    }

    public String getNameAndProfessionPath(Gender gender) {
            return getName(gender) + "\n" + getProfessionPath(gender);
    }

    public int getRandomProfessionStat() {
        int random = ( int ) (Math.random() * professionStats.length);
        return professionStats[random];
    }

    public Talent randomTalent(){
        Random random = new Random();
        return talents.get( random.nextInt(talents.size()) );
    }

    public boolean isProfessionStat(int statToCheck) {
        for (int stat : this.professionStats) {
            if ( stat == statToCheck )
                return true;
        }
        return false;
    }

    public int compareTo(Profession profession) {
        return name.compareTo( profession.toString() );
    }

    /*
    needed for the JComboBox<Object> proper name in GUI
     */
    public String toString() {
        return name;
    }
}
