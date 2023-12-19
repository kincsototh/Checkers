
import java.awt.Color;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class testGridCaptures {
	Play testPlay;
	Player testPlayer1;
	Player testPlayer2;
	ArrayList<Piece> piecesOfPlayer1;
	ArrayList<Piece> piecesOfPlayer2;
	Grid newGrid;
	
	@Before
	public void setUp()  {
		testPlayer1 = new Player(Player.PLAYERONE, "testPlayerOne", false, "w");
		testPlayer2 = new Player(Player.PLAYERTWO, "testPlayerTwo", false, "m");
		
		testPlay = new Play(6, 4, testPlayer1, testPlayer2, testPlayer1);
		testPlay.setCurrPlayer(testPlayer1);
		testPlay.getBoard().printBoard();
		newGrid = new Grid(testPlay);
		newGrid.buildBoardPanel();
		newGrid.paintBoard();
	}

	/**
	 * Ha rákattintunk az (1,1) mezőre, összegyűjti és kijelöli-e pirossal a (3,3) mezőt, 
	 * hiszen oda fog tudni ütni az (1,1)-en álló figura. 
	 */
	@Test
	public void testMultipleCaptures() {
		newGrid.makeMove(newGrid.getSquare(0, 4), newGrid.getSquare(1, 3));
		newGrid.makeMove(newGrid.getSquare(1, 3), newGrid.getSquare(2, 2));
		newGrid.makeMove(newGrid.getSquare(5, 5), newGrid.getSquare(4, 4));
		
		testPlay.setCurrPlayer(testPlayer1);
		Square selectedSquare = newGrid.getSquare(1, 1);
		
		//tud ütni, ezért true kell legyen és ez a metódus színezi is a mezőket ,ahova ütni
		//vagy lépni lehet
		Assert.assertTrue(newGrid.collectTargetSquares(selectedSquare));
		testPlay.getBoard().printBoard();

		//nézzük meg, megszínezte-e a (3,3) mezőt, hiszen oda tud ütni
		Assert.assertEquals(newGrid.getSquare(3, 3).getBackground(), Color.red);
		
		/**
		 * Ezt követően megtesszük a lépést, azaz leütjük a figurát, majd ellenőrizzük, 
		 * a mi figuránk ellépett-e az eredeti helyéről grafikusan is, 
		 * le lett-e ütve az ellenfél figurája.
		 */
		newGrid.makeMove(selectedSquare, newGrid.getSquare(3, 3));
		Assert.assertEquals(newGrid.getSquare(1, 1).getPiece(), null);
		
		//ellenőrizzük, hogy az ellenfél figurája eltűnt-e
		Assert.assertEquals(testPlay.getBoard().getPiece(2, 2), null);
		
		//ellenőrizzük, hogy piros lett-e az, ahova tovább kell ütni
		Assert.assertEquals(newGrid.getSquare(5, 5).getBackground(), Color.red);
		
		Assert.assertEquals(testPlay.getCurrPlayer(), testPlayer1);
		
		selectedSquare = newGrid.getSquare(3, 3);
				
		/**
		 * Megtesszük a lépést, majd ellenőrizzük, eltűnt-e az ellenfél figurája a (4,4) mezőről
		 */
		newGrid.makeMove(selectedSquare, newGrid.getSquare(5, 5));
		Assert.assertEquals(newGrid.getSquare(4, 4).getPiece(), null);
	}

}
