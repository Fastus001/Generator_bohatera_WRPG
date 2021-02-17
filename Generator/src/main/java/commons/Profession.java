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


    public Profession(Profession pr) {
        name = pr.name;
        professionPath = pr.professionPath;
        level = pr.level;
        skills = new ArrayList<>();
        for (Skill temp : pr.skills) {
            Skill nowa = temp.toBuilder().build();
            skills.add( nowa );
        }
        talents = new ArrayList<>();
        for (Talent tempT : pr.talents) {
            Talent nowyTalent = tempT.toBuilder().build();
            talents.add( nowyTalent );
        }
        races = pr.races;
        professionStats = pr.professionStats;
        this.finished = pr.finished;
        this.career = pr.career;
        this.items = pr.items;
    }

    /*
    needed for the JComboBox<Object> proper name in GUI
     */
    public String toString() {
        return name;
    }

    public int compareTo(Profession pr) {
        return name.compareTo( pr.toString() );
    }

    public String getName(boolean male) {
        String[] split = name.split( "/" );

        return male ? split[0] : split [1];
    }

    public String getProfessionPath(boolean male) {
        String[] split = professionPath.split( "/" );

        return male ? split[0] : split [1];
    }

    public String getNameAndProfessionPath(boolean male) {
        if ( male ) {
            return getName(true) + "\n" + getProfessionPath(true);
        }
        return getName( false ) + "\n" + getProfessionPath( false );
    }

    public int getRacesSize() {
        return races.length;
    }

    public Talent getTalentAt(int index) {
        return talents.get( index );
    }

    public int getRandomProfessionStat() {
        int random = ( int ) (Math.random() * professionStats.length);
        return professionStats[random];
    }

    public boolean czyJestCechaRozwoju(int x) {
        for (int stat : this.professionStats) {
            if ( stat == x )
                return true;
        }
        return false;
    }

}
