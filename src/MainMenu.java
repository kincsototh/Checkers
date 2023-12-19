import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainMenu implements ActionListener {
	public JFrame frame;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem exitMenuItem;
	private JLabel welcomePhoto = new JLabel();
	private JButton butNewGame;
	private JButton butAnalysis;
	private JButton butHelp ;
	
	
	public MainMenu() {
		initialize();
	}
	
	/**
	   * Létrehozza a főmenüt, beállítja a főmenü hátterét, fejlécét (ami itt egy kép), méreteit
	   */
	public void initialize() {
		frame = new JFrame();
		frame.setTitle("Checkers");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 250);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout(10, 10));
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground( Color.black );
		frame.setVisible(true);
		
		ImageIcon image = new ImageIcon("./checkers.png");
		frame.setIconImage(image.getImage());
		
		welcomePhoto.setIcon(new ImageIcon("welcomePhoto.png")); 
	    Dimension size = welcomePhoto.getPreferredSize(); 
	    welcomePhoto.setBounds(50, 30, size.width, size.height); 
		frame.add(welcomePhoto, BorderLayout.PAGE_START);
		
		menuBarInit(frame);
		menuButtons(frame);
		
	}
	
	/**
	   * Felveszi a gombokat a panelekbe, valamint tesz rájuk ActionListener-t, hogy a felhasználó 
	   * tudjon rájuk kattintani
	   * @param frame: JFrame, egy már létező frame
	   */
	public void menuButtons(JFrame frame) {
		butNewGame = new JButton("Új játék létrehozása");
		butAnalysis = new JButton("Új elemzés létrehozása");
		butHelp = new JButton("Szabályok");

		JPanel Panel = new JPanel();
		
		Panel.setBackground( Color.black );
		
		Panel.add(butNewGame);
		Panel.add(butAnalysis);
		Panel.add(butHelp);
		
		butNewGame.addActionListener(this);
		butAnalysis.addActionListener(this);
		butHelp.addActionListener(this);
		
		frame.add(Panel);
		Panel.revalidate();
        Panel.repaint(); 
	}
	
	/**
	   * A felső sávba tesz egy kilépésre szolgáló gombot
	   * @param frame: JFrame, egy már létező frame
	   */
	public void menuBarInit(JFrame frame) {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Kilépés");
		
		exitMenuItem = new JMenuItem("Kilépés");
		ImageIcon exitIcon = new ImageIcon("exit-2860.png");
		exitMenuItem.setIcon(exitIcon);
		exitMenuItem.setIconTextGap(10);
		exitMenuItem.addActionListener(this);

		fileMenu.add(exitMenuItem);
		
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == butNewGame) {
			frame.dispose();
			NewWindow myWindow = new NewWindow();
			myWindow.newGame();
		}
		else if(e.getSource() == butAnalysis) {
			frame.dispose();
			NewWindow myWindow = new NewWindow();
			myWindow.newAnalysis();
		}
		
		else if(e.getSource() == butHelp) {
			frame.dispose();
			NewWindow myWindow = new NewWindow();
			myWindow.gameHelp();
		}
		
		else if(e.getSource() == exitMenuItem) {
			System.exit(0);
		}
	}
}
