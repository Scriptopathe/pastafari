package pastafari;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Stream;

import pastafari.structures.Building;
import pastafari.structures.BuildingType;
import pastafari.structures.City;

public class Grid {
	private Tile[][] tiles;
	private int size;


	public Grid(int size) {
		this.tiles = new Tile[size][size];
		this.size = size;
	}
	
	public Tile getTile(int x, int y) {
		return this.tiles[x][y];
	}
	
	public City getCity()
	{
		for(int x = 0; x < size; x++)
		{
			for(int y = 0; y < size; y++)
			{
				Building b = tiles[x][y].getBuilding();
				if(b != null && b.getType() == BuildingType.CITY)
					return (City)b;
					
			}
		}
		System.out.println("Grid.getCity(): no city found !!");
		return null;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setTile(int x, int y, Tile tile) {
		this.tiles[x][y] = tile;
	}

	
	public ArrayList<Tile> getNeighbors(Tile tile, boolean allowRiver) {
		ArrayList<Tile> neighs = new ArrayList<>();
		for(int i = -1; i <= 1; i ++) {
			for(int j = -1; j <= -1; j++) {
				if(i == 0 && j == 0) continue;
				
				int x = tile.getX() + i;
				int y = tile.getY() + j;
				
				if(x < 0 || x >= this.size || y < 0 || y >= this.size) continue;
				
				Tile t = this.tiles[x][y];
				if((t.getType() == TileType.RIVER && allowRiver) || t.getType() != TileType.RIVER) {
					neighs.add(t);
				}
			}
		}
		return neighs;
	}
	
	public static int getDistance(Tile from, Tile to) {
		return Math.max(Math.abs(from.getX()-to.getX()), Math.abs(from.getY()-to.getY()));
	}
	
	public double[][] getPeasantMatrice(){
		double result[][] = new double [this.size][this.size];
		
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				if (!this.tiles[i][j].getOwner().isMe() && !(this.tiles[i][j].getType() == TileType.RIVER))
					result[i][j] = 1;
				else
					result[i][j] = 0;
			}
		}

		double coeffs[][] = new double [this.size][this.size];
		
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++)
			if(result[i][j] > 0) {
				LinkedList<int[]> q = new LinkedList<>();
				double tmp[][] = new double [this.size][this.size];
				tmp[i][j] = result[i][j];
				q.add(new int[]{i, j});
				while (!q.isEmpty()){
					int p[] = q.pop();
					for (int k = -1; k < 2; k++){
						for (int l = -1; l < 2; l++){
							if (p[0] + k >= 0 && p[0] + k < this.size && p[1] + l >= 0 && p[1] + l < this.size){
								if (tmp[p[0] + k][p[1] + l] == 0 && this.tiles[p[0] + k][p[1] + l].isAccessible(false)){
									tmp[p[0] + k][p[1] + l] = tmp[i][j] * 0.7;
									q.add(new int[]{p[0] + k, p[1] + l});
								}
							}
						}
					}
					//System.out.println("p(0)=" + p[0] + ", p(1)=" + p[1] + "tmp=" + tmp[p[0] + 0][p[1] + 0]);
				}
				for (int k = 0; k < this.size; k++){
					for (int l = 0; l < this.size; l++){
						coeffs[k][l] += tmp[k][l];
					}
				}
			}
		}
		
		return coeffs;
	}
}
