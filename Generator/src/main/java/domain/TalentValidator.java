package domain;

public class TalentValidator {

    public static Skill validate(Talent talent, Stats stats) {
        Skill skill = null;
        switch(talent.getName()) {
            case "Urodzony Wojownik": if(talent.isShow()){
                stats.increaseStatAt( 5, 0, false); talent.setShow( false);
            }break;
            case "Strzelec Wyborowy": if(talent.isShow()){
                stats.increaseStatAt( 5, 1, false); talent.setShow( false);
            }break;
            case "Bardzo Silny": if(talent.isShow()){
                stats.increaseStatAt( 5, 2, false);talent.setShow( false);
            }break;
            case "Niezwykle Odporny": if(talent.isShow()){
                stats.increaseStatAt( 5, 3, false); talent.setShow( false);
            }break;
            case "Czujny": if(talent.isShow()){
                stats.increaseStatAt( 5, 4, false); talent.setShow( false);
            }break;
            case "Szybki Refleks": if(talent.isShow()){
                stats.increaseStatAt( 5, 5, false); talent.setShow( false);
            }break;
            case "Zręczny": if(talent.isShow()){
                stats.increaseStatAt( 5, 6, false); talent.setShow( false);
            }break;
            case "Błyskotliwość":  if(talent.isShow()){
                stats.increaseStatAt( 5, 7, false); talent.setShow( false);
            }break;
            case "Zimna krew": if(talent.isShow()){
                stats.increaseStatAt( 5, 8, false); talent.setShow( false);
            }break;
            case "Charyzmatyczny": if(talent.isShow()){
                stats.increaseStatAt( 5, 9, false); talent.setShow( false);
            }break;
            case "Bardzo Szybki": if(talent.isShow()){
                stats.addOneToSpeed(); talent.setShow( false);
            }break;

            case "Słuch Absolutny":  skill = Skill.builder().name( "Występy (Śpiewanie)").statNumber(9).type( "podstawowa").level( 0).isProfession( false ).build();  break;
            case "Obieżyświat":  skill = Skill.builder().name( "Wiedza (Lokalna)").statNumber(7).type("zaawansowana").level(0).isProfession( false).build();  break;
            case "Czarownica!":  skill = Skill.builder().name( "Język (Magiczny)").statNumber(7).type("zaawansowana").level(0).isProfession( false).build();  break;
            case "Wytwórca (Dowolny)":  skill = Skill.builder().name("Rzemiosło (Dowolny)").statNumber(6).type("zaawansowana").level(0).isProfession( false).build();  break;
            case "Wytwórca (Materiały Wybuchowe)":  skill = Skill.builder().name( "Rzemiosło (Materiały Wybuchowe)").statNumber(6).type("zaawansowana").level(0).isProfession( false).build();  break;
            case "Wytwórca (Zielarz)":  skill = Skill.builder().name( "Rzemiosło (Zielarz)").statNumber(6).type("zaawansowana").level(0).isProfession( false).build();  break;
            case "Wytwórca (Dowolne Rzemiosło)":  skill = Skill.builder().name( "Rzemiosło (Dowolne Rzemiosło)").statNumber(6).type("zaawansowana").level(0).isProfession( false).build();  break;
            case "Wytwórca (Szkutnik)":  skill = Skill.builder().name( "Rzemiosło (Szkutnik)").statNumber(6).type("zaawansowana").level(0).isProfession( false).build();  break;
            case "Wytwórca (Aptekarz)":  skill = Skill.builder().name( "Rzemiosło (Aptekarz)").statNumber(6).type("zaawansowana").level(0).isProfession( false).build();  break;
            case "Talent Artystyczny":  skill = Skill.builder().name( "Sztuka (Dowolna)").statNumber(6).type("podstawowa").level(0).isProfession( false).build(); break;
        }
        return skill;
    }
}
