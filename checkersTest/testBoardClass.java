
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class testBoardClass {
	Board testBoard;
	Player testPlayer1;
	Player testPlayer2;
	ArrayList<Piece> piecesOfPlayer1;
	ArrayList<Piece> piecesOfPlayer2;
	
	@Before
	public void setUp()  {
		testBoard = new Board(4, 4);
		testPlayer1 = new Player(Player.PLAYERONE, "testPlayerOne", false, "w");
		testPlayer2 = new Player(Player.PLAYERTWO, "testPlayerTwo", false, "m");
		
		piecesOfPlayer1 =  new ArrayList<Piece>(
				List.of(new Piece(testPlayer1), new Piece(testPlayer1))
		); 
		
		piecesOfPlayer2 =  new ArrayList<Piece>(
				List.of(new Piece(testPlayer2), new Piece(testPlayer2))
		); 
		
		testBoard.setPiece(piecesOfPlayer1.get(0), 0, 0);
		testBoard.setPiece(piecesOfPlayer1.get(1), 2, 0);
		testBoard.setPiece(piecesOfPlayer2.get(0), 1, 3);
		testBoard.setPiece(piecesOfPlayer2.get(1), 3, 3);
	}
	
	/**
	 * A getPlayerTest, illetve a getNoPlayerTest-ben ellenőrizzük, 
	 * hogy vissza tudja-e adni a Board a koordináták alapján, 
	 * hogy melyik játékos figurája van az adott helyen, illetve null-t, 
	 * ha nincs ott figura.
	   */
	@Test
	public void getPlayerTest() {
		Player testPlayer = testBoard.getPlayer(2, 0);
		Assert.assertEquals(testPlayer, testPlayer1);
	}
	
	@Test
	public void getNoPlayerTest() {
		Player testPlayer = testBoard.getPlayer(2, 3);
		Assert.assertEquals(testPlayer, null);
	}
	
	/**
	 * A getPiecesOfPlayerTest metódussal ellenőrizzük, 
	 * hogy ha lekérdezzük egy játékos figurának listáját, 
	 * akkor valóban megtalálja-e a program az összeset.
	 */
	@Test
	public void piecesOfPlayerTest() {
		ArrayList<Piece> testPieceList = testBoard.getPiecesOfPlayer(testPlayer1);
		Assert.assertTrue(testPieceList.containsAll(piecesOfPlayer1) && piecesOfPlayer1.containsAll(testPieceList));
		Assert.assertFalse(testPieceList.containsAll(piecesOfPlayer2) || piecesOfPlayer2.containsAll(testPieceList));
	}
	
	/**
	 * Levesszük a deletePiece metódusokkal az egyik játékos összes fuguráját a 
	 * tábláról és utána megnézzük, hogy játékos figuráinak lekérdezése valóban üres 
	 * listát ad-e vissza. Ez fontos funkció, mert innen tudjuk, hogy veszített-e a játékos.
	 */
	@Test
	public void deletePiecesTest() {
		testBoard.deletePiece(1, 3);
		testBoard.deletePiece(3, 3);
		ArrayList<Piece> testPieceList = testBoard.getPiecesOfPlayer(testPlayer2);
		Assert.assertTrue(testPieceList.isEmpty());
	}
	
	/**
	 * Ellenőrizzük, hogy a kivételeket dobó metódusok valóban jelzik-e, 
	 * ha érvénytelen koordinátákra érkezik hívás. 
	 */
	@Test
	public void outOfBoundsTest() {
		boolean outOfBound = false;
		
		try {
			testBoard.deletePiece(9, 10);
		} catch(Exception e) {
			outOfBound = true;
		}

		Assert.assertTrue(outOfBound);
	}
	
	/**
	 * Ellenőrizzük, hogy ha vezérré állítjuk az egyik fehér figurát
	 * akkor valóban a fehér vezér képét adja-e vissza.
	 */
	@Test
	public void setQueenTest() {
		testBoard.getPiece(2, 0).setQueen();
		Assert.assertEquals(testBoard.getPiece(2, 0).getImage(), "wK.png");
	}
}
