package commons;

import enums.RaceEnum;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

//dostępne talenty
//ludzie wybór między 0 a 1, w dostępnych talentach
//krasnoludy mają do wyboru pomiędzy 0 a 1, oraz 2-3
//wysokie elfy 0 lub 1  oraz 2 lub 3
//lesne elfy, 0 lub 1, oraz 2 lub 3

public class Rasa  {
		public String nazwa;
		public int [] cechyBazowe;
		private ArrayList<Skill> dostepneUmiejetnosci;
		private ArrayList<Talent> dostepneTalenty;
		private String iloscLosowychTalentow;
		static public String[] LISTA_TALENTOW = {"Atrakcyjny","Silne Nogi","Bardzo Silny","Słuch Absolutny","Błękitna Krew","Strzelec Wyborowy","Błyskotliwość","Szczęście","Charyzmatyczny","Szósty Zmysł","Chodu!","Szybki Refleks","Czujny","Talent Artystyczny","Czysta Dusza","Tragarz","Czytanie-Pisanie","Twardziel","Geniusz Arytmetyczny","Urodzony Wojownik","Naśladowca","Widzenie w Ciemności","Niezwykle Odporny","Wyczucie Kierunku","Oburęczność","Wyczulony Zmysł","Odporny na (Zagrożenie)","Wytwórca (Dowolny)","Poliglota","Zimna krew"};
	
		
		public Rasa(String n, int [] cb, ArrayList<Skill> dU, ArrayList<Talent> dT, String iT){
			nazwa = n;
			cechyBazowe = cb;
			dostepneUmiejetnosci = dU;
			dostepneTalenty = dT;
			iloscLosowychTalentow = iT;
		}
		
		public Rasa(Rasa rs) {
			nazwa = rs.nazwa;
			cechyBazowe = rs.cechyBazowe;
			dostepneUmiejetnosci = new ArrayList<Skill>();
			for(Skill tempUm : rs.dostepneUmiejetnosci){
				Skill nowaUm = tempUm.toBuilder().build();
				dostepneUmiejetnosci.add(nowaUm);
			}
			dostepneTalenty = new ArrayList<Talent>();
			for(Talent tempTl : rs.dostepneTalenty){
				Talent nowyTl = tempTl.toBuilder().build();
				dostepneTalenty.add(nowyTl);
			}
			iloscLosowychTalentow = rs.iloscLosowychTalentow;
		}
		
		public String toString(){
			return nazwa;
		}
		
		public String wyswietlRasyWszystko(){
			String doWyswietlenia = new String();
			doWyswietlenia += nazwa + "\nCechy Bazowe: ";
			for(int x: cechyBazowe){
				doWyswietlenia += Integer.toString(x) + " ,";
			}
			doWyswietlenia +="\nDostępne umiejętności: ";
			for(Skill s: dostepneUmiejetnosci){
			}
			doWyswietlenia +="\nDostępne talenty: ";
			for(Talent x: dostepneTalenty){
				doWyswietlenia +=x.showAll() +",";
			}
			doWyswietlenia +="\nDostępne losowe talenty:" + iloscLosowychTalentow;
			return doWyswietlenia;			
		}
		
		public String getName(){
			return nazwa;
		}
		
		public ArrayList<Skill> getListaDostepnychUmiejetnosci() {
			return dostepneUmiejetnosci;
		}
		
		public Talent getTalentDostepne(int x){
			return dostepneTalenty.get(x);
		}
		
		public Talent getRandomTalent() {
			ArrayList<Talent> listaLosowychTalentow = new ArrayList<Talent>();
			ArrayList<Talent> listaTalentow2 = new ArrayList<Talent>();
			try{
				/*
				ClassLoader classLoader2 = getClass().getClassLoader();
				InputStream inputStream2 = classLoader2.getResourceAsStream("resources/talenty.txt");
				InputStreamReader czytaj = new InputStreamReader(inputStream2);
				
					File plik = new File("../GeneratorBohatera/src/resources/talenty.txt");
					FileReader czytaj = new FileReader(plik);
				*/	
					ClassLoader classLoader = getClass().getClassLoader();
					InputStream input = classLoader.getResourceAsStream("talenty.txt");
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
			for(String x:LISTA_TALENTOW){
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
		
		public int getIloscLosowychTalentow(){
			return parseInt(iloscLosowychTalentow);
		}
		
		public int getIloscDostepnychTalentow(){
			return dostepneTalenty.size();
		}

		public RaceEnum getRaceEnum(){
			return RaceEnum.getRaceEnumByName( nazwa );
		}
		
	}