package factories;


import appearance.AppearanceFactory;
import domain.*;
import enums.Gender;
import enums.RaceType;
import hero.HeroProgress;
import utilities.NameGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeroFactory {
    public static final int MAX_SKILL_ADVANCES = 40;
    public static final int MAX_SKILL_LEVEL = 10;
    private static HeroFactory instance;
    private static final Random RANDOM = new Random();

    private HeroFactory() {
    }

    public static HeroFactory getInstance(){
        if ( instance == null ){
            instance = new HeroFactory();
        }
        return instance;
    }

    public Hero create(RaceType raceType, String professionName, Gender gender){
        Race race = RaceFactory.getInstance().createRace( raceType );
        Profession profession = ProfessionFactory.getInstance().create( professionName, 1 );

        Hero hero = Hero.builder()
                .name( NameGenerator.getInstance().generateFullName( raceType, gender ) )
                .gender( gender )
                .appearance( AppearanceFactory.create( raceType ) )
                .race( raceType )
                .profession( profession )
                .stats( new Stats( raceType ) )
                .knownSkills( race.getSkillsFromRace() )
                .knownTalents( race.addKnownTalentsFromRace( raceType ) )
                .history( new ArrayList<>() )
                .build();
        hero.getHistory().add( profession );
        HeroProgress heroProgress = new HeroProgress( hero );
        heroProgress.randomBonus( 1 );
        heroProgress.addTalentFromProfession( profession );
        getStartingSkillsFromProfession( profession.getSkills() ).forEach( hero::addSkill );

        return heroProgress.getHero();
    }

    private List<Skill> getStartingSkillsFromProfession(List<Skill> skills){

        for(int i = 0; i < MAX_SKILL_ADVANCES; i++) {
            int random = RANDOM.nextInt( skills.size() );
            if( skills.get( random).getLevel()<= MAX_SKILL_LEVEL ){
                skills.get( random).addToSkillLevel( 1);
            }else{
                i--;
            }
        }
        skills.forEach( skill -> skill.setProfession( true ) );
        return skills;
    }
}
