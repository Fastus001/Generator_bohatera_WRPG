package appearance;

import utilities.DiceRoll;
import utilities.Reader;

import java.util.List;

public class DwarfAppearance extends Appearance {

    public DwarfAppearance() {
        setAge( 15 + DiceRoll.one( 10, 10));
        setHeight( 130 + DiceRoll.one( 2, 10));
        setHairAndEyes();
    }

    private void setHairAndEyes() {
        List<String[]> hairAndEyes = Reader.getListOfArraysFrom(FILE_NAME);
        assert hairAndEyes != null;

        int randEyes = 18 + ((int) (Math.random()*19));
        setEyesColor( hairAndEyes.get( randEyes )[1] );
        
        int randHair = (int) (Math.random()*19);
        setHairColor( hairAndEyes.get( randHair )[1] );
    }

    @Override
    public void addAge() {
        age += DiceRoll.one( 10, 4 );
    }

}
