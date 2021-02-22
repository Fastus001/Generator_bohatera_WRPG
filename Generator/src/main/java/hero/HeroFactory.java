package hero;


import commons.Hero;
import enums.Gender;
import enums.RaceType;

public class HeroFactory {
    private static HeroFactory instance;

    private HeroFactory() {
    }

    public static HeroFactory getInstance(){
        if ( instance == null ){
            instance = new HeroFactory();
        }
        return instance;
    }

    public Hero create(RaceType raceType, String profession, Gender gender){
        return null;
    }

}
