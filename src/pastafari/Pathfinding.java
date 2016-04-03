package pastafari;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.Stack;

import pastafari.structures.BuildingType;
import pastafari.units.Unit;
import pastafari.units.UnitType;

public class Pathfinding 
{
	public class Label implements Comparable<Label>
	{
		public int pathCost;
		public int heuristicCost;
		public Tile tile;
		public boolean marked;
		public Label(Tile tile)
		{
			this.tile = tile;
			this.marked = false;
		}
		
		public int getTotalCost() { return pathCost + heuristicCost; }

		@Override
		public int compareTo(Label o) {
			return this.getTotalCost() - o.getTotalCost();
		}
	}
	
	private GameServer srv;
	
	public Pathfinding(GameServer srv)
	{
		this.srv = srv;
	}
	
	public int getHeuristicCost(Tile src, Tile dst)
	{
		return Grid.getDistance(src, dst) * 1;
	}
	
	public int getCost(Tile t)
	{
		int div = 1;
		if(t.getBuildingType() == BuildingType.ROAD) {
			div = 2;
		}
		
		switch(t.getType())
		{
		case RIVER:
			if(t.getBuildingType() == BuildingType.BRIDGE)
				return 4;
			return 8 / div;
		case FOREST:
			return 2 / div;
		case LOWLAND:
			return 4 / div;
		case MOUNTAIN:
			return 6 / div;
		}
		return 1000;
	}
	
	/**
	 * Obtient la composante connexe accessible par l'unité donnée.
	 * @param from
	 * @return
	 */
	public List<Tile> GetCCL(Unit from)
	{
		List<Tile> tiles = new ArrayList<Tile>();
		Stack<Tile> stack = new Stack<Tile>();
		HashSet<Tile> closedSet = new HashSet<>();
		stack.push(from.getTile());
		while(!stack.isEmpty())
		{
			Tile t = stack.pop();
			tiles.add(t);
			
			for(Tile neigh : srv.getGameState().getGrid().getFreeNeighbors(t, true))
			{
				if(closedSet.contains(neigh))
					continue;
				
				if(neigh.getType() == TileType.RIVER)
				{
					if(from.getType() == UnitType.ENGINEER)
					{
						stack.push(neigh);
						closedSet.add(neigh);
					}
				}
				else if(neigh.getType() == TileType.MOUNTAIN)
				{
					if(from.getMaxAction() >= 4)
					{
						stack.push(neigh);
						closedSet.add(neigh);
					}
				}
				else {
					stack.push(neigh);
					closedSet.add(neigh);
				}
					
			}
		}
		return tiles;
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param allowRiver
	 * @return
	 */
	public List<Tile> FindPath(Tile from, Tile to, boolean allowRiver)
	{
		HashMap<Tile, Tile> cameFrom = new HashMap<>();
		PriorityQueue<Label> openset = new PriorityQueue<>();
		HashMap<Tile, Integer> closedSet = new HashMap<>();
		
		Label f = new Label(from);
		f.heuristicCost = getHeuristicCost(from, to);
		openset.add(f);
		
		closedSet.put(from, 0);
		cameFrom.put(from, null);
		while(!openset.isEmpty()) {
			Label currentTile = openset.poll();
			
			if(currentTile.equals(to)) break;
			
			for(Tile neigh : srv.getGameState().getGrid().getFreeNeighbors(currentTile.tile, allowRiver)) {
				int newCost = closedSet.get(currentTile.tile) + getCost(neigh);
				if(!closedSet.containsKey(neigh) || newCost < closedSet.get(neigh)) {
					closedSet.put(neigh, newCost);
					Label label = new Label(neigh);
					label.heuristicCost = getHeuristicCost(to, neigh);
					label.pathCost = newCost;
					openset.add(label);
					cameFrom.put(neigh, currentTile.tile);
				}
			}
		}
		
		
		List<Tile> tiles = new ArrayList<>();
		Tile current = to;
		while(current != from)
		{
			tiles.add(current);
			current = cameFrom.get(current);
		}
		
		return tiles;
	}
	
	
}
