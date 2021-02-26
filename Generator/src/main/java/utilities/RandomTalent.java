package utilities;

import domain.Talent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class RandomTalent {
    private static RandomTalent instance;

    private Map<String,Talent> talents;

    public static final String[] RANDOM_TALENTS_NAMES = {"Atrakcyjny","Silne Nogi","Bardzo Silny","Słuch Absolutny",
            "Błękitna Krew","Strzelec Wyborowy","Błyskotliwość","Szczęście","Charyzmatyczny","Szósty Zmysł","Chodu!",
            "Szybki Refleks","Czujny","Talent Artystyczny","Czysta Dusza","Tragarz","Czytanie-Pisanie","Twardziel",
            "Geniusz Arytmetyczny","Urodzony Wojownik","Naśladowca","Widzenie w Ciemności","Niezwykle Odporny",
            "Wyczucie Kierunku","Oburęczność","Wyczulony Zmysł","Odporny na (Zagrożenie)","Wytwórca (Dowolny)","Poliglota"
            ,"Zimna krew"};

    private RandomTalent() {
        List<String[]> temp = Reader.getListOfArraysFrom( "talenty.txt" );
        assert temp != null;
        addTalentsToMap( temp );
    }

    private void addTalentsToMap(List<String[]> temp) {
        talents = temp.stream()
                .map( this::convert )
                .collect( Collectors.toMap( Talent::getName, talent -> talent ) );
    }

    public static RandomTalent getInstance(){
        if(RandomTalent.instance==null){
            instance = new RandomTalent();
        }
        return instance;
    }

    private Talent convert(String[] split){
        return Talent.builder()
                .name(split[0])
                .relatedStat(parseInt( split[1] ))
                .test( split[2])
                .description(split[3] ).build();
    }

    public Talent getTalent(){
        int random = (int) (Math.random()*RANDOM_TALENTS_NAMES.length );

        return talents.get( RANDOM_TALENTS_NAMES[random] );
    }
}
