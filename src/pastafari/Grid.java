package pastafari;

public class Grid {
	private Tile[][] tiles;

	public Grid(int width, int height) {
		// TODO Auto-generated constructor stub
		this.tiles = new Tile[width][height];
	}
	
	public Tile getTile(int x, int y) {
		return this.tiles[x][y];
	}
	
	public void setTile(int x, int y, Tile tile) {
		this.tiles[x][y] = tile;
	}
	
	public void getMovingCost(Tile from, Tile to) {
		return;
	}
	
	public static double getDistance(Tile from, Tile to) {
		return Math.sqrt(Math.pow((from.getX() - to.getX()), 2) + Math.pow(from.getY()- to.getY(), 2));
	}
}