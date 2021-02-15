package npcGenerator;

import commons.RzutKoscia;

//generator cech postaci, 
public class Cechy implements RzutKoscia{
		private int [] stat;
		private int [] rozwiniecia;
		int hp;
		private int szybkosc;
		static String[] CECHYNAZWA = {"WW", "US", "S", "Wt", "I", "Zw", "Zr", "Int", "SW", "Ogd"};
		static int IC = 10; //iloďż˝ďż˝ cech
		
		public Cechy(int [] cechyBazowe, String nazwaRasy){
			stat = new int[IC]; //HP traktowane jako dodatkowa osobna cecha
			rozwiniecia = new int[IC];
		
			for (int i = 0; i < IC; i++){
				stat[i] = cechyBazowe[i]+ RzutKoscia.rzutK(10,2);			
				rozwiniecia[i] = 0;
			}
			//ustelenie iloďż˝ci HP 
			updateHp(nazwaRasy,0);
			
			
			switch(nazwaRasy){
				case "Ludzie": szybkosc =4; break;
				case "Krasnoludy": szybkosc = 3; break;
				case "Niziołki": szybkosc = 3; break;
				case "Wysokie elfy": szybkosc = 5; break;
				case "Leśne elfy": szybkosc = 5; break;
			}
		
		}
		


		public Cechy(Cechy stary) {
			this.stat = new int[IC];
			this.rozwiniecia = new int[IC];
			for(int i = 0; i  < IC; i++) {
				this.stat[i] = stary.stat[i];
				this.rozwiniecia[i] = stary.rozwiniecia[i];
			}
			this.hp = stary.hp;
			this.szybkosc = stary.szybkosc;
		}
		
		public String wyswietlStaty(int [] tablica){
			String tekst = "";
			String cechyNazwaI = "";
			for (int i =0; i < IC; i++){
				cechyNazwaI = CECHYNAZWA[i];
				for(int y = 0; y < tablica.length; y++){
					if(i == tablica[y]){
						cechyNazwaI += "*";
					}
				}
				tekst += cechyNazwaI +": " + Integer.toString(stat[i]) + " (+" + Integer.toString(rozwiniecia[i]) + ") ";
			}
			tekst += " \nPunkty życia: "+ Integer.toString(hp)+ "\nSzybkość: " + szybkosc + "\n";
			return tekst;
		}
		
		public void updateHp(String nazwaRasy, int twardziel){
			
			//ustelenie iloďż˝ci HP 
			if(nazwaRasy.equals( "Niziołki"))
				hp = 2*((int)(stat[3]/10))+(int)(stat[8]/10); //(2 ďż˝ TB)+WPB
			else {
				hp = (int)(stat[2]/10)+2*((int)(stat[3]/10))+(int)(stat[8]/10); //SB+(2 ďż˝ TB)+WPB - iloďż˝ďż˝ ďż˝ycia w odniesieniu do statystyk
			}
			System.out.println("Żywotność przed twardzielem = " + hp);
			if(twardziel>0) {
				hp += twardziel*((int)(stat[3]/10));
			}
			System.out.println("Żywotność z twardzielem = " + hp);
		}
		
		public int getCecha(int x){
			return stat[x];
		}

		//podniesc cechďż˝, boolean jest w zaleďż˝noďż˝ci czy dodatkowa cecha ma siďż˝ wliczaďż˝ do rozwinieďż˝ aktualnych
		public void podniesCeche(int oIle, int ktoraCecha, boolean or) {
			stat[ktoraCecha] +=oIle;
			if(or){
				rozwiniecia[ktoraCecha] +=oIle;
			}
			
		}
		
		public void addSzybkosc(){
			szybkosc +=1;
		}
		
		public int getRozwiniecia(int pozycja){
			return rozwiniecia[pozycja];
		}
		
		/**
		 * @return the szybkosc
		 */
		public int getSzybkosc() {
			return szybkosc;
		}
		
		/**
		 * @return the hp
		 */
		public int getHp() {
			return hp;
		}
		
		
		
	}//koniec klasy
	
	
	


