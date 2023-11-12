import java.util.*;

public class Board {
	private Piece[][] pieces;
	private int columns = 0;
	private int rows = 0;
	
	// lehessen allitani a meretet
	public Board(int col, int row) {
		this.columns = col;
		this.rows = row;
		this.pieces = new Piece[col][row];
	}
	
	public int getColumns() {
		return this.columns;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	//koordinata alapjan visszaad egy figurat. null-t ha nincs ott játékos vagy érvénytelen a koordináta
	public Piece getPiece(int c, int r) {
		if(c < 0 || r < 0 || c >= this.columns || r >= this.rows)
			return null;
		return this.pieces[c][r];
	}
	
	
	//egy játékos összes figurájának listáját szerezzük meg 
	public ArrayList<Piece> getPiecesOfPlayer(Player player){
		ArrayList<Piece> piecesOfPlayer = new ArrayList<Piece>();
		for(int c = 0; c < this.columns; c++) {
			for (int r = 0; r < this.rows; r++) {
				Piece piece = pieces[c][r];
				if(piece != null) {
					if(piece.getPlayer() == player) {
						piecesOfPlayer.add(piece);
					}
				}
			}
		}
		
		return piecesOfPlayer;
	}
	
/*	
	public int getPieceCol(Piece piece) {
		if(piece == null)
			return -1;
		
		for(int c= 0; c < columns; c++) {
			for(int r = 0; r< rows; r++) {
				if(pieces[c][r] == piece) {
					return c;
				}
			}
		}
		
		return -1;
	}
	
	public int getPieceRow(Piece piece) {
		if(piece == null)
			return -1;
		
		for(int c= 0; c < columns; c++) {
			for(int r = 0; r< rows; r++) {
				if(pieces[c][r] == piece) {
					return r;
				}
			}
		}
		
		return -1;
	}*/
	
	public void setPiece(Piece piece, int c, int r) {
		if(c < 0 || r < 0 || c >= this.columns || r >= this.rows)
			return;
		this.pieces[c][r] = piece;
		if(piece != null) {
			piece.setCol(c);
			piece.setRow(r);
		}
	}
	
	public void deletePiece(int c, int r) {
		if(c < 0 || r < 0 || c >= this.columns || r >= this.rows)
			return;
		if(this.pieces[c][r] == null)
			return;
		
		this.pieces[c][r] = null;
	}
	
	//koordinata alapjan visszaad egy jatekost. null-t ha nincs ott játékos vagy érvénytelen a koordináta
	public Player getPlayer(int c, int r) {
		if (this.getPiece(c, r) != null) {
			return this.getPiece(c, r).getPlayer();
		}
		return null;
	}

	public int getPlayerID(int c, int r) {
		Player player = this.getPlayer(c, r);
		if (player != null) 
			return player.getID();
		return 0;
	}
	
	// Megmondja, hogy szabad-e az adott mező (false, ha van ott valaki vagy ha kívül van a táblán)
	public boolean isFree(int c, int r) {
		if(c < 0 || r < 0 || c >= this.columns || r >= this.rows)
			return false;
		return (this.pieces[c][r] == null);
	}
	
	
	
	// nPieces : jatekosonkent hany figura lehessen
	public void initBoard(int boardSize, int nPieces, Player player1, Player player2) {
		System.out.println("Initializing board");
		int piecesLeft = nPieces;
		for(int r = 0; r < this.rows; r++) {
			for(int c = 0; c < this.columns; c+=2) {
				Piece newPiece = new Piece(player1, "wM.png", (c + (r % 2)), r);
				this.pieces[c + (r % 2)][r] = newPiece;
				piecesLeft--;
				if(piecesLeft <= 0)
					break;
			}
			
			if(piecesLeft <= 0)
				break;
		}
		
		piecesLeft = nPieces;
		
		for(int r = this.rows-1; r >= 0; r--) {
			for(int c = 0; c < this.columns; c+=2) {
				Piece newPiece = new Piece(player2, "bM.png", (c + (r % 2)), r);
				this.pieces[c + (r % 2)][r] = newPiece;
				piecesLeft--;
				if(piecesLeft <= 0)
					break;
			}
			
			if(piecesLeft <= 0)
				break;
		}	
	}
	
	public void printBoard() {
		System.out.println("Board status: ");
		for(int r = this.rows-1; r >= 0; r--) {
			System.out.print((r) + ": ");
			for(int c = 0; c < this.columns; c++) {
				if(pieces[c][r] != null) {
					System.out.format("[%2d]",pieces[c][r].getPlayer().getID());
				}
				else 
					System.out.print("[  ]");
			}
			System.out.println();
		}
	}
}
