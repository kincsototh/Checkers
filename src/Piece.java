public class Piece {
	// attributumok
	//
	private boolean isQueen = false;
	private Player player;
	private String image;
	private int col;
	private int row;
	
	public Piece(Player p, String image, int col, int row) {
		this.player = p;
		this.image = image;
		this.col = col;
		this.row = row;
	}
	
	//getterek es setterek
	
	public boolean isQueen() {
		return this.isQueen;
	}
	
	public void setQueen() {
		this.isQueen = true;
		if(this.getPlayer().getID() == -1) {
			image = "bK.png";
		}
		else 
			image = "wK.png";
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public String getImage() {
		return this.image;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public int getRow() {
		return this.row;
	}
	
}
