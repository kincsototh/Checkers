import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Board {
	/**
	 * Attribútumok:
	   * pieces: Piece[][], A táblán lévő figurák, koordinátájuk szerint elhelyezve 
	   * egy kétdimenziós tömbbe. Pl. ha a figura a (3,5) mezőn van, akkor a Piece tömbből 
	   * a Piece[3][5]-el tudjuk elérni. 
	   * columns, rows: int, A tábla méretei: oszlopok és sorok száma
	   */
	private Piece[][] pieces;
	private int columns;
	private int rows;
	
	/**
	   * A konstruktor beállítja a tábla nagyságát kapott két paraméter alapján
	   * @param col: columns, int típusú, az oszlopok száma
	   * @param row: int típusú, a sorok száma
	   */
	public Board(int col, int row) {
		this.columns = col;
		this.rows = row;
		this.pieces = new Piece[col][row];
	}
	
	/**
	   * Visszaadja a tábla oszlopainak számát
	   * @return int Visszatér az oszlopok számával, ami egy int
	   */
	public int getColumns() {
		return this.columns;
	}
	
	/**
	   * Visszaadja a tábla sorainak számát
	   * @return int Visszatér a sorok számával, ami egy int
	   */
	public int getRows() {
		return this.rows;
	}
	
	/**
	   * Koordináta alapján visszaad egy figurát, amely a paraméterek által megadott mezőn áll
	   * @param c: columns, a hivatkozott mező oszlopkoordinátája
	   * @param r: rows, a hivatkozott mező sorkoordinátája
	   * @return Piece Visszatér a mezőn álló figurával, amennyiben a mezőn nincs játékos vagy érvénytelen a 
	   * hivatkozott koordináta, null-lal tér vissza
	   */
	public Piece getPiece(int c, int r){
		if(c < 0 || r < 0 || c >= this.columns || r >= this.rows)
			return null;
		return this.pieces[c][r];
	}
	
	/**
	   * Visszaadja egy játékos összes figuráját
	   * @param player: Player típusú, a játékos, amelynek a figuráit szeretnénk megszerezni
	   * @return ArrayList<Piece> Visszatér a játékos figuráinak listájával
	   */
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
	
	/**
	   * Beállítja, hogy a megadott koordinátájú mezőre a paraméterként kapott figura kerüljön
	   * @param piece: Piece, a mezőre helyezendő figura
	   * @param c: columns, int, a figura helyének oszlopkoordinátája
	   * @param r: rows, int, a figura helyének sorkoordinátája
	   * @exception ArrayIndexOutOfBoundsException Amennyiben a táblán kívülre címeznek
	   * @see ArrayIndexOutOfBoundsException
	   */
	public void setPiece(Piece piece, int c, int r) throws ArrayIndexOutOfBoundsException {
		if(c < 0 || r < 0 || c >= this.columns || r >= this.rows) {
			throw new ArrayIndexOutOfBoundsException();
		}
		this.pieces[c][r] = piece;
		if(piece != null) {
			piece.setCol(c);
			piece.setRow(r);
		}
	}
	
	/**
	   * Töröl figurát a paraméterként adott mezőről (ha nincs rajta figura, akkor nem csinál semmit)
	   * @param c: columns, int a hivatkozott mező oszlopkoordinátája
	   * @param r: rows, int a hivatkozott mező sorkoordinátája
	   * @exception ArrayIndexOutOfBoundsException amennyiben a táblán kívülre címeznénk
	   * @see ArrayIndexOutOfBoundsException 
	   */
	public void deletePiece(int c, int r) throws ArrayIndexOutOfBoundsException {
		if(c < 0 || r < 0 || c >= this.columns || r >= this.rows) {
			throw new ArrayIndexOutOfBoundsException();
		}
		if(this.pieces[c][r] == null)
			return;
		
		this.pieces[c][r] = null;
	}
	
	//koordinata alapjan visszaad egy jatekost. null-t ha nincs ott játékos vagy érvénytelen a koordináta
	/**
	   * Visszaad paraméterként kapott koordináták alapján megadott mezőn lévő figura játékosát
	   * @param c: columns, int a hivatkozott mező oszlopkoordinátája
	   * @param r: rows, int a hivatkozott mező sorkoordinátája
	   * @return Piece Visszatér az adott mezőn lévő figura játékosával vagy null-lal, ha a mezőn nincs
	   * figura
	   */
	public Player getPlayer(int c, int r){
		if (this.getPiece(c, r) != null) {
			return this.getPiece(c, r).getPlayer();
		}
		return null;
	}

	/**
	   * Visszaadja a paraméterként kapott mezőn lévő figura játékosának azonosítóját
	   * @param c: columns, int a hivatkozott mező oszlopkoordinátája
	   * @param r: rows, int a hivazkotozz mező sorkoordinátája
	   * @return int Visszatér a játékos ID-jával (ami ugye 1 vagy -1) vagy pedig 0-val, ha 
	   * az adott mezőn nincs játékos
	   */
	public int getPlayerID(int c, int r){
		Player player = this.getPlayer(c, r);
		if (player != null) 
			return player.getID();
		return 0;
	}
	
	/**
	   * Megmondja, szabad-e a paraméterként koordinátával megadott mező
	   * @param c: columns, int a hivatkozott mező oszlopkoordinátája
	   * @param r: rows, int a hivatkozott mező sorkoordinátája
	   * @return true - ha nincs a mezőn figura (tehát szabad a mező), false - ha van a mezőn figura vagy
	   * a táblánt kívülre hivatkoztunk
	   */
	public boolean isFree(int c, int r) {
		if(c < 0 || r < 0 || c >= this.columns || r >= this.rows)
			return false;
		return (this.pieces[c][r] == null);
	}
	
	// nPieces : jatekosonkent hany figura lehessen
	/**
	   * A tábla inicializálása játék kezdetekor, létrehozza (a modellben) a táblán 
	   * lévő figurákat és koordináta szerint eltárolja őket a pieces kétdimenziós tömbben
	   * @param boardSize, int a tábla valamely mérete (tekintve, hogy mindig négyzet, elég egy koordináta)
	   * @param nPieces, int a figurák száma/játékos
	   * @param player1, Player az első játékos játékosa
	   * @param player2, Player a második játékos játékosa  
	   */
	public void initBoard(int boardSize, int nPieces, Player player1, Player player2) {
		//System.out.println("Initializing board");
		int piecesLeft = nPieces;
		for(int r = 0; r < this.rows; r++) {
			for(int c = 0; c < this.columns; c+=2) {
				Piece newPiece = new Piece(player1, (c + (r % 2)), r);
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
				Piece newPiece = new Piece(player2, (c + (r % 2)), r);
				this.pieces[c + (r % 2)][r] = newPiece;
				piecesLeft--;
				if(piecesLeft <= 0)
					break;
			}
			
			if(piecesLeft <= 0)
				break;
		}	
	}
	
	/**
	   * Ezt a függvényt a fejlesztés során használjuk, ezzel a View (GUI) nélkül lehet követni a játék
	   * állását 
	   */
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
	
	/**
	   * Elmenti a tábla jelenlegi állását, azaz a rajta levő figurákat, valamint a tábla paramétereit
	   * @param writeStream, ObjectOutputStream az a stream, amivel a fájlba írunk, ezt majd fennebbről,
	   * a másik mentés függvényből fogjuk megkapni
	   */
	public void saveBoard(ObjectOutputStream writeStream) {
		//pun intended
		ArrayList<Piece> tmPieces = new ArrayList<Piece>();
		try	{
		    for(int r = this.rows-1; r >= 0; r--) {
				//System.out.print((r) + ": ");
				for(int c = 0; c < this.columns; c++) {
					if(pieces[c][r] != null) {
						tmPieces.add(pieces[c][r]);
					}
				}
			}
		    
		    writeStream.writeObject(tmPieces);
		    writeStream.writeObject(this.columns);
		    writeStream.writeObject(this.rows);
		    writeStream.flush();
		    System.out.println("Sikeres mentes!");
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	/**
	   * Visszatölti a játék állását, azaz a figurák helyét, illetve a tábla mentett paramétereit
	   * @param readStream, ObjectInputStream az a stream, amivel a fájlból olvasunk, ezt fennebbről, a
	   * másik olvasás függvényből fogjuk megkapni
	   */
	@SuppressWarnings("unchecked")
	public void loadBoard(ObjectInputStream readStream) {
		//pun intended
		ArrayList<Piece> tmPieces = new ArrayList<Piece>();
		
		int tmpCol = 0;
		int tmpRow = 0;
		
		File f = new File("test.txt");
		
			if (f.exists()) 
			{
				try	{				    
					tmPieces = (ArrayList<Piece>) readStream.readObject();
					tmpCol = (int) readStream.readObject();
					tmpRow = (int) readStream.readObject();
				    
				    //System.out.println("Columns " + tmpCol + " Rows: " + tmpRow);
				    
				    this.columns = tmpCol;
					this.rows = tmpRow;
					this.pieces = new Piece[this.columns][this.rows];
				    
				    for(int i = 0; i < tmPieces.size(); i++) {
				    	this.pieces[tmPieces.get(i).getCol()][tmPieces.get(i).getRow()] = tmPieces.get(i);
				    }
				    
				    //be kell allitani hogy a lista az az legyen amit most felepitettunk, kulonben nem 
				    //fogja tartalmazni amit visszatoltottunk
				    //this.pieces = newPieces;
				    System.out.println("Sikeres visszatoltes!");
				}
				catch (Exception e) {
				    e.printStackTrace();
				}
			}
	}
}
