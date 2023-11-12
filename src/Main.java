public class Main {
	public static void main(String[] args) {
		Player player1 = new Player(+1, "Kincso", false);
		Player player2 = new Player(-1, "apa", false);
		
		Play play = new Play(8, 12, player1, player2, player1);
		
		Grid newGrid = new Grid(play);
		newGrid.paintGrid();
		newGrid.paintBoard();
		
		System.out.println("end");
	}
}
