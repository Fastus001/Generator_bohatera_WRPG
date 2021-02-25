package commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Skill implements Comparable<Skill> {

    private String name;
    private String type;
    private int statNumber;
    private int level;
    private boolean isProfession;

    public void addToSkillLevel(int toAdd) {
        level += toAdd;
    }

    public String showSkill() {
        String temp = name + " +" + level;
        return isProfession ? temp + "*" : temp;
    }

    public int compareTo(Skill skill) {
        return name.compareTo( skill.getName() );
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        Skill skill = ( Skill ) o;

        return name != null ? name.equals( skill.name ) : skill.name == null;
    }

}

