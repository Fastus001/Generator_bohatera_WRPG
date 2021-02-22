package export;

import appearance.Appearance;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import commons.Hero;
import commons.Skill;
import commons.Stats;
import commons.Talent;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import static java.lang.String.*;


public class ExportToPdf {
	//po kompilcaji działa, w pracy w IDE, trzeba zmienić ścieżkę
	public static final String FONT_CASLON_PL = "CaslonAntiquePolskieZnaki.ttf";
	//public static final String FONT_CASLON_PL = "../GeneratorBohatera/src/resources/CaslonAntiquePolskieZnaki.ttf";
	public static final float SZESC = 6f;
	public static final float OSIEM = 8f;
	public static final float TEN = 10f;
	
	private PdfDocument pdf;
	private PdfFont font1;

	private PdfAcroForm form;
	private Hero hero;
	
	
	public ExportToPdf(Hero h, String des) throws IOException{

			
			this.hero = h;
			String nazwaPliku = hero.getName().replace( " ", "_")+"_"+ hero.getCurrentProfesjaName() + "_poz"+ hero.getCurrentProfPoziom() +".pdf";
			
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("WFRP_4ed_final_edytowalna.pdf");
			
			
			pdf = new PdfDocument(new PdfReader(inputStream),new PdfWriter(des+nazwaPliku));
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
		Iterator<Talent> talentIterator = hero.knownTalents.iterator();
		if(hero.knownTalents.size()<17) {
			for(int i = 0; i<hero.knownTalents.size(); i++)
			{
//				Talent tl = hero.knownTalents.get( i);
				Talent tl = talentIterator.next();
				talenty(tl, i+1);
			}	
		}else {
			JOptionPane.showMessageDialog(null, "Ilość znanych talentów u postaci przekracza ilość miejsca na karcie, zapisz postć do pliku excel", "Profesja  w Krysztaďż˝ach Czasu..", JOptionPane.INFORMATION_MESSAGE);
		}

	}



	private void sprawdzUmiejetnosci() {

		//sprawdzenie ile razy wystďż˝puje w umiejetnoďż˝ciach Broń Biała(xxx), jeďż˝eli wiecej niz dwa, to trzeba zmieniďż˝ na zaawansowanďż˝ ..
		// podobnie ze sztukďż˝
		int ileRazyBB = 0;
		int ileRazyWystepy = 0;
		int ileRazySztuka = 0;
		int ileRazySkradanie = 0;
		int ileRazyJazda = 0;
		for(Skill um:hero.knownSkills) {
			if(um.getName().contains( "Broń Biała"))
				{
				ileRazyBB++;
				if(ileRazyBB>1 && !um.getName().equals( "Broń Biała (Podstawowa)"))
					um.setType( "zaawansowana");
				}
			if(um.getName().contains( "Występy"))
				{
				ileRazyWystepy++;
				if(ileRazyWystepy>1)
					um.setType( "zaawansowana");
				}
			if(um.getName().contains( "Sztuka ("))
					{
						ileRazySztuka++;
						if(ileRazySztuka>1)
							um.setType( "zaawansowana");
						
					}
			if(um.getName().contains( "Skradanie"))
			{
				ileRazySkradanie++;
				if(ileRazySkradanie>1)
					um.setType( "zaawansowana");
			}
			if(um.getName().contains( "Jeździectwo"))
			{
				ileRazyJazda++;
				if(ileRazyJazda>1)
					um.setType( "zaawansowana");
			}
		}//koniec for
		
	}

	private void szybkosc() {
		int speed = hero.getStats().getSpeed();
		form.getField("Szybkość").setValue( valueOf( speed ), font1, TEN);
		speed = speed*2;
		form.getField("Chód").setValue( valueOf( speed ), font1, TEN);
		speed = speed*2;
		form.getField("Bieg").setValue( valueOf( speed ), font1, OSIEM);
		
	}
	
	private String getBonusCechy(int index, int ileRazy) {
		int [] cechyAktualne = hero.getStats().getStats();
		int bonusSily = cechyAktualne[index];
		bonusSily = ileRazy*((int) (bonusSily/10));
		Integer bs = bonusSily;
		return bs.toString();
	}

	private void wczytanieZywotnosci() {
		if(hero.getRace().getName().equals( "Niziołki"))
			form.getField("BS").setValue(getBonusCechy(2, 1),font1, TEN);
		
		form.getField("BWtx2").setValue(getBonusCechy(3, 2),font1, TEN);
		form.getField("BSW").setValue(getBonusCechy(8, 1),font1, TEN);
		int twardziel = hero.getHardyLevel();
		System.out.println("to PDF Twardziel razy - " + twardziel);
		if(twardziel>0) {
			form.getField("Twardziel").setValue(getBonusCechy(3, twardziel),font1, TEN);
		}
			
		
		//suma ďż˝ywotnoďż˝ci
		form.getField("fill_123").setValue(hero.getCechyHpString(),font1, TEN);
		
	}

	private void talenty(Talent tl,int licznik) {
		String [] formTab = {"Talent","talentPoz","Opis_talentu"};

			try {
				form.getField(formTab[0]+Integer.toString(licznik)).setValue( tl.showTalentNameWithLevel(), font1, SZESC);
				form.getField(formTab[1]+Integer.toString(licznik)).setValue( Integer.toString(tl.getLevel()), font1, SZESC);
				form.getField(formTab[2]+Integer.toString(licznik)).setValue( tl.getTest(), font1, SZESC);
			}catch (Exception e) {
				e.printStackTrace();
			}		
	}

	private void umiejetnosciZaawansowane(Skill um, int licznik) {
		String [] formTab = {"uzaw","Cecha","rozw","suma"};
		String [] cechyAktualneForms = {"WW_aktualna","US_aktualna","S_aktualna","WT_aktualna","I_aktualna","ZW_aktualna","ZR_aktualna","INT_aktualna","SW_aktualna","OGD_aktualna"};
		
		try {
			form.getField(formTab[0]+Integer.toString(licznik)).setValue( um.getName(), font1, SZESC);
			form.getField(formTab[1]+Integer.toString(licznik)).setValue( form.getField(cechyAktualneForms[um.getStatNumber()]).getValueAsString(), font1, SZESC);
			form.getField(formTab[2]+Integer.toString(licznik)).setValue(Integer.toString(um.getLevel()),font1, SZESC);
			form.getField(formTab[3]+Integer.toString(licznik)).setValue( dodajDwaStringi( form.getField(cechyAktualneForms[um.getStatNumber()]).getValueAsString(), Integer.toString( um.getLevel())), font1, SZESC);
		} catch (Exception e) {
			e.printStackTrace();
		}		

	}

	private void opisBohateraCechy() {
		String zmienna = "";
		form.setGenerateAppearance(true);
		//opis postaci
		form.getField("Imię").setValue( hero.getName(), font1, 10f);
		form.getField("Rasa").setValue(hero.getRace().getName(),font1, 10f);

		form.getField("Klasa").setValue(hero.getKlasaProfesji(),font1, 10f);
		
		form.getField("Profesja").setValue(hero.getProfesjaNameMain(),font1, 10f);
		Integer poziom = hero.getCurrentProfPoziom();
		zmienna = poziom.toString();
		
		form.getField("Poziom Profesji").setValue(zmienna,font1, OSIEM);
		form.getField("Ścieżka Profesji").setValue(hero.getProfesjaSciezka(),font1, OSIEM);
		form.getField("Status").setValue(hero.getProfesjaStatus(),font1, OSIEM);
		Appearance appearance = hero.getAppearance();
		form.getField("Wiek").setValue( valueOf( appearance.getAge() ), font1, OSIEM);
		form.getField("Wzrost").setValue( valueOf( appearance.getHeight() ), font1, OSIEM);
		form.getField("fill_118").setValue( appearance.getHairColor(),font1, OSIEM);
		form.getField("Oczy").setValue( appearance.getEyesColor(),font1, OSIEM);
		
		/*
		 * opisanie poziom aktualnych cech bohatera
		 */
		
		String [] cechyAktualneForms = {"WW_aktualna","US_aktualna","S_aktualna","WT_aktualna","I_aktualna","ZW_aktualna","ZR_aktualna","INT_aktualna","SW_aktualna","OGD_aktualna"};
		String [] cechyRozwinieciaForms = {"WW_rozwieniecie","US_rozwienicie","S_rozwienicie","WT_rozwienicie","I_rozwienicie","ZW_rozwienicie","ZR_rozwienicie","INT_rozwienicie","SW_rozwienicie","OGD_rozwienicie"};
		Stats stats = hero.getStats();
		int[] statsCurrent = stats.getStats();
		int[] statsAdvances = stats.getAdvances();
		String [] cechyBazoweForms = {"WW_poczatkowa","US_poczatkowa","S_poczatkowa","Wt_poczatkowa","I_poczatkowa","ZW_poczatkowa","ZR_poczatkowa","INT_poczatkowa","SW_poczatkowa","OGD_poczatkowa"};
		

		for(int x = 0; x < statsCurrent.length; x++) {
			form.getField(cechyAktualneForms[x]).setValue( valueOf( statsCurrent[x] ), font1, 10f);
			if(statsAdvances[x] != 0) {
				form.getField(cechyRozwinieciaForms[x]).setValue( valueOf( statsAdvances[x] ), font1, 10f);
				int wartosc = statsCurrent[x] - statsAdvances[x];
				if(hero.czyJestCechaRozwojuProfesji(x)) {
					form.getField(cechyBazoweForms[x]).setValue(wartosc+"*",font1, 10f);
				}else {
					form.getField(cechyBazoweForms[x]).setValue( valueOf( wartosc ), font1, 10f);
				}
			}	
			else {
				form.getField(cechyBazoweForms[x]).setValue( valueOf( statsCurrent[x] ), font1, 10f);
			}
			
		}//koniec for..
		

	}
	
	private void wczytajUmiejetnosci() {
		int liczbaUmZaawansow = 0;
		for(Skill um:hero.knownSkills) {
			//System.out.println("Umiejetntosci podstawowe wszystkie" + um.showTalentNameWithLevel());
			if(um.getType().equals( "podstawowa"))
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
			for(Skill um:hero.knownSkills)
			{
				if(um.getType().equals( "zaawansowana"))
				{
					licznik++;
					umiejetnosciZaawansowane(um, licznik);
				}
			}
		}else {
			JOptionPane.showMessageDialog(null, "Niestety plik PDF nie posiada tyle miejsc na umiejętności zaawansowane :-(", "Zapisz postać do pliku Excel!!", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	/*
	 * metoda ze switchem do opisania poszczegďż˝lnych umiejetnosci,
	 * trzeba zwrďż˝ciďż˝ uwagďż˝ na broďż˝ biaďż˝ďż˝ innďż˝ niďż˝ podstawowa, oraz sztukďż˝
	 * 
	 */
	private void umiejetnosciPodstawowe(Skill um) {
		//Umiejďż˝tnoďż˝ci podstawowe i zaawansowane
				String nazwa= um.getName();
				int poziomUmiejetnosci = um.getLevel();
				String poziomUmS = Integer.toString(poziomUmiejetnosci);
				System.out.println("Nazwa umiejetności " + um.getName() + ".");
				switch (nazwa) {
				case "Atletyka": {
					form.getField("Atletyka_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("Atletyka_ZW_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Atletyka_ZW_cecha_SUMA").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Broń Biała (Podstawowa)": {
					form.getField("BB_podstawoa_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Broń Biała (Dwuręczna)": {
					form.getField("Broń biała X").setValue("(Dwuręczna)",font1, 4.5f);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Broń Biała (Korbacz)": {
					form.getField("Broń biała X").setValue("(Korbacz)",font1, SZESC);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Broń Biała (Bijatyka)": {
					form.getField("Broń biała X").setValue("(Bijatyka)",font1, SZESC);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Broń Biała (Dowolna)": {
					form.getField("Broń biała X").setValue("(Dowolna)",font1, SZESC);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Broń Biała (Drzewcowa)": {
					form.getField("Broń biała X").setValue("(Drzewcowa)",font1, 5f);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Broń Biała (Korbacz albo Dwuręczna)": {
					form.getField("Broń biała X").setValue("(Korbacz albo Dwuręczna)",font1, 3f);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Broń Biała (Kawaleryjska)": {
					form.getField("Broń biała X").setValue("(Kawaleryjska)",font1, 4.5f);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Broń Biała (Szermiercza)": {
					form.getField("Broń biała X").setValue("(Szermiercza)",font1, SZESC);
					form.getField("BB_X_cecha").setValue(form.getField("WW_aktualna").getValueAsString(),font1, 10f);
					form.getField("BB_X_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("BB_X_suma").setValue(dodajDwaStringi(form.getField("WW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Broń Biała (Parująca)": {
					form.getField("Broń biała X").setValue("(Parująca)",font1, SZESC);
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
				case "Jeździectwo (Dowolne)": {
					form.getField("jazda_x").setValue("(Dowolne)",font1, 4f);
					form.getField("Jezdziectwo_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("Jezdziectwo_ZW_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Jezdziectwo_ZW_cecha_suma").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Jeździectwo (Konie)": {
					form.getField("jazda_x").setValue("(Konie)",font1, 6f);
					form.getField("Jezdziectwo_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("Jezdziectwo_ZW_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Jezdziectwo_ZW_cecha_suma").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Mocna Głowa": {
					form.getField("Mocna_g").setValue(form.getField("WT_aktualna").getValueAsString(),font1, 10f);
					form.getField("Mocna_g_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Mocna_g_suma").setValue(dodajDwaStringi(form.getField("WT_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;	
				case "Nawigacja": {
					form.getField("Nawigacja_cecha_I_").setValue(form.getField("I_aktualna").getValueAsString(),font1, 10f);
					form.getField("Nawigacja_cecha_I_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Nawigacja_cecha_I_rozwiniecie_SUMA").setValue(dodajDwaStringi(form.getField("I_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Odporność": {
					form.getField("Odporność_WT_cecha").setValue(form.getField("WT_aktualna").getValueAsString(),font1, 10f);
					form.getField("Odporność_WT_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Odporność_WT_cecha_SUMA").setValue(dodajDwaStringi(form.getField("WT_aktualna").getValueAsString(),poziomUmS),font1, 10f);
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
				case "Powożenie": {
					form.getField("POWOŻENIE_ZW_cecha").setValue(form.getField("ZW_aktualna").getValueAsString(),font1, 10f);
					form.getField("POWOŻENIE_ZW_cecha_rozwiniecie").setValue(poziomUmS,font1, 10f);
					form.getField("POWOŻENIE_ZW_cecha_SUMA").setValue(dodajDwaStringi(form.getField("ZW_aktualna").getValueAsString(),poziomUmS),font1, 10f);
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
				case "Skradanie (Miasto albo Wieś)": {
					form.getField("skradanie_x").setValue("(Miasto albo WieĹ›)",font1, 4f);
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
				case "Skradanie (Wieś)": {
					form.getField("skradanie_x").setValue("(WieĹ›)",font1, SZESC);
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
				case "Wioślarstwo": {
					form.getField("wioslarstwo_cecha_S_").setValue(form.getField("S_aktualna").getValueAsString(),font1, 10f);
					form.getField("wioslarstwo_cecha_S_roziwniecie").setValue(poziomUmS,font1, 10f);
					form.getField("wioslarstwo_cecha_S_SUMA").setValue(dodajDwaStringi(form.getField("S_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Wspinaczka": {
					form.getField("Wspinaczka_cecha_S_").setValue(form.getField("S_aktualna").getValueAsString(),font1, 10f);
					form.getField("Wspinaczka_cecha_S_rozwiniecie_").setValue(poziomUmS,font1, 10f);
					form.getField("Wspinaczka_cecha_S_SUMA").setValue(dodajDwaStringi(form.getField("S_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Gawędziarstwo)": {
					form.getField("wystepy_jakie").setValue("(Gawędziarstwo)",font1, 4.5f);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Przemowy)": {
					form.getField("wystepy_jakie").setValue("(Przemowy)",font1, SZESC);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Taniec)": {
					form.getField("wystepy_jakie").setValue("(Taniec)",font1, SZESC);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Wykłady)": {
					form.getField("wystepy_jakie").setValue("(Wykłady)",font1, SZESC);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Retoryka)": {
					form.getField("wystepy_jakie").setValue("(Retoryka)",font1, SZESC);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Aktorstwo)": {
					form.getField("wystepy_jakie").setValue("(Aktorstwo)",font1, SZESC);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Dowolne)": {
					form.getField("wystepy_jakie").setValue("(Dowolne)",font1, SZESC);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Akrobatyka)": {
					form.getField("wystepy_jakie").setValue("(Akrobatyka)",font1, SZESC);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Wróżenie)": {
					form.getField("wystepy_jakie").setValue("(Wróżenie)",font1, SZESC);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Proroctwa)": {
					form.getField("wystepy_jakie").setValue("(Proroctwa)",font1, SZESC);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Śpiewanie)": {
					form.getField("wystepy_jakie").setValue("(Śpiewanie)",font1, SZESC);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Przemawianie)": {
					form.getField("wystepy_jakie").setValue("(Przemawianie)",font1, SZESC);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
				}break;
				case "Występy (Szydzenie)": {
					form.getField("wystepy_jakie").setValue("(Szydzenie)",font1, SZESC);
					form.getField("Występy_OGD_cecha_").setValue(form.getField("OGD_aktualna").getValueAsString(),font1, 10f);
					form.getField("Występy_OGD_cecha_rozwieniecie").setValue(poziomUmS,font1, 10f);
					form.getField("Występy_OGD_cecha_SUMA").setValue(dodajDwaStringi(form.getField("OGD_aktualna").getValueAsString(),poziomUmS),font1, 10f);
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
	 * funkcja do sumowania dwóch stringów (umiejetnosci,ect...)
	 */
	private String dodajDwaStringi(String valueAsString, String string) {
		int pierwszy = Integer.parseInt(valueAsString);
		int drugi = Integer.parseInt(string);
		pierwszy += drugi;
		return Integer.toString(pierwszy);
	}

}
