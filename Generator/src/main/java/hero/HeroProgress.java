package hero;

import commons.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class HeroProgress {
    private Hero hero;

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
                    .forEach( skill -> skill.setProfessional( false ) );
        }

        hero.setProfession( profession.toBuilder().build());
        getKnownSkillsFromProfession(currentProfession);
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

    private void getKnownSkillsFromProfession(Profession profession) {
        Set<Skill> knownSkills = hero.getKnownSkills();

        Set<Skill> newProfSkills = profession.getSkills().stream()
                .filter( skill -> !knownSkills.contains( skill ) )
                .collect( Collectors.toSet() );

        knownSkills.addAll( newProfSkills );
    }

    private void setProfessionSkills() {
        List<Skill> skills = new ArrayList<>();
        for (Profession p : hero.getHistory()) {
            if ( p.toString().equals( hero.getProfession().toString() ) ) {
                skills.addAll( p.getSkills() );
            }
        }

        for (Skill skill : skills) {
            for (Skill knownSkill : hero.getKnownSkills()) {
                if ( skill.showSkill().equals( knownSkill.showSkill() ) ) {
                    knownSkill.setProfessional( true );
                }
            }
        }
    }

    public void finishProfession(int level) {
        Profession currentProfession = hero.getProfession();
        updateStatsForNewProfessionLevel( level, currentProfession );

        hero.getStats().updateHp( hero.getHardyLevel() );
        currentProfession.setFinished( true );
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
                .filter( skill ->  skill.isProfessional() && skill.getLevel() >= minStatLevel)
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
            int index = ( int ) (Math.random() * hero.getKnownSkills().size());
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
        int poziom = hero.getProfession().getLevel();
        switch (experienceLevel) {
            case 1:
                randomBonus( 1 * poziom );
                hero.changeProfessionPathDescription( "początkujący" );
                break;
            case 2:
                randomBonus( 3 * poziom );
                hero.changeProfessionPathDescription( "Średniozaawansowana" );
                break;
            case 3:
                randomBonus( 5 * poziom );
                hero.changeProfessionPathDescription( "doświadczona" );
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
