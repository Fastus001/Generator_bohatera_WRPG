package appearance;

import utilities.DiceRoll;
import utilities.Reader;

import java.util.List;

public class HumanAppearance extends Appearance {

    public HumanAppearance() {
        setAge( 15 + DiceRoll.one( 10) );
        setHeight( 150 + DiceRoll.one( 4, 10 ) );
        setHairAndEyes();
    }

    private void setHairAndEyes() {
        List<String[]> hairAndEyes = Reader.getListOfArraysFrom(FILE_NAME);
        assert hairAndEyes != null;

        int randEyes = 18 + ((int) (Math.random()*19));
        setEyesColor( hairAndEyes.get( randEyes )[0] );

        int randHair = (int) (Math.random()*19);
        setHairColor( hairAndEyes.get( randHair )[0] );
    }

    @Override
    public void addAge() {
        age += DiceRoll.one( 6, 2 );
    }

}
