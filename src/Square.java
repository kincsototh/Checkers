import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Square extends JButton {
	// attributumok: 
	// piece : Piece, ez a mezon levo figura
	// col : int, ez az egyenes (fuggoleges)
	// row : int, ez a sor (vizszintes)
	private Color color;
	private Piece piece = null;
	private int col = 0;
	private int row = 0;
	
	// konstruktor 
	// param: 
	// 
	public Square(Piece piece, Color color, int col, int row) {
		super();
		this.piece = piece;
		this.color = color;
		this.setBackground(color);
		this.col = col;
		this.row = row;
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public void setPiece(Piece p) {
		this.piece = p;
	}
	
	public void setCol(int c) {
		this.col = c;
	}
	
	public void setRow(int r) {
		this.row = r;
	}
	
	public void setColor(Color color) {
		this.setBackground(color);
	}
	
	public void setDefaultColor() {
		this.setBackground(this.color);
	}
	
	public void setImage(String image) {
		if(image == null) {
			this.setIcon(null);
			return;
		}
		
		ImageIcon icon = new ImageIcon(image);
		this.setIcon(icon);
	}
}
