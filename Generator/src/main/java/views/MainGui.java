package views;

import controllers.MainGuiController;
import domain.Hero;
import hero.HeroDisplay;
import npcGenerator.NpcKontroler;
import npcGenerator.NpcModel;
import npcGenerator.Potwory;
import observers.MainGuiObserver;
import services.HeroService;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;

public class MainGui extends JFrame implements MainGuiObserver {

    private static final long serialVersionUID = 1L;
    HeroService service;
    MainGuiController controller;

	private JComboBox<Object> comboBoxProfession;
    private JComboBox<String> comboBoxRace;
    private JComboBox<String> comboBoxExperience;

    private JButton buttonNewHero;
    private JButton buttonAddLevel;
    private JButton buttonSaveHero;
    private JButton buttonExportToPdf;
	private JButton buttonExportExcel;
	private JButton buttonNPC;
	private JButton buttonNewProfession;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private JRadioButton radioButtonMan;
	private JCheckBox ShowTalentsCheckBox;
	private JTextArea textArea;

	private JList<Object> list;
	private final DefaultListModel<Object> heroList = new DefaultListModel<>();

    public MainGui(MainGuiController controller, HeroService service) {
        setIconImage( Toolkit.getDefaultToolkit().getImage( MainGui.class.getResource( "/items/sledgehammer.png" ) ) );

        this.controller = controller;
        this.service = service;
        this.service.subscribeObserver( this );
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch (Throwable e) {
            e.printStackTrace();
        }
        initComponents();
        createEvents();
    }

    private void createEvents() {
        buttonNPC.addActionListener( e -> {
            NpcModel npcModel = new NpcModel();
            new NpcKontroler( npcModel, heroList, MainGui.this );
        } );

        list.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object obj = heroList.elementAt( list.getSelectedIndex() );
                if ( obj instanceof Hero ) {
                    Hero nBohater = (( Hero ) heroList.elementAt( list.getSelectedIndex() )).toBuilder().build();
                    HeroDisplay heroDisplay = new HeroDisplay( nBohater );
                    textArea.setText( heroDisplay.showHero( ShowTalentsCheckBox.isSelected() ) );
                    controller.activateExportDoPdf();
                }
                else if ( obj instanceof Potwory ) {
                    Potwory nowyBohater = new Potwory( ( Potwory ) heroList.elementAt( list.getSelectedIndex() ) );
                    textArea.setText( nowyBohater.wyswietl() );
                    controller.deactivateExportDoPdf();
                }

            }
        } );

        buttonSaveHero.addActionListener( e -> service.saveHeroToList() );

        ShowTalentsCheckBox.addActionListener( e -> service.showHeroTalents( ShowTalentsCheckBox.isSelected() ) );

        list.setCellRenderer( new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
                if ( renderer instanceof JLabel && value instanceof Hero ) {
                    (( JLabel ) renderer).setText( (value).toString() );
                }
                return renderer;
            }
        } );

        buttonNewHero.addActionListener( e -> {
            service.newHero( comboBoxRace.getSelectedIndex(), comboBoxProfession.getSelectedIndex(),
							 comboBoxExperience.getSelectedIndex(), radioButtonMan.isSelected(),
							 ShowTalentsCheckBox.isSelected() );
            controller.activateLevelUpButton();
            controller.activateSaveHeroButton();
        } );

        buttonAddLevel.addActionListener( e -> service.levelUp( comboBoxExperience.getSelectedIndex(), ShowTalentsCheckBox.isSelected() ) );

        comboBoxRace.addActionListener( e -> {
            String chosenRaceName = ( String ) comboBoxRace.getSelectedItem();
            service.setRace( chosenRaceName );
            controller.selectRace( chosenRaceName );
        } );

        comboBoxRace.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ComboBoxModel<String> cbModel = comboBoxRace.getModel();
                if ( cbModel.getSize() == 0 ) {
                    controller.setRaceComboBox();
                }
            }
        } );

        buttonNewProfession.addActionListener( e -> service.newProfession( comboBoxExperience.getSelectedIndex(), ShowTalentsCheckBox.isSelected(), buttonAddLevel.isSelected() ) );

        comboBoxProfession.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ( comboBoxProfession.getModel().getSize() == 0 ) {
                    JOptionPane.showMessageDialog( null, "Najpierw trzeba wybrać rasę, aby można było wybrać odpowiednią profesję." );
                }
            }
        } );

        comboBoxProfession.addActionListener( e -> {
            if ( comboBoxProfession.getModel().getSize() != 0 ) {
                String prof = ( String ) comboBoxProfession.getSelectedItem();
                service.setProfession( prof );
            }
        } );

        buttonExportToPdf.addActionListener( e -> {
			Hero nBohater = (( Hero ) heroList.elementAt( list.getSelectedIndex() )).toBuilder().build();
			service.exportDoPdf( nBohater );
		} );

        buttonExportExcel.addActionListener( e -> {
			if ( list.isSelectionEmpty() ) {
				int wybor = JOptionPane.showConfirmDialog( null,
			   "Nie zaznaczyłeś konkretnego rekordu, czy zapisać do arkusza Excel wszystkie utworzone postacie?",
				 "Eksport?", JOptionPane.YES_NO_OPTION );
				if ( wybor == JOptionPane.YES_OPTION ) {
					service.exportDoExcel( heroList.toArray(), 0 );
				}
			}else {
				int index = list.getSelectedIndex();
				service.exportDoExcel( heroList.toArray(), index );
			}
		} );
    }

    public void setComboBoxRace(List<String> raceNames) {
        comboBoxRace.setModel( new DefaultComboBoxModel<>( new Vector<>( raceNames ) ) );
    }

    public void setComboBoxProfession(List<String> professionNames) {
        comboBoxProfession.removeAllItems();
        comboBoxProfession.setModel( new DefaultComboBoxModel<>( new Vector<>( professionNames ) ) );
        comboBoxProfession.setEnabled( true );
        if ( buttonNewProfession.isEnabled() ) {
            buttonNewProfession.setEnabled( false );
        }
    }

    private void initComponents() {
        setTitle( "Generator Postaci Warhammer 4ed" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100, 1200, 800 );
		//Components
		JPanel contentPane = new JPanel();
        contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
        setContentPane( contentPane );
        textArea = new JTextArea();
        textArea.setWrapStyleWord( true );
        textArea.setMargin( new Insets( 4, 4, 3, 3 ) );
        textArea.setLineWrap( true );
        textArea.setBounds( new Rectangle( 5, 5, 5, 5 ) );
        textArea.setBorder( new EtchedBorder( EtchedBorder.LOWERED, null, null ) );
        textArea.setFont( new Font( "Arial", Font.PLAIN, 12 ) );

        buttonNewHero = new JButton( "Nowy Bohater" );
        buttonNewHero.setIcon( new ImageIcon( MainGui.class.getResource( "/items/knight (1).png" ) ) );
        buttonNewHero.setSelectedIcon( null );
        buttonNewHero.setToolTipText( "Utw\u00F3rz nowego bohatera.\r\nJe\u017Celi nie wybra\u0142e\u015B rasy ani profesji, bohater zostanie utworzony\r\nzasad z podr\u0119cznika z szans\u0105 na ras\u0119 i profesj\u0119. " );

        comboBoxRace = new JComboBox<>();
        comboBoxRace.setToolTipText( "Wybierz ras\u0119, je\u017Celi nie chcesz aby by\u0142a ona losowa." );

        comboBoxProfession = new JComboBox<>();
        comboBoxProfession.setToolTipText( "Wybierz profesj\u0119 dla bohatera." );

        comboBoxExperience = new JComboBox<>();
        comboBoxExperience.setModel( new DefaultComboBoxModel<>( new String[]{"Brak", "Pocz\u0105tkuj\u0105ca", "\u015Arednio zaawansowana", "Do\u015Bwiadczona"} ) );
        comboBoxExperience.setToolTipText( "Czy posta\u0107 posiada ju\u017C jakie\u015B rozwini\u0119cia. W zale\u017Cno\u015Bci od wybranej opcji, posta\u0107 otrzymuje  (3,5,7) rozwini\u0119\u0107 (umiej\u0119tno\u015Bci, cechy klasowe lub talent).\r\n" );

        buttonAddLevel = new JButton( "Podnie\u015B poziom" );
        buttonAddLevel.setIcon( new ImageIcon( MainGui.class.getResource( "/items/crossbow.png" ) ) );
        buttonAddLevel.setToolTipText( "Posta\u0107 awansuje na nast\u0119pny poziom rozwoju swojej \u015Bcie\u017Cki profesji.\r\nJe\u017Celi poziom cech lub przynajmniej o\u015Bmiu umiej\u0119tno\u015Bci jest zbyt niski, to s\u0105 one automoatyczne podnoszone do wymaganego poziomu (aby uko\u0144czy\u0107 dany poziom profesji)." );
        buttonAddLevel.setEnabled( false );

        ShowTalentsCheckBox = new JCheckBox( "Wy\u015Bwietl talenty" );
        ShowTalentsCheckBox.setSelected( true );

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );

        buttonSaveHero = new JButton( "Zapisz posta\u0107" );
        buttonSaveHero.setIcon( new ImageIcon( MainGui.class.getResource( "/items/save.png" ) ) );
        buttonSaveHero.setEnabled( false );


		JScrollPane scrollPaneLista = new JScrollPane();
        scrollPaneLista.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );

        radioButtonMan = new JRadioButton( "M\u0119\u017Cczyzna" );
        radioButtonMan.setToolTipText( "P\u00F3ki co nie chce mi si\u0119 zmienia\u0107 \r\nopis\u00F3w nazw profesji aby by\u0142y adekwatne\r\ndo wybranej p\u0142ci. Mo\u017Ce kiedy\u015B...." );
        radioButtonMan.setSelected( true );
        buttonGroup.add( radioButtonMan );

		JRadioButton radioButtonWomen = new JRadioButton( "Kobieta" );
        radioButtonWomen.setToolTipText( "P\u00F3ki co nie chce mi si\u0119 zmienia\u0107 \r\nopis\u00F3w nazw profesji aby by\u0142y adekwatne\r\ndo wybranej p\u0142ci. Mo\u017Ce kiedy\u015B...." );
        buttonGroup.add( radioButtonWomen );

        buttonNewProfession = new JButton( "Nowa profesja" );
        buttonNewProfession.setIcon( new ImageIcon( MainGui.class.getResource( "/items/wizard.png" ) ) );
        buttonNewProfession.setToolTipText( "Dodaj now\u0105 porfesj\u0119 do aktualnie tworzonego bohatera.\r\nNowa profesja zawsze zaczyna si\u0119 od pierwszego poziomu." );
        buttonNewProfession.setEnabled( false );

        buttonExportToPdf = new JButton( "Zapisz do PDF" );
        buttonExportToPdf.setEnabled( false );
        buttonExportToPdf.setIcon( new ImageIcon( MainGui.class.getResource( "/items/document.png" ) ) );

        JLabel lblLabelRasa = new JLabel( "Rasa:" );

        JLabel lblLabelProfesja = new JLabel( "Profesja:" );
        lblLabelProfesja.setDisplayedMnemonic( KeyEvent.VK_ENTER );

        buttonNPC = new JButton( "Utw\u00F3rz NPCa" );

        buttonExportExcel = new JButton( "Zapisz do Excel" );
        buttonExportExcel.setIcon( new ImageIcon( MainGui.class.getResource( "/items/excel.png" ) ) );
        buttonExportExcel.setEnabled( false );

        GroupLayout gl_contentPane = new GroupLayout( contentPane );
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup( Alignment.LEADING )
                        .addGroup( gl_contentPane.createSequentialGroup()
                                           .addComponent( scrollPane, GroupLayout.PREFERRED_SIZE, 745, GroupLayout.PREFERRED_SIZE )
                                           .addPreferredGap( ComponentPlacement.RELATED )
                                           .addGroup( gl_contentPane.createParallelGroup( Alignment.LEADING )
                                                              .addComponent( buttonNPC, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                                              .addComponent( buttonExportExcel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                                              .addComponent( lblLabelRasa )
                                                              .addComponent( buttonAddLevel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                                              .addComponent( lblLabelProfesja )
                                                              .addComponent( buttonSaveHero, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                                              .addComponent( buttonExportToPdf, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                                              .addComponent( buttonNewProfession, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE )
                                                              .addComponent( buttonNewHero, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE )
                                                              .addComponent( comboBoxProfession, 0, 143, Short.MAX_VALUE )
                                                              .addComponent( comboBoxRace, 0, 143, Short.MAX_VALUE )
                                                              .addComponent( ShowTalentsCheckBox, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE )
                                                              .addGroup( gl_contentPane.createSequentialGroup()
                                                                                 .addComponent( radioButtonMan )
                                                                                 .addPreferredGap( ComponentPlacement.RELATED )
                                                                                 .addComponent( radioButtonWomen ) )
                                                              .addComponent( comboBoxExperience, 0, 143, Short.MAX_VALUE ) )
                                           .addPreferredGap( ComponentPlacement.RELATED )
                                           .addComponent( scrollPaneLista, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE )
                                           .addContainerGap() )
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup( Alignment.TRAILING )
                        .addGroup( gl_contentPane.createSequentialGroup()
                                           .addContainerGap()
                                           .addGroup( gl_contentPane.createParallelGroup( Alignment.LEADING )
                                                              .addComponent( scrollPaneLista, GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE )
                                                              .addGroup( gl_contentPane.createParallelGroup( Alignment.BASELINE )
                                                                                 .addComponent( scrollPane, GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE )
                                                                                 .addGroup( gl_contentPane.createSequentialGroup()
                                                                                                    .addComponent( lblLabelRasa )
                                                                                                    .addPreferredGap( ComponentPlacement.RELATED )
                                                                                                    .addComponent( comboBoxRace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
                                                                                                    .addPreferredGap( ComponentPlacement.RELATED, 10, Short.MAX_VALUE )
                                                                                                    .addComponent( lblLabelProfesja )
                                                                                                    .addGap( 7 )
                                                                                                    .addComponent( comboBoxProfession, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
                                                                                                    .addGap( 20 )
                                                                                                    .addComponent( buttonNewHero )
                                                                                                    .addGap( 12 )
                                                                                                    .addComponent( buttonAddLevel )
                                                                                                    .addGap( 12 )
                                                                                                    .addComponent( buttonNewProfession )
                                                                                                    .addGap( 13 )
                                                                                                    .addComponent( buttonSaveHero )
                                                                                                    .addGap( 12 )
                                                                                                    .addComponent( buttonExportToPdf )
                                                                                                    .addGap( 12 )
                                                                                                    .addComponent( buttonExportExcel )
                                                                                                    .addGap( 8 )
                                                                                                    .addComponent( ShowTalentsCheckBox )
                                                                                                    .addGap( 7 )
                                                                                                    .addGroup( gl_contentPane.createParallelGroup( Alignment.BASELINE )
                                                                                                                       .addComponent( radioButtonMan )
                                                                                                                       .addComponent( radioButtonWomen ) )
                                                                                                    .addGap( 7 )
                                                                                                    .addComponent( comboBoxExperience, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE )
                                                                                                    .addGap( 249 )
                                                                                                    .addComponent( buttonNPC, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE ) ) ) )
                                           .addGap( 41 ) )
        );
        list = new JList<>( heroList );
        scrollPaneLista.setViewportView( list );
        scrollPane.setViewportView( textArea );
        contentPane.setLayout( gl_contentPane );
    }

    @Override
    public void updateHero(String description) {
        textArea.setText( description );
    }

    @Override
    public void activateButtonNewHero() {
        buttonNewProfession.setEnabled( false );
    }

    @Override
    public void activateNewLevelButton() {
        buttonAddLevel.setEnabled( true );
    }

    @Override
    public void activateNewProfessionButton() {
        buttonNewProfession.setEnabled( true );
    }

    @Override
    public void deactivateNewLevelButton() {
        buttonAddLevel.setEnabled( false );
    }

    @Override
    public void updateListModelWithNewHero(Hero hero) {
        heroList.addElement( hero );
        if ( heroList.size() > 0 ) {
            controller.activateExportDoExcel();
        }
    }

    public void addToModelList(Object object) {
        heroList.addElement( object );
        if ( heroList.size() > 0 ) {
            controller.activateExportDoExcel();
        }
    }

    public void setButtonExportExcelEnabled() {
        buttonExportExcel.setEnabled( true );
    }

    public void setButtonIncreaseLevelEnabled(boolean e) {
        buttonAddLevel.setEnabled( e );
    }

    public void setButtonSaveHeroEnabled(boolean e) {
        buttonSaveHero.setEnabled( e );
    }

    public void setButtonExportToPdfActive() {
        buttonExportToPdf.setEnabled( true );
    }

    public void setButtonExportToPdfInactive() {
        buttonExportToPdf.setEnabled( false );
    }
}
