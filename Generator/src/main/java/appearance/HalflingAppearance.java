package appearance;

import utilities.DiceRoll;
import utilities.Reader;

import java.util.List;

public class HalflingAppearance extends Appearance {

    public HalflingAppearance() {
        setAge( 15 + DiceRoll.one( 10, 5) );
        setHeight( 95 + DiceRoll.one( 2, 10) );
        setHairAndEyes();
    }

    private void setHairAndEyes() {
        List<String[]> hairAndEyes = Reader.getHairAndEyes();
        assert hairAndEyes != null;

        int randEyes = 18 + ((int) (Math.random()*19));
        setEyesColor( hairAndEyes.get( randEyes )[2] );
        
        int randHair = (int) (Math.random()*19);
        setHairColor( hairAndEyes.get( randHair )[2] );
    }

    @Override
    public void addAge() {
        age += DiceRoll.one( 10, 2);
    }

}
