package enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Mężczyzna"),
    FEMALE("Kobieta");

    private String name;

    Gender(String name) {
        this.name = name;
    }

    public static Gender getGender(boolean male) {
        return male ? Gender.MALE : Gender.FEMALE;
    }
}
