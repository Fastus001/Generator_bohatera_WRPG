package commons;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.JOptionPane;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;


public class ExportToPdf {
	
	public static final String FONT_CASLON_PL = "../GeneratorBohatera/src/resources/CaslonAntiquePolskieZnaki.ttf";
	//public static final String FONT_CASLON_PL = "resources/CaslonAntiquePolskieZnaki.ttf";
	public static final String SRC = "../GeneratorBohatera/src/resources/WFRP_4ed_final_edytowalna.pdf";
	public static final float SZESC = 6f;
	public static final float OSIEM = 8f;
	public static final float TEN = 10f;
	
	private PdfDocument pdf;
	private PdfFont font1;

	private PdfAcroForm form;
	private Bohater hero;
	
	
	public ExportToPdf(Bohater h, String des) throws IOException{

			
			this.hero = h;
			String nazwaPliku = hero.getImieNazwisko().replace(" ", "_")+"_"+ hero.getCurrentProfesjaName() + "_poz"+ hero.getCurrentProfPoziom() +".pdf";
			/*
			ClassLoader classLoader2 = getClass().getClassLoader();
			InputStream inputStream2 = classLoader2.getResourceAsStream("resources/WFRP_4ed_final_edytowalna.pdf");
			*/
			
			pdf = new PdfDocument(new PdfReader(SRC),new PdfWriter(des+nazwaPliku));
			//czcionka i ustawienie kodowania
			font1 = PdfFontFactory.createFont(FONT_CASLON_PL, PdfEncodings.CP1250,true);

			form = PdfAcroForm.getAcroForm(pdf, true);
				
			opisBohateraCechy();
			szybkosc();
			wczytanieZywotnosci();
			sprawdzUmiejetnosci();
			wczytajUmiejetnosci();
			wczytajTalenty();
			
			
			//form.flattenFields();
			pdf.close();


		
	}



	private void wczytajTalenty() {
		if(hero.znaneTalenty.size()<17) {
			for(int i = 0; i<hero.znaneTalenty.size();i++)
			{
				Talent tl = hero.znaneTalenty.get(i);
				talenty(tl, i+1);
			}	
		}else {
			JOptionPane.showMessageDialog(null, "Ilo�� znanych talent�w u postaci przekracza ilo�� miejsca na karcie, zapisz post� do pliku excel", "Profesja  w Kryszta�ach Czasu..", JOptionPane.INFORMATION_MESSAGE);
		}

	}



	private void sprawdzUmiejetnosci() {

		//sprawdzenie ile razy wyst�puje w umiejetno�ciach Bro� Bia�a(xxx), je�eli wiecej niz dwa, to trzeba zmieni� na zaawansowan� ..
		// podobnie ze sztuk�
		int ileRazyBB = 0;
		int ileRazyWystepy = 0;
		int ileRazySztuka = 0;
		int ileRazySkradanie = 0;
		int ileRazyJazda = 0;
		for(Umiejetnosc um:hero.znaneUmiejetnosci) {
			if(um.getName().contains("Bro� Bia�a"))
				{
				ileRazyBB++;
				if(ileRazyBB>1 && !um.getName().equals("Bro� Bia�a (Podstawowa)"))
					um.setTyp("zaawansowana");
				}
			if(um.getName().contains("Wyst�py"))
				{
				ileRazyWystepy++;
				if(ileRazyWystepy>1)
					um.setTyp("zaawansowana");
				}
			if(um.getName().contains("Sztuka ("))
					{
						ileRazySztuka++;
						if(ileRazySztuka>1)
							um.setTyp("zaawansowana");
						
					}
			if(um.getName().contains("Skradanie"))
			{
				ileRazySkradanie++;
				if(ileRazySkradanie>1)
					um.setTyp("zaawansowana");
			}
			if(um.getName().contains("Je�dziectwo"))
			{
				ileRazyJazda++;
				if(ileRazyJazda>1)
					um.setTyp("zaawansowana");
			}
		}//koniec for
		
	}

	private void szybkosc() {
		Integer szybkosc = hero.getCechySzybkosc();
		form.getField("Szybko��").setValue(szybkosc.toString(),font1, TEN);
		szybkosc = szybkosc*2;
		form.getField("Ch�d").setValue(szybkosc.toString(),font1, TEN);
		szybkosc = szybkosc*2;
		form.getField("Bieg").setValue(szybkosc.toString(),font1, OSIEM);
		
	}
	
	private String getBonusCechy(int x, int ileRazy) {
		String [] cechyAktualne = hero.getCechyAktualne();
		int bonusSily = Integer.parseInt(cechyAktualne[x]);
		bonusSily = ileRazy*((int) (bonusSily/10));
		Integer bs = bonusSily;
		return bs.toString();
	}

	private void wczytanieZywotnosci() {
		if(hero.getRasaName() != "Nizio�ki")
			form.getField("BS").setValue(getBonusCechy(2, 1),font1, TEN);
		
		form.getField("BWtx2").setValue(getBonusCechy(3, 2),font1, TEN);
		form.getField("BSW").setValue(getBonusCechy(8, 1),font1, TEN);
		int twardziel = hero.getCzyJestTwardziel(); 
		System.out.println("to PDF Twardziel razy - " + twardziel);
		if(twardziel>0) {
			form.getField("Twardziel").setValue(getBonusCechy(3, twardziel),font1, TEN);
		}
			
		
		//suma �ywotno�ci
		form.getField("fill_123").setValue(hero.getCechyHpString(),font1, TEN);
		
	}

	private void talenty(Talent tl,int licznik) {
		String [] formTab = {"Talent","talentPoz","Opis_talentu"};

			try {
				form.getField(formTab[0]+Integer.toString(licznik)).setValue(tl.getName(),font1, SZESC);
				form.getField(formTab[1]+Integer.toString(licznik)).setValue(Integer.toString(tl.getPoziomValue()),font1, SZESC);
				form.getField(formTab[2]+Integer.toString(licznik)).setValue(tl.getTest(),font1, SZESC);
			}catch (Exception e) {
				e.printStackTrace();
			}		
	}

	private void umiejetnosciZaawansowane(Umiejetnosc um, int licznik) {
		String [] formTab = {"uzaw","Cecha","rozw","suma"};
		String [] cechyAktualneForms = {"WW_aktualna","US_aktualna","S_aktualna","WT_aktualna","I_aktualna","ZW_aktualna","ZR_aktualna","INT_aktualna","SW_aktualna","OGD_aktualna"};
		
		try {
			form.getField(formTab[0]+Integer.toString(licznik)).setValue(um.getName(),font1, SZESC);
			form.getField(formTab[1]+Integer.toString(licznik)).setValue(form.getField(cechyAktualneForms[um.tcecha]).getValueAsString(),font1, SZESC);
			form.getField(formTab[2]+Integer.toString(licznik)).setValue(Integer.toString(um.poz),font1, SZESC);
			form.getField(formTab[3]+Integer.toString(licznik)).setValue(dodajDwaStringi(form.getField(cechyAktualneForms[um.tcecha]).getValueAsString(), Integer.toString(um.poz)),font1, SZESC);
		} catch (Exception e) {
			e.printStackTrace();
		}		

	}

	private void opisBohateraCechy() {
		String zmienna = "";
		form.setGenerateAppearance(true);
		//opis postaci
		form.getField("Imi�").setValue(hero.getImieNazwisko(),font1, 10f);
		form.getField("Rasa").setValue(hero.getRasaName(),font1, 10f);

		form.getField("Klasa").setValue(hero.getKlasaProfesji(),font1, 10f);
		
		form.getField("Profesja").setValue(hero.getProfesjaNameMain(),font1, 10f);
		Integer poziom = hero.getCurrentProfPoziom();
		zmienna = poziom.toString();
		
		form.getField("Poziom Profesji").setValue(zmienna,font1, OSIEM);
		form.getField("�cie�ka Profesji").setValue(hero.getProfesjaSciezka(),font1, OSIEM);
		form.getField("Status").setValue(hero.getProfesjaStatus(),font1, OSIEM);
		form.getField("Wiek").setValue(hero.getWygladWiek(),font1, OSIEM);
		form.getField("Wzrost").setValue(hero.getWygladWzrost(),font1, OSIEM);
		form.getField("fill_118").setValue(hero.getWygladWlosy(),font1, OSIEM);
		form.getField("Oczy").setValue(hero.getWygladOczy(),font1, OSIEM);
		
		/*
		 * opisanie poziom aktualnych cech bohatera
		 */
		
		String [] cechyAktualneForms = {"WW_aktualna","US_aktualna","S_aktualna","WT_aktualna","I_aktualna","ZW_aktualna","ZR_aktualna","INT_aktualna","SW_aktualna","OGD_aktualna"};
		String [] cechyAktualne = hero.getCechyAktualne();
		String [] cechyRozwinieciaForms = {"WW_rozwieniecie","US_rozwienicie","S_rozwienicie","WT_rozwienicie","I_rozwienicie","ZW_rozwienicie","ZR_rozwienicie","INT_rozwienicie","SW_rozwienicie","OGD_rozwienicie"};
		String [] cechyRozwiniecia = hero.getCechyRozwiniecia();
		String [] cechyBazoweForms = {"WW_poczatkowa","US_poczatkowa","S_poczatkowa","Wt_poczatkowa","I_poczatkowa","ZW_poczatkowa","ZR_poczatkowa","INT_poczatkowa","SW_poczatkowa","OGD_poczatkowa"};
		

		for(int x = 0; x < cechyAktualne.length; x++) {
			form.getField(cechyAktualneForms[x]).setValue(cechyAktualne[x],font1, 10f);
			if(cechyRozwiniecia[x]!=null) {
				form.getField(cechyRozwinieciaForms[x]).setValue(cechyRozwiniecia[x],font1, 10f);
				Integer wartosc = Integer.parseInt(cechyAktualne[x]) - Integer.parseInt(cechyRozwiniecia[x]);
				if(hero.czyJestCechaRozwojuProfesji(x)) {
					form.getField(cechyBazoweForms[x]).setValue(wartosc.toString()+"*",font1, 10f);
				}else {
					form.getField(cechyBazoweForms[x]).setValue(wartosc.toString(),font1, 10f);
				}
				
				
			}	
			else {
				form.getField(cechyBazoweForms[x]).setValue(cechyAktualne[x],font1, 10f);
			}
			
		}//koniec for..
		

	}
	
	private void wczytajUmiejetnosci() {
		int liczbaUmZaawansow = 0;
		for(Umiejetnosc um:hero.znaneUmiejetnosci) {
			//System.out.println("Umiejetntosci podstawowe wszystkie" + um.getName());
			if(um.getTyp().equals("podstawowa"))
			{
				try {
					umiejetnosciPodstawowe(um);
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}else {
				liczbaUmZaawansow++;
			}
			
		}//koniec for...
		int maksymalna_liczba_um_zaaw_w_arkuszu = 16;
		System.out.println("Liczba umiejetnosci zaawansowanych to = " + liczbaUmZaawansow);
		int licznik = 0;
		if(liczbaUmZaawansow < maksymalna_liczba_um_zaaw_w_arkuszu) {
			for(Umiejetnosc um:hero.znaneUmiejetnosci)
			{
				if(um.getTyp().equals("zaawansowana"))
				{
					licznik++;
					umiejetnosciZaawansowane(um, licznik);
				}
			}
		}else {
			JOptionPane.showMessageDialog(null, "Niestety plik PDF nie posiada tyle miejsc na umiej�tno�ci zaawansowane :-(", "Zapisz posta� do pliku Excel!!", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	/*
	 * metoda ze switchem do opisania poszczeg�lnych umiejetnosci,
	 * trzeba zwr�ci� uwag� na bro� bia�� inn� ni� podstawowa, oraz sztuk�
	 * 
	 */
	private void umiejetnosciPodstawowe(Umiejetnosc um) {
		//Umiej�tno�ci podstawowe i zaawansowane
				String nazwa= um.getName();
				int poziomUmiejetnosci = um.getPoziom();
				String poziomUmS = Integer.toString(poziomUmiejetnosci);
				System.out.println("Nazwa umiejetno�ci " + um.getName() + ".");
				switch (nazwa) {
				case "Atletyka": {
					form.getField("Atletyka_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("Atletyka_ZW_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Atletyka_ZW_cecha_SUMA").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Bro� Bia�a (Podstawowa)": {
					form.getField("BB_podstawoa_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Bro� Bia�a (Dwur�czna)": {
					form.getField("Bro� bia�a X").setValue("(Dwur�czna)",font1, 4.5f);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Bro� Bia�a (Korbacz)": {
					form.getField("Bro� bia�a X").setValue("(Korbacz)",font1, SZESC);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Bro� Bia�a (Bijatyka)": {
					form.getField("Bro� bia�a X").setValue("(Bijatyka)",font1, SZESC);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Bro� Bia�a (Dowolna)": {
					form.getField("Bro� bia�a X").setValue("(Dowolna)",font1, SZESC);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Bro� Bia�a (Drzewcowa)": {
					form.getField("Bro� bia�a X").setValue("(Drzewcowa)",font1, 5f);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Bro� Bia�a (Korbacz albo Dwur�czna)": {
					form.getField("Bro� bia�a X").setValue("(Korbacz albo Dwur�czna)",font1, 3f);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Bro� Bia�a (Kawaleryjska)": {
					form.getField("Bro� bia�a X").setValue("(Kawaleryjska)",font1, 4.5f);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Bro� Bia�a (Szermiercza)": {
					form.getField("Bro� bia�a X").setValue("(Szermiercza)",font1, SZESC);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Bro� Bia�a (Paruj�ca)": {
					form.getField("Bro� bia�a X").setValue("(Paruj�ca)",font1, SZESC);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Charyzma": {
					form.getField("Charyzma_OGD_cecha").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Charyzma_OGD_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Charyzma_OGD_cecha_suma").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;		
				case "Dowodzenie": {
					form.getField("Dowodzenie_OGD_cecha").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Dowodzenie_OGD_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Dowodzenie_OGD_cecha_suma").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;		
				case "Hazard": {
					form.getField("HAZARD_INT_cecha").setValue(form.getField("INT_aktualna").getValueAsString(),font1, 10f);
					form.getField("HAZARD_INT_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("HAZARD_INT_cecha_suma").setValue(dodajDwaStringi(form.getField("INT_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Intuicja": {
					form.getField("INTUICJA_I_cecha").setValue(form.getField("I_aktualna").getValueAsString(),font1, 10f);
					form.getField("INTUICJA_I_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("INTUICJA_I_cecha_SUMA").setValue(dodajDwaStringi(form.getField("I_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Je�dziectwo (Dowolne)": {
					form.getField("jazda_x").setValue("(Dowolne)",font1, 4f);
					form.getField("Jezdziectwo_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("Jezdziectwo_ZW_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Jezdziectwo_ZW_cecha_suma").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Je�dziectwo (Konie)": {
					form.getField("jazda_x").setValue("(Konie)",font1, 6f);
					form.getField("Jezdziectwo_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("Jezdziectwo_ZW_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Jezdziectwo_ZW_cecha_suma").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Mocna G�owa": {
					form.getField("Mocna_g").setValue(form.getField("WT_aktualna").getValueAsString(),font1, 10f);
					form.getField("Mocna_g_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Mocna_g_suma").setValue(dodajDwaStringi(form.getField("WT_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;	
				case "Nawigacja": {
					form.getField("Nawigacja_cecha_I_").setValue(form.getField("I_aktualna").getValueAsString(),font1, 10f);
					form.getField("Nawigacja_cecha_I_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Nawigacja_cecha_I_rozwiniecie_SUMA").setValue(dodajDwaStringi(form.getField("I_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Odporno��": {
					form.getField("Odporno��_WT_cecha").setValue(form.getField("WT_aktualna").getValueAsString(),font1, 10f);
					form.getField("Odporno��_WT_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Odporno��_WT_cecha_SUMA").setValue(dodajDwaStringi(form.getField("WT_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Opanowanie": {
					form.getField("Opanowanie_SW_SW_cecha").setValue(form.getField("SW_aktualna").getValueAsString(),font1, 10f);
					form.getField("Opanowanie_SW_SW_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Opanowanie_SW_SW_cecha_suma").setValue(dodajDwaStringi(form.getField("SW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Oswajanie": {
					form.getField("Oswajanie_cecha_SW").setValue(form.getField("SW_aktualna").getValueAsString(),font1, 10f);
					form.getField("Oswajanie_cecha_SW_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Oswajanie_cecha_SW_SUMA").setValue(dodajDwaStringi(form.getField("SW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Percepcja": {
					form.getField("PERCEPCJA_cecha_I").setValue(form.getField("I_aktualna").getValueAsString(),font1, 10f);
					form.getField("PERCEPCJA_cecha_I_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("PERCEPCJA_cecha_I_SUMA").setValue(dodajDwaStringi(form.getField("I_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Plotkowanie": {
					form.getField("PLOTKOWANIE_CECHA_OGD").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("PLOTKOWANIE_CECHA_OGD_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("PLOTKOWANIE_CECHA_OGD_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Powo�enie": {
					form.getField("POWO�ENIE_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("POWO�ENIE_ZW_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("POWO�ENIE_ZW_cecha_SUMA").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Przekupstwo": {
					form.getField("Przekupstwo_cecha_OGD").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Przekupstwo_cecha_OGD_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Przekupstwo_cecha_OGD_suma").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Skradanie (Miasto)": {
					form.getField("skradanie_x").setValue("(Miasto)",font1, SZESC);
					form.getField("SKRADANIE_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("SKRADANIE_ZW_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("SKRADANIE_ZW_cecha_suma").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Skradanie (Dowolne)": {
					form.getField("skradanie_x").setValue("(Dowolne)",font1, SZESC);
					form.getField("SKRADANIE_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("SKRADANIE_ZW_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("SKRADANIE_ZW_cecha_suma").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Skradanie (Miasto albo Podziemia)": {
					form.getField("skradanie_x").setValue("(Miasto albo Podziemia)",font1, 4f);
					form.getField("SKRADANIE_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("SKRADANIE_ZW_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("SKRADANIE_ZW_cecha_suma").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Skradanie (Miasto albo Wie�)": {
					form.getField("skradanie_x").setValue("(Miasto albo Wieś)",font1, 4f);
					form.getField("SKRADANIE_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("SKRADANIE_ZW_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("SKRADANIE_ZW_cecha_suma").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Skradanie (Podziemia)": {
					form.getField("skradanie_x").setValue("(Podziemia)",font1, SZESC);
					form.getField("SKRADANIE_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("SKRADANIE_ZW_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("SKRADANIE_ZW_cecha_suma").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Skradanie (Wie�)": {
					form.getField("skradanie_x").setValue("(Wieś)",font1, SZESC);
					form.getField("SKRADANIE_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("SKRADANIE_ZW_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("SKRADANIE_ZW_cecha_suma").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Sztuka (Pisarstwo)": {
					form.getField("sztuka_jaka").setValue("(Pisarstwo)",font1, SZESC);
					form.getField("SZTUKA_cecha_Zr").setValue(form.getField("ZR_aktualna").getValueAsString(),font1, 10f);
					form.getField("SZTUKA_cecha_Zr_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("SZTUKA_cecha_Zr_SUMA").setValue(dodajDwaStringi(form.getField("ZR_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Sztuka (Kaligrafia)": {
					form.getField("sztuka_jaka").setValue("(Kaligrafia)",font1, SZESC);
					form.getField("SZTUKA_cecha_Zr").setValue(form.getField("ZR_aktualna").getValueAsString(),font1, 10f);
					form.getField("SZTUKA_cecha_Zr_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("SZTUKA_cecha_Zr_SUMA").setValue(dodajDwaStringi(form.getField("ZR_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Sztuka (Dowolna)": {
					form.getField("sztuka_jaka").setValue("(Dowolna)",font1, SZESC);
					form.getField("SZTUKA_cecha_Zr").setValue(form.getField("ZR_aktualna").getValueAsString(),font1, 10f);
					form.getField("SZTUKA_cecha_Zr_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("SZTUKA_cecha_Zr_SUMA").setValue(dodajDwaStringi(form.getField("ZR_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Sztuka (Ikony)": {
					form.getField("sztuka_jaka").setValue("(Ikony)",font1, SZESC);
					form.getField("SZTUKA_cecha_Zr").setValue(form.getField("ZR_aktualna").getValueAsString(),font1, 10f);
					form.getField("SZTUKA_cecha_Zr_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("SZTUKA_cecha_Zr_SUMA").setValue(dodajDwaStringi(form.getField("ZR_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Sztuka Przetrwania": {
					form.getField("Sztuka_przetrwania_INT_cecha").setValue(form.getField("INT_aktualna").getValueAsString(),font1, 10f);
					form.getField("Sztuka_przetrwania_INT_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Sztuka_przetrwania_INT_cecha_SUMA").setValue(dodajDwaStringi(form.getField("INT_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Targowanie": {
					form.getField("Targowanie_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Targowanie_OGD_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Targowanie_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Unik": {
					form.getField("UNIK_ZW_cecha_").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("UNIK_ZW_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("UNIK_ZW_cecha_SUMA").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wio�larstwo": {
					form.getField("wioslarstwo_cecha_S_").setValue(form.getField("S_aktualna").getValueAsString(),font1, 10f);
					form.getField("wioslarstwo_cecha_S_roziwniecie").setValue(poziomUmS,font1, 10f);
					form.getField("wioslarstwo_cecha_S_SUMA").setValue(dodajDwaStringi(form.getField("S_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wspinaczka": {
					form.getField("Wspinaczka_cecha_S_").setValue(form.getField("S_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wspinaczka_cecha_S_rozwiniecie_").setValue(poziomUmS,font1, 10f);
					form.getField("Wspinaczka_cecha_S_SUMA").setValue(dodajDwaStringi(form.getField("S_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (Gaw�dziarstwo)": {
					form.getField("wystepy_jakie").setValue("(Gaw�dziarstwo)",font1, 4.5f);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (Przemowy)": {
					form.getField("wystepy_jakie").setValue("(Przemowy)",font1, SZESC);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (Taniec)": {
					form.getField("wystepy_jakie").setValue("(Taniec)",font1, SZESC);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (Wyk�ady)": {
					form.getField("wystepy_jakie").setValue("(Wyk�ady)",font1, SZESC);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (Retoryka)": {
					form.getField("wystepy_jakie").setValue("(Retoryka)",font1, SZESC);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (Aktorstwo)": {
					form.getField("wystepy_jakie").setValue("(Aktorstwo)",font1, SZESC);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (Dowolne)": {
					form.getField("wystepy_jakie").setValue("(Dowolne)",font1, SZESC);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (Akrobatyka)": {
					form.getField("wystepy_jakie").setValue("(Akrobatyka)",font1, SZESC);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (Wr�enie)": {
					form.getField("wystepy_jakie").setValue("(Wr�enie)",font1, SZESC);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (Proroctwa)": {
					form.getField("wystepy_jakie").setValue("(Proroctwa)",font1, SZESC);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (�piewanie)": {
					form.getField("wystepy_jakie").setValue("(�piewanie)",font1, SZESC);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (Przemawianie)": {
					form.getField("wystepy_jakie").setValue("(Przemawianie)",font1, SZESC);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wyst�py (Szydzenie)": {
					form.getField("wystepy_jakie").setValue("(Szydzenie)",font1, SZESC);
					form.getField("Wyst�py_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wyst�py_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Wyst�py_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Zastraszanie": {
					form.getField("Zastraszanie_Cecha_S_").setValue(form.getField("S_aktualna").getValueAsString(),font1, 10f);
					form.getField("Zastraszanie_Cecha_S__rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Zastraszanie_Cecha_S_SUMA").setValue(dodajDwaStringi(form.getField("S_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + nazwa);
				}
		
	}

	/*
	 * funkcja do sumowania dw�ch string�w (umiejetnosci,ect...)
	 */
	private String dodajDwaStringi(String valueAsString, String string) {
		int pierwszy = Integer.parseInt(valueAsString);
		int drugi = Integer.parseInt(string);
		pierwszy += drugi;
		return Integer.toString(pierwszy);
	}

}
