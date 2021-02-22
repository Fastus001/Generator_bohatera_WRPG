package factories;

import commons.Skill;
import utilities.Reader;

import java.util.List;

public class SkillFactory {
    private static SkillFactory instance;
    private static List<String[]> rawSkills;

    public SkillFactory() {
        rawSkills = Reader.getListOfArraysFrom( "skills2.txt" );
    }

    public static SkillFactory getInstance(){
        if(instance == null){
            instance = new SkillFactory();
        }
        return instance;
    }

    public Skill createSkill(String name){
        return rawSkills.stream()
                .filter( s -> s[0].equals( name ) )
                .map( s -> mapSkill( s ) )
                .findFirst()
                .orElseThrow();
    }

    private Skill mapSkill(String[] strings) {
        return Skill.builder()
                .name( strings[0] )
                .statNumber( Integer.parseInt( strings[1] ) )
                .type( strings[2] )
                .build();
    }

}
