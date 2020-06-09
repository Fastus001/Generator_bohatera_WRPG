package commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

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
			System.out.println("Wszystkie cechy stworze� wgrano pomy�lnie!!");
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
	public void setPotworAktualny(Potwory potw) {
		potworAktualny = new Potwory(potw);
		
	}
	
	

}
