package commons;

//generator cech postaci, 
public class Cechy implements RzutKoscia{
		private int [] stat;
		private int [] rozwiniecia;
		int hp;
		private int szybkosc;
		static String[] CECHYNAZWA = {"WW", "US", "S", "Wt", "I", "Zw", "Zr", "Int", "SW", "Ogd"};
		static int IC = 10; //iloï¿½ï¿½ cech
		
		public Cechy(int [] cechyBazowe, String nazwaRasy){
			stat = new int[IC]; //HP traktowane jako dodatkowa osobna cecha
			rozwiniecia = new int[IC];
		
			for (int i = 0; i < IC; i++){
				stat[i] = cechyBazowe[i]+ RzutKoscia.rzutK(10,2);			
				rozwiniecia[i] = 0;
			}
			//ustelenie iloï¿½ci HP 
			updateHp(nazwaRasy,0);
			
			
			switch(nazwaRasy){
				case "Ludzie": szybkosc =4; break;
				case "Krasnoludy": szybkosc = 3; break;
				case "Nizio³ki": szybkosc = 3; break;
				case "Wysokie elfy": szybkosc = 5; break;
				case "Leœne elfy": szybkosc = 5; break;
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
		/*
		public Cechy(Rasa rs){
			stat = new int[IC]; //HP traktowane jako dodatkowa osobna cecha
			rozwiniecia = new int[IC];
			for (int i = 0; i < IC; i++){
				stat[i] = rs.cechyBazowe[i]+ RzutKoscia.rzutK(10,2);
				rozwiniecia[i] = 0;
			}
			//ustelenie iloï¿½ci HP 
			hp = (int)(stat[2]/10)+2*((int)(stat[3]/10))+(int)(stat[8]/10); //SB+(2 ï¿½ TB)+WPB - iloï¿½ï¿½ ï¿½ycia w odniesieniu do statystyk
			
		}
				
		*/
		
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
			tekst += " \nPunkty ¿ycia: "+ Integer.toString(hp)+ "\nSzybkoœæ: " + szybkosc + "\n";
			return tekst;
		}
		
		public void updateHp(String nazwaRasy, int twardziel){
			
			//ustelenie iloï¿½ci HP 
			if(nazwaRasy == "Nizio³ki")
				hp = 2*((int)(stat[3]/10))+(int)(stat[8]/10); //(2 ï¿½ TB)+WPB
			else {
				hp = (int)(stat[2]/10)+2*((int)(stat[3]/10))+(int)(stat[8]/10); //SB+(2 ï¿½ TB)+WPB - iloï¿½ï¿½ ï¿½ycia w odniesieniu do statystyk
			}
			System.out.println("¯ywotnoœæ przed twardzielem = " + hp);
			if(twardziel>0) {
				hp += twardziel*((int)(stat[3]/10));
			}
			System.out.println("¯ywotnoœæ z twardzielem = " + hp);
		}
		
		public int getCecha(int x){
			return stat[x];
		}




		//podniesc cechï¿½, boolean jest w zaleï¿½noï¿½ci czy dodatkowa cecha ma siï¿½ wliczaï¿½ do rozwinieï¿½ aktualnych
		public void podniesCeche(int oIle, int ktoraCecha, boolean or) {
			stat[ktoraCecha] +=oIle;
			if(or){
				rozwiniecia[ktoraCecha] +=oIle;
			}
			
		}
		//metoda dla talentu twardziel
		public void podniesZywotnosc(){
			hp += (int) stat[3]/10;
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
	
	
	


