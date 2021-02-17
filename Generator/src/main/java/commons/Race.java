package commons;

import enums.RaceType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

//dostępne talenty
//ludzie wybór między 0 a 1, w dostępnych talentach
//krasnoludy mają do wyboru pomiędzy 0 a 1, oraz 2-3
//wysokie elfy 0 lub 1  oraz 2 lub 3
//lesne elfy, 0 lub 1, oraz 2 lub 3

@Getter
@AllArgsConstructor
public class Race {

	public static String[] RANDOM_TALENTS_NAMES = {"Atrakcyjny","Silne Nogi","Bardzo Silny","Słuch Absolutny","Błękitna Krew","Strzelec Wyborowy","Błyskotliwość","Szczęście","Charyzmatyczny","Szósty Zmysł","Chodu!","Szybki Refleks","Czujny","Talent Artystyczny","Czysta Dusza","Tragarz","Czytanie-Pisanie","Twardziel","Geniusz Arytmetyczny","Urodzony Wojownik","Naśladowca","Widzenie w Ciemności","Niezwykle Odporny","Wyczucie Kierunku","Oburęczność","Wyczulony Zmysł","Odporny na (Zagrożenie)","Wytwórca (Dowolny)","Poliglota","Zimna krew"};

	public String name;
	public int [] baseStats;
	private final List<Skill> availableSkills;
	private final List<Talent> availableTalents;
	private final String numberOfRandomTalents;


		public Race(Race race) {
			name = race.name;
			baseStats = race.baseStats;
			availableSkills = new ArrayList<>();
			for(Skill skill : race.availableSkills){
				availableSkills.add( skill.toBuilder().build());
			}
			availableTalents = new ArrayList<>();
			for(Talent talent : race.availableTalents){
				availableTalents.add( talent.toBuilder().build());
			}
			numberOfRandomTalents = race.numberOfRandomTalents;
		}

		public List<Skill> getAvailableSkills() {
			return availableSkills;
		}
		
		public Talent getAvailableTalents(int x){
			return availableTalents.get( x);
		}
		
		public Talent getRandomTalent() {
			List<Talent> listaLosowychTalentow = new ArrayList<>();
			List<Talent> listaTalentow2 = new ArrayList<>();
			try{
				ClassLoader classLoader = getClass().getClassLoader();
				InputStream input = classLoader.getResourceAsStream("talenty.txt");
				assert input != null;
				InputStreamReader czytaj = new InputStreamReader( input, StandardCharsets.UTF_8);
					BufferedReader czytajBuf = new BufferedReader(czytaj);
					String wiersz = null;
					
					while((wiersz = czytajBuf.readLine()) !=null){
						if(wiersz.length()==0)
							break;
						String[] wynik = wiersz.split("/");
						Talent tl = Talent.builder()
								.name(wynik[0])
								.relatedStat(parseInt( wynik[1] ))
								.test( wynik[2])
								.description(wynik[3] ).build();
						listaTalentow2.add(tl);
					}
					czytajBuf.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			//wyszukanie i skopiowanie losowych talentow
			for(String x: RANDOM_TALENTS_NAMES){
				for(Talent talent:listaTalentow2){	
					if(x.equals(talent.getName())){
						listaLosowychTalentow.add(talent);
						break;
					}
				}
			}
			int numer = (int) (Math.random()*listaLosowychTalentow.size() );
			return listaLosowychTalentow.get(numer);	
		}
		
		public int getNumberOfRandomTalents(){
			return parseInt( numberOfRandomTalents );
		}
		
		public int getSizeOfAvailableTalents(){
			return availableTalents.size();
		}

		public RaceType getRaceEnum(){
			return RaceType.getRaceEnumByName( name );
		}
	}