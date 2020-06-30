package commons;

import java.util.*;

import npcGenerator.Cechy;

public class Bohater {

	private String imieNazwisko;


	private String plecBohatera;
	private Wyglad wyglad;
	
	
	private Rasa rasa;
	private Profesja prof;
	private Cechy cechy;
	public ArrayList <Umiejetnosc> znaneUmiejetnosci;
	public ArrayList<Talent> znaneTalenty;
	private ArrayList<Profesja> historiaProfesji;
	

	
	public Bohater(Rasa rs, Profesja pr, boolean plec) {
		rasa = new Rasa(rs);
		prof = new Profesja(pr);
		
		GeneratorImion genImion = new GeneratorImion();
		imieNazwisko = genImion.getFullName(rasa.getName(), plec);
		
		wyglad = new Wyglad(rasa);
		
		if(plec)
			plecBohatera = "M�czyzna";
		else
			plecBohatera = "Kobieta";

		
		cechy = new Cechy(rs.cechyBazowe, rs.nazwa);
		znaneUmiejetnosci = new ArrayList<Umiejetnosc>();
		dodajZnaneUmiejetnosciZRasy();
		znaneTalenty = new ArrayList<Talent>();
		dodajZnaneTalentyZRasy();
		dodajZnanyTalentZProfesji();
		for(Talent t:znaneTalenty){
			sprawdzTalenty(t);
		}
		
		dodajZnaneUmiejetnosciZProfesjiLosowePoczatkowe();
		//dodajZnaneUmiejetnosciZProfesji(prof.getPoziom());
		dodajLosowoPoczatkoweRozwiniecieCech();
		postacLosowyBonus(1);
		
		historiaProfesji = new ArrayList<Profesja>();
		historiaProfesji.add(prof);
	}
	
	/*
	 * konstruktor kopiuj�cy
	 */
	public Bohater(Bohater bh) {
		this.imieNazwisko = bh.imieNazwisko;
		this.plecBohatera = bh.plecBohatera;
		this.wyglad = new Wyglad(bh.wyglad);
		this.rasa = new Rasa(bh.rasa);
		this.prof = new Profesja(bh.prof);
		this.cechy = new Cechy(bh.cechy);
		this.znaneUmiejetnosci = new ArrayList<Umiejetnosc>();
		for(Umiejetnosc um:bh.znaneUmiejetnosci) {
			Umiejetnosc nowa = new Umiejetnosc(um);
			this.znaneUmiejetnosci.add(nowa);
		}
		this.znaneTalenty = new ArrayList<Talent>();
		for(Talent tl:bh.znaneTalenty) {
			Talent nowyTl = new Talent(tl);
			this.znaneTalenty.add(nowyTl);
		}
		this.historiaProfesji = new ArrayList<Profesja>();
		for(Profesja pr:bh.historiaProfesji) {
			Profesja nowaPr = new Profesja(pr);
			this.historiaProfesji.add(nowaPr);
		}
	}
	
	@Override
	public String toString() {
		return imieNazwisko + ", Profesja: " + prof.getName() + " poziom profesji: " + prof.getPoziom();
	}
	
	/*
	 * metoda kt�ra podnosi cechy aby mo�na by�o uko�czyc dany poziom profesji (przewa�nie 4 poziom)
	 */
	public void ukonczPoziomProfesji(int poziom) {
		if(prof.getPoziom() < poziom){
			int minPoziomUm = 5*prof.getPoziom();
			//sprawdzenie umiejetnosci
			nowyPoziomUmiejetnosciNowyLvl(minPoziomUm);
			//sprawdzenie cech
			nowyPoziomCechyNowyLvl(minPoziomUm);
		}
		for(Talent t:znaneTalenty){
			sprawdzTalenty(t);
		}
		
		//uaktualnienie poziomu zycia
		cechy.updateHp(rasa.nazwa, this.getCzyJestTwardziel());
		prof.setCzyUkonczona(true);
	}
	
		
	public void nowaProfesja(Profesja nowaProfesja){
		historiaProfesji.add(nowaProfesja);
		setUmiejetnosciProfesyjne();
		
		if(prof.getPoziom() < nowaProfesja.getPoziom()){
			int minPoziomUm = 5*prof.getPoziom();
			//sprawdzenie umiejetnosci
			nowyPoziomUmiejetnosciNowyLvl(minPoziomUm);
			//sprawdzenie cech
			nowyPoziomCechyNowyLvl(minPoziomUm);
		}
		wyglad.addWiekPoziomProf(rasa.nazwa);
		
		if(!prof.isCzyUkonczona())
			prof.setCzyUkonczona(true);
		
		
		
		if(!prof.toString().equals(nowaProfesja.toString()))
		{
			System.out.println("Kompletnie nowa profesja, zmieniam umiej�tno�ci profesyjne");
			for(Umiejetnosc um:znaneUmiejetnosci)
				um.setCzyProfesyjna(false);
		}
		prof = new Profesja(nowaProfesja);
		dodajZnaneUmiejetnosciZProfesji();
		setUmiejetnosciProfesyjne();
		
		if(prof.toString().equals("CZARODZIEJ") && prof.getPoziom()==2 ){
			Talent nowy = prof.getLosowyTalent(0);
			nowy.setTalentMax(cechy);
			znaneTalenty.add(nowy);
		}else{
			dodajZnanyTalentZProfesji();
		}
		
		
		for(Talent t:znaneTalenty){
			sprawdzTalenty(t);
		}
		
		//uaktualnienie poziomu zycia
		cechy.updateHp(rasa.nazwa, this.getCzyJestTwardziel());
	}
	
	private void setUmiejetnosciProfesyjne() {
		ArrayList<Umiejetnosc> tablicaUmiejetnosci = new ArrayList<Umiejetnosc>();
		//zapisanie ca�ej historii w tablicy
		for(Profesja p:historiaProfesji) {
			if(p.toString().equals(prof.toString())) {
				for(Umiejetnosc umP: p.dostepneUmiejetnosci) {
					tablicaUmiejetnosci.add(umP);
				}
			}
		}

		//ustawienie z ca�ej histrii umiejetnosci jako profesyjnych
		for(Umiejetnosc um:tablicaUmiejetnosci) {
			for(Umiejetnosc znaneUm:znaneUmiejetnosci) {
				if(um.toString().equals(znaneUm.toString())) {
					znaneUm.setCzyProfesyjna(true);
				}
			}
		}
		
		
	}

	//sprawdzenie czy atrybuty klasowe maj� odpowiedniu poziom do przejscia na nowy poziom
	public void nowyPoziomCechyNowyLvl(int minPoz){
		int[] klasoweAtrybuty = prof.getCechyRozwoju();
		for(int i = 0; i < klasoweAtrybuty.length; i++){
			int x = cechy.getRozwiniecia(klasoweAtrybuty[i]);
				if(x < minPoz){
				System.out.println("Roziwniecie cechy: " + x);
				x = minPoz - x;
				System.out.println("Roziwniecie cechy do dodania: " + x);
				cechy.podniesCeche(x, klasoweAtrybuty[i], true);
			}
		} 
	}
	
	//sprawdzenie znanych umiejetnosci czy jest przynajmniej osiem na minimalnym poziomie rowiniecia do przejscia na now� profesj�
	/*
	 * TODO - sprawdzenie umiej�tno�ci pod k�tem tego czy s� klasowe (profesji)
	 */
	public void nowyPoziomUmiejetnosciNowyLvl(int minPozUm) {
		int ile = 0;
		for(Umiejetnosc um:znaneUmiejetnosci){
			if(um.isCzyProfesyjna() && um.getPoziom() >= minPozUm)
			{
				ile++;
			}
		}
		if(ile<8){
			System.out.println("Brak wystarczaj�cej ilosci umiejetnosci na odpowiednim poziomie, jest tylko " + ile);
			/*
			 * metoda podnosi o jeden umiej�tno�ci ale tylko takiej, kt�ra ma poziom ni�szy od wymaganego, czyli efektywnie nie b�dzie 
			 * podnosic poziomu ponad 20
			 */
			podniesUmiejRandomMinPoz(3,minPozUm);
			nowyPoziomUmiejetnosciNowyLvl(minPozUm);
		}
	}
	
		
	//pewnie si� zmieni nazwe
	public String wyswietlBohatera(boolean czyWyswietlicTalent){
		
		StringBuilder stringBuilder = new StringBuilder(rasa.getName()+" " +imieNazwisko +" ("+ plecBohatera + ")\n");
		stringBuilder.append(wyglad.toString());
		stringBuilder.append(prof.getNameProfesjaSciezka());
		stringBuilder.append(" (Poziom profesji: " + prof.getPoziomUmiejetnosciString()+")\n");
		stringBuilder.append("Historia rozwoju bohatera: ");
		for(int i = 0; i < (historiaProfesji.size()-1); i++)
		{
			if(i>=0)
			{
				String[] nazwaSciezkiProfesji = historiaProfesji.get(i).getSciezkaProfesji().split("-");
				stringBuilder.append("ex-"+nazwaSciezkiProfesji[0] + "" + "(" + historiaProfesji.get(i).toString()+"),");
			}
		}
		stringBuilder.append("\n");
		stringBuilder.append(cechy.wyswietlStaty(prof.getTablicaCechyRozwoju()));
		stringBuilder.append("\nZnane Umiej�tno�ci:\n");
		
		
		Collections.sort(znaneUmiejetnosci);
		for(Umiejetnosc u:znaneUmiejetnosci){
			
			int poziomTestowanejUmiejetn = cechy.getCecha(u.getPozycjaCechy()) + u.getPoziom();
			stringBuilder.append(u.toString() + " (" + Integer.toString(poziomTestowanejUmiejetn) + "), ");
		}
		stringBuilder.append("\n\nZnane talenty:\n");
		
		Collections.sort(znaneTalenty);
		for(Talent t: znaneTalenty){
			stringBuilder.append(t.getName()+", ");
		}
		stringBuilder.append("\n\n");
		if(czyWyswietlicTalent){
			for(Talent st: znaneTalenty){
			if(st.getOpcjeWyswietlania()){
				stringBuilder.append(st.toString() + " - " +st.getOpisString() +"\nTesty: " + st.getTest() +"\nMaksymalny poziom Talentu " + st.getMaksimumValue() + "\n\n");
				}		
			}
		}

		
		
		return stringBuilder.toString();
	}
	
	//metoda tylko dla nowych postaci, wszelkie wy�sze poziomy musz� ju� sprawdza� profesyjne umiejetno�ci i minimum dla przejscia na nowy poziom profesji
	private void dodajZnaneUmiejetnosciZProfesjiLosowePoczatkowe(){
		
		for(int i = 0; i < 40; i++) {
			int random = (int) (Math.random()*prof.getSizeDostepneUm());
			if(prof.dostepneUmiejetnosci.get(random).getPoziom()<11){
				prof.dostepneUmiejetnosci.get(random).addPoziom(1);
			}else{
				i--;
			}
		}

		for (Umiejetnosc um:prof.getDostepneUmiejetnosciLista()) {
			for(Umiejetnosc umZnane: znaneUmiejetnosci){
				//jezeli juz jest taka umiejetnosc to dodajemy do znanej +5, a now� ustawiamy na -10
				if(um.getName().equals(umZnane.getName())){
					umZnane.addPoziom(um.getPoziom());
					umZnane.setCzyProfesyjna(true);
					um.addPoziom(-40);
					break;
				}	
			}
			//jezeli poziom umiejetnosci jest na minus, to nie dodawaj jej do znanych umiejetnosci
			if(um.getPoziom() >= 0){
				um.setCzyProfesyjna(true);
				znaneUmiejetnosci.add(um);
			}
		}
	}
	
	private void dodajZnaneUmiejetnosciZProfesji(){
		for (Umiejetnosc um:prof.getDostepneUmiejetnosciLista()) {
			for(Umiejetnosc umZnane: znaneUmiejetnosci){
				//jezeli juz jest taka umiejetnosc to ignorujemy
				if(um.getName().equals(umZnane.getName())){
					//umZnane.addPoziom(um.getPoziom());
					umZnane.setCzyProfesyjna(true);
					um.addPoziom(-40);
					break;
				}	
			}
			//jezeli poziom umiejetnosci jest na minus, to nie dodawaj jej do znanych umiejetnosci
			if(um.getPoziom() >= 0){
				System.out.println("Dodajemy now� umiej�tno�� z nowej profesji " + um.toString());
				um.setCzyProfesyjna(true);
				znaneUmiejetnosci.add(um);
			}
		}
	}
		
	
	//zaleca si� najpierw zaczytac dostepne umiejetno�ci z rasy a dopiero potem z profesji
	//
	@SuppressWarnings("unused")
	private void dodajZnaneUmiejetnosciZProfesji(int x){
		for (Umiejetnosc um:prof.getDostepneUmiejetnosciLista()) {
			for(Umiejetnosc umZnane: znaneUmiejetnosci){
				//jezeli juz jest taka umiejetnosc to dodajemy do znanej +5, a now� ustawiamy na -10
				if(um.getName().equals(umZnane.getName())){
					umZnane.addPoziom(5*x);
					um.addPoziom(-40);
					break;
				}	
			}
			um.addPoziom(5*x);
			//jezeli poziom umiejetnosci jest na minus, to nie dodawaj jej do znanych umiejetnosci
			if(um.getPoziom() > 0){
				znaneUmiejetnosci.add(um);
			}
		}
	}
	
	
	private void dodajZnaneUmiejetnosciZRasy() {
		
		ArrayList<Umiejetnosc> tempRasaZnaneUmiejetnosci = new ArrayList<Umiejetnosc>();
		
		//skopiowanie listy dost�pnych umiejetno�ci.
		for(Umiejetnosc m:rasa.getListaDostepnychUmiejetnosci()){
			tempRasaZnaneUmiejetnosci.add(m);
		}
		
		for(int i = 0; i < 6; i++){	
			int numer = (int) (Math.random()*tempRasaZnaneUmiejetnosci.size());
					
			Umiejetnosc nowaUmiejetnosc = tempRasaZnaneUmiejetnosci .get(numer);
			if(i<3){
				nowaUmiejetnosc.setPoziom(5);
				}else{
					nowaUmiejetnosc.setPoziom(3);
				}
			znaneUmiejetnosci.add(nowaUmiejetnosc);
			tempRasaZnaneUmiejetnosci.remove(numer);
		}
	}
	
	private void dodajZnaneTalentyZRasy(){
		// ludzie wyb�r mi�dzy 0 a 1, w dost�pnych talentach
		//krasnoludy maj� do wyboru pomi�dzy 0 a 1, oraz 2-3
		// wysokie elfy 0 lub 1  oraz 2 lub 3
		//lesne elfy, 0 lub 1, oraz 2 lub 3
		if(rasa.getName().equals("Ludzie")){
			znaneTalenty.add(rasa.getTalentDostepne(randomX(2)));	
		}else{
			znaneTalenty.add(rasa.getTalentDostepne(randomX(2)));
			znaneTalenty.add(rasa.getTalentDostepne(randomX(2)+2));
			if(rasa.getIloscDostepnychTalentow() >3){
				for(int i = 4; i<rasa.getIloscDostepnychTalentow(); i++){
					znaneTalenty.add(rasa.getTalentDostepne(i));
				}
			}
		}
		for(Talent t:znaneTalenty){
			t.setTalentMax(cechy);
		}
		//dodanie losowych talentow, + sprawdzenie czy si� nie powtarzaj�, ewentualnie zwi�kszenie o 1
		int losoweTalenty = rasa.getIloscLosowychTalentow();
		if(losoweTalenty > 0 ) {
			for(int i= 0; i<losoweTalenty; i++){
				Talent nowyTalent = rasa.getRandomTalent();
				int test = sprawdzCzyTalentJest(nowyTalent);
				if(test == -1){
					nowyTalent.setTalentMax(cechy);
					znaneTalenty.add(nowyTalent);
					System.out.println("Nowy losowy talent z rasy= " + nowyTalent.toString() + " numer i = " + i);
				}else{
						if(znaneTalenty.get(test).getMaksimumValue() == znaneTalenty.get(test).getPoziomValue()){
							System.out.println("Znany talent z rasy, kt�ry ma juz maks = " + znaneTalenty.get(test).toString()+ " numer i = " + i);
							i--;
						}else{
							
							znaneTalenty.get(test).addPoziom();
							System.out.println("Znany talent z rasy, podniesienie poziomu o 1 = " + znaneTalenty.get(test).toString()+ " numer i = " + i);
						}
						
				}
				
			}
		}
		
		
	}
	//dodaje jeden losowy talent z profesjii
	//ustalamy maksimum dla talentu oraz sprawdza wcze�niej czy juz nie jest dodany
	
	//uwaga!! - mo�e si� zdarzy� �e si� ta metoda zap�tli jak wszystkie talenty b�d� mia�y maksymalny poziom
	private void dodajZnanyTalentZProfesji() {
		int iloscTalentow = prof.getSizeOfDostepneTalenty();
		Talent losowyTalent = prof.getLosowyTalent(randomX(iloscTalentow));
		
		// sprawdzanie czy talenty si� powtarzaj�
		int test = sprawdzCzyTalentJest(losowyTalent);
		System.out.println(test);
		if(test == -1){
					losowyTalent.setTalentMax(cechy);
					znaneTalenty.add(losowyTalent);
					System.out.println("Nowy losowy talent z profesji= " + losowyTalent.toString());
				}else{
						if(znaneTalenty.get(test).getMaksimumValue() == znaneTalenty.get(test).getPoziomValue()){
							System.out.println("Znany talent, kt�ry ma juz maks z profesji= " + znaneTalenty.get(test).toString());
							dodajZnanyTalentZProfesji();
						}else{
							znaneTalenty.get(test).addPoziom();
							System.out.println("Znany talent, podniesienie poziomu o 1 = " + znaneTalenty.get(test).toString());
						}	
				}	
	}
	
	private int sprawdzCzyTalentJest(Talent nowyTalent){
		for(Talent t : znaneTalenty){
			if(t.toString().equals(nowyTalent.toString())){
				return znaneTalenty.indexOf(t);
			}
		}
		return -1;
	}
	
	private void dodajLosowoPoczatkoweRozwiniecieCech(){
		
		cechy.podniesCeche(5, prof.getLosowyAtrybutCechy(), true);
	}
	
	//w sumie mo�na te� u�y� tej metody, do okre�lenia, robienia bardziej zaawansowanej postaci
	public void postacLosowyBonus(int ileRazy) {
		for(int n = 0; n < ileRazy; n++){
			int opcja = (int) (Math.random()*10);
		if(opcja <3){
			opcja = 0;
		}else if(opcja>2 && opcja < 7){
			opcja = 1;
		}else {
			opcja = 2;
		}
		switch(opcja){
			case 0: for(int i = 0; i < 4; i++){
				cechy.podniesCeche(1, prof.getLosowyAtrybutCechy(), true);
			};System.out.println("losowa cecha dodatkowa"); break;
			case 1: podniesUmiejRandom(6); System.out.println("losowe umiejetnosci");break;
			case 2: dodajZnanyTalentZProfesji(); System.out.println("losowey talent");break;
			}//koniec switch
		}//koniec p�li fo
	}
	
	//podniesienie umiejetnosci losowe
		public void podniesUmiejRandom(int ileRazy) {
			for(int i = 0; i < ileRazy; i++){
				int ktora = (int) (Math.random()*znaneUmiejetnosci.size());
				znaneUmiejetnosci.get(ktora).addPoziom(1);
			}
		}
		// podniesienie umiejetnosci, ale z uwzgledniemie tylko tych ponizej konkretnego poziomu
		public void podniesUmiejRandomMinPoz(int ileRazy, int poziomUm) {
			for(int i = 0; i < ileRazy; i++){
				int ktora = (int) (Math.random()*znaneUmiejetnosci.size());
				if(znaneUmiejetnosci.get(ktora).getPoziom() <poziomUm){
					znaneUmiejetnosci.get(ktora).addPoziom(1);
				}
				
			}
		}
	
	
	//moloch odpowiadaj�cy za dodanie lub opisanie wszystkich cech, bonus�w z talent�w
	private void sprawdzTalenty(Talent talent) {
		Umiejetnosc nowa;
		switch(talent.toString()) {
			case "Urodzony Wojownik": if(talent.getOpcjeWyswietlania()){
				cechy.podniesCeche(5,0, false); talent.niePokazujOpisu();
				}break;
			case "Strzelec Wyborowy": if(talent.getOpcjeWyswietlania()){
				cechy.podniesCeche(5,1, false); talent.niePokazujOpisu();
				}break;
			case "Bardzo Silny": if(talent.getOpcjeWyswietlania()){
			cechy.podniesCeche(5,2, false);talent.niePokazujOpisu();
				}break;
			case "Niezwykle Odporny": if(talent.getOpcjeWyswietlania()){
			cechy.podniesCeche(5,3, false); talent.niePokazujOpisu();
				}break;
			case "Czujny": if(talent.getOpcjeWyswietlania()){
			cechy.podniesCeche(5,4, false); talent.niePokazujOpisu();
				}break;
			case "Szybki Refleks": if(talent.getOpcjeWyswietlania()){
			cechy.podniesCeche(5,5, false); talent.niePokazujOpisu();
				}break;
			case "Zr�czny": if(talent.getOpcjeWyswietlania()){
			cechy.podniesCeche(5,6, false); talent.niePokazujOpisu();
				}break;
			case "B�yskotliwo��":  if(talent.getOpcjeWyswietlania()){
			cechy.podniesCeche(5,7, false); talent.niePokazujOpisu(); System.out.println("B�yskotliwo��, int podniesiony!");
				}break;
			case "Zimna krew": if(talent.getOpcjeWyswietlania()){
			cechy.podniesCeche(5,8, false); talent.niePokazujOpisu();
				}break;
			case "Charyzmatyczny": if(talent.getOpcjeWyswietlania()){
				cechy.podniesCeche(5,9, false); talent.niePokazujOpisu();System.out.println("Charyzmatyczny, ogd podniesiona!");
				}break;
			case "Bardzo Szybki": if(talent.getOpcjeWyswietlania()){
				cechy.addSzybkosc(); talent.niePokazujOpisu();
				}break;

			case "S�uch Absolutny":  nowa = new Umiejetnosc("Wyst�py (�piewanie)",9,"podstawowa",0,false); prof.addUmiejetnoscDoDostepneUmiejetnosci(nowa); break;
			case "Obie�y�wiat":  nowa = new Umiejetnosc("Wiedza (Lokalna)",7,"zaawansowana",0,false); prof.addUmiejetnoscDoDostepneUmiejetnosci(nowa); break;
			case "Czarownica!":  nowa = new Umiejetnosc("J�zyk (Magiczny)",7,"zaawansowana",0,false); prof.addUmiejetnoscDoDostepneUmiejetnosci(nowa); break;
			case "Wytw�rca (Dowolny)":  nowa = new Umiejetnosc("Rzemios�o (Dowolny)",6,"zaawansowana",0,false); prof.addUmiejetnoscDoDostepneUmiejetnosci(nowa); break;
			case "Wytw�rca (Materia�y Wybuchowe)":  nowa = new Umiejetnosc("Rzemios�o (Materia�y Wybuchowe)",6,"zaawansowana",0,false); prof.addUmiejetnoscDoDostepneUmiejetnosci(nowa); break;
			case "Wytw�rca (Zielarz)":  nowa = new Umiejetnosc("Rzemios�o (Zielarz)",6,"zaawansowana",0,false); prof.addUmiejetnoscDoDostepneUmiejetnosci(nowa); break;
			case "Wytw�rca (Dowolne Rzemios�o)":  nowa = new Umiejetnosc("Rzemios�o (Dowolne Rzemios�o)",6,"zaawansowana",0,false); prof.addUmiejetnoscDoDostepneUmiejetnosci(nowa); break;
			case "Wytw�rca (Szkutnik)":  nowa = new Umiejetnosc("Rzemios�o (Szkutnik)",6,"zaawansowana",0,false); prof.addUmiejetnoscDoDostepneUmiejetnosci(nowa); break;
			case "Wytw�rca (Aptekarz)":  nowa = new Umiejetnosc("Rzemios�o (Aptekarz)",6,"zaawansowana",0,false); prof.addUmiejetnoscDoDostepneUmiejetnosci(nowa); break;
			case "Talent Artystyczny":  nowa = new Umiejetnosc("Sztuka (Dowolna)",6,"podstawowa",0,false); prof.addUmiejetnoscDoDostepneUmiejetnosci(nowa); break;
		}
	}
	
	
	private int randomX(int x){
		return (int) (Math.random() * x);
	}
	
	
	public void zmienOpisSciekiProfesji(String tekst) {
		prof.zmienOpisSciezki(tekst);
	}
	
	public void doswiadczenieBohatera(int opcjaDoswiadczenia) {
		int poziom = prof.getPoziom();
		System.out.println(poziom); 
		switch(opcjaDoswiadczenia){
				case 1:postacLosowyBonus(1*poziom);
				zmienOpisSciekiProfesji("pocz�tkuj�cy");
				System.out.println("profesja pocz�tkuj�ca"); break;
				case 2: postacLosowyBonus(3*poziom);
				zmienOpisSciekiProfesji("�redniozaawansowana");
				System.out.println("profesja �redniozaawansowana"); break;
				case 3: postacLosowyBonus(5*poziom);
				zmienOpisSciekiProfesji("do�wiadczona");
				System.out.println("profesja zaawansowana"); break;
			}
		cechy.updateHp(rasa.nazwa, this.getCzyJestTwardziel());
	}
	

	
	/**
	 * @param Profesja - nowa profesja
	 * @return - je�eli nie by�o jeszcze takiej profesji to zwraca -1, w innym wypadku zwraca poziom wczesniej rozwijanej 
	 * Sprawdzenie czy dana profesja juz nie by�a wcze�niej rozwijana u danego bohatera
	 * je�eli nie b�dzie to zwraca -1, inaczej podaje poziom profesji
	 */
	public int sprawdzHistorieProfesji(Profesja nowaProfesja) {
		int poziom = -1;
		for(Profesja staraProf:historiaProfesji) {
			if(nowaProfesja.toString().equals(staraProf.toString()))
				poziom = staraProf.getPoziom();
		}
		return poziom;
	}
	
	public boolean getProfesjaUkonczona() {
		return prof.isCzyUkonczona();
	}
	//getters
	/**
	 * @return the imieNazwisko
	 */
	public String getImieNazwisko() {
		return imieNazwisko;
	}
	public String getRasaName() {
		return rasa.getName();
	}
	
	public String getProfesjaNameMain() {
		return this.prof.toString();
	}
	
	public Profesja getCurrentProfesja(){
		return prof;
	}
	
	public String getCurrentProfesjaName(){
		return prof.toString();
	}
	
	public int getCurrentProfPoziom() {
		return prof.getPoziom();
	}
	
	public String getProfesjaSciezka() {
		String [] tablica =prof.getSciezkaProfesji().split(" � "); 
		return tablica[0];
	}
	
	public String getProfesjaStatus() {
		String [] tablica =prof.getSciezkaProfesji().split(" � "); 
		return tablica[1];
	}
	
	public String getWygladWiek() {
		Integer wiek = this.wyglad.getWiek();
		return wiek.toString();
	}
	
	public String getWygladWzrost() {
		Integer wzrost = this.wyglad.getWzrost();
		return wzrost.toString();
	}
	
	public String getWygladWlosy() {
		return this.wyglad.getKolorWlosow();
	}
	
	public String getWygladOczy() {
		return this.wyglad.getKolorOczu();
	}
	
	public String [] getCechyAktualne() {
		String [] tab = new String[10];
		for(int x =0; x<10; x++) {
			Integer aktualne = this.cechy.getCecha(x);
			tab[x] = aktualne.toString();
		}
		return tab;
	}
	
	public String [] getCechyRozwiniecia() {
		String [] tabRozw = new String[10];
		for(int x =0; x<10; x++) {
			Integer aktualne = this.cechy.getRozwiniecia(x);
			if(aktualne >0)
				tabRozw[x] = aktualne.toString();
			else {
				tabRozw[x] = null;
			}
		}
		return tabRozw;
	}
	
	public int getCechySzybkosc() {
		return this.cechy.getSzybkosc();
	}
	
	public int getCzyJestTwardziel() {
		int liczba = 0;
		for(Talent tl:znaneTalenty) {
			if(tl.nazwaTalentu().equals("Twardziel"))
			{
				liczba = tl.getPoziomValue();
			}
		}
		return liczba;
	}
	
	public String getCechyHpString() {
		Integer hp = this.cechy.getHp();
		return hp.toString();
	}
	
	public boolean czyJestCechaRozwojuProfesji(int x) {
		return this.prof.czyJestCechaRozwoju(x);
	}
	
	
}
