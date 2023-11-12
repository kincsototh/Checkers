/*public class Board2 {
	private Square[][] squares;
	private int columns = 0;
	private int rows = 0;
	
	// lehessen allitani a meretet
	public Board2(int col, int row) {
		this.columns = col;
		this.rows = row;
		squares = new Square[col][row];
	}
	
	public int getColumns() {
		return this.columns;
	}
	
	public int getRows() {
		return this.rows;
	}
	
	//koordinata alapjan visszaad egy mezot
	public Square getSquare(int c, int r) {
		if(c < 0 || r < 0 || c >= this.columns || r >= this.rows)
			return null;
		return this.squares[c][r];
	}
	
	// nPieces : jatekosonkent hany figura lehessen
	public void initBoard(int boardSize, int nPieces, Player player1, Player player2) {
		System.out.println("Initializing board");
		int piecesLeft = nPieces;
		for(int r = 0; r < this.rows; r++) {
			for(int c = 0; c < this.columns; c+=2) {
				Square newSquare = new Square(new Piece(player1), c + (r % 2), r);
				System.out.println("New square: c=" + newSquare.getCol() + " r=" + newSquare.getRow());
				this.squares[c][r] = newSquare;
				piecesLeft--;
			}
			
			if(piecesLeft == 0)
				break;
		}
	}
	
	public void printBoard() {
		for(int r = this.rows-1; r >= 0; r--) {
			for(int c = 0; c < this.columns; c++) {
				if(squares[c][r] != null) {
					System.out.print(squares[c][r].getPiece().getPlayer().getID());
				}
				else 
					System.out.print("0");
			}
			System.out.println();
		}
	}
}*/
