import java.io.Serializable;

@SuppressWarnings("serial")
public class Piece implements Serializable {
	/**
	 * Attribútumok:
	   * UNKNOWN: Beállítjuk a koordinátákat alapból -1-re, 
	   * hogy ha esetleg később nem állítja be őket senki, 
	   * akkor hibát kapjunk, mert kicímzünk a táblából.
	   * isQueen: boolean, Alapvetően beállítjuk false-ra, 
	   * mert a figurák kezdetben nem lehetnek vezérek
	   * player: Player, A figura tulajdonosa
	   * col, row: int, Oszlop- és sorkoordinátája a figurának
	   */
	private boolean isQueen = false;
	private Player player;	
	public static final int UNKNOWN = -1;
	private int col = UNKNOWN;
	private int row = UNKNOWN;
	
	/**
	   * A konstruktor beállítja a figura játékosát, valamint a helyét a táblán (ehhez 
	   * a koordinátáit állítjuk be)
	   * @param player: a figura "tulajdonosa"
	   * @param col: column, hányadik "oszlopban" található a figura
	   * @param row: hányadik "sorban" található a figura
	   */
	public Piece(Player p, int col, int row) {
		this.player = p;
		this.col = col;
		this.row = row;
	}
	
	/**
	   * Ez a konstruktor pedig egy játékos alapján az alap értékekre inicializál,
	   * valamint beállítja a figura játékosát
	   * A teszteléshez használjuk ezt a konstruktort
	   * @param p: a figura játékosa
	   */
	public Piece(Player p) {
		this.player = p;
	}
	
	/**
	   * Visszaadja, az adott figura vezér/király-e 
	   * @return true: ha a figura vezér/király, false: ha a figura nem vezér/király
	   */
	public boolean isQueen() {
		return this.isQueen;
	}
	
	/**
	   * Beállítja a figurát vezérré/királlyá
	   */
	public void setQueen() {
		this.isQueen = true;
	}
	
	/**
	   * Visszaadja a figura játékosát 
	   * @return Player visszaadja a hozzá tartozó játékost
	   */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	   * Visszaadja a figura "képét" (ami valójában egy String, hiszen a projektben 
	   * csak a fájl nevével tudunk rá hivatkozni 
	   * @return String A kép nevével tér vissza, ami egy String.
	   * A kép nevét úgy állítja elő, hogy a játékos színét veszi (ami a szín első karaktere mindig, tehát 
	   * ha zöld a játékos színe, akkor g a színe), majd mögé teszi, hogy "K.png" amennyiben vezér/király
	   * a figura (King), vagy pedig, hogy "M.png", amennyiben a figura gyalog még (Man). A projektben a 
	   * képek direkt így vannak eltárolva, ezzel is elkerülve a későbbi problémákat a grafikai 
	   * megjelenítéskor.
	   */
	public String getImage() {
		if(isQueen) {
			return this.player.getColor() + "K.png";
		}
		
		return this.player.getColor() + "M.png";
	}
	
	/**
	   * Visszatér a figura helyének oszlopszámával (pl ha a figura a (0,1) mezőn áll, akkor 0-val)
	   * @return int Visszatér az oszlopkoordinátával, ami egy int 
	   */
	public int getCol() {
		return this.col;
	}

	/**
	   * Beállítja a kapott koordinátára a figura oszlopkoordinátáját.
	   * @param col: column,  ez egy int, amelyre be szeretnénk állítani a figura helyét 
	   */
	public void setCol(int col) {
		this.col = col;
	}
	
	/**
	   * Visszaadja a figura helyének sorának számát (pl ha a figura a (0,1) mezőn áll, akkor az 1-et)
	   * @return int Visszatér a sorkoordinátával, ami egy int
	   */
	public int getRow() {
		return this.row;
	}

	/**
	   * Beállítja a figura sorkoordinátáját a paraméterként kapott számra
	   * @param row: ez egy int, amelyre be szeretnénk állítani a figura helyét
	   */
	public void setRow(int row) {
		this.row = row;
	}
}
