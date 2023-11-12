import java.util.ArrayList;


public class Play {
	private Board board;
	private Player[] players;
	//private Move[] moves;
	private Player firstPlayer;
	private Player currentPlayer;
	
	public Play(int boardSize, int nPieces, Player player1, Player player2, Player firstPlayer) {
		this.board = new Board(boardSize, boardSize);
		board.initBoard(boardSize, nPieces, player1, player2);
		this.players = new Player[2];
		this.players[0] = player1;
		this.players[1] = player2;
		this.firstPlayer = firstPlayer;
		this.currentPlayer = null;
	}
	
	public Player getCurrPlayer() {
		return this.currentPlayer;
	}
	
	public void setCurrPlayer(Player player) {
		this.currentPlayer = player;
	}
	
	public Player getFirstPlayer() {
		return this.firstPlayer;
	}
	
	public void switchPlayer() {
		if(currentPlayer == players[0]) {
			this.currentPlayer = players[1];
		}
		else 
			this.currentPlayer = players[0];
	}
	
	public void printBoard() {
		this.board.printBoard();
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public void setQueen(int c, int r) {
		this.board.getPiece(c, r).setQueen();
	}
	
	public ArrayList<Move> canGo(int c, int r) {
		ArrayList<Move> tmp = new ArrayList<Move>();
		Piece piece = board.getPiece(c, r);
		if(piece == null)
			return null;
		
		Player playerNow = piece.getPlayer();
		
		// Hova léphet:
		
		for(int h = c-1; h <= c+1; h+=2) {
			int v = (r + playerNow.getID());
			if (board.isFree(h, v)) {
				tmp.add(new Move(c, h, r, v)); 
			}
		}
		
		if(piece.isQueen()) {
			for(int h = c-1; h <= c+1; h+=2) {
				int v = (r - playerNow.getID());
				if (board.isFree(h, v)) {
					tmp.add(new Move(c, h, r, v)); 
				}
			}
		}
		
		return tmp;
	}
	
	public ArrayList<Move> canCapture(int c, int r) {
		ArrayList<Move> tmp = new ArrayList<Move>();
		Piece piece = board.getPiece(c, r);
		if(piece == null)
			return tmp;
		Player playerNow = piece.getPlayer();

		// Hova léphet:
		// dir: jobbra/balra
		for(int dir = -1; dir <= +1; dir+=2) {
			int h = c + dir;
			int v = (r + playerNow.getID());
			if (board.getPlayerID(h, v) == - playerNow.getID()) {
				if(board.isFree(h+dir, v+playerNow.getID())) {
					tmp.add(new Move(c, h+dir, r, v+playerNow.getID())); 
				}
			}
		}
		
		if(piece.isQueen()) {
			for(int dir = -1; dir <= +1; dir+=2) {
				int h = c + dir;
				int v = (r - playerNow.getID());
				if (board.getPlayerID(h, v) == - playerNow.getID()) {
					if(board.isFree(h+dir, v-playerNow.getID())) {
						tmp.add(new Move(c, h+dir, r, v-playerNow.getID())); 
					}
				}
			}
		}
		
		return tmp;
	}
	
	//megnézzük, hogy az ütéskényszer miatt van-e más figura, aki tud ütni, mert akkor ez 
	//nem mozdulhat addig el
	public ArrayList<Piece> allowedToMove(Piece piece){
		ArrayList<Piece> pieces = this.board.getPiecesOfPlayer(currentPlayer);
		ArrayList<Piece> myPiece = new ArrayList<Piece>();
		
		//mi magunk tudunk ütni? 
		if(piece != null) {
			if(!(canCapture(piece.getCol(), piece.getRow()).isEmpty())) {
				//üres listával visszatérünk
				return myPiece;
			}
		}
		
		//a többiek tudnak ütni, én pedig nem, akkor nem mozoghatok
		for(int i = 0; i < pieces.size(); i++) {
			Piece otherPiece = pieces.get(i);
			if((otherPiece != null) && (otherPiece != piece)) {
				//
				if(!(canCapture(otherPiece.getCol(), otherPiece.getRow()).isEmpty())) {
					myPiece.add(otherPiece);
				}
			}
		}
		
		//visszaadjuk a listát, amiben az ütésre kényszeríett figurák vannak
		//ez üres lista, ha nincs más, aki tud ütni
		return myPiece;
	}
	
	
	//figura mozgatasa a tablan
		public void forceMove(int c_start, int r_start, int c_where, int r_where) {
			if(c_where < 0 || r_where < 0 || c_where >= board.getColumns() || r_where >= board.getRows())
				return;
			if(!board.isFree(c_where, r_where) )
				return;
			
			Piece piece = board.getPiece(c_start, r_start);
			if(piece != null) {
				board.setPiece(piece, c_where, r_where);
				board.setPiece(null, c_start, r_start);
			}
		}
	
		
		//általános lépést végrehajt, ha kell, üt
		//visszaadja a leütött figurát (amelyik már nem része a Board-nak, de a GUI miatt szükség van az adataira)
		public Piece makeMove(Move move) {
			Piece toDelete = null;
			if(move.getEndCol() < 0 || move.getEndRow() < 0 || move.getEndCol() >= board.getColumns() || move.getEndRow() >= board.getRows())
				return null;
			if(!board.isFree(move.getEndCol(), move.getEndRow()) )
				return null;
			
			Piece piece = board.getPiece(move.getStartCol(), move.getStartRow());
			if(piece != null) {
				board.setPiece(piece, move.getEndCol(), move.getEndRow());
				board.setPiece(null, move.getStartCol(), move.getStartRow());
				if((Math.abs((move.getStartCol() - move.getEndCol())) == 2) && (Math.abs((move.getStartRow() - move.getEndRow())) == 2)) {
					int delCol = (move.getStartCol() + move.getEndCol())/2;
					int delRow = (move.getStartRow() + move.getEndRow())/2;
					toDelete = board.getPiece(delCol, delRow);
					board.deletePiece(delCol, delRow);	
				}
				
				//ellenőrizzük, hogy beért-e az alapsorra a figura, mert akkor átállítjuk királynőre
				if((piece.getPlayer() == players[0]) && (piece.getRow() == board.getRows()-1)) {
					piece.setQueen();
				} 
				if((piece.getPlayer() == players[1]) && (piece.getRow() == 0)) {
					piece.setQueen();
				} 
			}
			
			return toDelete;
		}
		
		/*public void save() {
			try	{
			    FileOutputStream writeData = new FileOutputStream("checkers.txt");
			    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
			    
			    for(int c = 0; c < board.getColumns(); c++)
			    	writeStream.writeObject(board.);
			    	writeStream.flush();
			    	writeStream.close();
			    	System.out.println("Sikeres mentes!");
			}
			catch (IOException e) {
			    e.printStackTrace();
			}
		}
		
		public void load() {
			File f = new File("test.txt");
			
			if (f.exists()) {	
				try	{
				    FileInputStream readData = new FileInputStream("checkers.txt");
				    ObjectInputStream readStream = new ObjectInputStream(readData);
				    
				    @SuppressWarnings("unchecked")
					ArrayList<Beer> beer = (ArrayList<Beer>) readStream.readObject();
				    readStream.close();
				    
				    System.out.println(beer.toString());
				    
				    //be kell allitani hogy a lista az az legyen amit most felepitettunk, kulonben nem 
				    //fogja tartalmazni amit visszatoltottunk
				    lt = beer;
				    System.out.println("Sikeres visszatoltes!");
				}
				catch (Exception e) {
				    e.printStackTrace();
				}
			}
		}
		
		/*
		public void paintBoard() {
			 frame = new JFrame();
		     frame.setBounds(10, 10, 600, 600);
		     panel = new JPanel(){
		    	@Override
		     	public void paint(Graphics g) {
		            boolean white=true;
		            
		           for(int y= 0;y<8;y++){
		            	for(int x= 0;x<8;x++){
		            		if(white){
		            			g.setColor(new Color(235,235, 208));
		            		} else {
		            			g.setColor(new Color(119, 148, 85));
		            		}
		            		
		            		g.fillRect(x*64, y*64, 64, 64);
		            		white=!white;
		            	}
		            	white=!white;
		            }
		    	}
		     };
		    
		    paintPieces();
		    frame.add(panel);
		    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    frame.setVisible(true);
		}
		
		//lépés ütés nélkül
		public void makeGo(Move move) {
			if(move.getEndCol() < 0 || move.getEndRow() < 0 || move.getEndCol() >= board.getColumns() || move.getEndRow() >= board.getRows())
				return;
			if(!board.isFree(move.getEndCol(), move.getEndRow()) )
				return;
			
			Piece piece = board.getPiece(move.getStartCol(), move.getStartRow());
			if(piece != null) {
				board.setPiece(piece, move.getEndCol(), move.getEndRow());
				board.setPiece(null, move.getStartCol(), move.getStartRow());
			}
		}
		
		//ütés
		public void makeCapture(Move move) {
			if(move.getEndCol() < 0 || move.getEndRow() < 0 || move.getEndCol() >= board.getColumns() || move.getEndRow() >= board.getRows())
				return;
			if(!board.isFree(move.getEndCol(), move.getEndRow()) )
				return;
			
			Piece piece = board.getPiece(move.getStartCol(), move.getStartRow());
			if(piece != null) {
				board.setPiece(piece, move.getEndCol(), move.getEndRow());
				board.setPiece(null, move.getStartCol(), move.getStartRow());
				board.deletePiece((move.getStartCol() + move.getEndCol())/2, (move.getStartRow() + move.getEndRow())/2);
			}
		}*/
	}
