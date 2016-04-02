package pastafari;

import java.util.LinkedList;

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
				if (this.tiles[i][j].getOwner() == 0 /* c'est moi */ && !(this.tiles[i][j].getType() == Tile.Type.RIVER))
					result[i][j] = 1;
				else
					result[i][j] = 0;
			}
		}

		double result[][] = new double [this.size][this.size];
		
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				LinkedList<int[]> q = new LinkedList<>();
				double tmp[][] = new double [this.size][this.size];
				while (!q.isEmpty()){
					int p[] = q.pop();
					for (int k = -1; k < 2; k++){
						for (int l = -1; l < 2; l++){
							if (p[0] + k >= 0 && p[0] + k < this.size && p[1] + l >= 0 && p[1] + l < this.size){
								if (tmp[p[0] + k][p[1] + l] == 0 && this.tiles[p[0] + k][p[1] + l].getType() != Tile.Type.RIVER){
									
								}
							}
						}
					}
					tmp[p[0]][p[1]] = 
				}
				result[i][j] = 0;
			}
		}
		
		return result;
	}
}
