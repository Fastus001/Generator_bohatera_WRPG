package views;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Toolkit;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import commons.Bohater;
import commons.Profesja;
import commons.Rasa;
import mvcOknoGlowne.GenBohKontrolerInterface;
import mvcOknoGlowne.GenBohModelInterface;
import mvcOknoGlowne.ObserwatorModel;
import npcGenerator.NpcKontroler;
import npcGenerator.NpcModel;
import npcGenerator.Potwory;

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
import javax.swing.ComboBoxModel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class NewGui extends JFrame implements ObserwatorModel{

	private static final long serialVersionUID = 1L;
	GenBohModelInterface model;
	GenBohKontrolerInterface kontroler;

	//Components
	private JPanel contentPane;
	private JComboBox<Object> cbProfesja;
	private JComboBox<Object> cbRasa;
	private JComboBox<String> cbDoswiadczenie;
	private JButton btnNowyBohater;
	private JButton btnPodniesPoziomPr;
	private JButton btsSaveHero;
	private JButton btnExportToPdf;
	private JButton btnNPC;
	private JButton btnNowaProfesja;
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnMen;
	private JRadioButton rdbtnWomen;
	private JCheckBox chckbxShowTalents;
	private JTextArea textArea;
	private JScrollPane scrlPaneLista;

	private JList<Object> list;
	private DefaultListModel<Object> listaBohaterow = new DefaultListModel<Object>();
	private JButton btnExportExcel;
	
	/**
	 * Create the frame.
	 */
	public NewGui(GenBohKontrolerInterface kontroler, GenBohModelInterface model ) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(NewGui.class.getResource("/items/sledgehammer.png")));
		
		this.kontroler = kontroler;
		this.model = model;
		this.model.zarejestrujObserwatora((ObserwatorModel) this);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		initComponents();
		createEvents();
	}



	private void createEvents() {
		
		/*
		 * tworzenie potwora / NPCa
		 */
		btnNPC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NpcModel npcModel = new NpcModel();
				new NpcKontroler(npcModel, listaBohaterow, NewGui.this);
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
				Object obj = listaBohaterow.elementAt(list.getSelectedIndex());
				if(obj instanceof Bohater) {
					Bohater nBohater = new Bohater((Bohater) listaBohaterow.elementAt(list.getSelectedIndex()));
					textArea.setText(nBohater.wyswietlBohatera(chckbxShowTalents.isSelected()));
					kontroler.aktywujExportDoPdf();
				}else if (obj instanceof Potwory) {
					Potwory nowyBohater = new Potwory((Potwory) listaBohaterow.elementAt(list.getSelectedIndex()));
					textArea.setText(nowyBohater.wyswietl());
					kontroler.wylaczExportDoPdf();
				}

			}
		});

		
		btsSaveHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.zapiszPostac();
			}
		});
		/**
		 * w³¹czenie/wy³¹czenie pokazywania talentów
		 */
		chckbxShowTalents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.opisPostaciTalenty(chckbxShowTalents.isSelected());
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
				model.nowyBohater(cbRasa.getSelectedIndex(), cbProfesja.getSelectedIndex(),cbDoswiadczenie.getSelectedIndex(), rdbtnMen.isSelected(), chckbxShowTalents.isSelected());
				kontroler.aktywujPodniesPoziom();
				kontroler.aktywujZapiszPostac();
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
				model.podniesPoziom(cbDoswiadczenie.getSelectedIndex(), chckbxShowTalents.isSelected());
			}
		});
		
		/////////////////////////////////////////////////////////////////////////
		//je¿eli zostanie wybrana konkretna Rasa, to mo¿liwe jest wyszukanie profesji dostêpnej dla danej rasy
		/////////////////////////////////////////////////////////////////////////
		cbRasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Rasa wybor = (Rasa) cbRasa.getSelectedItem();
				model.setRasa(wybor);
				kontroler.selectRasa(wybor);
			}
		});
		
		cbRasa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ComboBoxModel<Object> cbModel = cbRasa.getModel();
				if(cbModel.getSize() == 0)
					{
						kontroler.setRacaCbBox();
					}
			}
		});
		

		/*
		 * dodanie nowej profesji do istniej¹cego bohatera, który zaczyna³ z inn¹ profesj¹
		 */
		btnNowaProfesja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				model.nowaProfesja(cbDoswiadczenie.getSelectedIndex(), chckbxShowTalents.isSelected(), btnPodniesPoziomPr.isSelected());
			}
		});
		
		cbProfesja.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(cbProfesja.getModel().getSize()== 0)
				{
					
					JOptionPane.showMessageDialog(null, "Najpierw trzeba wybraæ rasê, aby mo¿na by³o wybraæ odpowiedni¹ profesjê.");
				}
			}
		});
	
		cbProfesja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cbProfesja.getModel().getSize()!=0)
				{
					Profesja prof = (Profesja) cbProfesja.getSelectedItem();
					model.setProfesja(prof);
				}
			}
		});
		
		/*
		 * Eksport utworzonego bohatera do pliku pdf
		 */
		btnExportToPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Bohater nBohater = new Bohater((Bohater) listaBohaterow.elementAt(list.getSelectedIndex()));
				model.exportDoPdf(nBohater);
			}
		});
		
		/**
		 * eskport postaci do arkusza Excel
		 */
		btnExportExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.isSelectionEmpty()) {
					int wybor = JOptionPane.showConfirmDialog(null, "Nie zaznaczy³eœ konkretnego rekordu, czy zapisaæ do arkusza Excel wszystkie utworzone postacie?","Eksport?",JOptionPane.YES_NO_OPTION);
					if(wybor == JOptionPane.YES_OPTION) {
						model.exportDoExcel(listaBohaterow.toArray(),0);
					}else {
						
					}
				}else {
					int pozycja = list.getSelectedIndex();
					model.exportDoExcel(listaBohaterow.toArray(),pozycja);
				}
				

			}
		});
	}
	

	
	/**
	 * @param cbRasa the cbRasa to set
	 */
	public void setCbRasa(Object[] cbRasa) {
		this.cbRasa.setModel(new DefaultComboBoxModel<Object>(cbRasa));
	}

	/**
	 * @param cbProfesja the cbProfesja to set
	 */
	public void setCbProfesja(Object[] cbProf) {
		this.cbProfesja.removeAllItems();
		this.cbProfesja.setModel(new DefaultComboBoxModel<Object>(cbProf));
		this.cbProfesja.setEnabled(true);
		if(btnNowaProfesja.isEnabled()) {
			btnNowaProfesja.setEnabled(false);
		}	
	}

	private void initComponents() {
		setTitle("Generator Postaci Warhammer 4ed");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setMargin(new Insets(4, 4, 3, 3));
		textArea.setLineWrap(true);
		textArea.setBounds(new Rectangle(5, 5, 5, 5));
		textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textArea.setFont(new Font("Arial", Font.PLAIN, 12));
	
		btnNowyBohater = new JButton("Nowy Bohater");
		btnNowyBohater.setIcon(new ImageIcon(NewGui.class.getResource("/items/knight (1).png")));
		btnNowyBohater.setSelectedIcon(null);
		btnNowyBohater.setToolTipText("Utw\u00F3rz nowego bohatera.\r\nJe\u017Celi nie wybra\u0142e\u015B rasy ani profesji, bohater zostanie utworzony\r\nzasad z podr\u0119cznika z szans\u0105 na ras\u0119 i profesj\u0119. ");

		cbRasa = new JComboBox<Object>();
		cbRasa.setToolTipText("Wybierz ras\u0119, je\u017Celi nie chcesz aby by\u0142a ona losowa.");
		
		cbProfesja = new JComboBox<Object>();
		cbProfesja.setToolTipText("Wybierz profesj\u0119 dla bohatera.");

		cbDoswiadczenie = new JComboBox<String>();
		cbDoswiadczenie.setModel(new DefaultComboBoxModel<String>(new String[] {"Brak", "Pocz\u0105tkuj\u0105ca", "\u015Arednio zaawansowana", "Do\u015Bwiadczona"}));
		cbDoswiadczenie.setToolTipText("Czy posta\u0107 posiada ju\u017C jakie\u015B rozwini\u0119cia. W zale\u017Cno\u015Bci od wybranej opcji, posta\u0107 otrzymuje  (3,5,7) rozwini\u0119\u0107 (umiej\u0119tno\u015Bci, cechy klasowe lub talent).\r\n");
		
		btnPodniesPoziomPr = new JButton("Podnie\u015B poziom");
		btnPodniesPoziomPr.setIcon(new ImageIcon(NewGui.class.getResource("/items/crossbow.png")));

		btnPodniesPoziomPr.setToolTipText("Posta\u0107 awansuje na nast\u0119pny poziom rozwoju swojej \u015Bcie\u017Cki profesji.\r\nJe\u017Celi poziom cech lub przynajmniej o\u015Bmiu umiej\u0119tno\u015Bci jest zbyt niski, to s\u0105 one automoatyczne podnoszone do wymaganego poziomu (aby uko\u0144czy\u0107 dany poziom profesji).");
		btnPodniesPoziomPr.setEnabled(false);

		chckbxShowTalents = new JCheckBox("Wy\u015Bwietl talenty");
		chckbxShowTalents.setSelected(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		btsSaveHero = new JButton("Zapisz posta\u0107");
		btsSaveHero.setIcon(new ImageIcon(NewGui.class.getResource("/items/save.png")));
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
		btnNowaProfesja.setIcon(new ImageIcon(NewGui.class.getResource("/items/wizard.png")));
		btnNowaProfesja.setToolTipText("Dodaj now\u0105 porfesj\u0119 do aktualnie tworzonego bohatera.\r\nNowa profesja zawsze zaczyna si\u0119 od pierwszego poziomu.");

		btnNowaProfesja.setEnabled(false);
		
		btnExportToPdf = new JButton("Zapisz do PDF");
		btnExportToPdf.setEnabled(false);

		btnExportToPdf.setIcon(new ImageIcon(NewGui.class.getResource("/items/document.png")));
		
		JLabel lblLabelRasa = new JLabel("Rasa:");
		
		JLabel lblLabelProfesja = new JLabel("Profesja:");
		lblLabelProfesja.setDisplayedMnemonic(KeyEvent.VK_ENTER);
		
		btnNPC = new JButton("Utw\u00F3rz NPCa");
		
		btnExportExcel = new JButton("Zapisz do Excel");
		btnExportExcel.setIcon(new ImageIcon(NewGui.class.getResource("/items/excel.png")));
		btnExportExcel.setEnabled(false);


		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 745, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnExportExcel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnNPC, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblLabelRasa)
								.addComponent(cbRasa, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnPodniesPoziomPr, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblLabelProfesja)
								.addComponent(cbProfesja, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
								.addComponent(btsSaveHero, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnExportToPdf, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnNowyBohater, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnNowaProfesja, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(24)
							.addComponent(scrlPaneLista, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
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
						.addComponent(scrlPaneLista, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 691, Short.MAX_VALUE)
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
								.addGap(12)
								.addComponent(btnExportExcel)
								.addGap(354)
								.addComponent(btnNPC))))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxShowTalents)
						.addComponent(rdbtnMen)
						.addComponent(rdbtnWomen)
						.addComponent(cbDoswiadczenie, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		list = new JList<Object>(listaBohaterow);

		scrlPaneLista.setViewportView(list);
		scrollPane.setViewportView(textArea);
		contentPane.setLayout(gl_contentPane);

		
	}
	@Override
	public void aktualizujPostac(String opis) {
		textArea.setText(opis);		
	}
	@Override
	public void wylaczbtnNowaProfesja() {
		btnNowaProfesja.setEnabled(false);
	}
	@Override
	public void wlaczPrzyciskbtnPodniesPoziomPr() {
		btnPodniesPoziomPr.setEnabled(true);
	}
	@Override
	public void wlaczbtnNowaProfesja() {
		btnNowaProfesja.setEnabled(true);
	}
	@Override
	public void wylaczPrzicskPodniesPoziomPr() {
		btnPodniesPoziomPr.setEnabled(false);
	}
	@Override
	public void aktualizujListeBohaterow(Bohater nowy) {
		listaBohaterow.addElement(nowy);
		if(listaBohaterow.size() > 0) {
			kontroler.aktywujExportDoExcel();
		}
			
	}
	public void addToListaBohaterow(Object nowy) {
		listaBohaterow.addElement(nowy);
		if(listaBohaterow.size() > 0) {
			kontroler.aktywujExportDoExcel();
		}
			
	}

	/**
	 * @param btnExportExcel the btnExportExcel to set
	 */
	public void setBtnExportExcelEnabled() {
		this.btnExportExcel.setEnabled(true);
	}



	public void setBtnPodniesPoziomPrEnabled(boolean e) {
		this.btnPodniesPoziomPr.setEnabled(e);
	}
	public void setBtsSaveHeroEnabled(boolean e) {
		this.btsSaveHero.setEnabled( e);
	}

	public void setBtnNowaProfesjaEnabled(Boolean e) {
		this.btnNowaProfesja.setEnabled(e);;
	}

	public void setBtnExportToPdfActive() {
		this.btnExportToPdf.setEnabled(true);;
	}
	public void setBtnExportToPdfInactive() {
		this.btnExportToPdf.setEnabled(false);;
	}

	public boolean getChckbxShowTalents() {
		return chckbxShowTalents.isSelected();
	}
}
