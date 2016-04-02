package pastafari;

public class Grid {
	private Tile[][] tiles;
	private int size;


	public Grid(int size) {
		// TODO Auto-generated constructor stub
		this.tiles = new Tile[size][size];
		this.size = size;
	}
	
	public Tile getTile(int x, int y) {
		return this.tiles[x][y];
	}
	
	public int getSize() {
		return size;
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
	
	public double[][] getPeasantMatrice(){
		double result[][] = new double [this.size][this.size];
		
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				
			}
		}
		
		return result;
	}
}
