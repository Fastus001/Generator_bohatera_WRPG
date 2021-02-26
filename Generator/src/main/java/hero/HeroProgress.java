package hero;

import domain.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
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
        profession.getSkills().forEach( hero::addSkill );
        hero.setProfession( profession);
        setProfessionSkills();

        addOneTalentFromNewProfession( profession );

        hero.getStats().updateHp( hero.getHardyLevel() );
    }

    private void addOneTalentFromNewProfession(Profession profession) {
        if ( profession.toString().equals( "CZARODZIEJ" ) && profession.getLevel() == 2 ) {
            Talent nowy = profession.getTalents().get( 0 );
            nowy.setTalentMax( hero.getStats() );
            hero.addTalent( nowy );
        }else {
            hero.addTalentFromProfession();
        }
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
        List<Skill> skills = hero.getHistory().stream()
                .filter( p->p.toString().equals( hero.getProfession().toString() ) )
                .flatMap( p->p.getSkills().stream() )
                .collect( Collectors.toList());

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

    private void setSkillsLevelOnNewProfessionLevel(int minStatLevel) {
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

    private void setStatsDueToNewLevel(int minLevel) {
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

    public int checkProfessionHistory(String newProfession) {
        return hero.getHistory().stream()
                .filter( p->p.getName().equals( newProfession ) )
                .mapToInt( Profession::getLevel )
                .max()
                .orElse( -1 );
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
            int random = RANDOM.nextInt(6);
            if(random<3) {
                for (int i = 0; i < 4; i++) {
                    hero.getStats().increaseStatAt( 1, hero.getProfession().randomProfessionStat(), true );
                }
            }else if(random<5) {
                increaseRandomSkillWithMinLevel( 6 ,35);
            }else{
                hero.addTalentFromProfession();
            }
        }
    }

    private void increaseRandomSkillWithMinLevel(int times, int level) {
        List<Skill> skillsBelowLevel = hero.getKnownSkills()
                .stream()
                .filter( skill -> skill.getLevel() < level )
                .collect( Collectors.toList() );

        for (int i = 0; i < times; i++) {
            int index = RANDOM.nextInt(skillsBelowLevel.size());
            Skill skill = skillsBelowLevel.get( index );
            if ( skill.getLevel() < level ) {
                skill.addToSkillLevel( 1 );
            }
        }
    }
}
