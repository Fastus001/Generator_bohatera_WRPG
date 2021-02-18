package commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class Profession implements Comparable<Profession> {
    private static final String SPLIT = "/";
    
    private String name;
    private String professionPath;
    private int level;
    public ArrayList<Skill> skills;
    public ArrayList<Talent> talents;
    public String[] races;
    private int[] professionStats;
    private boolean finished;
    private String career;
    private String[] items;

    public String getName(boolean male) {
        String[] split = name.split( SPLIT );

        return male ? split[0] : split [1];
    }

    public String getProfessionPath(boolean male) {
        String[] split = professionPath.split( SPLIT );

        return male ? split[0] : split [1];
    }

    public String getNameAndProfessionPath(boolean male) {
        if ( male ) {
            return getName(true) + "\n" + getProfessionPath(true);
        }
        return getName( false ) + "\n" + getProfessionPath( false );
    }

    public int getRandomProfessionStat() {
        int random = ( int ) (Math.random() * professionStats.length);
        return professionStats[random];
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
