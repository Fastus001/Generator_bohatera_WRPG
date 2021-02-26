package domain;

import appearance.Appearance;
import enums.Gender;
import enums.RaceType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Hero {

    private String name;
    private Gender gender;
    private Appearance appearance;
    private RaceType race;
    private Profession profession;
    private Stats stats;
    private Set<Skill> knownSkills = new TreeSet<>();
    private Set<Talent> knownTalents = new TreeSet<>();
    private List<Profession> history = new ArrayList<>();

    public void addSkill(Skill skill) {
        if ( knownSkills.contains( skill ) ) {
            knownSkills.stream()
                    .filter( s -> s.equals( skill ) )
                    .forEach( s -> {
                        s.addToSkillLevel( skill.getLevel() );
                        s.setProfession( true );
                    } );
        }
        knownSkills.add( skill );
    }

    public void addTalent(Talent talent) {
        if ( knownTalents.add( talent ) ) {
            Skill newSkill = TalentValidator.validate( talent, stats );
            if ( newSkill != null ) {
                addSkill( newSkill );
            }
        }
    }

    public void addTalentFromProfession() {
        Talent randomTalent = profession.randomTalent();
        Talent talent = findTalent( randomTalent );
        if ( talent == null ) {
            randomTalent.setTalentMax( stats );
            addTalent( randomTalent );
        }
        else {
            if ( talent.isUpgradable() ) {
                talent.addOneToLevel();
            }
            else {
                addTalentFromProfession();
            }
        }
    }

    public Talent findTalent(Talent talent) {
        return knownTalents.stream()
                .filter( t -> t.getName().equals( talent.getName() ) )
                .findFirst()
                .orElse( null );
    }

    public int getHardyLevel() {
        return knownTalents.stream()
                .filter( t -> t.getName().equals( "Twardziel" ) )
                .mapToInt( Talent::getLevel )
                .findFirst()
                .orElse( 0 );
    }

    public String getCurrentProfessionName() {
        String[] split = profession.getNameAndProfessionPath( gender ).split( "/" );
        if ( gender.equals( Gender.MALE ) )
            return split[0];
        else {
            return split[1];
        }
    }

    public String professionPathName() {
        String[] split = profession.getProfessionPath(gender).split( " – " );
        return split[0];
    }

    public String professionStatusName() {
        String[] split = profession.getProfessionPath(gender).split( " – " );
        return split[1];
    }

    @Override
    public String toString() {
        return name + ", Profesja: " + profession.getName( gender ) + " poziom profesji: " + profession.getLevel();
    }
}
