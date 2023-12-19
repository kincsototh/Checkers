import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Grid implements MouseListener {
	private int column;
	private int row;
	private Square[][] squares;
	private Play play;
	 
	private JLabel playerLabel = new JLabel();
	private JLabel counterLabel = new JLabel();
	private JLabel messageLabel = new JLabel();
	private JButton saveButton = new JButton();
	private JButton loadButton = new JButton();
	private JFrame frame = new JFrame("Checkers");
	private JPanel boardPanel = new JPanel();

	//a lépni kívánt figura mezőjét tároljuk itt
	private Square selectedSquare;
	
	//azok a mezők, amelyekre lehet lépni a selectedSquare-ről
	private ArrayList<Square> targetSquares;
	
	/**
	   * A konstruktor beállítja a jelenlegi játékot, inicialiálja a kijelölt mezőket egyelőre null-ra
	   * és egy üres listára, eltárolja a tábla méreteit, valamint létrehozza a Square kétdimenziós tömböt, 
	   * amely koordinátarendszerben tárolja a mezőket.
	   * @param play: Play, a játék, amelyet elindítottunk
	   */
	public Grid(Play play) {
		this.play = play;
		this.selectedSquare = null;
		this.targetSquares =  new ArrayList<Square>();
		if(play != null) {
			this.column = play.getBoard().getColumns();
			this.row = play.getBoard().getRows();
			this.squares = new Square[this.column][this.row];
		}
	}
	
	/**
	   * A tábla megjelenítését valósítja meg grafikusan, létrehozza a mezőket és inicializálja őket, valamint
	   * hozzáadja a MouseListener-t, hogy a felhasználó az egerével tudjon a mezőkre kattintani.
	   */
	public void buildBoardPanel() {
		boardPanel.setLayout(new GridLayout(this.row, this.column));
		boardPanel.removeAll();
		
		for(int r = row-1; r >= 0; r--) {
			for(int c = 0; c < column; c++) {
				if((c % 2) == (r % 2)) {
					squares[c][r] = new Square(null, Color.gray, c, r);
				}
				else {
					squares[c][r] = new Square(null, Color.white, c, r);
				}
				
				boardPanel.add(squares[c][r]);
				
				squares[c][r].addMouseListener(this);
			}
		}
	}
	
	/**
	   * A megnyíló ablakot állítja be, a rajta lévő üzenetekkel és gombokkal.
	   * Ezek: 
	   * 	- a soron következő játékos
	   * 	- a lépésszám
	   * 	- különféle üzenet jelző
	   * 	- mentés gomb
	   * 	- visszatöltés gomb.
	   */
	public void paintFrame() {
		this.frame.setSize(750,780); 
		this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.frame.setResizable(false);
		
		ImageIcon image = new ImageIcon("./checkers.png");
		this.frame.setIconImage(image.getImage());
		
		JPanel statusPanel = new JPanel();
		
		statusPanel.add(playerLabel);
		statusPanel.add(counterLabel);
		statusPanel.add(messageLabel);
		statusPanel.add(saveButton);
		statusPanel.add(loadButton);
		
		statusPanel.setLayout(new GridLayout(1, 5));
		this.frame.add(statusPanel, BorderLayout.PAGE_START);
		
		saveButton.setText("Mentés");
		saveButton.addMouseListener(this);
		
		loadButton.setText("Visszatöltés");
		loadButton.addMouseListener(this);
		
		this.buildBoardPanel();
		this.frame.add(boardPanel);
		this.frame.setVisible(true);
		nextPlayer();
		updateLabels();
	}
	
	/**
	   * A figurák felhelyezése grafikusan is a táblára, a Play Board-ja alapján, 
	   * azaz a játéklogika alapján
	   */
	public void paintBoard() {
		for(int r = row-1; r >= 0; r--) {
			for(int c = 0; c < this.column; c++) {
				Piece currentPiece = play.getBoard().getPiece(c, r);
				if(currentPiece != null) {
					squares[c][r].setImage(currentPiece.getImage());
				}
				else {
					squares[c][r].setImage(null);
				}
				
				squares[c][r].setPiece(currentPiece);
			}
		}
	}
	
	/**
	   * Az érvényes lehetséges célmezőket beszínezzük (ütés : piros, lépés: zöld, figura amivel ütéskényszer
	   * van: sárga) 
	   * és elhelyezzük a targetSquares listában 
	   * (ezt a listát ellenőrizzük majd a játékos második egérműveletére)  
	   * @param clieckedSquare: Square, az a mező, amelyre rákattintottunk
	   * @return true - ha sikerült olyan mező(ke)t találni, ahova szabad lépni erről a mezőről 
	   */
	public boolean collectTargetSquares(Square clickedSquare){
		// Először megnézzük, hogy az adott figura azé a játékosé-e, aki soron következő
		Piece currentPiece = clickedSquare.getPiece();
		if(currentPiece != null) {
			if(currentPiece.getPlayer() != play.getCurrPlayer())
				return false;
			
			
			ArrayList<Piece> listPieces =  play.allowedToMove(currentPiece);
			// Megnézzük, hogy más figura tud-e ütni, mert ha igen, akkor ez nem léphet el addig
			if(!listPieces.isEmpty()){	
				//megszínezzük a listában lévő figurák mezőit, mert valamelyikükkel kötelező lesz lépni
				for (int i = 0; i < listPieces.size(); i++) {
					Square square = squares[listPieces.get(i).getCol()][listPieces.get(i).getRow()];
					square.setBackground(Color.yellow);
				    messageLabel.setText("Ütéskényszer van");
				}
				
				// nem léphetünk el, false-szal térünk vissza és nem állítjuk be a selectedSquare-t semmire
				return false;
			}
		}
		
		//először megnézzük, lehet-e onnan ütni (ütéskényszer van!)
		ArrayList<Move> moveList = play.canCapture(clickedSquare.getCol(), clickedSquare.getRow());
		if(moveList != null) {
			for (int i = 0; i < moveList.size(); i++) {
				Square square = squares[ moveList.get(i).getEndCol()][moveList.get(i).getEndRow()];
			    targetSquares.add(square);
			    square.setBackground(Color.red);
			    messageLabel.setText("Ütéskényszer van");
			}
						
			//lehet ütni, ütéskényszer miatt visszatérünk, nem is nézzük a lépéslistát 
			if(!moveList.isEmpty()) 
				return true;
		}
		
		//majd megnézzük, hova lehet onnan lépni
		moveList = play.canGo(clickedSquare.getCol(), clickedSquare.getRow());
		if(moveList != null) {
			for (int i = 0; i < moveList.size(); i++) {
				Square square = squares[ moveList.get(i).getEndCol()][moveList.get(i).getEndRow()];
			    targetSquares.add(square);
			    square.setBackground(Color.green);
			    messageLabel.setText(null);
			}
			
			return true;
		}
		
		return false;
	}

	/**
	   * Visszaad koordináták alapján egy mezőt a mezők listájából
	   * @param c (column): int, a keresett mező oszlopkoordinátája
	   * @param r (row): int, a keresett mező sorkoordinátája
	   * @return Square Visszatér a paraméterek alapján meghatározott mezővel
	   */
	public Square getSquare(int c, int r ) {
		return this.squares[c][r];
	}
	
	/**
	   * Kitörli a kijelölt mezőket, amelyeket megszíneztünk/tároltunk, hogy oda ütni/lépni tudunk, majd 
	   * beállítja, hogy ne legyen kijelölt mező. Ezzel úgymond előkészülünk a következő játékos lépésére
	   */
	public void purgeTargetSquares() {
		for(int c = 0; c < column; c++) {
			for(int r = 0; r < row; r++) {
				squares[c][r].setDefaultColor();
			}
		}
			
		targetSquares.clear();
		selectedSquare = null;
	}
	
	/**
	   * Aktualizálja a Label-eket a lépések után. Ez azt jelenti, hogy átírja, ki következik, hányadik lépésben
	   * vagyunk, illetve ha valaki nem tud lépni, akkor kiírja ki veszített.
	   */
	public void updateLabels() {
		playerLabel.setText("Következik:  " + play.getCurrPlayer().getName());
		counterLabel.setText(String.valueOf(play.getCounter()) + ". lépése");
		
		if(!play.canMove(play.getCurrPlayer())) {
			messageLabel.setText(play.getCurrPlayer().getName() + " veszített");
		} 
	}

	/**
	   * Mindegyik Label-t kiüríti, így nem lesz rajtuk semmilyen üzenet.
	   */
	public void clearLabels() {
		playerLabel.setText(null);
		counterLabel.setText(null);
		messageLabel.setText(null);
	}
	
	/**
	   * Vagy az első lépésnél, vagy sikeres lépés után hívjuk meg. Ha gépi játékos következik, 
	   * elvégzi a lépést és visszavált a felhasználó játékosára.
	   * Szintén itt nézzük meg a játék állását és eldöntjük, hogy vége van-e
	   */
	public void nextPlayer() {
		//ha még nem volt játékos, akkor most beállítjuk a kezdő játékosra
		//ha pedig volt, akkor a következőre állítjuk
		if(play.getCurrPlayer() == null) {
			play.setCurrPlayer(play.getFirstPlayer());
		} else {
			play.switchPlayer();
			if(play.getCurrPlayer() == play.getFirstPlayer()) {
				play.increaseCounter();
			}
		}
		
		//ha a gép jön, akkor elvégzi a lépést
		//ha pedig nem a gép jön (vagy nincs is gép) akkor visszatérünk
		if(play.getCurrPlayer().isComputer()) {
			//keresünk egy mezőt, amin van a gépnek figurája
			Move randomMove = play.getRandomMove();
			if(randomMove != null) {
				makeMove(squares[randomMove.getStartCol()][randomMove.getStartRow()], squares[randomMove.getEndCol()][randomMove.getEndRow()]);
				
				//megnézzük, hogy ütött-e
				//ezt onnan tudjuk hogy volt-e selectedSquare, mert ezt a makeMove beállítja, ha lehet tovább ütni onnan
				if(selectedSquare == null) {
					return;
				}
				
				randomMove = play.getRandomCapture(randomMove.getEndCol(), randomMove.getEndRow());
				while(randomMove != null){
					//System.out.println("A gép lépése: ");
					//randomMove.printMove();
					makeMove(squares[randomMove.getStartCol()][randomMove.getStartRow()], squares[randomMove.getEndCol()][randomMove.getEndRow()]);
					randomMove = play.getRandomCapture(randomMove.getEndCol(), randomMove.getEndRow());
				}
			}
			else {
				System.out.println("A gép nem tud lépni, vesztett ");
			}
		}
	}
	
	/**
	   * Megteszünk egy lépést a paraméterként kapott kezdő mezőről a paraméterként kapott végső mezőre. 
	   * A figurák áthelyezését is itt valósítjuk meg.
	   * @param startSquare: Square, a kezdő mező
	   * @param endAquare: Square, a végső mező
	   */
	public void makeMove(Square startSquare, Square endSquare) {
		Move nowMoved = new Move(startSquare.getCol(), startSquare.getRow(), endSquare.getCol(), endSquare.getRow());
		
		//itt lépünk logikailag és utána a mezőket is aktualizáljuk
		Piece capturedPiece = play.makeMove(nowMoved);
		endSquare.setImage(startSquare.getPiece().getImage());
		endSquare.setPiece(startSquare.getPiece());
		
		//figura törlése a korábbi mezőről
		startSquare.setPiece(null);
		startSquare.setImage(null);
		
		//Ha volt leütött figura, ezt vizuálisan is töröljük a mezőről és megnézzük, lehet-e onnan tovább
		//ütni, mert ha igen, akkor újra kikeressük, hova
		if(capturedPiece != null) {
			//System.out.println("Leütjük: " + capturedPiece.getCol() + " : " + capturedPiece.getRow());
			
			Square capturedSquare = squares[capturedPiece.getCol()][capturedPiece.getRow()];
			if(capturedSquare != null) {
				capturedSquare.setPiece(null);
				capturedSquare.setImage(null);
			}
			
			// megnézzük, lehet-e onnan tovább ütni (ütéskényszer van!)
			ArrayList<Move> moveList = play.canCapture(endSquare.getCol(), endSquare.getRow());
			if(moveList != null) {
				if(!moveList.isEmpty()) {
					//System.out.println("Lehet tovább ütni");
					messageLabel.setText("Ütéskényszer van");
					//ha lehet tovább ütni, akkor megkeressük, honnan lehet ütni, megszínezzük
					//majd ki is jelöljük azt kiindulási lépésnek
					purgeTargetSquares();
					collectTargetSquares(endSquare);
					selectedSquare = endSquare;
					return;
				} 
			}
			// Nem lehet tovább ütni
			messageLabel.setText(null);
		}
		
		//alapállapotba állítás
		purgeTargetSquares();
		nextPlayer();
		updateLabels();
	}
	

	/**
	   * Megnézzük, milyen gombot nyomott meg a felhasználó.
	   */
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == saveButton) {
			//System.out.println("Save");
			play.savePlay();
			return;
		}
		
		if(e.getSource() == loadButton) {
			//System.out.println("Load");
			play.loadPlay();
			if(play != null) {
				this.column = play.getBoard().getColumns();
				this.row = play.getBoard().getRows();
				this.squares = new Square[this.column][this.row];
				this.buildBoardPanel();
				this.paintBoard();
				this.clearLabels();
				this.updateLabels();
			}
			return;
		}
		
		// Ha eddig eljutottunk, akkor nem a Save/Load-ra, hanem valamelyik táblamezőre kattintottunk:
		
		Square clickedSquare = (Square) e.getComponent();
		
		//ha még nincs kiválasztott mező, feltöltjük a targetSquares listát a lehetséges célmezőkkel 
		// és jelezzük a következő egérművelethez, hogy már van érvényes kiválasztott kiindulási mezőnk 
		if(selectedSquare == null) {
			if (collectTargetSquares(clickedSquare)) {
				selectedSquare = clickedSquare;
			}
		}
		
		//ha már volt kiválasztott mező, ÉS onnan lehet vagy lépni vagy ütni:
		else {
			//megnézzük, hogy érvényes mezőre klikkelt-e másodikra a játékos
			if(targetSquares.contains(clickedSquare)) {
				
				//itt elvégezzük a mozgást/mozgásokat, törlést/törléseket
				makeMove(selectedSquare, clickedSquare);
			}
			else {
				//akkor úgy tekintjük, mintha ez az első klikkelés lett volna 
				purgeTargetSquares();
				if (collectTargetSquares(clickedSquare)) {
					selectedSquare = clickedSquare;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
