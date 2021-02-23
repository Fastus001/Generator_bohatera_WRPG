package hero;


import appearance.AppearanceFactory;
import commons.*;
import enums.Gender;
import enums.RaceType;
import factories.ProfessionFactory;
import factories.RaceFactory;
import utilities.NameGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeroFactory {
    private static HeroFactory instance;
    private static Random RANDOM = new Random();

    private HeroFactory() {
    }

    public static HeroFactory getInstance(){
        if ( instance == null ){
            instance = new HeroFactory();
        }
        return instance;
    }

    public Hero create(RaceType raceType, String profession, Gender gender){
        Race race = RaceFactory.getInstance().createRace( raceType );
        Profession professionToAdd = ProfessionFactory.getInstance().create( profession, 1 );

        Hero hero = Hero.builder()
                .name( NameGenerator.getInstance().generateFullName( raceType, gender ) )
                .gender( gender )
                .appearance( AppearanceFactory.create( raceType ) )
                .race( raceType )
                .profession( professionToAdd )
                .stats( new Stats( raceType ) )
                .knownSkills( race.getSkillsFromRace() )
                .knownTalents( race.addKnownTalentsFromRace( raceType ) )
                .history( new ArrayList<>() )
                .build();

        addKnownTalentFromProfession( hero);
        getStartingSkillsFromProfession( professionToAdd.getSkills() ).forEach( hero::addSkill );
        addRandomStatistic( hero );
        randomBonus( hero,1 );
        hero.getHistory().add( professionToAdd );

        return hero;
    }

    private void addKnownTalentFromProfession(Hero build) {
        Talent randomTalent = build.getProfession().randomTalent();
        if(!build.knownTalents.contains( randomTalent )){
            build.knownTalents.add( randomTalent );
        }else{
            Talent talent = build.knownTalents.stream()
                    .filter( t -> t.equals( randomTalent ) )
                    .findFirst()
                    .orElseThrow();

            if(talent.isUpgradable()){
                talent.addOneToLevel();
            }else{
                addKnownTalentFromProfession( build );
            }
        }
    }

    private List<Skill> getStartingSkillsFromProfession(List<Skill> skills){

        for(int i = 0; i < 40; i++) {
            int random = RANDOM.nextInt( skills.size() );
            if( skills.get( random).getLevel()<11){
                skills.get( random).addToSkillLevel( 1);
            }else{
                i--;
            }
        }
        skills.forEach( skill -> skill.setProfessional( true ) );
        return skills;
    }

    private void addRandomStatistic(Hero hero){
        hero.getStats().increaseStatAt( 5, hero.getProfession().getRandomProfessionStat(), true);
    }

    public void randomBonus(Hero hero,int times) {
        for(int n = 0; n < times; n++){
            int random = (int) (Math.random()*5);
            switch(random){
                case 0:
                case 1:
                case 2: for(int i = 0; i < 4; i++){
                    hero.getStats().increaseStatAt( 1, hero.getProfession().getRandomProfessionStat(), true);
                } break;
                case 3:
                case 4: hero.increaseRandomSkill( 6); break;
                case 5: addKnownTalentFromProfession( hero); break;
            }
        }
    }


}
