public class Move {
	/**
	 * Attribútumok:
	   * Lépést definiálja a kezdő koordinátája és a végső koordinátája, 
	   * azaz honnan lépett a figura és hova.
	   */
	private int start_col;
	private int end_col;
	private int start_row;
	private int end_row;

	/**
	   * A konstruktor beállítja egy lépés kezdő koordinátáit és végkoordinátáit (ezzel definiáljuk a lépést,
	   * honnan-hova)
	   * @param start_c: int, kezdő oszlopkoordináta
	   * @param start_r: int, kezdő sorkoordináta
	   * @param end_c: int, végső oszlopkoordináta
	   * @param end_r: int, végső sorkoordináta 
	   */
	public Move(int start_c, int start_r, int end_c, int end_r) {
		this.start_col = start_c;
		this.end_col = end_c;
		this.start_row = start_r;
		this.end_row = end_r;
	}
	
	/**
	   * Visszaadja a kezdő oszlopkoordinátát
	   * @return int Visszatér a kezdő oszlopkoordinátával, ami egy int  
	   */
	public int getStartCol() {
		return this.start_col;
	}
	
	/**
	   * Beállítja a kezdő oszlopkoordinátát a paraméterként kapott értékre
	   * @param start_c: int, az oszlopkoordináta, amire át szeretnénk állítani a kezdő oszlopkoordinátát
	   */
	public void setStartCol(int start_c) {
		this.start_col = start_c;
	}
	
	//end_col getter és setter
	/**
	   * Visszaadja a végső oszlopkoordinátát
	   * @return int Visszatér a végső oszlopkoordinátával, ami egy int  
	   */
	public int getEndCol() {
		return this.end_col;
	}
	
	/**
	   * Beállítja a végső oszlopkoordinátát a paraméterként kapott értékre
	   * @param end_c: int, az oszlopkoordináta, amire át szeretnénk állítani a végső oszlopkoordinátát
	   */
	public void setEndCol(int end_c) {
		this.end_col = end_c;
	}
	
	
	//start_row getter és setter
	/**
	   * Visszaadja a kezdő sorkoordinátát
	   * @return int Visszatér a kezdő sorkoordinátával, ami egy int  
	   */
	public int getStartRow() {
		return this.start_row;
	}
	
	/**
	   * Beállítja a kezdő sorkoordinátát a paraméterként kapott értékre
	   * @param start_r: int, a sorkoordináta, amire át szeretnénk állítani a kezdő sorkoordinátát
	   */
	public void setStartRow(int start_r) {
		this.start_row = start_r;
	}
	
	//end_row getter és setter
	/**
	   * Visszaadja a végső sorkoordinátát
	   * @return int Visszatér a végső sorkoordinátával, ami egy int  
	   */
	public int getEndRow() {
		return this.end_row;
	}
	
	/**
	   * Beállítja a végső sorkoordinátát a paraméterként kapott értékre
	   * @param end_r: int, a sorkoordináta, amire át szeretnénk állítani a végső sorkoordinátát
	   */
	public void setEndRow(int end_r) {
		this.end_row = end_r;
	}
	
	public void printMove() {
		System.out.println(" START: " + this.start_col + " : " + this.start_row + "  END: " + this.end_col + " : " + this.end_row);
	}
	
	/**
	   * Megmondja, a két lépés egyenlő-e (amire meghívtuk és amit paraméterként kapunk)
	   * Ezt a tesztesetekhez használjuk, és azért szükséges, mivel két Move objektum hivatkozhat ugyanarra 
	   * a lépésre, attól még nem lesz a két objektum egyenlő, így ezzel tudjuk megnézni ezt.
	   * @param otherMove: Move, a másik lépés, amivel össze szeretnénk hasonlítani
	   * @return true - ha a kezdő koordináták és a végső koordináták is megegyeznek, azaz ugyanazt a 
	   * lépés írják le, false - ha nem ugyanazt írják le, azaz eltérnek legalább egy koordinátában
	   */
	public boolean isEquals(Move otherMove) {
		int otherStartCol = otherMove.getStartCol();
		int otherStartRow = otherMove.getStartRow();
		int otherEndCol = otherMove.getEndCol();
		int otherEndRow = otherMove.getEndRow();
		
		if(this.start_col == otherStartCol && this.start_row == otherStartRow && this.end_col == otherEndCol && this.end_row == otherEndRow) {
			return true;
		}
		
		return false;
	}
	
	
}
