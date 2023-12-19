import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Play {
	/**
	 * Attribútumok:
	   * board: Board, a tábla, amin a logikai állás van tárolva
	   * players: Player[], a játékosok tömbje
	   * firstPlayer, currentPlayer: Player, az elsőként lépő játékos, valamint a jelenleg
	   * soron következő játékos
	   * moveCounter: int, a lépésszámláló
	   */
	private Board board;
	private Player[] players;
	private Player firstPlayer;
	private Player currentPlayer;
	private int moveCounter = 1;
	
	/**
	   * A konstruktor beállítja a tábla méreteit, inicializálja azt a paraméterként kapott értékekkel
	   * valamint létrehozza a játékosok tömbjét és beállítja a játékosokat a kapott játékosokra
	   * Eltárolja emellett a kezdő és jelenlegi (soron következő) játékost is
	   * @param boardSize: int, a tábla mérete, itt elég egy mérték, hiszen a tábla négyzet
	   * @param nPieces: int a játékosok figuráinak száma, a tábla inicializálásához szükséges
	   * @param player1: Player, az első számú játékos 
	   * @param player2: Player, a második számú játékos
	   * @param firstPlayer: Player, az a játékos, aki a játékban az első lépést kell tegye
	   */
	public Play(int boardSize, int nPieces, Player player1, Player player2, Player firstPlayer) {
		this.board = new Board(boardSize, boardSize);
		board.initBoard(boardSize, nPieces, player1, player2);
		this.players = new Player[2];
		this.players[0] = player1;
		this.players[1] = player2;
		this.firstPlayer = firstPlayer;
		this.currentPlayer = null;
	}
	
	/**
	   * Visszaadja a soron következő játékost
	   * @return Player Visszatér a soron következő játékossal, ami Player típusú  
	   */
	public Player getCurrPlayer() {
		return this.currentPlayer;
	}
	
	/**
	   * Beállítja a soron következő játékost
	   * @param player: Player, a jelenleg lépésen lévő játékos
	   */
	public void setCurrPlayer(Player player) {
		this.currentPlayer = player;
	}
	
	/**
	   * Visszaadja, melyik játékos az, amelyik először lép/kell lépjen
	   * @return Player Visszatér az első lépést tevő játékossal, ami Player típusú  
	   */
	public Player getFirstPlayer() {
		return this.firstPlayer;
	}
	
	/**
	   * Átállítja a soron következő játékost a másik játékosra, ez akkor kell nekünk, amikor valaki 
	   * lépett, és a másik játékos kell következzen 
	   */
	public void switchPlayer() {
		if(currentPlayer == players[0]) {
			this.currentPlayer = players[1];
		}
		else 
			this.currentPlayer = players[0];
	}
	
	/**
	   * Visszaadja a játék tábláját
	   * @return Board Visszatér a játék táblájával, ami Board típusú  
	   */
	public Board getBoard() {
		return this.board;
	}
	
	/**
	   * Növeli a játék lépésszámlálóját, lépés végén lesz fontos  
	   */
	public void increaseCounter() {
		this.moveCounter++;
	}
	
	/**
	   * Visszaadja a jelenlegi lépésszámot, hogy hányadik lépésnél tartunk
	   * @return int Visszatér azzal, hogy hányadik lépésnél járunk, ami int típusú  
	   */
	public int getCounter() {
		return this.moveCounter;
	}
	
	/**
	   * Visszaad egy úgymond "random"/véletlenszerűen választott lépést
	   * A számítógép elleni játék során ezzel szerez a számítógép egy lépést
	   * @return Move Visszatér a véletlenszerűen választott lépéssel 
	   * null-lal tér vissza, ha nem tud semmit lépni (elfogytak a szabályos lépések)
	   */
	public Move getRandomMove() {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		pieces = board.getPiecesOfPlayer(currentPlayer);
		
		//addig keresünk, ameddig találunk mozgatható figurát vagy elfogynak azok
		while(!pieces.isEmpty()) {
			Piece chosenPiece = pieces.get(ThreadLocalRandom.current().nextInt(0, pieces.size()));
			
			//nézzük meg, hogy egyáltalán léphet-e ez a figura (nincs-e kényszerütés valami más figurára)
			if(!(allowedToMove(chosenPiece).isEmpty())) {
				//kivesszük a pieces listából, mert ezzel nem léphetünk
				pieces.remove(chosenPiece);
				continue;
			}
			
			ArrayList<Move> moveList = new ArrayList<Move>();
			
			moveList = canCapture(chosenPiece.getCol(), chosenPiece.getRow());
			
			
			//ha random figura tud ütni, jó is lesz nekünk, visszatértünk egy random lépéssel
			if(!moveList.isEmpty()) {
				return moveList.get(ThreadLocalRandom.current().nextInt(0, moveList.size()));
			}
			
			//ha random figura nem tud ütni, akkor megnézzük, hova tud lépni, majd ezzel visszatérünk
			moveList = canGo(chosenPiece.getCol(), chosenPiece.getRow());
			if(!moveList.isEmpty()) {
				return moveList.get(ThreadLocalRandom.current().nextInt(0, moveList.size()));
			}
			//sem ütni, sem lépni nem tudott, vegyük ki a listából:
			pieces.remove(chosenPiece);
		}
		//ha kiürült a lista és semmivel nem tudott a gép lépni, akkor vesztett a gép
		//ekkor null-lal térünk vissza
		return null;
	}
	
	/**
	   * Visszaad egy véletlenszerűen választott ütést
	   * Mivel ütéskényszer van, így először mindig meg kell nézni, lehet-e ütni.
	   * Ekkor lehetséges, hogy több szabályos ütés is létezik, ilyenkor ezek közül fogunk
	   * választani egyet véletlenszerűen, majd ezt fogja a gép lépni
	   * @param startCol (start Column): int, azon mező oszlopkoordinátája, amelyről lépni szeretnénk
	   * @param statRow: int, azon mező sorkoordinátája, amelykről lépni szeretnénk
	   * @return Move Visszatér a véletlenszerűen választott ütéssel  
	   */
	public Move getRandomCapture(int startCol, int startRow) {
		ArrayList<Move> moveList = new ArrayList<Move>();
		
		moveList = canCapture(startCol, startRow);
		
		//ha onnan lehet ütni, visszatértünk egy random ütéssel
		if(!moveList.isEmpty()) {
			return moveList.get(ThreadLocalRandom.current().nextInt(0, moveList.size()));
		}
		
		//ha nem tudtunk ütni, akkor null-lal térünk vissza
		return null;
	}
	
	/**
	   * Visszaadja azon lépések listáját, amiket szabályosan lehet lépni a paraméterként megadott
	   * koordinátájú mezőről (itt nem ütést nézünk, hanem csak "csúsztatást")
	   * @param c (column): int, azon mező oszlopkoordinátája, ahonnan lépni szeretnénk
	   * @param r (row): int, azon mező sorkoordinátája, ahonnan lépni szeretnénk
	   * @return ArrayList<Move> Visszatér azoknak a lépéseknek a listájával, amiket léphet az adott
	   * mezőn lévő figura
	   */
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
				tmp.add(new Move(c, r, h, v)); 
			}
		}
		
		if(piece.isQueen()) {
			for(int h = c-1; h <= c+1; h+=2) {
				int v = (r - playerNow.getID());
				if (board.isFree(h, v)) {
					tmp.add(new Move(c, r, h, v)); 
				}
			}
		}
		
		return tmp;
	}
	
	/**
	   * Visszaadja azon lépések listáját, amiket szabályosan lehet ütni a paraméterként megadott
	   * koordinátájú mezőről (itt csak ütést nézünk)
	   * @param c (column): int, azon mező oszlopkoordinátája, ahonnan lépni szeretnénk
	   * @param r (row): int, azon mező sorkoordinátája, ahonnan lépni szeretnénk
	   * @return ArrayList<Move> Visszatér azoknak a lépéseknek a listájával, amivel üthet az adott
	   * mezőn lévő figura
	   */
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
					tmp.add(new Move(c, r, h+dir, v+playerNow.getID())); 
				}
			}
		}
		
		if(piece.isQueen()) {
			for(int dir = -1; dir <= +1; dir+=2) {
				int h = c + dir;
				int v = (r - playerNow.getID());
				if (board.getPlayerID(h, v) == - playerNow.getID()) {
					if(board.isFree(h+dir, v-playerNow.getID())) {
						tmp.add(new Move(c, r, h+dir, v-playerNow.getID())); 
					}
				}
			}
		}
		
		return tmp;
	}
	
	/**
	   * Visszaadja, hogy tud-e a paraméterként adott játékos bármely figurája szabályosan lépni
	   * @param player: Player, az a játékos, akinek a figuráit szeretnénk ellenőrizni/megnézni
	   * @return true - ha van figurája, amellyel tud lépni, false - ha elfogytak a figurái/nem tud 
	   * szabályosat lépni
	   */
	public boolean canMove(Player player) {
		ArrayList<Piece> pieces = this.board.getPiecesOfPlayer(player);
		
		for(int i = 0; i < pieces.size(); i++) {
			Piece piece = pieces.get(i);
			if(piece != null) {
				if(!(canCapture(piece.getCol(), piece.getRow()).isEmpty())) {
					return true;
				}
				if(!(canGo(piece.getCol(), piece.getRow()).isEmpty())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	   * Visszaadja azon figurák listáját, amelyek tudnak lépni, ez ahhoz kell, hogy tudjuk színezni
	   * a mezőket és hogy eldönthessük, szabad-e lépni a kiválasztott figurával
	   * @param piece: Piece, az a figura, amit mi "kiválasztottunk"
	   * @return ArrayList<Piece> ez üres lista, ha léphetünk a figurával
	   * Nem üres lista, ha nem szabad ellépnünk, és a listában az ütésre kényszeríett 
	   * lehetséges figurák vannak
	   */
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

	
	/**
	   * Általános lépést végrehajt, ha kell, üt, visszaadja a leütött figurát (amelyik már nem része a
	   * Board-nak, de a GUI miatt szükség van az adataira)
	   * @param move: Move, a végrehajtandó lépést
	   * @return Piece visszatér a leütött figurával (ha történt ütés), null-lal tér vissza, ha nem volt leütött
	   * figura
	   */
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
	
	/**
	   * Elmenti a játék jelenlegi állását, azaz a tábla állását, a játékosokat, a lépésszámlálót.
	   * A tábla elmentéséhez meghívja a Board save() függvényét, paraméterként átadja neki azt a stream-et,
	   * amelyet már "nyitott" a play-nek
	   */
	public void savePlay() {
		try	{
		    FileOutputStream writeData = new FileOutputStream("test.txt");
		    ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
		    
		    this.board.saveBoard(writeStream);

		    writeStream.writeObject(players[0]);
		    writeStream.writeObject(players[1]);
		    writeStream.writeObject(firstPlayer);
		    writeStream.writeObject(currentPlayer);
		    writeStream.writeObject(moveCounter);
		    
		    writeStream.flush();
		    writeStream.close();
		    System.out.println("Sikeres mentes!");
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
	}

	/**
	   * Visszatölti a játék állását, azaz a figurák helyét, a tábla mentett paramétereit, a játékosokat,
	   * a lépésszámlálót.
	   * A tábla visszatöltéséhez a Board osztály load() függvényét használja, paraméterként pedig átadja neki,
	   * melyik stream-et használjuk
	   */
	public void loadPlay() {
		File f = new File("test.txt");
		
			if (f.exists()) 
			{	
				try	{
					//test.txt-be mentettunk
				    FileInputStream readData = new FileInputStream("test.txt");
				    ObjectInputStream readStream = new ObjectInputStream(readData);
				    
					this.board.loadBoard(readStream);
				    
					this.players[0] = (Player) readStream.readObject();
					//System.out.println(players[0].getName());
					this.players[1] = (Player) readStream.readObject();
					this.firstPlayer = (Player) readStream.readObject();
					this.currentPlayer = (Player) readStream.readObject();
					this.moveCounter = (int) readStream.readObject();
				    readStream.close();
				
				    System.out.println("Sikeres visszatoltes!");
				}
				catch (Exception e) {
				    e.printStackTrace();
				}
			}
	}
}
