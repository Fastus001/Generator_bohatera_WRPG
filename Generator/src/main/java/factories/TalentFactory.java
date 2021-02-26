package factories;

import domain.Talent;
import utilities.Reader;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class TalentFactory {
    private static TalentFactory instance;
    private static List<String[]> rawTalents;

    private TalentFactory() {
        rawTalents = Reader.getListOfArraysFrom( "talenty.txt" );
    }

    public static TalentFactory getInstance(){
        if(instance == null){
            instance =  new TalentFactory();
        }
        return instance;
    }


    public Talent createTalent(String name){
        return rawTalents.stream().filter( s->s[0].equals( name ) )
                .findFirst()
                .map( this::mapTalent )
                .orElseThrow();
    }

    public List<Talent> createAll(){
        return rawTalents.stream()
                .map( this::mapTalent )
                .collect( Collectors.toList());
    }

    private Talent mapTalent(String[] o) {
        return Talent.builder()
                .name( o[0] )
                .relatedStat( parseInt( o[1] ) )
                .test( o[2] )
                .description( o[3] ).build();
    }
}
