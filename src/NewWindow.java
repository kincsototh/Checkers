import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class NewWindow {
	JFrame frame = new JFrame();
	private JButton startButton = new JButton("Indítás");
	
	private String[] boardSize = {"8x8", "10x10"};
	private JComboBox<?> boardBox = new JComboBox<Object>(boardSize);
	
	private String[] playerNum = {"1", "2"};
	private JComboBox<?>  playerBox = new JComboBox<Object>(playerNum);
	
	private JTextField name1 = new JTextField(20);
	private JTextField name2 = new JTextField(20);
	
	private String[] colors = { "b", "w", "y", "r", "g", "m"};
	
	private String[] player1Col = {"fekete", "fehér", "sárga", "piros", "zöld", "magenta"};
	private JComboBox<?> player1Box = new JComboBox<Object>(player1Col);
	
	private String[] player2Col = {"fekete", "fehér", "sárga", "piros", "zöld", "magenta"};
	private JComboBox<?> player2Box = new JComboBox<Object>(player2Col);
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem backMenuItem;

	JPanel downPanel = new JPanel();
	JPanel topPanel = new JPanel();
	JPanel midPanel = new JPanel();
	
	public NewWindow(){
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
		frame.setSize(500, 400);
		frame.getContentPane().setBackground( Color.black );
		frame.setLayout(new BorderLayout(10, 10));
		frame.setLocationRelativeTo(null);
		frame.setVisible(false);
		ImageIcon image = new ImageIcon("./checkers.png");
		frame.setIconImage(image.getImage());
	}
	
	public void menuBar(JFrame myFrame) {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Visszalépés");
		
		backMenuItem = new JMenuItem("Visszalépés");
		ImageIcon gameIcon = new ImageIcon("exit-2860.png");
		backMenuItem.setIcon(gameIcon);
		backMenuItem.setIconTextGap(10);
		backMenuItem.addActionListener(new backMenuListener());
		
		fileMenu.add(backMenuItem);
		menuBar.add(fileMenu);
		myFrame.setJMenuBar(menuBar);
	}
	
	public void newGame() {
		startButton.addActionListener(new startGameListener());
		playerBox.addActionListener(new playerBoxListener());
		menuBar(frame);
		frame.setTitle("Új játék beállítása");
		JLabel boardSize = new JLabel("Tábla mérete: ");
		boardSize.setBorder(new EmptyBorder(0,10,0,0));
		boardSize.setForeground (Color.white);
		midPanel.add(boardSize);
		midPanel.add(boardBox);
		GridLayout myGrid = new GridLayout(7, 12);
		midPanel.setLayout(myGrid);

		
		JLabel newGamePhoto = new JLabel();
		newGamePhoto.setIcon(new ImageIcon("newGame.png")); 
	    Dimension size = newGamePhoto.getPreferredSize(); 
	    newGamePhoto.setBounds(50, 30, size.width, size.height); 
		
		JLabel player1Label = new JLabel("Első játékos színe: ");
		player1Label.setBorder(new EmptyBorder(0,10,0,0));
		JLabel player2Label = new JLabel("Második játékos színe: ");
		player2Label.setBorder(new EmptyBorder(0,10,0,0));
		player1Label.setForeground (Color.white);
		player2Label.setForeground (Color.white);
		
		JLabel playerLabel = new JLabel("Játékosok száma: ");
		playerLabel.setForeground (Color.white);
		
		playerLabel.setBorder(new EmptyBorder(0,10,0,0));
		midPanel.add(playerLabel);
		midPanel.add(playerBox);
		player1Label.setBorder(new EmptyBorder(0,10,0,0));
		midPanel.add(player1Label);
		midPanel.add(player1Box);
		player1Box.setSelectedIndex(1);
		
		player2Label.setBorder(new EmptyBorder(0,10,0,0));
		midPanel.add(player2Label);
		midPanel.add(player2Box);
		
		JLabel playerOne = new JLabel("Első játékos neve: ");
		playerOne.setForeground (Color.white);
		playerOne.setBorder(new EmptyBorder(0,10,0,0));
		midPanel.add(playerOne);
		midPanel.add(name1);
		
		JLabel playerTwo = new JLabel("Második játékos neve: ");
		playerTwo.setForeground (Color.white);
		playerTwo.setBorder(new EmptyBorder(0,10,0,0));
		midPanel.add(playerTwo);
		
		name1.setEditable(true);
		name1.setText("Játékos1");

		name2.setEditable(false);
		name2.setText("Számítógép");
		midPanel.add(name2);
		
		topPanel.add(newGamePhoto);
		downPanel.add(startButton);
		
		downPanel.setBackground(Color.black);
		topPanel.setBackground(Color.black);
		midPanel.setBackground(Color.black);
		
		frame.add(downPanel, BorderLayout.PAGE_END);
		frame.add(topPanel, BorderLayout.PAGE_START);
		frame.add(midPanel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public void newAnalysis() {
		startButton.addActionListener(new startAnalyzeListener());
		
		menuBar(frame);
		frame.setTitle("Új elemzőtábla beállítása");
		JLabel boardSize = new JLabel("Elemzőtábla mérete: ");
		boardSize.setForeground (Color.white);
		GridLayout myGrid = new GridLayout(5, 3);
		midPanel.setLayout(myGrid);
		
		boardSize.setBorder(new EmptyBorder(0,10,0,0));
		midPanel.add(boardSize);
		midPanel.add(boardBox);
		
		JLabel player1Label = new JLabel("Első játékos színe: ");
		JLabel player2Label = new JLabel("Második játékos színe: ");
		player1Label.setForeground (Color.white);
		player2Label.setForeground (Color.white);
		midPanel.add(player1Label);
		player1Label.setBorder(new EmptyBorder(0,10,0,0));
		midPanel.add(player1Box);
		player1Box.setSelectedIndex(1);
		
		midPanel.add(player2Label);
		player2Label.setBorder(new EmptyBorder(0,10,0,0));
		midPanel.add(player2Box);
		midPanel.add(startButton);
		
		downPanel.add(startButton);
		
		JLabel newAnalyzePhoto = new JLabel();
		newAnalyzePhoto.setIcon(new ImageIcon("newAnalyze.png")); 
	    Dimension size = newAnalyzePhoto.getPreferredSize(); 
	    newAnalyzePhoto.setBounds(50, 30, size.width, size.height); 
	    topPanel.add(newAnalyzePhoto);
		
		downPanel.setBackground(Color.black);
		topPanel.setBackground(Color.black);
		midPanel.setBackground(Color.black);
		
		frame.add(downPanel, BorderLayout.PAGE_END);
		frame.add(midPanel, BorderLayout.CENTER);
		frame.add(topPanel, BorderLayout.PAGE_START);
		frame.setVisible(true);
	}
	
	public void gameHelp() {
		menuBar(frame);
		frame.setSize(500, 470);
		frame.setTitle("A játék szabályai");
		
		JTextArea textArea = new JTextArea(
				"A Dámát két játékos játssza. Mindkét játékos 12/20 koronggal kezdi a játszmát.\n"
						+ "A játék célja hogy elenfelünk összes korongját levegyük, vagy mozgásképtelenné tegyük. "
						+ "A játék egy 8x8-as/10x10-es táblán játszódik. A játékosok felváltva következnek. Lépést nem lehet kihagyni."
						+ "A lépés abból áll, hogy egyik korongunkat egy másik átlósan szomszédos mezőre helyezzük" 
						+ "A dámajátékban úgy lehet ütni, hogy átugorjuk az ellenfél figuráját. Az ütés szabályai:\r\n"
						+ "Csak előre lehet ütni, és csak akkor, ha az átugrandó figura mögötti mező üres.\r\n"
						+ "Ebből következőleg a tábla szélén álló figurát nem lehet leütni.\r\n"
						+ "Nem lehet leütni saját figuránkat, és leütés nélkül átugrani sem.\r\n"
						+ "A klasszikus dámajáték szabályai szerint az ütés kötelező. Ez azt jelenti, hogy ha a soron következő játékosnak ütési lehetősége van, mindenképpen ütnie kell; természetesen ő választ a lehetséges ütések közül, ha több figurájának is módja van ütni, illetve ugyanaz a figura több irányba üthet.\r\n"
						+ "Ha az ütés után ugyanaz a figura tovább tud ütni arról a helyről, ahova az ütéssel jutott, ugyanabban a lépésben folytathatja az ütést, vagyis ütéssorozatot csinálhat."
						+ "Az a figura, amely elérte a tábla szemközti sorát dámává lép elõ, melyet megkülönböztetünk a többi bábutól.\r\n"
						+ "A dáma speciális tulajdonsága hogy visszafelé is léphet és üthet.", 
                10, 
                45);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setForeground (Color.white);
        
        JLabel newHelpPhoto = new JLabel();
        newHelpPhoto.setIcon(new ImageIcon("newRules.png")); 
	    Dimension size = newHelpPhoto.getPreferredSize(); 
	    newHelpPhoto.setBounds(50, 30, size.width, size.height); 
	    topPanel.add(newHelpPhoto);

		topPanel.setBackground(Color.black);
        topPanel.add(textArea);
        
		frame.add(topPanel);
		frame.setVisible(true);
	}
	
	private class backMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			@SuppressWarnings("unused")
			MainMenu backToMenu = new MainMenu();
		}
	}
	
	private class playerBoxListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(playerBox.getSelectedItem() == "2") {
				name2.setEditable(true);
				name2.setText("Játékos2");
			}
			else {
				name2.setEditable(false);
				name2.setText("Számítógép");
			}
		}
	}
	
	private class startGameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			Play play;
			boolean isComputer = (playerBox.getSelectedItem() == "1") ;
			
			String color1 = colors[player1Box.getSelectedIndex()];
			String color2 = colors[player2Box.getSelectedIndex()];
			
			Player player1 = new Player(Player.PLAYERONE, name1.getText(), false, color1);
			Player player2 = new Player(Player.PLAYERTWO, name2.getText(), isComputer, color2);
			
			if(boardBox.getSelectedIndex() == 0) {
				play = new Play(8, 12, player1, player2, player1);
			}
			else {
				play = new Play(10, 20, player1, player2, player1);
			}
			
			Grid newGrid = new Grid(play);
			newGrid.paintFrame();
			newGrid.paintBoard();
		}
	}
	
	
	private class startAnalyzeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			Play play;
			
			String color1 = colors[player1Box.getSelectedIndex()];
			String color2 = colors[player2Box.getSelectedIndex()];
			
			Player player1 = new Player(Player.PLAYERONE, "Elemzés", false, color1);
			Player player2 = new Player(Player.PLAYERTWO, "Elemzés", false, color2);
			
			if(boardBox.getSelectedIndex() == 0) {
				play = new Play(8, 12, player1, player2, player1);
			}
			else {
				play = new Play(10, 20, player1, player2, player1);
			}
			
			Grid newGrid = new Grid(play);
			newGrid.paintFrame();
			newGrid.paintBoard();
		}
	}
}
