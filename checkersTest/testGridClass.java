import java.awt.Color;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class testGridClass {
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
			
			testPlay = new Play(4, 2, testPlayer1, testPlayer2, testPlayer1);
			testPlay.setCurrPlayer(testPlayer1);
			testPlay.getBoard().printBoard();
			newGrid = new Grid(testPlay);
			newGrid.buildBoardPanel();
			newGrid.paintBoard();
		}
	
	/**
	 * Teszteljük, hogy ha virtuálisan kiválasztunk a “UI-on” egy mezőt, 
	 * akkor a Grid osztály vajon jól átállítja-e azon szomszédos JButton elemek 
	 * “színét” zöldre, ahova onnan lehet lépni és azt a szomszédot nem, ahova nem léphetünk.
	 * Tényleges rajzolás nem terténik, csak a Swing elemek tulajdonságait állítja a 
	 * Grid osztály és a tesztben ezeket ellőrizzük.
	 */
	@Test
	public void testTargetSelection() {
		Square selectedSquare = newGrid.getSquare(2, 0);
		String imageName = selectedSquare.getPiece().getImage();
		
		Assert.assertEquals(imageName, "wM.png");
		
		Assert.assertTrue(newGrid.collectTargetSquares(selectedSquare));
	
		//nézzük meg, megszínezte-e az (1,1) és (3,1) mezőt
		Assert.assertEquals(newGrid.getSquare(1, 1).getBackground(), Color.green);
		Assert.assertEquals(newGrid.getSquare(3, 1).getBackground(), Color.green);
		
		//a (2,1)-nek nem lehet zöld színe
		Assert.assertNotEquals(newGrid.getSquare(2, 1).getBackground(), Color.green);
	}
	
	/**
	 * Ütéskényszer miatt, ha a felhasználó olyan figurát választ, amelyik nem tud ütni, 
	 * akkor a Grid (a Play-ben futtatott szabályok szerint) megszínezi sárgára azokat a 
	 * figurákat, amik tudnának ütni, ezért azokkal kell majd lépni. 
	 * Ebben a tesztesetben a jobb oldalit választja a “felhasználó”, de a program a 
	 * másikat sárga hátterűre színezi, hogy jelezze, azzal kötelező ütni. 
	 * A tesztesetben a megfelelő állás kialakításához teszteljük a Grid osztály 
	 * makeMove metódusát is.
	 */
	@Test
	public void testObligatedCapture() {
		newGrid.makeMove(newGrid.getSquare(3, 3), newGrid.getSquare(2, 2));
		newGrid.makeMove(newGrid.getSquare(2, 2), newGrid.getSquare(1, 1));
		//teszteljük, hogy a makeMove jól mozgatta-e a figurát
		Assert.assertEquals(newGrid.getSquare(1, 1).getPiece().getPlayer(), testPlayer2);
		
		newGrid.makeMove(newGrid.getSquare(2, 0), newGrid.getSquare(3, 1));
		//teszteljük, hogy a makeMove jól mozgatta-e a figurát
		Assert.assertEquals(newGrid.getSquare(3, 1).getPiece().getPlayer(), testPlayer1);
		Assert.assertEquals(newGrid.getSquare(2, 0).getPiece(), null);
		
		Square selectedSquare = newGrid.getSquare(3, 1);
		testPlay.setCurrPlayer(testPlayer1);
		
		Assert.assertFalse(newGrid.collectTargetSquares(selectedSquare));

		//nézzük meg, megszínezte-e a (0,0) mezőt, hiszen onnan tud ütni
		Assert.assertEquals(newGrid.getSquare(0, 0).getBackground(), Color.yellow);
		
		//a (2,2)-nek nem lehet zöld színe
		Assert.assertNotEquals(newGrid.getSquare(2, 2).getBackground(), Color.green);
		
	}
	
	/**
	 * Az előző tesztesettel megegyező felállásban, a megfelelő (ütéskényszerben levő) 
	 * figurát kiválasztotta a felhasználó, akkor 
	 * 					- ellenőrizzük, megjelölte-e pirossal azt a mezőt (JButton), 
	 * 						ahova ugrania kell a felhasználónak. 
	 * 					- léptessük a fugurát a piros mezőre és ellőrizzük, 
	 * 						hogy eltűnt-e a köztes ellenfél figura
	 */
	@Test
	public void testCapture() {
		newGrid.makeMove(newGrid.getSquare(3, 3), newGrid.getSquare(2, 2));
		newGrid.makeMove(newGrid.getSquare(2, 2), newGrid.getSquare(1, 1));
		
		newGrid.makeMove(newGrid.getSquare(2, 0), newGrid.getSquare(3, 1));
		
		testPlay.setCurrPlayer(testPlayer1);
		Square selectedSquare = newGrid.getSquare(0, 0);
		
		//tud ütni, ezért true kell legyen és ez a metódus színezi is a mezőket ,ahova ütni
		//vagy lépni lehet
		Assert.assertTrue(newGrid.collectTargetSquares(selectedSquare));

		//nézzük meg, megszínezte-e a (2,2) mezőt, hiszen oda tud ütni
		Assert.assertEquals(newGrid.getSquare(2, 2).getBackground(), Color.red);
		
		//végezzük el az ütést, nézzük meg, eltűnt-e az ellenfél figurája az (1,1)-ről
		newGrid.makeMove(selectedSquare, newGrid.getSquare(2, 2));
		Assert.assertEquals(newGrid.getSquare(1, 1).getPiece(), null);
		
		//ellenőrizzük, hogy a logikai tábláról is eltűnt-e
		Assert.assertEquals(testPlay.getBoard().getPiece(1, 1), null);
	}
}
