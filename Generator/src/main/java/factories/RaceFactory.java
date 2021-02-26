package factories;

import domain.Race;
import domain.Skill;
import domain.Talent;
import enums.RaceType;
import utilities.Reader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class RaceFactory {
    private static RaceFactory instance;
    private static List<String[]> rawRaces;

    private RaceFactory() throws IOException {
        rawRaces = Reader.getListOfArraysFrom( "races.txt" );
    }

    public static RaceFactory getInstance(){
        if ( instance ==null ){
            try {
                instance = new RaceFactory();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public Race createRace(RaceType raceType){
        Race race = rawRaces.stream()
                .filter( s -> s[0].equals( raceType.getName() ) )
                .map( this::mapRace )
                .findFirst()
                .orElseThrow();
        return race;
    }

    public List<Race> createAll(){
        return rawRaces.stream()
                .map( this::mapRace )
                .collect( Collectors.toList());
    }


    private Race mapRace(String[] rawRaceToBuild){
        return Race.builder()
                .name( rawRaceToBuild[0] )
                .skills( createSkills( rawRaceToBuild[1] ) )
                .talents( createTalents( rawRaceToBuild[2] ) )
                .build();
    }

    private List<Skill> createSkills(String names) {
        return Arrays.stream( names.split( ":" ) )
                .map( s-> SkillFactory.getInstance().createSkill( s ) )
                .collect( Collectors.toList());
    }

    private List<Talent> createTalents(String s1) {
        return Arrays.stream( s1.split( ":" ) )
                .map( s-> TalentFactory.getInstance().createTalent( s ) )
                .collect( Collectors.toList());
    }


}
