package pastafari;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pastafari.structures.Building;
import pastafari.structures.BuildingType;
import pastafari.structures.City;
import pastafari.units.Unit;
import pastafari.units.UnitType;

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
	
	public City getCity(int player)
	{
		for(int x = 0; x < size; x++)
		{
			for(int y = 0; y < size; y++)
			{
				Building b = tiles[x][y].getBuilding();
				if(b != null && b.getType() == BuildingType.CITY && tiles[x][y].getOwner().getId() == player)
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

	/**
	 * Obtient la liste des voisins du tile potentiellement accessibles pouvant contenir
	 * une unité.
	 * @param tile
	 * @param allowRiver
	 * @return
	 */
	public ArrayList<Tile> getNeighbors(Tile tile, boolean allowRiver) {
		ArrayList<Tile> neighs = new ArrayList<>();
		for(int i = Math.max(tile.getX() - 1, 0); i <= Math.min(tile.getX()+1, this.size - 1); i++) {
			for(int j = Math.max(tile.getY() - 1, 0); j <= Math.min(tile.getY()+1, this.size - 1); j++) {
				if(i == tile.getX() && j == tile.getY()) continue;
				Tile t = this.tiles[i][j];
				if((t.getType() == TileType.RIVER && allowRiver) || t.getType() != TileType.RIVER) {
					neighs.add(t);
				}
			}
		}
		return neighs;
	}
	
	/** 
	 * Obtient les voisins qui sont réellement accessibles.
	 * N'autorise pas les voisins avec unité.
	 * @param tile
	 * @param allowRiver
	 * @return
	 */
	public ArrayList<Tile> getFreeNeighbors(Tile tile, boolean allowRiver, boolean ignoreEnnemy) {
		ArrayList<Tile> neighs = new ArrayList<>();
		for(int i = Math.max(tile.getX() - 1, 0); i <= Math.min(tile.getX()+1, this.size - 1); i++) {
			for(int j = Math.max(tile.getY() - 1, 0); j <= Math.min(tile.getY()+1, this.size - 1); j++) {
				if(i == tile.getX() && j == tile.getY()) continue;
				Tile t = this.tiles[i][j];
				if((t.getUnitType() == UnitType.VOID || (ignoreEnnemy && !t.getUnit().getPlayer().isMe())) && ((t.getType() == TileType.RIVER && allowRiver) || t.getType() != TileType.RIVER)) {
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
		int actionPeasant = 2;
		double peak[][] = new double [this.size][this.size];
		
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				if (!this.tiles[i][j].getOwner().isMe() && canMove(false, actionPeasant, tiles[i][j]))
					peak[i][j] = 1;
				else
					peak[i][j] = 0;
			}
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
				if(tmp[k][l] == 0 && canMove(false, actionPeasant, this.tiles[k][l])) {
					tmp[k][l] = tmp[p[0]][p[1]] * 0.7;
					q.add(new int[]{k, l});
				}
			}
			
			for(int k = 0; k < this.size; k++)
			for(int l = 0; l < this.size; l++)
				result[k][l] += tmp[k][l];
		}
		
		return result;
	}
	
	public void display() {
		System.out.println(String.join("", Collections.nCopies(this.size * 15, "-")));

		for(int i = 0; i < this.size; i++) {
			StringBuilder buffer = new StringBuilder();
			buffer.append("|");
			for(int j = 0; j < this.size; j++) {
				Tile tile = this.tiles[j][i];
				
				if(tile.getOwner().getId() >= 0) buffer.append(" ");
				
				buffer.append(tile.getOwner().getId()); buffer.append(";");
				buffer.append(Building.getBuildingCode(tile.getBuildingType())); buffer.append(";");
				buffer.append(Unit.getCharCode(tile.getUnitType())); buffer.append(";");
				buffer.append("(").append(tile.getX()).append(",").append(tile.getY()).append(")").append(";");
				buffer.append(tile.getType().toString().charAt(0)); buffer.append("|");
			}
			System.out.println(buffer.toString());
			System.out.println(String.join("", Collections.nCopies(this.size * 15, "-")));
		}
	}

	
	/** Retourne le cout requis pour parcourir le chemin donnï¿½ */
	public static int getMoveCost(List<Tile> path)
	{
		int cost = 0;
		for(Tile t : path)
		{
			cost += getMoveCost(t, t);
		}
		return cost;
	}
	
	public static int getMoveCost(Tile from, Tile to) {
		if(Grid.getDistance(from, to) > 1) return Integer.MAX_VALUE;
		if(to.getUnitType() != UnitType.VOID) return Integer.MAX_VALUE;
		if(to.getType() == TileType.RIVER && !(from.getUnitType() == UnitType.ENGINEER || to.getBuildingType() == BuildingType.BRIDGE)) return Integer.MAX_VALUE;
		
		int cost = 2;
		
		if(to.getType() == TileType.MOUNTAIN) cost += 2;
		if(to.getBuildingType() == BuildingType.ROAD) cost /= 2;
		
		return cost;
	}
	
	public static boolean canMove(boolean isEngineer, int action, Tile to) {
		if(to.getUnitType() != UnitType.VOID) return false;
		if(to.getType() == TileType.RIVER && !(isEngineer || to.getBuildingType() == BuildingType.BRIDGE)) return false;
		
		int cost = 2;
		
		if(to.getType() == TileType.MOUNTAIN) cost += 2;
		if(to.getBuildingType() == BuildingType.ROAD) cost /= 2;
		
		return cost <= action;
	}

}
