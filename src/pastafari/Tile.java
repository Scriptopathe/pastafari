package pastafari;

public class Tile {
	public static enum Type {
		FOREST,
		LOWLAND,
		MOUNTAIN,
		RIVER
	}
	
	private int x;
	private int y;
	private Type type;
	private Player owner;
	
	public Tile(int x, int y, Type type) {
		this.x = x;
		this.y = y;
		this.type = type;
		// TODO Auto-generated constructor stub
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
