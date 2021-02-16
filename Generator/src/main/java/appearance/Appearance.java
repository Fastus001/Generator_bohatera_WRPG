package appearance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class Appearance {
    public static final String FILE_NAME = "hair_color_eyes_color.txt";

    protected int age;
    private int height;
    private String eyesColor;
    private String hairColor;

    public abstract void addAge();

    public String showAll(){
        return "Wiek: " + age +
                ", Wzrost: " + height +
                " cm, Kolor oczu: " + eyesColor +
                ", Kolor włosów: " + hairColor + ".\n";
    }
}
