package npcGenerator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream input = classLoader.getResourceAsStream("CECHY STWORZEN.txt");
			InputStreamReader czytaj = new InputStreamReader( input, StandardCharsets.UTF_8);
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
			System.out.println("Wszystkie cechy stworzeń wgrano pomyślnie!!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Object[] wgrajPotwory() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream input = classLoader.getResourceAsStream("potwory.txt");
			InputStreamReader czytaj = new InputStreamReader(input,StandardCharsets.UTF_8);
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
	
		ArrayList<CechyPotworow> lista = new ArrayList<>();
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
			String [] powszecheCechyStworzen = {"Broń +X","Duży","Elita","Nienawiść","Pancerz (wartość)","Przebiegły","Przestraszony","Przywódca","Siłacz","Sprytny","Szybki","Twardy","Twardziel","Uprzedzenie (Obiekt)","Wrogość (Obiekt)"};
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
		// jeżeli nie ma podanej wielkości domyślnie zakłada że rozmiar jest średni
		if(rozmiar.length()==0)
			rozmiar = "Rozmiar (Średni)";
		
		//obliczenie żywotności z rozmiaru
		switch (rozmiar) {
		case "Rozmiar (Drobny)": {
			hp +=1;
		}break;
		case "Rozmiar (Niewielki)": {
			wytrzymalosc = (int)(potworAktualny.getStatyPotwora(StatyNPC.WT)/10);
			hp +=wytrzymalosc;
			
		}break;
		case "Rozmiar (Mały)": {
			wytrzymalosc = (int)(potworAktualny.getStatyPotwora(StatyNPC.WT)/10);
			silaWoli = (int)(potworAktualny.getStatyPotwora(StatyNPC.SW)/10);
			hp +=(wytrzymalosc*2)+silaWoli;
			
		}break;
		case "Rozmiar (Średni)": {
			sila = (int)(potworAktualny.getStatyPotwora(StatyNPC.S)/10);
			wytrzymalosc = (int)(potworAktualny.getStatyPotwora(StatyNPC.WT)/10);
			silaWoli = (int)(potworAktualny.getStatyPotwora(StatyNPC.SW)/10);
			hp +=sila + (wytrzymalosc*2)+silaWoli;
			
		}break;
		case "Rozmiar (Duży)": {
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
 *metoda sprawdza aktualne cechy NPC i przernosi aktualny rozmiar do opcjonalnych, jeżeli jest. 
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
	 * @param plus - czy cecha stwora jest dodawana do cech głownych czy nie. False oznacza  że usuwamy...
	 */
	private void zmianaWartosciCechPotwora(CechyPotworow nowa,boolean plus) {
		switch (nowa.toString()) {
		case "Duży": {
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.S, plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.WT, plus);
			potworAktualny.addRemoveStatyPotwora(5, StatyNPC.ZW, !plus);
					}break;
		case "Mutacja": //TODO - zrobić mutacje ze strony 184 w podręczniku
					break;
		case "Spaczenie Umysłu": //TODO - zrobić mutacje ze strony 185 w podręczniku
			break;
		case "Przebiegły": {
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.INT,plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.SW, plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.OGD, plus);
		}break;
		case "Przywódca": {
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.SW, plus);
			potworAktualny.addRemoveStatyPotwora(10, StatyNPC.OGD, plus);
		}break;
		case "Siłacz": {
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
			//throw new IllegalArgumentException("Unexpected value: " + nowa.getName());
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
