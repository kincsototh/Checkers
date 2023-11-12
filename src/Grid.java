import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
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
	private JLabel buttonLabel = new JLabel();
	
	//a lépni kívánt figura mezőjét tároljuk itt
	private Square selectedSquare;
	
	//azok a mezők, amelyekre lehet lépni a selectedSquare-ről
	private ArrayList<Square> targetSquares;
	
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
	
	public void paintGrid() {
		JFrame frame = new JFrame("Checkers");
		frame.setSize(620,650); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel statusPanel = new JPanel();
		
		statusPanel.add(playerLabel);
		statusPanel.add(counterLabel);
		statusPanel.add(messageLabel);
		statusPanel.add(buttonLabel);
		
		statusPanel.setLayout(new GridLayout(1, 4));
		frame.add(statusPanel, BorderLayout.PAGE_START);
		
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(this.row, this.column));
		

		for(int r = row-1; r >= 0; r--) {
			for(int c = 0; c < column; c++) {
				if((c % 2) == (r % 2)) {
					squares[c][r] = new Square(null, Color.gray, c, r);
				}
				else {
					squares[c][r] = new Square(null,Color.white, c, r);
				}
				
				boardPanel.add(squares[c][r]);
				
				squares[c][r].addMouseListener(this);
			}
		}
		
		frame.add(boardPanel);
		frame.setVisible(true);
		nextPlayer();
	}
	
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
	
	// true, ha sikerült olyan mező(ke)t találni, ahova szabad lépni erről a mezőről 
	// az érvényes lehetséges célmezőket beszínezzük (ütés : piros, lépés: zöld) és elhelyezzük a targetSquares listában   
	// (ezt a listát ellenőrizzük majd a játékos második egérműveletére)
	
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
				    square.setColor(Color.yellow);
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
			    square.setColor(Color.red);
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
			    square.setColor(Color.green);
			}
			
			return true;
		}
		
		return false;
	}
	
	public void purgeTargetSquares() {
		for(int c = 0; c < column; c++) {
			for(int r = 0; r < row; r++) {
				squares[c][r].setDefaultColor();
			}
		}
			
		targetSquares.clear();
	}
	
	public void updateLabels() {
		playerLabel.setText(play.getCurrPlayer().getName());
	}
	
	//vagy az első lépésnél, vagy sikeres lépés után hívjuk meg 
	//ha gépi játékos következik, elvégzi a lépést és visszavált a felhasználó játékosára
	//szintén itt nézzük meg a játék állását és eldöntjük, hogy vége van-e
	public void nextPlayer() {
		//TODO ellenőrizzük a játék állását
		
		//ha még nem volt játékos, akkor most beállítjuk a kezdő játékosra
		//ha pedig volt, akkor a következőre állítjuk
		if(play.getCurrPlayer() == null) {
			play.setCurrPlayer(play.getFirstPlayer());
		} else {
			play.switchPlayer();
		}
		
		//ha a gép jön, akkor elvégzi a lépést
		//ha pedig nem a gép jön (vagy nincs is gép) akkor visszatérünk
		if(play.getCurrPlayer().isComputer()) {
			
		}
	}
	
	
	public void makeMove(Square  startSquare, Square endSquare) {
		Move nowMoved = new Move(startSquare.getCol(), endSquare.getCol(), startSquare.getRow(), endSquare.getRow());
		
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
			Square capturedSquare = squares[capturedPiece.getCol()][capturedPiece.getRow()];
			if(capturedSquare != null) {
				capturedSquare.setPiece(null);
				capturedSquare.setImage(null);
			}
			
			// megnézzük, lehet-e onnan tovább ütni (ütéskényszer van!)
			ArrayList<Move> moveList = play.canCapture(endSquare.getCol(), endSquare.getRow());
			if(moveList != null) {
				if(!moveList.isEmpty()) {
					System.out.println("Lehet tovább ütni");
					//ha lehet tovább ütni, akkor megkeressük, hova lehet ütni, megszínezzük
					//majd ki is jelöljük azt kiindulási lépésnek
					purgeTargetSquares();
					collectTargetSquares(endSquare);
					selectedSquare = endSquare;
					return;
				} 
			}
			// Nem lehet tovább ütni
		}
		
		//alapállapotba állítás
		purgeTargetSquares();
		nextPlayer();
		updateLabels();
	}
	
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
