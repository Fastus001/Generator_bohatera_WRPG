package factories;

import commons.Profession;
import commons.Skill;
import commons.Talent;
import enums.Gender;
import utilities.Reader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.*;

public class ProfessionFactory {
    private static ProfessionFactory instance;
    private static List<String []> rawProfessions;

    private ProfessionFactory() {
        rawProfessions = Reader.getListOfArraysFrom( "professions.txt" );
    }

    public static ProfessionFactory getInstance(){
        if(instance==null){
            instance = new ProfessionFactory();
        }
        return instance;
    }

    public Profession create(String name ,int level){
        return rawProfessions.stream()
                .filter( s -> s[1].equals( name ) && parseInt( s[8]) == level)
                .findFirst()
                .map( this::mapProfession )
                .orElseThrow();
    }

    private Profession mapProfession(String [] split){
        return Profession.builder()
                .career( split[0] )
                .name( split[1] )
                .professionPath( split[2] )
                .races( split[3].split( "," ) )
                .skills( createSkills( split[4] ) )
                .talents( createTalents( split[5] ) )
                .items( split[6] )
                .professionStats( getProfessionStats( split[7] ) )
                .level( parseInt( split[8]) )
                .build();

    }

    private List<Skill> createSkills(String toSplit) {
        return Arrays.stream( toSplit.split( "/" ) )
                .map( s-> SkillFactory.getInstance().createSkill( s ) )
                .collect( Collectors.toList());
    }

    private List<Talent> createTalents(String toSplit) {
        return Arrays.stream( toSplit.split( "/" ) )
                .map( s-> TalentFactory.getInstance().createTalent( s ) )
                .collect( Collectors.toList());
    }

    private int[] getProfessionStats(String s) {
        return Arrays.stream( s.split( "," ) )
                .mapToInt( Integer::parseInt )
                .toArray();
    }
}
