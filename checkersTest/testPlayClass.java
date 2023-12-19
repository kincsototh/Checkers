import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class testPlayClass {
	Play testPlay;
	Player testPlayer1;
	Player testPlayer2;
	ArrayList<Piece> piecesOfPlayer1;
	ArrayList<Piece> piecesOfPlayer2;
	
	@Before
	public void setUp()  {
		testPlayer1 = new Player(Player.PLAYERONE, "testPlayerOne", false, "w");
		testPlayer2 = new Player(Player.PLAYERTWO, "testPlayerTwo", false, "m");
		
		testPlay = new Play(4, 2, testPlayer1, testPlayer2, testPlayer1);
		testPlay.setCurrPlayer(testPlayer1);
		testPlay.getBoard().printBoard();
		
	}
	
	/**
	 * Ellenőrizzük, hogy a soron következő játékoshoz visszaadott 
	 * véletlenszerűen választott lépés (Move) egyike-e a teszt esetben megadott 
	 * referencia lépéseknek, amiket egy Move listában helyezünk el.
	 */
	@Test
	public void getRandomMoveTest() {
		Move testMove1 = new Move(2, 0, 1, 1);
		Move testMove2 = new Move(2, 0, 3, 1);
		Move testMove3 = new Move(0, 0, 1, 1);
		
		ArrayList<Move> testMoveList = new ArrayList<Move>(
				List.of(testMove1, testMove2, testMove3)
		);
		
		for(int counter = 0; counter < 10; counter++) {
			Move move = testPlay.getRandomMove();
		
			boolean contains = false;
		
			for(int i = 0; i < testMoveList.size(); i++) {
				if(testMoveList.get(i).isEquals(move)) {
					contains = true;
				}
			}
		
			Assert.assertTrue(contains);
		}
	}
	
	/**
	 * Ütéskényszer teszt: Ha lehet ütni valamelyik figurával, akkor a getRandomMove 
	 * nem adhat vissza lépést, hanem csak ütéses ugrás típusú Move-ot. 
	 * Ehhez a metódus meghívása előtt az ellenfél figuráját átmozgatjuk olyan helyre, 
	 * hogy le lehessen ütni azt, akár két lehetséges figurával is. 
	 * Az X-szel jelzett mezőre (dokumentáció) való mozgást nem adhatja vissza 
	 * az ütéskényszer miatt
	 */
	@Test
	public void getRandomCaptureTest() {
		Move testCapture1 = new Move(2, 0, 0, 2);
		Move testCapture2 = new Move(0, 0, 2, 2);
		
		Move firstMove = new Move(3, 3, 2, 2);
		Move secondMove = new Move(2, 2, 1, 1);
		
		testPlay.makeMove(firstMove);
		testPlay.makeMove(secondMove);
		
		testPlay.getBoard().printBoard();
		
		ArrayList<Move> testMoveList = new ArrayList<Move>(
				List.of(testCapture1, testCapture2)
		);
		
		boolean contains = false;
		
		for(int counter = 0; counter < 10; counter++) {
			Move move = testPlay.getRandomMove();
			move.printMove();
			
			for(int i = 0; i < testMoveList.size(); i++) {
				if(testMoveList.get(i).isEquals(move)) {
					contains = true;
				}
			}
	
			Assert.assertTrue(contains);
		}
	}

	/**
	 * Bezártság teszt: Itt egy olyan táblát építünk, amelyiknek minden mezőjén van figura, 
	 * így nem lehet lépni. 
	 * Az az elvárt, hogy a getRandomMove null-lal tér vissza, mert nincs megléphető lépés.
	 */
	@Test
	public void blockedTest() {
		testPlay.getBoard().initBoard(4, 4, testPlayer1, testPlayer2);
		testPlay.setCurrPlayer(testPlayer1);
		testPlay.getBoard().printBoard();
		
		Assert.assertFalse(testPlay.canMove(testPlayer1));
		
		Move move = testPlay.getRandomMove();

		Assert.assertTrue(move == null);

	}

	/**
	 * Teszteljük, hogy a vezér visszafelé is léphet-e. 
	 * Teszteljük, hogy a Play osztály canGo metódusa üres listát ad, 
	 * amíg nem vezér az adott fugura, és miután átváltoztatjuk azzá, utána már visszaadja 
	 * a két lehetséges visszairányú lépést
	 */
	@Test
	public void queenTest() {
		Move testMove1 = new Move(1, 3, 0, 2);
		Move testMove2 = new Move(0, 2, 1, 1);
		
		testPlay.makeMove(testMove1);
		testPlay.makeMove(testMove2);
		
		ArrayList<Move> moves = testPlay.canGo(1, 1);
		
		Assert.assertTrue(moves.isEmpty());
		
		testPlay.getBoard().getPiece(1, 1).setQueen();
		
		Move testValidMove1 = new Move(1, 1, 0, 2);
		Move testValidMove2 = new Move(1, 1, 2, 2);
	
		ArrayList<Move> testMoveList = new ArrayList<Move>(
				List.of(testValidMove1, testValidMove2)
				);
		
		moves = testPlay.canGo(1, 1);
		
		int contains = 0;
		
		for(int i = 0; i < testMoveList.size(); i++) {
			for(int j = 0; j < moves.size(); j++) {
				if(testMoveList.get(i).isEquals(moves.get(j))) {
					contains++;
					testMoveList.remove(i);
				}
			}
		}

		Assert.assertTrue(contains == 2);

	}
	
	/**
	 * Teszteljük, hogy a vezér visszafelé is tud ütni. 
	 * Ilyenkor a Play osztály canCapture metódusát a vezér mezőjére futtatva csak 
	 * 1 lehetséges mozgást ad vissza, mégpedig az ütést a jobb felső sarokba, (3, 3) mező
	 */
	@Test
	public void queenCaptureTest() {
		Move testMove1 = new Move(3, 3, 2, 2);
		Move testMove2 = new Move(2, 2, 1, 1);
		
		Move testMove3 = new Move(2, 0, 3, 1);
		Move testMove4 = new Move(3, 1, 2, 2);
		
		testPlay.makeMove(testMove1);
		testPlay.makeMove(testMove2);
		testPlay.makeMove(testMove3);
		testPlay.makeMove(testMove4);
		
		ArrayList<Move> moves = testPlay.canCapture(1, 1);
		
		Assert.assertTrue(moves.isEmpty());
		
		testPlay.getBoard().getPiece(1, 1).setQueen();
		
		Move testValidMove1 = new Move(1, 1, 3, 3);
	
		ArrayList<Move> testMoveList = new ArrayList<Move>(
				List.of(testValidMove1)
				);
		
		moves = testPlay.canCapture(1, 1);
		
		int contains = 0;
		
		for(int i = 0; i < testMoveList.size(); i++) {
			for(int j = 0; j < moves.size(); j++) {
				if(testMoveList.get(i).isEquals(moves.get(j))) {
					contains++;
					testMoveList.remove(i);
				}
			}
		}

		Assert.assertTrue(contains == 1);

	}
	
}
