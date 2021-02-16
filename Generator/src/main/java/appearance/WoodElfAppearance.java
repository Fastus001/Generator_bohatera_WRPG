package appearance;

import utilities.DiceRoll;
import utilities.Reader;

import java.util.List;

public class WoodElfAppearance extends Appearance {

    public WoodElfAppearance() {
        setAge( 30 + DiceRoll.one( 10, 10) );
        setHeight( 180 + DiceRoll.one( 3, 10) );
        setHairAndEyes();
    }

    private void setHairAndEyes() {
        List<String[]> hairAndEyes = Reader.getListOfArraysFrom(FILE_NAME);
        assert hairAndEyes != null;

        int randEyes = 18 + ((int) (Math.random()*19));
        setEyesColor( hairAndEyes.get( randEyes )[4] );
        
        int randHair = (int) (Math.random()*19);
        setHairColor( hairAndEyes.get( randHair )[4] );
    }

    @Override
    public void addAge() {
        age += DiceRoll.one( 10, 8);
    }

}
