package views;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import commons.Bohater;
import commons.Profesja;
import commons.Rasa;
import commons.Talent;
import commons.Umiejetnosc;

import java.awt.Rectangle;
import javax.swing.UIManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.ScrollPaneConstants;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class NewGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Components
	private JPanel contentPane;
	private JComboBox<Profesja> cbProfesja;
	private JComboBox<Object> cbRasa;
	private JComboBox<String> cbDoswiadczenie;
	private JButton btnNowyBohater;
	private JButton btnPodniesPoziomPr;
	private JCheckBox chckbxShowTalents;
	private JTextArea textArea;
	private JList<Bohater> list;
	
	
	private DefaultListModel<Bohater> listaBohaterow = new DefaultListModel<Bohater>();
	private Bohater nowyBohater;
	private ArrayList<Rasa> listaRas;
	public ArrayList<Talent> listaTalentow;	
	private ArrayList<Profesja> listaProfesji;
	private ArrayList<Profesja> profesjePierwszyPoziom;
	private ArrayList<Umiejetnosc> listaUm;
	private JScrollPane scrlPaneLista;
	private JButton btsSaveHero;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnMen;
	private JRadioButton rdbtnWomen;
	

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewGui frame = new NewGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewGui() {
		initComponents();
		createEvents();
	}



	private void createEvents() {
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});

		
		btsSaveHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listaBohaterow.addElement(nowyBohater);
			}
		});
		
		///////////////////////////////////////////////////////////////
		list.setCellRenderer(new DefaultListCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
			{
				Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		                if (renderer instanceof JLabel && value instanceof Bohater)
			{
		                    // Here value will be of the Type 'Reservation'
		                    ((JLabel) renderer).setText(((Bohater) value).toString());
		                }
		                return renderer;
		        }
		});
		
		/////////////////////////////////////////////////////////////////////////
		//utworzenie nowego bohatera
		/////////////////////////////////////////////////////////////////////////
		btnNowyBohater.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Rasa losowaRasa = listaRas.get(losowanieRasy());
				Profesja losowaProfesja;
				
				if(cbRasa.getSelectedIndex()!= -1){
					losowaRasa = (Rasa) cbRasa.getSelectedItem();
				}
				
				
				if(cbProfesja.getSelectedIndex()!= -1){
					losowaProfesja = (Profesja) cbProfesja.getSelectedItem();
				}else{
					szukajProfesjiPierwszyPoziom(losowaRasa);
					losowaProfesja = profesjePierwszyPoziom.get( (int)(Math.random()*profesjePierwszyPoziom.size() )) ;
				}
				if(rdbtnMen.isSelected())
					nowyBohater = new Bohater(losowaRasa,losowaProfesja, true);
				else
					nowyBohater = new Bohater(losowaRasa,losowaProfesja, false);
				
				int opcjaDoswiadczenia = cbDoswiadczenie.getSelectedIndex();
				nowyBohater.doswiadczenieBohatera(opcjaDoswiadczenia);
				
						
				//wyswietlenie nowego bohatera
				textArea.setText(nowyBohater.wyswietlBohatera(chckbxShowTalents.isSelected()));
				//System.out.println(checkBox1.isSelected());
				//nowyBohater.closeBohater();
				btnPodniesPoziomPr.setEnabled(true);
				btsSaveHero.setEnabled(true);
			}
		});
		
		/////////////////////////////////////////////////////////////////////////
		//podniesienie poziomu we wczeœniej wygenerowanym bohaterze
		/////////////////////////////////////////////////////////////////////////				
		btnPodniesPoziomPr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Profesja profesjaNowyPoziom = null;
				for(Profesja p: listaProfesji){
					if(p.toString().equals(nowyBohater.getCurrentProfesjaName()) && (p.getPoziom() == nowyBohater.getCurrentProfPoziom()+1)){
						profesjaNowyPoziom = new Profesja(p);
					}
				}
				if(profesjaNowyPoziom != null){
				//System.out.println("Nowa œcie¿ka profesji to: " + profesjaNowyPoziom.getNameProfesjaSciezka());
				nowyBohater.nowaProfesja(profesjaNowyPoziom);
				
				int opcjaDoswiadczenia = cbDoswiadczenie.getSelectedIndex();
				nowyBohater.doswiadczenieBohatera(opcjaDoswiadczenia);
				
				
				textArea.setText(nowyBohater.wyswietlBohatera(chckbxShowTalents.isSelected()));
				
				if(profesjaNowyPoziom.getPoziom() == 4){
					btnPodniesPoziomPr.setEnabled(false);
					}
				}
			}
		});
		
		/////////////////////////////////////////////////////////////////////////
		//je¿eli zostanie wybrana konkretna Rasa, to mo¿liwe jest wyszukanie profesji dostêpnej dla danej rasy
		/////////////////////////////////////////////////////////////////////////
		cbRasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rasa wybor = (Rasa) cbRasa.getSelectedItem();
				
				szukajProfesjiPierwszyPoziom(wybor);
				
				cbProfesja.removeAllItems();
				Collections.sort(profesjePierwszyPoziom);
				for(Profesja p:profesjePierwszyPoziom){
					cbProfesja.addItem(p);
				}
				cbProfesja.setEnabled(true);
				
				textArea.append(wybor.toString());
			}
		});
		
	}

	private void initComponents() {
		setTitle("Generator Postaci Warhammer 4ed");
		setIconImage(Toolkit.getDefaultToolkit().getImage(NewGui.class.getResource("/resources/sledgehammer.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		textArea = new JTextArea();
		textArea.setMargin(new Insets(4, 4, 3, 3));
		textArea.setLineWrap(true);
		textArea.setBounds(new Rectangle(5, 5, 5, 5));
		textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textArea.setFont(new Font("Arial", Font.PLAIN, 12));

		
		btnNowyBohater = new JButton("Nowy Bohater");
		
		WczytajTalenty();
		
		cbRasa = new JComboBox<Object>(WczytajRasy());

		cbRasa.setToolTipText("Wybierz ras\u0119, je\u017Celi nie chcesz aby by\u0142a ona losowa.");
		
		cbProfesja = new JComboBox<Profesja>();
		cbProfesja.setToolTipText("Wybierz profesj\u0119 dla bohatera.");
		
		WczytajProfesje();
		
		cbDoswiadczenie = new JComboBox<String>();
		cbDoswiadczenie.setModel(new DefaultComboBoxModel<String>(new String[] {"Brak", "Pocz\u0105tkuj\u0105ca", "\u015Arednio zaawansowana", "Do\u015Bwiadczona"}));
		cbDoswiadczenie.setToolTipText("Czy posta\u0107 posiada ju\u017C jakie\u015B rozwini\u0119cia. W zale\u017Cno\u015Bci od wybranej opcji, posta\u0107 otrzymuje  (3,5,7) rozwini\u0119\u0107 (umiej\u0119tno\u015Bci, cechy klasowe lub talent).\r\n");
		
		
		btnPodniesPoziomPr = new JButton("Podnie\u015B poziom");

		btnPodniesPoziomPr.setToolTipText("Posta\u0107 awansuje na nast\u0119pny poziom rozwoju swojej \u015Bcie\u017Cki profesji.\r\nJe\u017Celi poziom cech lub przynajmniej o\u015Bmiu umiej\u0119tno\u015Bci jest zbyt niski, to s\u0105 one automoatyczne podnoszone do wymaganego poziomu (aby uko\u0144czy\u0107 dany poziom profesji).");
		

		
		chckbxShowTalents = new JCheckBox("Wy\u015Bwietl talenty");
		chckbxShowTalents.setSelected(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		btsSaveHero = new JButton("Zapisz posta\u0107");
		btsSaveHero.setEnabled(false);

		
		scrlPaneLista = new JScrollPane();
		scrlPaneLista.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		rdbtnMen = new JRadioButton("M\u0119\u017Cczyzna");
		rdbtnMen.setSelected(true);
		buttonGroup.add(rdbtnMen);
		
		rdbtnWomen = new JRadioButton("Kobieta");
		buttonGroup.add(rdbtnWomen);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnNowyBohater)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbRasa, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbProfesja, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbDoswiadczenie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPodniesPoziomPr)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(chckbxShowTalents)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnMen)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnWomen))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 745, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addComponent(scrlPaneLista, GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btsSaveHero)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrlPaneLista, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNowyBohater)
						.addComponent(cbRasa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbProfesja, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbDoswiadczenie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPodniesPoziomPr)
						.addComponent(chckbxShowTalents)
						.addComponent(rdbtnMen)
						.addComponent(rdbtnWomen)
						.addComponent(btsSaveHero)))
		);
		
		list = new JList<Bohater>(listaBohaterow);


		scrlPaneLista.setViewportView(list);
		scrollPane.setViewportView(textArea);
		contentPane.setLayout(gl_contentPane);

		
	}
	
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
			//wyswietlRasyWszystkie();
			//wczytajProfesjePrzycisk.setEnabled(true);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return listaRas.toArray();
		
}//koniec metody
	
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
			//wyswietlProfesjeWszystkoPrzycisk();
			btnNowyBohater.setEnabled(true);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
}//koniec metody
	
	/////////////////////////////////////////////////////////////////////////
	//zapisanie zaimportowanych z pliku txt opsiów w obiektach talent;
	/////////////////////////////////////////////////////////////////////////
	public void tworzTalent(String wierszDanych){
		String[] wynik = wierszDanych.split("/");
		Talent tl = new Talent(wynik[0],Integer.parseInt(wynik[1]),wynik[2], wynik[3]);
		listaTalentow.add(tl);
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
	

	/////////////////////////////////////////////////////////////////////////
	//szansa wylosowanie jak w podrêczniku 4ed
	/////////////////////////////////////////////////////////////////////////	
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
		
	/////////////////////////////////////////////////////////////////////////
	//konwersja wiersza z pliku txt na umiejêtnoœci zapisywane w tablicy
	/////////////////////////////////////////////////////////////////////////	
	public void tworzUm(String wierszDanych){
		String[] wynik = wierszDanych.split("/");
		Umiejetnosc um = new Umiejetnosc(wynik[0], Integer.parseInt(wynik[1]),wynik[2],0);
		listaUm.add(um);
		}
	
	public void wyswietlTalentyWszystkie(){
		for(Talent x:listaTalentow){
			textArea.append(x.wyswietlWszystkoTalent()+"\n");
		}
	}
	
	public void wyswietlRasyWszystkie(){
		for(Rasa x:listaRas){
			textArea.append(x.wyswietlRasyWszystko()+"\n");
		}
	}
	
	public void wyswietlProfesjeWszystkoPrzycisk(){
		for(Profesja p:listaProfesji){
			textArea.append(p.wyswietlProfesje()+"\n");
		}
	}
	
	public ArrayList<Talent> getListaTalentow(){
		return listaTalentow;
	}
}
