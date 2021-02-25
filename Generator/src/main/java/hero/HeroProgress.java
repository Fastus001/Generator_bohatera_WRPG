package hero;

import commons.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class HeroProgress {
    private static final Random RANDOM = new Random();
    private final Hero hero;

    public HeroProgress(Hero hero) {
        this.hero = hero;
    }

    public void newProfession(Profession profession) {
        hero.getHistory().add( profession );
        setProfessionSkills();

        Profession currentProfession = hero.getProfession();
        updateStatsForNewProfessionLevel( profession.getLevel(), currentProfession );

        hero.getAppearance().addAge();

        if ( currentProfession.isNotFinished() ){
            currentProfession.setFinished( true );
        }

        if ( !currentProfession.toString().equals( profession.toString() ) ) {
            hero.getKnownSkills()
                    .forEach( skill -> skill.setProfession( false ) );
        }

        getKnownSkillsFromProfession(profession);
        hero.setProfession( profession);
        setProfessionSkills();

        if ( currentProfession.toString().equals( "CZARODZIEJ" ) && currentProfession.getLevel() == 2 ) {
            Talent nowy = currentProfession.getTalents().get( 0 );
            nowy.setTalentMax( hero.getStats() );
            hero.addTalent( nowy );
        }else {
            hero.addTalentFromProfession();
        }

        hero.getStats().updateHp( hero.getHardyLevel() );
    }

    public void getKnownSkillsFromProfession(Profession profession) {
        hero.getKnownSkills().addAll( profession.getSkills() );
    }

    public void addTalentFromProfession(Profession profession) {
        Talent randomTalent = profession.randomTalent();
        Talent talent = hero.findTalent( randomTalent );
        if(talent == null){
            randomTalent.setTalentMax( hero.getStats() );
            hero.addTalent( randomTalent);
        }else{
            if( talent.isUpgradable()){
                talent.addOneToLevel();
            }else{
                addTalentFromProfession(profession);
            }
        }
    }

    public void setProfessionSkills() {
        List<Skill> skills = new ArrayList<>();
            for (Profession p : hero.getHistory()) {
            if ( p.toString().equals( hero.getProfession().toString() ) ) {
                skills.addAll( p.getSkills() );
            }
        }
        hero.getKnownSkills()
                .stream()
                .filter( skills::contains )
                .forEach( skill -> skill.setProfession( true ) );
    }

    public void finishProfession(int level) {
        updateStatsForNewProfessionLevel( level, hero.getProfession() );

        hero.getStats().updateHp( hero.getHardyLevel() );
        hero.getProfession().setFinished( true );
    }

    private void updateStatsForNewProfessionLevel(int level, Profession currentProfession) {
        if ( currentProfession.getLevel() < level ) {
            int minSkillLevel = 5 * currentProfession.getLevel();
            setSkillsLevelOnNewProfessionLevel( minSkillLevel );
            setStatsDueToNewLevel( minSkillLevel );
        }
    }

    public void setSkillsLevelOnNewProfessionLevel(int minStatLevel) {
        long numberOfSkills = hero.getKnownSkills()
                .stream()
                .filter( skill ->  skill.isProfession() && skill.getLevel() >= minStatLevel)
                .count();

        if ( numberOfSkills < 8 ) {
            log.debug( "not enough skills to get eight to correct level!" + numberOfSkills );
            increaseRandomSkillWithMinLevel( 3, minStatLevel );
            setSkillsLevelOnNewProfessionLevel( minStatLevel );
        }
    }

    public void increaseRandomSkillWithMinLevel(int times, int level) {
        List<Skill> temp = new ArrayList<>( hero.getKnownSkills() );
        for (int i = 0; i < times; i++) {
            int index = RANDOM.nextInt(hero.getKnownSkills().size());
            Skill skill = temp.get( index );
            if ( skill.getLevel() < level ) {
                skill.addToSkillLevel( 1 );
            }
        }
    }

    public void setStatsDueToNewLevel(int minLevel) {
        int[] professionStats = hero.getProfession().getProfessionStats();
        Stats stats = hero.getStats();
        for (int stat : professionStats) {
            int advances = stats.getAdvancesAt( stat );
            if ( advances < minLevel ) {
                advances = minLevel - advances;
                stats.increaseStatAt( advances, stat, true );
            }
        }
    }

    public int checkProfessionHistory(Profession newProfession) {
        int poziom = -1;
        for (Profession oldProfession : hero.getHistory()) {
            if ( newProfession.toString().equals( oldProfession.toString() ) )
                poziom = oldProfession.getLevel();
        }
        return poziom;
    }

    public void experienceLevel(int experienceLevel) {
        int level = hero.getProfession().getLevel();
        switch (experienceLevel) {
            case 1:
                randomBonus( level );
                break;
            case 2:
                randomBonus( 3 * level );
                break;
            case 3:
                randomBonus( 5 * level );
                break;
        }
        hero.getStats().updateHp( hero.getHardyLevel() );
    }

    public void randomBonus(int times) {
        for (int n = 0; n < times; n++) {
            int random = ( int ) (Math.random() * 5);
            switch (random) {
                case 0:
                case 1:
                case 2:
                    for (int i = 0; i < 4; i++) {
                        hero.getStats().increaseStatAt( 1, hero.getProfession().randomProfessionStat(), true );
                    }
                    break;
                case 3:
                case 4:
                    increaseRandomSkill( 6 );
                    break;
                case 5:
                    hero.addTalentFromProfession();
                    break;
            }
        }
    }

    public void increaseRandomSkill(int times) {
        List<Skill> temp = new ArrayList<>( hero.getKnownSkills() );
        for (int i = 0; i < times; i++) {
            int index = ( int ) (Math.random() * hero.getKnownSkills().size());
            temp.get( index ).addToSkillLevel( 1 );
        }
    }
}
