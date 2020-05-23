package commons;
import java.util.*;
import java.io.*;

//dostêpne talenty
//ludzie wybór miêdzy 0 a 1, w dostêpnych talentach
//krasnoludy maj¹ do wyboru pomiêdzy 0 a 1, oraz 2-3
//wysokie elfy 0 lub 1  oraz 2 lub 3
//lesne elfy, 0 lub 1, oraz 2 lub 3

public class Rasa  {
		public String nazwa;
		public int [] cechyBazowe;
		private ArrayList<Umiejetnosc> dostepneUmiejetnosci;
		private ArrayList<Talent> dostepneTalenty;
		private String iloscLosowychTalentow;
		static public String[] LISTA_TALENTOW = {"Atrakcyjny","Silne Nogi","Bardzo Silny","S³uch Absolutny","B³êkitna Krew","Strzelec Wyborowy","B³yskotliwoœæ","Szczêœcie","Charyzmatyczny","Szósty Zmys³","Chodu!","Szybki Refleks","Czujny","Talent Artystyczny","Czysta Dusza","Tragarz","Czytanie-Pisanie","Twardziel","Geniusz Arytmetyczny","Urodzony Wojownik","Naœladowca","Widzenie w Ciemnoœci","Niezwykle Odporny","Wyczucie Kierunku","Oburêcznoœæ","Wyczulony Zmys³","Odporny na (Zagro¿enie)","Wytwórca (Dowolny)","Poliglota","Zimna krew"};
	
		
		public Rasa(String n, int [] cb, ArrayList<Umiejetnosc> dU, ArrayList<Talent> dT, String iT){
			nazwa = n;
			cechyBazowe = cb;
			dostepneUmiejetnosci = dU;
			dostepneTalenty = dT;
			iloscLosowychTalentow = iT;
		}
		
		public Rasa(Rasa rs) {
			nazwa = rs.nazwa;
			cechyBazowe = rs.cechyBazowe;
			dostepneUmiejetnosci = new ArrayList<Umiejetnosc>();
			for(Umiejetnosc tempUm : rs.dostepneUmiejetnosci){
				Umiejetnosc nowaUm = new Umiejetnosc(tempUm);
				dostepneUmiejetnosci.add(nowaUm);
			}
			dostepneTalenty = new ArrayList<Talent>();
			for(Talent tempTl : rs.dostepneTalenty){
				Talent nowyTl = new Talent(tempTl);
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
			doWyswietlenia +="\nDostêpne umiejêtnoœci: ";
			for(Umiejetnosc s: dostepneUmiejetnosci){
				doWyswietlenia +=s.wyswietlWszystko() +",";
			}
			doWyswietlenia +="\nDostêpne talenty: ";
			for(Talent x: dostepneTalenty){
				doWyswietlenia +=x.wyswietlWszystkoTalent() +",";
			}
			doWyswietlenia +="\nDostêpne losowe talenty:" + iloscLosowychTalentow;
			return doWyswietlenia;			
		}
		
		public String getName(){
			return nazwa;
		}
		
		public ArrayList<Umiejetnosc> getListaDostepnychUmiejetnosci() {
			return dostepneUmiejetnosci;
		}
		
		public Talent getTalentDostepne(int x){
			return dostepneTalenty.get(x);
		}
		
		public Talent getRandomTalent() {
			ArrayList<Talent> listaLosowychTalentow = new ArrayList<Talent>();
			ArrayList<Talent> listaTalentow2 = new ArrayList<Talent>();
			try{
				ClassLoader classLoader2 = getClass().getClassLoader();
				InputStream inputStream2 = classLoader2.getResourceAsStream("resources/talenty.txt");
				InputStreamReader czytaj = new InputStreamReader(inputStream2);
				/*
					File plik = new File("../GeneratorBohatera/src/resources/talenty.txt");
					FileReader czytaj = new FileReader(plik);
					*/
					BufferedReader czytajBuf = new BufferedReader(czytaj);
					String wiersz = null;
					
					while((wiersz = czytajBuf.readLine()) !=null){
						if(wiersz.length()==0)
							break;
						String[] wynik = wiersz.split("/");
						Talent tl = new Talent(wynik[0],Integer.parseInt(wynik[1]),wynik[2], wynik[3]);
						listaTalentow2.add(tl);
					}
					czytajBuf.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			//wyszukanie i skopiowanie losowych talentow
			for(String x:LISTA_TALENTOW){
				for(Talent talent:listaTalentow2){	
					if(x.equals(talent.toString())){
						listaLosowychTalentow.add(talent);
						break;
					}
				}
			}
			int numer = (int) (Math.random()*listaLosowychTalentow.size() );
			return listaLosowychTalentow.get(numer);	
		}
		
		public int getIloscLosowychTalentow(){
			return Integer.parseInt(iloscLosowychTalentow);
		}
		
		public int getIloscDostepnychTalentow(){
			return dostepneTalenty.size();
		}
		
	}