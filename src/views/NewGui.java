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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import commons.Bohater;
import commons.ExportToPdf;
import commons.NpcKontroler;
import commons.NpcModel;
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
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class NewGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String urlSavaPdf = null;
	//Components
	private JPanel contentPane;
	private JComboBox<Profesja> cbProfesja;
	private JComboBox<Object> cbRasa;
	private JComboBox<String> cbDoswiadczenie;
	private JButton btnNowyBohater;
	private JButton btnPodniesPoziomPr;
	private JCheckBox chckbxShowTalents;
	private JTextArea textArea;
	private JScrollPane scrlPaneLista;
	private JButton btsSaveHero;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnMen;
	private JRadioButton rdbtnWomen;
	private JButton btnNowaProfesja;
	private JList<Bohater> list;
	
	
	private DefaultListModel<Bohater> listaBohaterow = new DefaultListModel<Bohater>();
	private Bohater nowyBohater;
	private ArrayList<Rasa> listaRas;
	public ArrayList<Talent> listaTalentow;	
	private ArrayList<Profesja> listaProfesji;
	private ArrayList<Profesja> profesjePierwszyPoziom;
	private ArrayList<Umiejetnosc> listaUm;
	private Profesja nowaProfesja;
	private JButton btnExportToPdf;
	private JButton btnNPC;

	

	
	
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
		
		/*
		 * tworzenie potwora / NPCa
		 */
		btnNPC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO - kwestia zapisania NPCa, nie otwierania wielu kart na raz, plus jeszcze nie wiem co
				NpcModel npcModel = new NpcModel();
				NpcKontroler kontroler = new NpcKontroler(npcModel);
			}
		});
		
		list.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				//btnExportToPdf.setEnabled(false);
				
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nowyBohater = new Bohater(listaBohaterow.elementAt(list.getSelectedIndex()));
				textArea.setText(nowyBohater.wyswietlBohatera(chckbxShowTalents.isSelected()));
				btnExportToPdf.setEnabled(true);
			}
		});

		
		btsSaveHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Bohater nowy = new Bohater(nowyBohater);
				listaBohaterow.addElement(nowy);
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
				try {
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
					
					btnNowaProfesja.setEnabled(false);
				} catch (Exception e2) {
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e2.printStackTrace(pw);
					String sStackTrace = sw.toString(); // stack trace as a string
					textArea.append(sStackTrace);
				}

			}
		});
		
		/////////////////////////////////////////////////////////////////////////
		//podniesienie poziomu we wczeœniej wygenerowanym bohaterze
		/////////////////////////////////////////////////////////////////////////				
		btnPodniesPoziomPr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				Profesja profesjaNowyPoziom = null;
				/*
				 * wgranie akutalnej nazwy profesji do stringa
				 * wgranie aktualnego poziomu aktualnie wybranej klasy postaci
				 */
				String nazwaProfesji = nowyBohater.getProfesjaNameMain();
				int poziomProfesji = nowyBohater.getCurrentProfPoziom()+1;
				
				if(poziomProfesji>4)
				{
					if(!nowyBohater.getProfesjaUkonczona())
					{
					int potwierdznie = JOptionPane.showConfirmDialog(null, "Postaæ osi¹gne³a maksymalny poziom profesji,czy chcesz aby \"ukoñczy³a\" ten poziom?", "Koks", JOptionPane.YES_NO_OPTION);
						if(potwierdznie == JOptionPane.OK_OPTION)
							{
							nowyBohater.ukonczPoziomProfesji(5);
							textArea.setText(nowyBohater.wyswietlBohatera(chckbxShowTalents.isSelected()));
							}
						
					}else {
						JOptionPane.showMessageDialog(null, "Postaæ osi¹gnê³a maksymalny poziom, wybierz inn¹ profesjê jeœli dalej chcesz rozwijaæ postaæ!", "Maksymalny poziom", JOptionPane.INFORMATION_MESSAGE);
					}
				}else {
					
							
				/*
				 * TODO - w tym miejsu zmieniæ automatyczne podnoszenie wczesniej wybranej profesji
				 * i dodanie opcji, ¿e w przypadku juz posiadania rozwiniec danej profesji to podnosi o jeden
				 * oczywiscie z uwaglêdnieniem tego ¿e maks to 4
				 */
				
				int testCbBoxa = cbProfesja.getSelectedIndex();
				//sprawdzenie czy s¹ obiekty z CB profesje, je¿eli s¹, to idziemy dalej
				if(testCbBoxa != -1)
				{
					//wczytanie wybranej profesji (lvl1) do zmiennej
					profesjaNowyPoziom = (Profesja) cbProfesja.getSelectedItem();
					

						
						//sprawdzenie historii bohatera czy ta profesja juz nie by³a wczesniej rozwijana
						int sprawdzenieHistoriiProfesji = nowyBohater.sprawdzHistorieProfesji(profesjaNowyPoziom);
						System.out.println("Sprawdzenie poziomu profesji " + sprawdzenieHistoriiProfesji );
						if(sprawdzenieHistoriiProfesji != -1)
						{
							/*
							 * jeœli nie by³a to przypisujemy nowy poziom profesji jaka ma byæ wyszukana do rozwiniecia, plus nazwa tej profesji, przy za³o¿eniu
							 * ¿e poziom nie jest wy¿szy od 4 
							 */
							
							if((sprawdzenieHistoriiProfesji+1) <5)
							{
								System.out.println("Sprawdzenie poziomu 2 = " + sprawdzenieHistoriiProfesji);
								nazwaProfesji = profesjaNowyPoziom.toString();
								poziomProfesji = sprawdzenieHistoriiProfesji+1;
								System.out.println("Sprawdzenie poziomu 3 (nazwa profesji) = " + nazwaProfesji);
								System.out.println("Sprawdzenie poziomu 3 (poziom profesji) = " + poziomProfesji);
							}else {
								JOptionPane.showMessageDialog(null, "Wybrana profesja posiada ju¿ maksymlany poziom, postaæ awansuje we wczeœniej wybranej profesji", "Maksymalny poziom wybranej profesjii!!", JOptionPane.INFORMATION_MESSAGE);
							}								
							System.out.println("Poziom tej samej profesji z najwy¿szym poziomem z historii to  = " + sprawdzenieHistoriiProfesji);
							
						}
				}
				
				
				for(Profesja p: listaProfesji)
				{
					if(p.toString().equals(nazwaProfesji) && (p.getPoziom() == poziomProfesji))
					{
						profesjaNowyPoziom = new Profesja(p);
					}
				}
				
						
				if(profesjaNowyPoziom != null)
				{
				nowyBohater.nowaProfesja(profesjaNowyPoziom);
				
				int opcjaDoswiadczenia = cbDoswiadczenie.getSelectedIndex();
				nowyBohater.doswiadczenieBohatera(opcjaDoswiadczenia);
				


				textArea.setText(nowyBohater.wyswietlBohatera(chckbxShowTalents.isSelected()));
				}
				}//koniec else
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
				
				textArea.append(wybor.toString()+"\n");
				if(btnNowaProfesja.isEnabled()) {
					btnNowaProfesja.setEnabled(false);
				}
			}
		});
		

		/*
		 * dodanie nowej profesji do istniej¹cego bohatera, który zaczyna³ z inn¹ profesj¹
		 */

		btnNowaProfesja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//pytanie czy wczesniejsza sciezka ma byæ ukoñczona
				if(!nowyBohater.getProfesjaUkonczona())
				{
					int potwierdzenie = JOptionPane.showConfirmDialog(null, "Czy aktualny poziom profesji ma byæ ukoñczony przed zmian¹ profesji?", "Zmiana profesji!", JOptionPane.YES_NO_OPTION);
					if(potwierdzenie == JOptionPane.OK_OPTION) 
					{
						nowyBohater.ukonczPoziomProfesji(nowyBohater.getCurrentProfPoziom()+1);
					}
				}

				
				int sprawdzHistorieProfesji = nowyBohater.sprawdzHistorieProfesji(nowaProfesja);
				
				if(sprawdzHistorieProfesji == -1) {
					nowyBohater.nowaProfesja(nowaProfesja);
					int opcjaDoswiadczenia = cbDoswiadczenie.getSelectedIndex();
					nowyBohater.doswiadczenieBohatera(opcjaDoswiadczenia);
					//wyswietlenie nowego bohatera
					textArea.setText(nowyBohater.wyswietlBohatera(chckbxShowTalents.isSelected()));
					btnNowaProfesja.setEnabled(false);
				}
				
				if(!btnPodniesPoziomPr.isEnabled())
					btnPodniesPoziomPr.setEnabled(true);

			}
		});
		

		
		cbProfesja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if (btnPodniesPoziomPr.isEnabled()) 
				//{
					
					if(nowyBohater!=null) 
					{
					/*
					 * sprawdzamy czy combo box profesje jest wybrany, bo by³y problemy gdy postaæ by³a utworzona i zmienia³o siê rasê...
					 */
					int select = cbProfesja.getSelectedIndex();
					System.out.println("Kod selected item = " + select);
					if(select >= 0) {
						nowaProfesja = (Profesja) cbProfesja.getSelectedItem();

						select = nowyBohater.sprawdzHistorieProfesji(nowaProfesja);
						if(nowaProfesja.toString().equals(nowyBohater.getCurrentProfesjaName()) || select > 0)
						{
							btnNowaProfesja.setEnabled(false);
						}
						else {
							btnNowaProfesja.setEnabled(true);
							}
					}
						
					}//koniec if(nowy bohater...
					
				//}
			}
			
		});
		
		/*
		 * Eksport utworzonego bohatera do pliku pdf
		 */
		btnExportToPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(urlSavaPdf==null)
				{
					JFileChooser dialogFolder = new JFileChooser();
					dialogFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = dialogFolder.showOpenDialog(getParent());
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						urlSavaPdf = dialogFolder.getSelectedFile().getAbsolutePath()+"\\";
					}
				}
				
				try {
					new ExportToPdf(nowyBohater,urlSavaPdf);	
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
				
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
		btnNowyBohater.setIcon(new ImageIcon(NewGui.class.getResource("/resources/knight (1).png")));
		btnNowyBohater.setSelectedIcon(null);
		btnNowyBohater.setToolTipText("Utw\u00F3rz nowego bohatera.\r\nJe\u017Celi nie wybra\u0142e\u015B rasy ani profesji, bohater zostanie utworzony\r\nzasad z podr\u0119cznika z szans\u0105 na ras\u0119 i profesj\u0119. ");
		
		WczytajTalenty();
		//do wczytania z pliku jar
		//WczytajTalentyStream();
		
		cbRasa = new JComboBox<Object>(WczytajRasy());

		cbRasa.setToolTipText("Wybierz ras\u0119, je\u017Celi nie chcesz aby by\u0142a ona losowa.");
		
		cbProfesja = new JComboBox<Profesja>();



		cbProfesja.setToolTipText("Wybierz profesj\u0119 dla bohatera.");
		
		WczytajProfesje();
		
		cbDoswiadczenie = new JComboBox<String>();
		cbDoswiadczenie.setModel(new DefaultComboBoxModel<String>(new String[] {"Brak", "Pocz\u0105tkuj\u0105ca", "\u015Arednio zaawansowana", "Do\u015Bwiadczona"}));
		cbDoswiadczenie.setToolTipText("Czy posta\u0107 posiada ju\u017C jakie\u015B rozwini\u0119cia. W zale\u017Cno\u015Bci od wybranej opcji, posta\u0107 otrzymuje  (3,5,7) rozwini\u0119\u0107 (umiej\u0119tno\u015Bci, cechy klasowe lub talent).\r\n");
		
		
		btnPodniesPoziomPr = new JButton("Podnie\u015B poziom");
		btnPodniesPoziomPr.setIcon(new ImageIcon(NewGui.class.getResource("/resources/crossbow.png")));

		btnPodniesPoziomPr.setToolTipText("Posta\u0107 awansuje na nast\u0119pny poziom rozwoju swojej \u015Bcie\u017Cki profesji.\r\nJe\u017Celi poziom cech lub przynajmniej o\u015Bmiu umiej\u0119tno\u015Bci jest zbyt niski, to s\u0105 one automoatyczne podnoszone do wymaganego poziomu (aby uko\u0144czy\u0107 dany poziom profesji).");
		btnPodniesPoziomPr.setEnabled(false);

		
		chckbxShowTalents = new JCheckBox("Wy\u015Bwietl talenty");
		chckbxShowTalents.setSelected(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		btsSaveHero = new JButton("Zapisz posta\u0107");
		btsSaveHero.setIcon(new ImageIcon(NewGui.class.getResource("/resources/save.png")));
		btsSaveHero.setEnabled(false);

		
		scrlPaneLista = new JScrollPane();
		scrlPaneLista.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		rdbtnMen = new JRadioButton("M\u0119\u017Cczyzna");
		rdbtnMen.setToolTipText("P\u00F3ki co nie chce mi si\u0119 zmienia\u0107 \r\nopis\u00F3w nazw profesji aby by\u0142y adekwatne\r\ndo wybranej p\u0142ci. Mo\u017Ce kiedy\u015B....");
		rdbtnMen.setSelected(true);
		buttonGroup.add(rdbtnMen);
		
		rdbtnWomen = new JRadioButton("Kobieta");
		rdbtnWomen.setToolTipText("P\u00F3ki co nie chce mi si\u0119 zmienia\u0107 \r\nopis\u00F3w nazw profesji aby by\u0142y adekwatne\r\ndo wybranej p\u0142ci. Mo\u017Ce kiedy\u015B....");
		buttonGroup.add(rdbtnWomen);
		
		btnNowaProfesja = new JButton("Nowa profesja");
		btnNowaProfesja.setIcon(new ImageIcon(NewGui.class.getResource("/resources/wizard.png")));
		btnNowaProfesja.setToolTipText("Dodaj now\u0105 porfesj\u0119 do aktualnie tworzonego bohatera.\r\nNowa profesja zawsze zaczyna si\u0119 od pierwszego poziomu.");

		btnNowaProfesja.setEnabled(false);
		
		btnExportToPdf = new JButton("Zapisz do PDF");
		btnExportToPdf.setEnabled(false);

		btnExportToPdf.setIcon(new ImageIcon(NewGui.class.getResource("/resources/document.png")));
		
		JLabel lblLabelRasa = new JLabel("Rasa:");
		
		JLabel lblLabelProfesja = new JLabel("Profesja:");
		lblLabelProfesja.setDisplayedMnemonic(KeyEvent.VK_ENTER);
		
		btnNPC = new JButton("Utw\u00F3rz NPCa");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 745, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnNPC, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblLabelRasa)
								.addComponent(cbRasa, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNowaProfesja)
								.addComponent(btnPodniesPoziomPr, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnNowyBohater)
								.addComponent(lblLabelProfesja)
								.addComponent(cbProfesja, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
								.addComponent(btsSaveHero, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnExportToPdf, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(24)
							.addComponent(scrlPaneLista, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(529)
							.addComponent(chckbxShowTalents)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnMen)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnWomen)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbDoswiadczenie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrlPaneLista, GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblLabelRasa)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cbRasa, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(10)
								.addComponent(lblLabelProfesja)
								.addGap(7)
								.addComponent(cbProfesja, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(20)
								.addComponent(btnNowyBohater)
								.addGap(12)
								.addComponent(btnPodniesPoziomPr)
								.addGap(12)
								.addComponent(btnNowaProfesja)
								.addGap(13)
								.addComponent(btsSaveHero)
								.addGap(12)
								.addComponent(btnExportToPdf)
								.addGap(389)
								.addComponent(btnNPC))))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxShowTalents)
						.addComponent(rdbtnMen)
						.addComponent(rdbtnWomen)
						.addComponent(cbDoswiadczenie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
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
	
	/*
	 * wersja do wczytania z pliku .JAR
	 */
	public void WczytajTalentyStream(){
		
		try{
				listaTalentow = new ArrayList<Talent>();
				ClassLoader classLoader2 = getClass().getClassLoader();
				InputStream inputStream2 = classLoader2.getResourceAsStream("resources/talenty.txt");
				InputStreamReader strumien = new InputStreamReader(inputStream2);
				BufferedReader czytajBuf = new BufferedReader(strumien);
				String wiersz = null;
				
				while((wiersz = czytajBuf.readLine()) !=null){
					if(wiersz.length()==0)
						break;
					tworzTalent(wiersz);
				}
				czytajBuf.close();
				//wyswietlTalentyWszystkie();
		}catch(Exception ex){
			textArea.append(ex.toString());
			ex.printStackTrace();
			System.out.println("Talenty nie wczytane!!");
		}
}//koniec metody
	
	public Object[] WczytajRasy(){
		
		try{
			listaRas = new ArrayList<Rasa>();
			/*
			ClassLoader classLoader2 = getClass().getClassLoader();
			InputStream inputStream2 = classLoader2.getResourceAsStream("resources/rasy.txt");
			InputStreamReader czytaj = new InputStreamReader(inputStream2);
			/*/
			String urlRasy = "../GeneratorBohatera/src/resources/rasy.txt";
			File plik = new File(urlRasy);
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
						Umiejetnosc tempUm = new Umiejetnosc(doZapisaniaUm[0], Integer.parseInt(doZapisaniaUm[1]), doZapisaniaUm[2],0,false);
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
			textArea.append(ex.toString());
			ex.printStackTrace();
		}
		
		return listaRas.toArray();
		
}//koniec metody
	
	public void WczytajProfesje(){
		
		
		try{
			listaProfesji = new ArrayList<Profesja>();
			/*
			ClassLoader classLoader2 = getClass().getClassLoader();
			InputStream inputStream2 = classLoader2.getResourceAsStream("resources/profesje.txt");
			InputStreamReader czytaj = new InputStreamReader(inputStream2);
			*/
			String urlProfesja = "../GeneratorBohatera/src/resources/profesje.txt";
			File plik = new File(urlProfesja);
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
						Umiejetnosc tempUm = new Umiejetnosc(doZapisaniaUm[0], Integer.parseInt(doZapisaniaUm[1]), doZapisaniaUm[2],0,false);
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
				
				Profesja prof = new Profesja(nazwaProfesji, sciezkaProfesji, poziomProfesji, umiej,listaZnTalentow,dostepneRasy,cechyRozwoju,false );
				listaProfesji.add(prof);

			}
			czytajB.close();
			//wyswietlProfesjeWszystkoPrzycisk();
			btnNowyBohater.setEnabled(true);
			
			
		}catch(Exception ex){
			textArea.append(ex.toString());
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
	
	/*
	 * wyszukanie profesji z konkretnym poziomem
	 * zabezpieczyc przed podaniemi poziomu wyzszego niz 4!
	 */
	public Profesja getProfesjaWybranyPoziom(String nazwaP, int poziom) {
		Profesja nowa = new Profesja();
		for(Profesja pr: listaProfesji) {
			if(pr.toString().equals(nazwaP) && pr.getPoziom()==poziom) {
				nowa = new Profesja(pr);
			}		
		}
		return nowa;
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
		Umiejetnosc um = new Umiejetnosc(wynik[0], Integer.parseInt(wynik[1]),wynik[2],0,false);
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
