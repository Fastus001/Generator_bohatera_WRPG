package npcGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
/**
 * 
 * @author Tom
 *
 */
public class NpcModel implements NpcModelInterface{
	ArrayList<Potwory> potworyLista;
	ArrayList<CechyPotworow> cechyPotworowLista;
	Potwory potworAktualny;
	
	public NpcModel() {
		potworyLista = new ArrayList<Potwory>();
		cechyPotworowLista = new ArrayList<CechyPotworow>();
	}
	
	public void wgrajCechyPotworow() {
		try {
			/*
			ClassLoader classLoader2 = getClass().getClassLoader();
			InputStream inputStream2 = classLoader2.getResourceAsStream("resources/CECHY STWORZEN.txt");
			InputStreamReader czytaj = new InputStreamReader(inputStream2);
			/*/
			String urlCechyStworzen = "../GeneratorBohatera/src/resources/CECHY STWORZEN.txt";
			File plik = new File(urlCechyStworzen);
			FileReader czytaj = new FileReader(plik);
			
			BufferedReader czytajBuf = new BufferedReader(czytaj);
			String wiersz = null;
			
			while((wiersz = czytajBuf.readLine()) !=null){
				if(wiersz.length()==0)
					break;
				String nazwaCechy = wiersz;
				wiersz = czytajBuf.readLine();
				String opisCechy = wiersz;
				CechyPotworow nowaCecha = new CechyPotworow(nazwaCechy, opisCechy);
				cechyPotworowLista.add(nowaCecha);
	
			}
			czytajBuf.close();	
			System.out.println("Wszystkie cechy stworzeñ wgrano pomyœlnie!!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Object[] wgrajPotwory() {
		try {
			/*
			ClassLoader classLoader2 = getClass().getClassLoader();
			InputStream inputStream2 = classLoader2.getResourceAsStream("resources/potwory.txt");
			InputStreamReader czytaj = new InputStreamReader(inputStream2);
			/*/
			
			String urlCechyStworzen = "../GeneratorBohatera/src/resources/potwory.txt";
			File plik = new File(urlCechyStworzen);
			FileReader czytaj = new FileReader(plik);
			
			BufferedReader czytajBuf = new BufferedReader(czytaj);
			String wiersz = null;
			
			while((wiersz = czytajBuf.readLine()) !=null){
				if(wiersz.length()==0)
					break;
				String nazwaPotwora = wiersz;
				wiersz = czytajBuf.readLine();
				String[] tablicaStatow = wiersz.split(",");
				wiersz = czytajBuf.readLine();
				String [] tablicaCech = wiersz.split(",");

				
				wiersz = czytajBuf.readLine();
				String [] tablicaCechOpcja = wiersz.split(",");
				
				wiersz = czytajBuf.readLine();
				String opisPotwora = wiersz;
				
				Potwory potwor = new Potwory(nazwaPotwora, tablicaStatow, getCechyPotworow(tablicaCech), getCechyPotworow(tablicaCechOpcja), opisPotwora);
				potworyLista.add(potwor);
	
			}
			
			czytajBuf.close();
			System.out.println("Wszystkie potwory wgrano poprawnie");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return potworyLista.toArray();
	}

	@Override
	public ArrayList<CechyPotworow> getCechyPotworow(String [] tab) {
	
		ArrayList<CechyPotworow> lista = new ArrayList<CechyPotworow>();
		for(int i = 0; i < tab.length; i++) 
		{
			//System.out.println(tab[i]);
			for(CechyPotworow cp:cechyPotworowLista) 
			{
				if(cp.toString().equals(tab[i]))
				{
					lista.add(cp);
				}
			}
		}
		return lista;
	}

	@Override
	public Potwory setPotworAktualny(Potwory potw, boolean czyPCS) {
		potworAktualny = new Potwory(potw);
		if(czyPCS)
		{
			String [] powszecheCechyStworzen = {"Broñ +X","Du¿y","Elita","Nienawiœæ","Pancerz (wartoœæ)","Przebieg³y","Przestraszony","Przywódca","Si³acz","Sprytny","Szybki","Twardy","Twardziel","Uprzedzenie (Obiekt)","Wrogoœæ (Obiekt)"};
			for(int i = 0; i < powszecheCechyStworzen.length; i++) 
			{
				//System.out.println(tab[i]);
				for(CechyPotworow cp:cechyPotworowLista) 
				{
					if(cp.toString().equals(powszecheCechyStworzen[i]))
					{
						potworAktualny.getCechyOpcjonalne().add(cp);
					}
				}
			}
		}
		return potworAktualny;
	}

	@Override
	public Potwory dodajCecheModel(String cPotw) {
		ArrayList<CechyPotworow> tablica = potworAktualny.getCechyOpcjonalne();
		for(int i = 0; i<tablica.size(); i++)
		{
			
			if(cPotw.equals(tablica.get(i).toString()))
			{
				CechyPotworow nowa = new CechyPotworow(tablica.get(i));
				if(nowa.toString().contains("Rozmiar")) {
					zmianaRozmiaruNpc();
				}
				zmianaWartosciCechPotwora(nowa, true);
				potworAktualny.setCechy(nowa);
				tablica.remove(i);
				
				break;	
			}
		}
		uaktualnijHp();
		return potworAktualny;
	}
	/*
	 * Metoda sprawdza i uaktualnia hp obiektu NPC potwór w oparciu o statystyki, cechy i rozmiar potwora
	 */
	public void uaktualnijHp() {
		int hp = 0;
		int sila = 0;
		int wytrzymalosc = 0;
		int silaWoli = 0;
		String rozmiar = "";
		//sprawdzenie czy jest twardziel
		for (CechyPotworow cechyPotworow : potworAktualny.getCechy()) {
			if(cechyPotworow.toString().equals("Twardziel")) {
				hp += (int) potworAktualny.getStatyPotwora(StatyNPC.WT)/10;
			}
			if(cechyPotworow.toString().contains("Rozmiar")) {
				rozmiar = cechyPotworow.toString();
			}
		}
		// je¿eli nie ma podanej wielkoœci domyœlnie zak³ada ¿e rozmiar jest œredni
		if(rozmiar.length()==0)
			rozmiar = "Rozmiar (Œredni)";
		
		//obliczenie ¿ywotnoœci z rozmiaru
		switch (rozmiar) {
		case "Rozmiar (Drobny)": {
			hp +=1;
		}break;
		case "Rozmiar (Niewielki)": {
			wytrzymalosc = (int)(potworAktualny.getStatyPotwora(StatyNPC.WT)/10);
			hp +=wytrzymalosc;
			
		}break;
		case "Rozmiar (Ma³y)": {
			wytrzymalosc = (int)(potworAktualny.getStatyPotwora(StatyNPC.WT)/10);
			silaWoli = (int)(potworAktualny.getStatyPotwora(StatyNPC.SW)/10);
			hp +=(wytrzymalosc*2)+silaWoli;
			
		}break;
		case "Rozmiar (Œredni)": {
			sila = (int)(potworAktualny.getStatyPotwora(StatyNPC.S)/10);
			wytrzymalosc = (int)(potworAktualny.getStatyPotwora(StatyNPC.WT)/10);
			silaWoli = (int)(potworAktualny.getStatyPotwora(StatyNPC.SW)/10);
			hp +=sila + (wytrzymalosc*2)+silaWoli;
			
		}break;
		case "Rozmiar (Du¿y)": {
			sila = (int)(potworAktualny.getStatyPotwora(StatyNPC.S)/10);
			wytrzymalosc = (int)(potworAktualny.getStatyPotwora(StatyNPC.WT)/10);
			silaWoli = (int)(potworAktualny.getStatyPotwora(StatyNPC.SW)/10);
			hp +=(sila + (wytrzymalosc*2)+silaWoli)*2;
			
		}break;
		case "Rozmiar (Wielki)": {
			sila = (int)(potworAktualny.getStatyPotwora(StatyNPC.S)/10);
			wytrzymalosc = (int)(potworAktualny.getStatyPotwora(StatyNPC.WT)/10);
			silaWoli = (int)(potworAktualny.getStatyPotwora(StatyNPC.SW)/10);
			hp +=(sila + (wytrzymalosc*2)+silaWoli)*4;
			
		}break;
		case "Rozmiar (Monstrualny)": {
			sila = (int)(potworAktualny.getStatyPotwora(StatyNPC.S)/10);
			wytrzymalosc = (int)(potworAktualny.getStatyPotwora(StatyNPC.WT)/10);
			silaWoli = (int)(potworAktualny.getStatyPotwora(StatyNPC.SW)/10);
			hp +=(sila + (wytrzymalosc*2)+silaWoli)*8;
			
		}break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + rozmiar);
		}
	
		potworAktualny.setStatyPotwora(hp,11);
	}


/**
 *metoda sprawdza aktualne cechy NPC i przernosi aktualny rozmiar do opcjonalnych, je¿eli jest. 
 */
	private void zmianaRozmiaruNpc() {
		for(int i = 0; i<potworAktualny.getCechy().size();i++)
		{
			if(potworAktualny.getCechy().get(i).toString().contains("Rozmiar")) {
				CechyPotworow cechaNowa = new CechyPotworow(potworAktualny.getCechy().get(i));
				potworAktualny.getCechy().remove(i);
				potworAktualny.getCechyOpcjonalne().add(cechaNowa);
			}
		}
		
	}

	@Override
	public Potwory usunCecheModel(String cPotw) {
		ArrayList<CechyPotworow> tablica = potworAktualny.getCechy();
		for(int i = 0; i<tablica.size(); i++)
		{
			
			if(cPotw.equals(tablica.get(i).toString()))
			{
				CechyPotworow nowa = new CechyPotworow(tablica.get(i));
				zmianaWartosciCechPotwora(nowa, false);
				potworAktualny.addCechyOpcjonalne(nowa);
				tablica.remove(i);
				
				break;	
			}
		}
		System.out.println("Tutaj");
		uaktualnijHp();
		return potworAktualny;
	}

	@Override
	public void zmienStatystykeModel(int ile, int ktora) {
		potworAktualny.setStatyPotwora(ile, ktora);
		if(ktora == 3 || ktora == 4 || ktora == 9)
			uaktualnijHp();
	}
	
	/**
	 * 
	 * @param nowa - nowa cecha
	 * @param plus - czy cecha stwora jest dodawana do cech g³ownych czy nie. False oznacza  ¿e usuwamy...
	 */
	private void zmianaWartosciCechPotwora(CechyPotworow nowa,boolean plus) {
		// TODO Auto-generated method stub
		switch (nowa.toString()) {
		case "Du¿y": {
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.S, plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.WT, plus);
			potworAktualny.addRemoveStatyPotwora(5, StatyNPC.ZW, !plus);
					}break;
		case "Mutacja": //TODO - zrobiæ mutacje ze strony 184 w podrêczniku
					break;
		case "Spaczenie Umys³u": //TODO - zrobiæ mutacje ze strony 185 w podrêczniku
			break;
		case "Przebieg³y": {
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.INT,plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.SW, plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.OGD, plus);
		}break;
		case "Przywódca": {
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.SW, plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.OGD, plus);
		}break;
		case "Si³acz": {
			potworAktualny.addRemoveStatyPotwora(1, StatyNPC.SZ, !plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.ZW, !plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.S, plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.WT, plus);
		}break;
		case "Sprytny": {
			potworAktualny.addRemoveStatyPotwora(20, StatyNPC.INT, plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.I, plus);
		}break;
		case "Szybki": {
			potworAktualny.addRemoveStatyPotwora(1, StatyNPC.SZ, plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.ZW, plus);
		}break;
		case "Twardy": {
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.WT, plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.SW, plus);
		}break;
		case "Elita": {
			potworAktualny.addRemoveStatyPotwora(20, StatyNPC.WW, plus);
			potworAktualny.addRemoveStatyPotwora(20, StatyNPC.US, plus);
			potworAktualny.addRemoveStatyPotwora(20, StatyNPC.SW, plus);
		}break;
		
		
		default:
			System.out.println("Dodana cecha: " + nowa.toString());
			//throw new IllegalArgumentException("Unexpected value: " + nowa.toString());
		}
		
	}

	@Override
	public Potwory getNpc(String nazwaOpis) {
		potworAktualny.setNazwa(nazwaOpis);
		return potworAktualny;
	}

	@Override
	public Potwory zmienNazweCechyModel(String stara, String nowa) {
		
		for(int i = 0; i<potworAktualny.getCechy().size(); i++)
		{
			if(stara.equals(potworAktualny.getCechy().get(i).toString()))
			{
				potworAktualny.getCechy().get(i).setNazwa(nowa);
				break;
			}
		}
		
		return potworAktualny;
	}
}
