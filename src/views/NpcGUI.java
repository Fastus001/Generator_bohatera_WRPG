package views;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class NpcGUI extends JFrame {

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
	private JList listCechy;
	private JList listOpcjonalne;
	private JTextArea textAreaOpis;
	private JComboBox cbWyborNPC;

	/**
	 * Launch the application.
	 */
	public void run() {
				try {
					NpcGUI frame = new NpcGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
	
	/**
	 * Create the frame.
	 */
	public NpcGUI() {
		
		initComponents();
		createEvents();
		
	}


	private void createEvents() {
		// TODO Auto-generated method stub
		
	}


	private void initComponents() {
		setTitle("Tworzenie NPC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 435);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panelCechy = new JPanel();
		
		cbWyborNPC = new JComboBox();
		
		panelNazwa = new JPanel();
		
		JScrollPane scrollPaneCechy = new JScrollPane();
		
		JLabel lblCechyPotwora = new JLabel("Cechy Potwora:");
		lblCechyPotwora.setFont(new Font("Caslon Antique", Font.PLAIN, 15));
		
		JLabel lblOpcjonalneCechyPotwora = new JLabel("Opcjonalne Cechy:");
		lblOpcjonalneCechyPotwora.setFont(new Font("Caslon Antique", Font.PLAIN, 15));
		
		JScrollPane scrollPaneOpcjonalneCechy = new JScrollPane();
		
		JLabel lblOpis = new JLabel("Opis:");
		lblOpis.setFont(new Font("Caslon Antique", Font.PLAIN, 13));
		
		JScrollPane scrollPaneOpis = new JScrollPane();
		
		btnDodaj = new JButton("Dodaj");
		
		JButton btnUsun = new JButton("Usu\u0144");
		
		JLabel lblwybierzPotwora = new JLabel("Wybierz potwora");
		lblwybierzPotwora.setFont(new Font("Caslon Antique", Font.PLAIN, 15));
		
		btnZapisz = new JButton("Zapisz");
		btnZapisz.setFont(new Font("Caslon Antique", Font.PLAIN, 20));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblwybierzPotwora, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panelCechy, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 96, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(panelNazwa, GroupLayout.PREFERRED_SIZE, 572, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblOpis, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPaneOpis, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(scrollPaneCechy, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblCechyPotwora))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnUsun, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnDodaj, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblOpcjonalneCechyPotwora, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
										.addComponent(scrollPaneOpcjonalneCechy, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(cbWyborNPC, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 500, Short.MAX_VALUE)
							.addComponent(btnZapisz)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panelNazwa, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblOpcjonalneCechyPotwora, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(scrollPaneOpcjonalneCechy, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(btnDodaj)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnUsun))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblCechyPotwora)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(scrollPaneCechy, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblOpis, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPaneOpis, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))
						.addComponent(panelCechy, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblwybierzPotwora, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(cbWyborNPC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addComponent(btnZapisz)))
		);
		
		textAreaOpis = new JTextArea();
		scrollPaneOpis.setViewportView(textAreaOpis);
		
		listOpcjonalne = new JList();
		scrollPaneOpcjonalneCechy.setViewportView(listOpcjonalne);
		
		listCechy = new JList();
		scrollPaneCechy.setViewportView(listCechy);
		
	
		
		
		
		JLabel lblNazwa = new JLabel("Nazwa Potwora");
		lblNazwa.setFont(new Font("Caslon Antique", Font.PLAIN, 20));
		panelNazwa.add(lblNazwa);
		
		JLabel lblSz = new JLabel("Sz");
		lblSz.setFont(new Font("Caslon Antique", Font.PLAIN, 14));
		
		JLabel lblWW = new JLabel("WW");
		lblWW.setFont(new Font("Caslon Antique", Font.PLAIN, 14));
		
		spinner_0 = new JSpinner();
		spinner_0.setModel(new SpinnerNumberModel(0, null, 19, 1));
		
		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(0, 0, 120, 1));
		
		JLabel lblUs = new JLabel("US");
		lblUs.setFont(new Font("Caslon Antique", Font.PLAIN, 14));
		
		spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(0, null, 120, 1));
		
		JLabel lblSila = new JLabel("S");
		lblSila.setFont(new Font("Caslon Antique", Font.PLAIN, 14));
		
		spinner_3 = new JSpinner();
		spinner_3.setModel(new SpinnerNumberModel(0, null, 120, 1));
		
		JLabel lblWytrzymalosc = new JLabel("Wt");
		lblWytrzymalosc.setFont(new Font("Caslon Antique", Font.PLAIN, 14));
		
		spinner_4 = new JSpinner();
		spinner_4.setModel(new SpinnerNumberModel(0, null, 120, 1));
		
		JLabel lblInicjatywa = new JLabel("I");
		lblInicjatywa.setFont(new Font("Caslon Antique", Font.PLAIN, 14));
		
		spinner_5 = new JSpinner();
		spinner_5.setModel(new SpinnerNumberModel(0, null, 120, 1));
		
		JLabel lblZwinnosc = new JLabel("Zw");
		lblZwinnosc.setFont(new Font("Caslon Antique", Font.PLAIN, 14));
		
		JLabel lblZrecznosc = new JLabel("Zr");
		lblZrecznosc.setFont(new Font("Caslon Antique", Font.PLAIN, 14));
		
		JLabel lblInteligencja = new JLabel("Int");
		lblInteligencja.setFont(new Font("Caslon Antique", Font.PLAIN, 14));
		
		JLabel lblSilaWoli = new JLabel("SW");
		lblSilaWoli.setFont(new Font("Caslon Antique", Font.PLAIN, 14));
		
		JLabel lblOglada = new JLabel("Ogd");
		lblOglada.setFont(new Font("Caslon Antique", Font.PLAIN, 14));
		
		JLabel lblZywotnosc = new JLabel("\u017Byw");
		lblZywotnosc.setFont(new Font("Caslon Antique", Font.PLAIN, 14));
		
		spinner_6 = new JSpinner();
		spinner_6.setModel(new SpinnerNumberModel(0, null, 120, 1));
		
		spinner_7 = new JSpinner();
		spinner_7.setModel(new SpinnerNumberModel(0, null, 120, 1));
		
		spinner_8 = new JSpinner();
		spinner_8.setModel(new SpinnerNumberModel(0, null, 120, 1));
		
		spinner_9 = new JSpinner();
		spinner_9.setModel(new SpinnerNumberModel(0, null, 120, 1));
		
		spinner_10 = new JSpinner();
		spinner_10.setModel(new SpinnerNumberModel(0, null, 120, 1));
		
		spinner_11 = new JSpinner();
		spinner_11.setModel(new SpinnerNumberModel(0, null, 120, 1));
		GroupLayout gl_panelCechy = new GroupLayout(panelCechy);
		gl_panelCechy.setHorizontalGroup(
			gl_panelCechy.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCechy.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelCechy.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addGroup(gl_panelCechy.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelCechy.createSequentialGroup()
									.addComponent(lblSz)
									.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
									.addComponent(spinner_0, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelCechy.createSequentialGroup()
									.addComponent(lblWW, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addContainerGap(10, Short.MAX_VALUE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblUs, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblSila, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblWytrzymalosc, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblInicjatywa, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblZwinnosc, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblZrecznosc, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblInteligencja, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblSilaWoli, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblOglada, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panelCechy.createSequentialGroup()
							.addComponent(lblZywotnosc, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinner_11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
					.addContainerGap(24, Short.MAX_VALUE))
		);
		panelCechy.setLayout(gl_panelCechy);
		contentPane.setLayout(gl_contentPane);
		
	}
}
