import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Square extends JButton {
	/**
	 * Attribútumok:
	   * color : Color, ez a mező színe
	   * piece : Piece, ez a mezőn lévő figura
	   * col : int, ez az egyenes/oszlop (függőleges)
	   * row : int, ez a sor (vízszintes)
	   */
	private Color color;
	private Piece piece = null;
	private int col = 0;
	private int row = 0;
	
	/**
	   * A konstruktor beállítja a mező attribútumait, azaz a rajta lévő figurát 
	   * (ha van, ha nem akkor marad null), a mező színét, a mező koordinátáit
	   * @param piece: Piece, a mezőre helyezendő figura
	   * @param color: Color, a mező színe
	   * @param col (column): int, a mező oszlopkoordinátája
	   * @param row: int, a mező sorkoordinátája
	   */
	public Square(Piece piece, Color color, int col, int row) {
		this.piece = piece;
		this.color = color;
		this.setBackground(color);
		this.col = col;
		this.row = row;
	}
	
	/**
	   * Visszaadja a mezőn lévő figurát
	   * @return Piece Visszatér a mezőn lévő figurával, ami egy Piece  
	   */
	public Piece getPiece() {
		return this.piece;
	}
	
	/**
	   * Beállítja a mezőn lévő figurát a paraméterként kapott figurára
	   * @param p (piece): Piece a beállítandó figura
	   */
	public void setPiece(Piece p) {
		this.piece = p;
	}

	/**
	   * Visszaadja a mező oszlopkoordinátáját
	   * @return int Visszatér a mező oszlopkoordinátájával, ami egy int
	   */
	public int getCol() {
		return this.col;
	}
	
	/**
	   * Beállítja az oszlopkoordinátát a paraméterként kapott értékre
	   * @param c (columns): int, az oszlopkoordináta, amire át szeretnénk állítani a mező
	   * oszlopkoordinátáját
	   */
	public void setCol(int c) {
		this.col = c;
	}

	/**
	   * Visszaadja a mező sorkoordinátáját
	   * @return int Visszatér a mező sorkoordinátájával, ami egy int 
	   */
	public int getRow() {
		return this.row;
	}
	
	/**
	   * Beállítja a sorkoordinátát a paraméterként kapott értékre
	   * @param r (rows): int, a sorkoordináta, amire át szeretnénk állítani a mező sorkoordinátáját
	   */
	public void setRow(int r) {
		this.row = r;
	}
	
	/**
	   * Visszaállítja az alap színére a mezőt (ez van eltárolva, de lehet ideiglenesen más volt a színe
	   * és vissza kell állítanunk)
	   */
	public void setDefaultColor() {
		this.setBackground(this.color);
	}
	
	/**
	   * Beállítja a mezőn a figura képét (tehát vagy az üres korong, vagy a vezér)
	   * ha van az adott mezőn figura, ha nincs, akkor null-t állít be, tehát a mezőn nem lesz figura 
	   * grafikusan sem
	   * @param image: String, a kép neve
	   */
	public void setImage(String image) {
		if(image == null) {
			this.setIcon(null);
			return;
		}
		
		ImageIcon icon = new ImageIcon(image);
		this.setIcon(icon);
	}
}
