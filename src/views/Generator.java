package views;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.border.Border;

import commons.Bohater;
import commons.Profesja;
import commons.Rasa;
import commons.Talent;
import commons.Umiejetnosc;

import javax.swing.BorderFactory;

//klasa do  GUI oraz testowania
class GeneratorCech{
	private JFrame ramka;
	private JPanel panel;
	private JTextArea panelTekst;
	private ArrayList<Umiejetnosc> listaUm;
	public ArrayList<Talent> listaTalentow;	
	private ArrayList<Rasa> listaRas;
	private ArrayList<Profesja> listaProfesji;
	private ArrayList<Profesja> profesjePierwszyPoziom;
	private JButton tworzPostacPrzycisk;
	private JButton dodajPoziomProfesjiPrzycisk;
	public JButton wczytajProfesjePrzycisk;
	private JCheckBox checkBox1;
	private JComboBox<String> cBoxPozZaawansowania;
	private JComboBox<Object> cBoxRasy;
	private JComboBox<Profesja> cBoxProfesje;
	private Bohater nowyBohater;
	private JPanel panelNaCheckBoxy;
	private static String [] poziomyZaawansowania = {"Brak","Pocz¹tkuj¹ca", "Œredniozaawansowana", "Doœwiadczona"};
	
	public static void main(String[] args){
		GeneratorCech gui = new GeneratorCech();
		gui.doRoboty();
	}
	
	public void doRoboty(){
		// okno aplikacji
		ramka = new JFrame("Generator Postaci Warhammer 4ed by Fastus, ver 1.0");
		ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Image ikonaRamka = new ImageIcon("../GeneratorBohatera/src/resources/warhBanner.jpg").getImage();
		ramka.setIconImage(ikonaRamka);
		
		ImageIcon obrazekHeader = new ImageIcon("../GeneratorBohatera/src/resources/WarhammerHeader.jpg");
		JLabel panelHeader = new JLabel(obrazekHeader);
		JPanel panelNorth = new JPanel();
		panelNorth.setBackground(Color.white);
		panelNorth.add(panelHeader);
		
		
		//panel tekstowy do wyœwietlania wygenerowenej postaci
		panelTekst = new JTextArea(45,70);
		panelTekst.setLineWrap(true);
		panelTekst.setWrapStyleWord(true);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		panelTekst.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		
		JScrollPane przewijanie = new JScrollPane(panelTekst);
		przewijanie.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		przewijanie.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				
		panel = new JPanel()	;
		panel.setBackground(Color.white);
		
		panel.add(przewijanie);

			
		tworzPostacPrzycisk = new JButton("Utwórz now¹ Postaæ");
		tworzPostacPrzycisk.addActionListener(new TworzNowaPostacListener());
		//tworzPostacPrzycisk.setEnabled(false);
		cBoxPozZaawansowania = new JComboBox<String>(poziomyZaawansowania);
		
		
		panelNaCheckBoxy = new JPanel();
		panelNaCheckBoxy.add(tworzPostacPrzycisk);
		panelNaCheckBoxy.add(cBoxPozZaawansowania);
		
		
		
		JPanel panelNaPrzyciski = new JPanel();
		
		WczytajTalenty();
		
		cBoxRasy = new JComboBox<Object>(WczytajRasy());
		cBoxRasy.addActionListener(new cBoxRasyListener());
		
		cBoxProfesje = new JComboBox<Profesja>();
		cBoxProfesje.setEnabled(false);
		
		checkBox1 = new JCheckBox("Wyœwietl opisy talentów");
		checkBox1.setSelected(true);
		checkBox1.setBackground(Color.white);
		
		WczytajProfesje();
		
		panelNaCheckBoxy.add(cBoxRasy);
		panelNaCheckBoxy.add(cBoxProfesje);
		dodajPoziomProfesjiPrzycisk = new JButton("Dodaj poziom do profesji");
		dodajPoziomProfesjiPrzycisk.addActionListener(new dodajPoziomProfesjiPrzyciskListener());
		dodajPoziomProfesjiPrzycisk.setEnabled(false);
		
		
		panelNaPrzyciski.add(panelNaCheckBoxy);
		panelNaPrzyciski.add(dodajPoziomProfesjiPrzycisk);
		panelNaPrzyciski.add(checkBox1);
		
		panelNaPrzyciski.setBackground(Color.white);
		panelNaCheckBoxy.setBackground(Color.white);
		ramka.getContentPane().add(BorderLayout.NORTH, panelNorth);
		ramka.getContentPane().add(BorderLayout.CENTER, panel);
		ramka.getContentPane().add(BorderLayout.SOUTH, panelNaPrzyciski);
		
		ramka.setSize(1000,1000);
		ramka.setVisible(true);
		
		
	} //koniec metody
	
	
	
		
	public class TworzNowaPostacListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			Rasa losowaRasa = listaRas.get(losowanieRasy());
			Profesja losowaProfesja;
			
			if(cBoxRasy.getSelectedIndex()!= -1){
				losowaRasa = (Rasa) cBoxRasy.getSelectedItem();
			}
			
			
			if(cBoxProfesje.getSelectedIndex()!= -1){
				losowaProfesja = (Profesja) cBoxProfesje.getSelectedItem();
			}else{
				szukajProfesjiPierwszyPoziom(losowaRasa);
				losowaProfesja = profesjePierwszyPoziom.get( (int)(Math.random()*profesjePierwszyPoziom.size() )) ;
			}
			
			nowyBohater = new Bohater(losowaRasa,losowaProfesja);
			
			int opcjaDoswiadczenia = cBoxPozZaawansowania.getSelectedIndex();
			nowyBohater.doswiadczenieBohatera(opcjaDoswiadczenia);
			
					
			//wyswietlenie nowego bohatera
			panelTekst.setText(nowyBohater.wyswietlBohatera(checkBox1.isSelected()));
			//System.out.println(checkBox1.isSelected());
			//nowyBohater.closeBohater();
			dodajPoziomProfesjiPrzycisk.setEnabled(true);
		}
	}
	
	public void szukajProfesjiPierwszyPoziom(Rasa losowaRasa) {
		profesjePierwszyPoziom = new ArrayList<Profesja>();
			
			//sprawdzenie profesji p¹d k¹tem poziomy (lvl1) oraz tego jaka rasa w danej profesji jest dostêpna
			int poziomPierwszy = 1;
			for(Profesja p:listaProfesji){
				if(p.getPoziom()==poziomPierwszy){
					for(int i = 0; i < p.getLiczbaDostepnychRas(); i++){						
						String nazwaP = losowaRasa.getName();
						if(nazwaP.equals(p.dostepnaRasa[i])){
						profesjePierwszyPoziom.add(p);							
						}
					}	
				}
			}
	}
	
	
	public class dodajPoziomProfesjiPrzyciskListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			Profesja profesjaNowyPoziom = null;
			for(Profesja p: listaProfesji){
				if(p.toString().equals(nowyBohater.getCurrentProfesjaName()) && (p.getPoziom() == nowyBohater.getCurrentProfPoziom()+1)){
					profesjaNowyPoziom = new Profesja(p);
				}
			}
			if(profesjaNowyPoziom != null){
			//System.out.println("Nowa œcie¿ka profesji to: " + profesjaNowyPoziom.getNameProfesjaSciezka());
			nowyBohater.nowaProfesja(profesjaNowyPoziom);
			
			int opcjaDoswiadczenia = cBoxPozZaawansowania.getSelectedIndex();
			nowyBohater.doswiadczenieBohatera(opcjaDoswiadczenia);
			
			
			panelTekst.setText(nowyBohater.wyswietlBohatera(checkBox1.isSelected()));
			
			if(profesjaNowyPoziom.getPoziom() == 4){
				dodajPoziomProfesjiPrzycisk.setEnabled(false);
				}
			}
			
		}
	}
	
	//je¿eli zostanie wybrana konkretna Rasa, to mo¿liwe jest wyszukanie profesji dostêpnej dla danej rasy
	public class cBoxRasyListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
				Rasa wybor = (Rasa) cBoxRasy.getSelectedItem();
				
				szukajProfesjiPierwszyPoziom(wybor);
				
				cBoxProfesje.removeAllItems();
				Collections.sort(profesjePierwszyPoziom);
				for(Profesja p:profesjePierwszyPoziom){
					cBoxProfesje.addItem(p);
				}
				cBoxProfesje.setEnabled(true);
				
				panelTekst.append(wybor.toString());
		}
	}
	
	public void WczytajProfesje(){
			
			
			try{
				listaProfesji = new ArrayList<Profesja>();
				File plik = new File("../GeneratorBohatera/src/resources/profesje.txt");
				FileReader czytaj = new FileReader(plik);
				BufferedReader czytajB = new BufferedReader(czytaj);
				
				String wiersz = null;
				while((wiersz = czytajB.readLine()) != null){
					if(wiersz.length()==0)
						break;
					String nazwaProfesji = wiersz;
					String sciezkaProfesji = czytajB.readLine();
					//rasy
					wiersz = czytajB.readLine();
					String dostepneRasy[] = wiersz.split(",");
					
					//dostêpne umiejêtnoœci
					wiersz = czytajB.readLine();
					String dostepneUm[] = wiersz.split(",");
					
					ArrayList<Umiejetnosc> umiej = new ArrayList<Umiejetnosc>();
					for(String x:dostepneUm){
							String[] doZapisaniaUm = x.split("/");
							Umiejetnosc tempUm = new Umiejetnosc(doZapisaniaUm[0], Integer.parseInt(doZapisaniaUm[1]), doZapisaniaUm[2],0);
							umiej.add(tempUm);
						}
					
					//dostêpne talenty
					wiersz = czytajB.readLine();
					String dostepneTlnt[] = wiersz.split(",");
					
					ArrayList<Talent> listaZnTalentow = new ArrayList<Talent>();
						
						for(String x:dostepneTlnt){
							String[] doZapTalenty = x.split("/");
							Talent tempTlnt = new Talent(doZapTalenty[0],Integer.parseInt(doZapTalenty[1]),doZapTalenty[2]);
							tempTlnt.setOpis(dodajOpisDoTalentu(tempTlnt));
							listaZnTalentow.add(tempTlnt);
						}
					
					
					//cechy rozwoju
					wiersz = czytajB.readLine();
					String wynik3[] = wiersz.split(",");
					int [] cechyRozwoju = new int [wynik3.length];
					for (int i = 0; i<wynik3.length; i++){
						cechyRozwoju[i] = Integer.parseInt(wynik3[i]);
					}
					//poziom profesji
					int poziomProfesji = Integer.parseInt(czytajB.readLine());
					
					Profesja prof = new Profesja(nazwaProfesji, sciezkaProfesji, poziomProfesji, umiej,listaZnTalentow,dostepneRasy,cechyRozwoju );
					listaProfesji.add(prof);

				}
				czytajB.close();
				panelTekst.append("Profesje wczytane pomyœlnie\n");
				//wyswietlProfesjeWszystkoPrzycisk();
				tworzPostacPrzycisk.setEnabled(true);
				
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
	}//koniec metody
	
	
	public void WczytajTalenty(){
			try{
					listaTalentow = new ArrayList<Talent>();
					File plik = new File("../GeneratorBohatera/src/resources/talenty.txt");
					FileReader czytaj = new FileReader(plik);
					BufferedReader czytajBuf = new BufferedReader(czytaj);
					String wiersz = null;
					
					while((wiersz = czytajBuf.readLine()) !=null){
						if(wiersz.length()==0)
							break;
						tworzTalent(wiersz);
					}
					czytajBuf.close();
					panelTekst.append("Talenty wczytane pomyœlnie\n");
					//wyswietlTalentyWszystkie();
			}catch(Exception ex){
				ex.printStackTrace();
				System.out.println("Talenty nie wczytane!!");
			}
	}//koniec metody
	
	
	
	public Object[] WczytajRasy(){
			try{
				listaRas = new ArrayList<Rasa>();
				File plik = new File("../GeneratorBohatera/src/resources/rasy.txt");
				FileReader czytaj = new FileReader(plik);
				BufferedReader czytajBuf = new BufferedReader(czytaj);
				String wiersz = null;
				
				
				while((wiersz = czytajBuf.readLine()) !=null){
						if(wiersz.length()==0)
							break;
						String nazwa = wiersz;
						wiersz = czytajBuf.readLine();
						String wynik1[] = wiersz.split(",");
						wiersz = czytajBuf.readLine();
						String wynik2[] = wiersz.split(",");
						wiersz = czytajBuf.readLine();
						String talenty[] = wiersz.split(",");
						wiersz = czytajBuf.readLine();
						//zamiana stringów na tablice intów -cechy bazowe
						int [] tablica = new int[10];
						for(int i = 0; i<10; i++){
							tablica[i] = Integer.parseInt(wynik1[i]);
						}
						//zapisanie umiejetnosci jako obiekty
						ArrayList<Umiejetnosc> umiej = new ArrayList<Umiejetnosc>();
						for(String x:wynik2){
							String[] doZapisaniaUm = x.split("/");
							Umiejetnosc tempUm = new Umiejetnosc(doZapisaniaUm[0], Integer.parseInt(doZapisaniaUm[1]), doZapisaniaUm[2],0);
							umiej.add(tempUm);
						}
						//konwersja talentow na obiekty
						ArrayList<Talent> listaZnTalnetow = new ArrayList<Talent>();
						
						for(String x:talenty){
							String[] doZapTalenty = x.split("/");
							Talent tempTlnt = new Talent(doZapTalenty[0],Integer.parseInt(doZapTalenty[1]),doZapTalenty[2]);
							//todo dodanie opisu do talentow
							tempTlnt.setOpis(dodajOpisDoTalentu(tempTlnt));
							listaZnTalnetow.add(tempTlnt);
						}
						
						
						Rasa rs = new Rasa(nazwa, tablica ,umiej , listaZnTalnetow, wiersz);
						listaRas.add(rs);	
					}	
				czytajBuf.close();
				panelTekst.append("Rasy wczytane pomyœlnie\n");
				//wyswietlRasyWszystkie();
				//wczytajProfesjePrzycisk.setEnabled(true);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
			return listaRas.toArray();
			
	}//koniec metody
	
	
	//konwersja wiersza z pliku txt na umiejêtnoœci zapisywane w tablicy
	public void tworzUm(String wierszDanych){
		String[] wynik = wierszDanych.split("/");
		Umiejetnosc um = new Umiejetnosc(wynik[0], Integer.parseInt(wynik[1]),wynik[2],0);
		listaUm.add(um);
		}
	
	//zapisanie zaimportowanych z pliku txt opsiów w obiektach talent;
	public void tworzTalent(String wierszDanych){
		String[] wynik = wierszDanych.split("/");
		Talent tl = new Talent(wynik[0],Integer.parseInt(wynik[1]),wynik[2], wynik[3]);
		listaTalentow.add(tl);
	}
	
	public void wyswietlUmiejetnosciWszystkie(){
		for(Umiejetnosc x:listaUm){
			panelTekst.append(x.wyswietlWszystko()+"\n");
		}
	}
	
	
	public void wyswietlTalentyWszystkie(){
		for(Talent x:listaTalentow){
			panelTekst.append(x.wyswietlWszystkoTalent()+"\n");
		}
	}
	
	public void wyswietlRasyWszystkie(){
		for(Rasa x:listaRas){
			panelTekst.append(x.wyswietlRasyWszystko()+"\n");
		}
	}
	
	public void wyswietlProfesjeWszystkoPrzycisk(){
		for(Profesja p:listaProfesji){
			panelTekst.append(p.wyswietlProfesje()+"\n");
		}
	}
	
	public ArrayList<Talent> getListaTalentow(){
		return listaTalentow;
	}
	
	
	//szansa jak w podrêczniku
	private int losowanieRasy() {
		int rzut = (int) (Math.random()*100)+1;
		if(rzut <91){
			return 0;
		}else if(rzut >90 && rzut <95){
			return 2;
		}else if(rzut>94 && rzut <99){
			return 1;
		}else if(rzut==99){
			return 3;
		}else{
			return 4;
		}
	}
	
	public String dodajOpisDoTalentu(Talent tal) {
		if(listaTalentow.size()>0) {
			for(Talent temp:listaTalentow){
				if(temp.toString().equals(tal.toString())){
					return temp.getOpisString();
				}
			}
		}
		return "brak opis - ups";
	}
}




	
	
	
	
	
	
	
	
	