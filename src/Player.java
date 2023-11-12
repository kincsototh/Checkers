public class Player {
	//Az ID igazából a "színe" is 
	private int ID;
	private String name;
	private boolean isComputer;
	
	public Player(int id, String n, boolean comp) {
		this.ID = id;
		this.name = n;
		this.isComputer = comp;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isComputer() {
		return this.isComputer;
	}
}
