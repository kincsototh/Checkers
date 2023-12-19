import java.io.Serializable;

@SuppressWarnings("serial")
public class Player implements Serializable {
	/**
	 * Attribútumok:
	   * PLAYERONE, PLAYERTWO A játékosok ID-ja, amelyre szükség van, hogy mindig ismerjük.
	   * ID: int, Játékos ID-ja, amely lehet PLAYERONE (azaz 1) vagy PLAYERTWO (azaz -1)
	   * name: String, Játékos nevét tárolja, nekünk ez a grafikai megjelenítéskor lesz fontos, 
	   * hogy a program jelezze, ki következik.
	   * isComputer: boolean, Megadja, egy játékos úgymond gép-e, 
	   * ez alapján tudjuk majd eldönteni, ember kell lépjen, 
	   * vagy pedig a program kell-e generáljon egy lépést. 
	   * color: String, Az adott játékos színe, 
	   * mely szintén a grafikai megjelenítéskor lesz hasznos. 
	   */
	public static final int PLAYERONE = 1; 
	public static final int PLAYERTWO = -1;
	
	private int ID;
	private String name;
	private boolean isComputer;
	private String color;
	
	/**
	   * A konstruktor beállítja a játékos ID-ját, nevét, számítógép létét
	   * valamint a színét
	   * @param ID: a játékos egyéni azonosítója, amely 1 vagy -1
	   * @param name: a játékos neve
	   * @param isComputer: a játékos számítógép-e, vagy sem
	   * @param color: a játékos színe, ez csak egy egykarakteres String 
	   */
	public Player(int id, String n, boolean comp, String color) {
		this.ID = id;
		this.name = n;
		this.isComputer = comp;
		this.color = color;
	}
	
	/**
	   * Visszaadja a játékos ID-ját 
	   * @return int Visszatér a játékos ID-jával, amely egy int
	   */
	public int getID() {
		return this.ID;
	}
	
	/**
	   * Visszaadja a játékos nevét
	   * @return String Visszatér a játékos nevével, amely egy String
	   */
	public String getName() {
		return this.name;
	}
	
	/**
	   * Visszaadja, a játékos számítógép-e, vagy sem
	   * @return true - ha az adott játékos számítógép, false - ha a játékos ember
	   */
	public boolean isComputer() {
		return this.isComputer;
	}
	
	/**
	   * Visszaadja a játékos színét
	   * @return String Visszatér a játékos színével, ami egy egykarakteres String
	   */
	public String getColor() {
		return this.color;
	}
}
