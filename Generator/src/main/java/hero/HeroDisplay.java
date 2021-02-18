package hero;

import commons.*;
import enums.Gender;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HeroDisplay {
    private final Hero hero;
    private StringBuilder sb = new StringBuilder();

    public HeroDisplay(Hero hero) {
        this.hero = hero;
    }

    public String showHero(boolean withTalentsDescription){
        Profession profession = hero.getProfession();
        Set<Skill> skills = hero.getKnownSkills();
        Set<Talent> talents = hero.getKnownTalents();
        Stats stats = hero.getStats();

        heroDescription( profession);

        sb.append("\n").append( stats.showStats( profession.getProfessionStats())).append( "\n" );

        sb.append("Znane Umiejętności:\n");
        for(Skill skill: skills){
            int skillLevel = stats.getStatAt( skill.getStatNumber()) + skill.getLevel();
            sb.append(skill.showSkill() + " (" +  skillLevel + "), ");
        }
        sb.append("\n\nZnane talenty:\n");

        for(Talent t: talents){
            sb.append(t.showTalentNameWithLevel()+", ");
        }
        sb.append("\n\nPrzedmioty dostępne z profesji:\n")
        .append( getItemsString( ));
        sb.append("\n");


        if(withTalentsDescription){
            for(Talent st: talents){
                if(st.isShow()){
                    sb.append(st.getName() + " - " +st.getDescription() +"\nTesty: " + st.getTest() +"\nMaksymalny poziom Talentu " + st.getMaxLevel() + "\n\n");
                }
            }
        }

        return sb.toString();
    }

    private String getItemsString() {
        return hero.getHistory()
                .stream()
                .map( Profession::getItems )
                .collect( Collectors.joining( "," ) );
    }

    private void heroDescription(Profession profession) {
        Gender gender = hero.getGender();
        List<Profession> history = hero.getHistory();
        sb.append( hero.getRace().getName() ).append( "\n" )
          .append( hero.getName() ).append( " (" ).append( gender.getName() ).append( ")\n" )
          .append( hero.getAppearance().showAll() ).append( "Klasa postaci: " )
          .append( profession.getCareer() ).append( "\n" )
          .append( profession.getNameAndProfessionPath( gender ) )
          .append( " (Poziom profesji: " ).append( profession.getLevel() ).append( ")\n" )
          .append("Historia rozwoju bohatera: ");
        for(int i = 0; i < (history.size()-1); i++)
        {
            String[] nazwaSciezkiProfesji = history.get( i).getProfessionPath( gender ).split( "–");
            sb.append( "ex-" ).append( nazwaSciezkiProfesji[0] ).append( "," );
        }
    }


}
