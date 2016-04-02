package pastafari;

import java.util.ArrayList;
import java.util.LinkedList;

import pastafari.structures.Building;
import pastafari.structures.BuildingType;
import pastafari.structures.City;
import pastafari.units.Peasant;

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
		double peak[][] = new double [this.size][this.size];
		Peasant lambda = new Peasant(0, null, null);
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				if (!this.tiles[i][j].getOwner().isMe() && lambda.canMove(tiles[i][j]))
					peak[i][j] = 1;
				else
					peak[i][j] = 0;
				System.out.print(" "+peak[i][j]);
			}
			System.out.println();
		}

		double result[][] = new double [this.size][this.size];
		LinkedList<int[]> q = new LinkedList<>();
		
		for (int i = 0; i < this.size; i++)
		for (int j = 0; j < this.size; j++)
		if(peak[i][j] > 0) {
			q.clear();
			double tmp[][] = new double [this.size][this.size];
			tmp[i][j] = peak[i][j];
			q.add(new int[]{i, j});
			
			while(!q.isEmpty()) {
				int p[] = q.pop();
				
				// Neighbor
				for (int k = Math.max(p[0]-1, 0); k <= Math.min(p[0]+1, size-1); k++)
				for (int l = Math.max(p[1]-1, 0); l <= Math.min(p[1]+1, size-1); l++)
				if(tmp[k][l] == 0 && lambda.canMove(this.tiles[k][l])){
					tmp[k][l] = tmp[p[0]][p[1]] * 0.7;
					q.add(new int[]{k, l});
				}
				//System.out.println("p(0)=" + p[0] + ", p(1)=" + p[1] + "tmp=" + tmp[p[0] + 0][p[1] + 0]);
			}
			
			for(int k = 0; k < this.size; k++)
			for(int l = 0; l < this.size; l++)
				result[k][l] += tmp[k][l];
		}
		
		return result;
	}
}
