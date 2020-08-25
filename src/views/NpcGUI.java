package views;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import npcGenerator.CechyPotworow;
import npcGenerator.KontrolerInterface;
import npcGenerator.Potwory;
import javax.swing.border.EtchedBorder;
import java.awt.Rectangle;
import javax.swing.SwingConstants;

public class NpcGUI extends JFrame implements KeyListener{


	/**
	 * 
	 */
	private static final long serialVersionUID = -662614772020108489L;
	KontrolerInterface kontroler;
	private JPanel contentPane;
	private JButton btnDodaj;
	private JButton btnZapisz;
	private JSpinner spinner_0;
	private JSpinner spinner_1;
	private JSpinner spinner_2;
	private JSpinner spinner_3;
	private JSpinner spinner_4;
	private JSpinner spinner_5;
	private JSpinner spinner_6;
	private JSpinner spinner_7;
	private JSpinner spinner_8;
	private JSpinner spinner_9;
	private JSpinner spinner_10;
	private JSpinner spinner_11;
	private JPanel panelNazwa;
	private JList<Object> listCechy;
	private JList<Object> listOpcjonalne;
	private JTextArea textAreaOpis;
	private JComboBox<Object> cbWyborNPC;
	private JLabel lblNazwa;
	private JCheckBox chckbxPowCechyStworzen;
	private JButton btnUsun;
	Potwory potwor;
	private JPopupMenu popupMenu;
	private JMenuItem popMenuZmienCeche;
	Font fontCaslon;


		
	
	/**
	 * Create the frame.
	 */
	public NpcGUI(KontrolerInterface kontroler) {
		this.kontroler = kontroler;
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		initComponents();
		createEvents();
		
	}


	private void createEvents() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				requestFocusInWindow();
			}
		});


		/**
		 * Menu po ppm w oknie z cechami
		 */
		popMenuZmienCeche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!listCechy.isSelectionEmpty()) {
					CechyPotworow cechyP = (CechyPotworow) listCechy.getModel().getElementAt(listCechy.getSelectedIndex());
					String nowaNazwa = JOptionPane.showInputDialog("Podaj now¹ nazwê cechy lub zmieñ j¹", cechyP.toString());
					if(nowaNazwa != null) {
						while(nowaNazwa.isEmpty())
						{
							nowaNazwa = JOptionPane.showInputDialog("Podaj now¹ nazwê cechy to pole nie mo¿e byæ puste!!", cechyP.toString());		
						}
						kontroler.zmienNazweCechy(cechyP.getNazwa(), nowaNazwa);
					}
					
				}
				
			}
		});
		
		/**
		 * Zapisanie konkretnej postaci NPC
		 */
		btnZapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String opis = JOptionPane.showInputDialog("Zmieñ lub dodaj coœ do nazwy potwora.", lblNazwa.getText());
				kontroler.zapiszPostac(opis);
			}
		});
		/*
		 * wybór konkretnego NPCa
		 */
		cbWyborNPC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				potwor = (Potwory) cbWyborNPC.getSelectedItem();
				if(chckbxPowCechyStworzen.isSelected())
					kontroler.setPotwora(potwor, true);
				else {
					kontroler.setPotwora(potwor, false);
				}
				//dodajPotworaDoGui(potwor);
			}


		});
		/*
		 * zaznaczenie cechy w oknie
		 */
		listCechy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CechyPotworow cechyP = (CechyPotworow) listCechy.getModel().getElementAt(listCechy.getSelectedIndex());
				textAreaOpis.setText(cechyP.getOpis());
			}

		});
		
		/*
		 * zaznaczenie cechy opcjonalnej
		 */
		listOpcjonalne.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CechyPotworow cechyOpcjaOpis = (CechyPotworow) listOpcjonalne.getModel().getElementAt(listOpcjonalne.getSelectedIndex());
				textAreaOpis.setText(cechyOpcjaOpis.getOpis());
			}
		});
		/*
		 * dodanie cechyOpcjonalnej do g³ownych i ca³a z tym zwi¹zana zabawa
		 */
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listOpcjonalne.isSelectionEmpty())
				{
					CechyPotworow cOpcja = (CechyPotworow) listOpcjonalne.getModel().getElementAt(listOpcjonalne.getSelectedIndex());
					kontroler.dodajCeche(cOpcja.toString());
				}
			}
		});
		/*
		 * usuniêcie cechy g³ownej i przesuniêcie jej do opcjonalnych
		 */
		btnUsun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listCechy.isSelectionEmpty()) 
				{
					CechyPotworow cOpcja = (CechyPotworow) listCechy.getModel().getElementAt(listCechy.getSelectedIndex());
					kontroler.usunCeche(cOpcja.toString());
				}
				
			}
		});
		
		/*
		 * obs³uga zdarzeñ na spinerach :-(
		 */
		spinner_0.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int ile = (int) spinner_0.getModel().getValue();
				kontroler.zmianaStatystyki(ile, 0);
			}
		});
		
		spinner_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int ile = (int) spinner_1.getModel().getValue();
				kontroler.zmianaStatystyki(ile, 1);
			}
		});
		
		spinner_2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int ile = (int) spinner_2.getModel().getValue();
				kontroler.zmianaStatystyki(ile, 2);
			}
		});
		
		spinner_3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int ile = (int) spinner_3.getModel().getValue();
				kontroler.zmianaStatystyki(ile, 3);
			}
		});
		
		spinner_4.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int ile = (int) spinner_4.getModel().getValue();
				kontroler.zmianaStatystyki(ile, 4);
			}
		});
		
		spinner_5.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int ile = (int) spinner_5.getModel().getValue();
				kontroler.zmianaStatystyki(ile, 5);
			}
		});
	
		spinner_6.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int ile = (int) spinner_6.getModel().getValue();
				kontroler.zmianaStatystyki(ile, 6);
			}
		});
		
		spinner_7.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int ile = (int) spinner_7.getModel().getValue();
				kontroler.zmianaStatystyki(ile, 7);
			}
		});
		
		spinner_8.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int ile = (int) spinner_8.getModel().getValue();
				kontroler.zmianaStatystyki(ile, 8);
			}
		});
		
		spinner_9.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int ile = (int) spinner_9.getModel().getValue();
				kontroler.zmianaStatystyki(ile, 9);
			}
		});
		
		spinner_10.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int ile = (int) spinner_10.getModel().getValue();
				kontroler.zmianaStatystyki(ile, 10);
			}
		});
		
		spinner_11.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int ile = (int) spinner_11.getModel().getValue();
				kontroler.zmianaStatystyki(ile, 11);
			}
		});
		
	}
	public void dodajPotworaDoGui(Potwory potwor) {
		int [] tab = potwor.getStatyPotwora();
		//dodanie statów do spinnerów
		spinner_0.getModel().setValue(tab[0]);
		spinner_1.getModel().setValue(tab[1]);
		spinner_2.getModel().setValue(tab[2]);
		spinner_3.getModel().setValue(tab[3]);
		spinner_4.getModel().setValue(tab[4]);
		spinner_5.getModel().setValue(tab[5]);
		spinner_6.getModel().setValue(tab[6]);
		spinner_7.getModel().setValue(tab[7]);
		spinner_8.getModel().setValue(tab[8]);
		spinner_9.getModel().setValue(tab[9]);
		spinner_10.getModel().setValue(tab[10]);
		spinner_11.getModel().setValue(tab[11]);
		//nazwa
		lblNazwa.setText(potwor.getNazwa());
		//cechy
		listCechy.setModel(new DefaultComboBoxModel<Object>(potwor.getCechy().toArray()));
		//cechy opcjonalne
		listOpcjonalne.setModel(new DefaultComboBoxModel<Object>(potwor.getCechyOpcjonalne().toArray()));
		//ustawienie opisu 
		textAreaOpis.setText(potwor.getOpisStwora());

		
	}

	public void setComboBox(Object [] obj) {
		cbWyborNPC.setModel(new DefaultComboBoxModel<Object>(obj));
		potwor = (Potwory) cbWyborNPC.getItemAt(0);
		kontroler.setPotwora(potwor, false);
	}
	
	public void enableComponents() {
		spinner_0.setEnabled(true);
		spinner_1.setEnabled(true);
		spinner_2.setEnabled(true);
		spinner_3.setEnabled(true);
		spinner_4.setEnabled(true);
		spinner_5.setEnabled(true);
		spinner_6.setEnabled(true);
		spinner_7.setEnabled(true);
		spinner_8.setEnabled(true);
		spinner_9.setEnabled(true);
		spinner_10.setEnabled(true);
		spinner_11.setEnabled(true);
		listCechy.setEnabled(true);
		listOpcjonalne.setEnabled(true);
		
	}


	private void initComponents() {
		setTitle("Tworzenie NPC");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 620);
		contentPane = new JPanel();


		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panelCechy = new JPanel();
		
		cbWyborNPC = new JComboBox<Object>();

		
		panelNazwa = new JPanel();
		
		JScrollPane scrollPaneCechy = new JScrollPane();
		scrollPaneCechy.setEnabled(false);
		InputStream inputFont = NpcGUI.class.getClassLoader().getResourceAsStream("CaslonAntiquePolskieZnaki.ttf");
		try {
			fontCaslon = Font.createFont(Font.TRUETYPE_FONT, inputFont);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(fontCaslon);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel lblCechyPotwora = new JLabel("Cechy Potwora:");
		lblCechyPotwora.setFont(new Font("Caslon Antique", Font.PLAIN, 15));
		
		JLabel lblOpcjonalneCechyPotwora = new JLabel("Opcjonalne Cechy:");
		lblOpcjonalneCechyPotwora.setFont(new Font("Caslon Antique", Font.PLAIN, 15));
		
		JScrollPane scrollPaneOpcjonalneCechy = new JScrollPane();
		scrollPaneOpcjonalneCechy.setEnabled(false);
		
		JLabel lblOpis = new JLabel("Opis:");
		lblOpis.setFont(new Font("Caslon Antique", Font.PLAIN, 16));
		
		JScrollPane scrollPaneOpis = new JScrollPane();
		scrollPaneOpis.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneOpis.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		btnDodaj = new JButton("Dodaj");

		btnDodaj.setIcon(new ImageIcon(NpcGUI.class.getResource("/items/move.png")));
		
		btnUsun = new JButton("Usu\u0144");

		btnUsun.setIcon(new ImageIcon(NpcGUI.class.getResource("/items/move (1).png")));
		
		JLabel lblwybierzPotwora = new JLabel("Wybierz potwora");
		lblwybierzPotwora.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		btnZapisz = new JButton("Zapisz");
		btnZapisz.setToolTipText("Zapisz posta\u0107 NPC i zamknij okno.");

		btnZapisz.setFont(new Font("Caslon Antique", Font.PLAIN, 20));
		
		chckbxPowCechyStworzen = new JCheckBox("Powszechne Cechy Stworze\u0144");
		chckbxPowCechyStworzen.setToolTipText("Zaznacz, je\u015Bli chcesz aby w opcjonalnych cechach by\u0142y dost\u0119pne dodatkowe cechy stworze\u0144!");
		chckbxPowCechyStworzen.setFont(new Font("Caslon Antique", Font.PLAIN, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelCechy, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnZapisz, Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblwybierzPotwora, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cbWyborNPC, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE))
								.addComponent(chckbxPowCechyStworzen)
								.addComponent(lblCechyPotwora, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblOpis, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnDodaj, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
								.addComponent(scrollPaneCechy, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnUsun, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblOpcjonalneCechyPotwora, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(panelNazwa, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
									.addComponent(scrollPaneOpcjonalneCechy, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE))))
						.addComponent(scrollPaneOpis, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE))
					.addGap(0))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelCechy, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblwybierzPotwora, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
										.addComponent(cbWyborNPC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(chckbxPowCechyStworzen))
								.addComponent(panelNazwa, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCechyPotwora, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblOpcjonalneCechyPotwora, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPaneCechy, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
								.addComponent(scrollPaneOpcjonalneCechy, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblOpis, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnUsun)
									.addComponent(btnDodaj)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPaneOpis, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
					.addGap(2)
					.addComponent(btnZapisz))
		);
		
		textAreaOpis = new JTextArea();
		textAreaOpis.setBounds(new Rectangle(2, 2, 2, 2));
		textAreaOpis.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textAreaOpis.setWrapStyleWord(true);
		textAreaOpis.setLineWrap(true);
		textAreaOpis.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		scrollPaneOpis.setViewportView(textAreaOpis);
		
		listOpcjonalne = new JList<Object>();
		listOpcjonalne.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOpcjonalne.setEnabled(false);

		listOpcjonalne.setFont(new Font("Caslon Antique", Font.PLAIN, 15));
		scrollPaneOpcjonalneCechy.setViewportView(listOpcjonalne);
		
		listCechy = new JList<Object>();


		listCechy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listCechy.setEnabled(false);

		listCechy.setFont(new Font("Caslon Antique", Font.PLAIN, 15));
		scrollPaneCechy.setViewportView(listCechy);
		
		popupMenu = new JPopupMenu();
		addPopup(listCechy, popupMenu);
		
		popMenuZmienCeche = new JMenuItem("Zmie\u0144 opis cechy potwora");

		popMenuZmienCeche.setFont(new Font("Caslon Antique", Font.PLAIN, 15));
		popupMenu.add(popMenuZmienCeche);
		
	
		
		
		
		lblNazwa = new JLabel("Nazwa Potwora");
		lblNazwa.setFont(new Font("Caslon Antique", Font.PLAIN, 20));
		panelNazwa.add(lblNazwa);
		
		JLabel lblSz = new JLabel("Sz");
		lblSz.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		JLabel lblWW = new JLabel("WW");
		lblWW.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		spinner_0 = new JSpinner();
		spinner_0.setEnabled(false);

		spinner_0.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner_0.setModel(new SpinnerNumberModel(0, 0, 19, 1));
		
		spinner_1 = new JSpinner();

		spinner_1.setEnabled(false);
		spinner_1.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner_1.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		
		JLabel lblUs = new JLabel("US");
		lblUs.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		spinner_2 = new JSpinner();

		spinner_2.setEnabled(false);
		spinner_2.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner_2.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		
		JLabel lblSila = new JLabel("S");
		lblSila.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		spinner_3 = new JSpinner();

		spinner_3.setEnabled(false);
		spinner_3.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner_3.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		
		JLabel lblWytrzymalosc = new JLabel("Wt");
		lblWytrzymalosc.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		spinner_4 = new JSpinner();

		spinner_4.setEnabled(false);
		spinner_4.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner_4.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		
		JLabel lblInicjatywa = new JLabel("I");
		lblInicjatywa.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		spinner_5 = new JSpinner();

		spinner_5.setEnabled(false);
		spinner_5.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner_5.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		
		JLabel lblZwinnosc = new JLabel("Zw");
		lblZwinnosc.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		JLabel lblZrecznosc = new JLabel("Zr");
		lblZrecznosc.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		JLabel lblInteligencja = new JLabel("Int");
		lblInteligencja.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		JLabel lblSilaWoli = new JLabel("SW");
		lblSilaWoli.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		JLabel lblOglada = new JLabel("Ogd");
		lblOglada.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		JLabel lblZywotnosc = new JLabel("\u017Byw");
		lblZywotnosc.setFont(new Font("Caslon Antique", Font.PLAIN, 17));
		
		spinner_6 = new JSpinner();

		spinner_6.setEnabled(false);
		spinner_6.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner_6.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		
		spinner_7 = new JSpinner();

		spinner_7.setEnabled(false);
		spinner_7.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner_7.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		
		spinner_8 = new JSpinner();

		spinner_8.setEnabled(false);
		spinner_8.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner_8.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		
		spinner_9 = new JSpinner();

		spinner_9.setEnabled(false);
		spinner_9.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner_9.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		
		spinner_10 = new JSpinner();

		spinner_10.setEnabled(false);
		spinner_10.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner_10.setModel(new SpinnerNumberModel(0, 0, 200, 1));
		
		spinner_11 = new JSpinner();

		spinner_11.setEnabled(false);
		spinner_11.setFont(new Font("Dialog", Font.PLAIN, 17));
		spinner_11.setModel(new SpinnerNumberModel(0, 0, 500, 1));
		GroupLayout gl_panelCechy = new GroupLayout(panelCechy);
		gl_panelCechy.setHorizontalGroup(
			gl_panelCechy.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCechy.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addGroup(gl_panelCechy.createParallelGroup(Alignment.LEADING)
								.addComponent(lblWW, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSz))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelCechy.createParallelGroup(Alignment.LEADING, false)
								.addComponent(spinner_0)
								.addComponent(spinner_1)))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblUs, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblSila, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblWytrzymalosc, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblInicjatywa, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblZwinnosc, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblZrecznosc, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblInteligencja, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblSilaWoli, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblOglada, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblZywotnosc, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_panelCechy.setVerticalGroup(
			gl_panelCechy.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCechy.createSequentialGroup()
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSz)
						.addComponent(spinner_0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWW)
						.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUs, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSila, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWytrzymalosc, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInicjatywa, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblZwinnosc, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblZrecznosc, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInteligencja, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSilaWoli, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOglada, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblZywotnosc, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner_11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(108, Short.MAX_VALUE))
		);
		panelCechy.setLayout(gl_panelCechy);
		contentPane.setLayout(gl_contentPane);
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}


		});
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Naciœniêcie Escape na klawiaturze usuwa focus z okien z listami cech
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			listCechy.clearSelection();
			listOpcjonalne.clearSelection();
		}

	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}
