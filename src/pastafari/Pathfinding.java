package pastafari;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;

import pastafari.structures.BuildingType;

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
		return Grid.getDistance(src, dst) * 4;
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
	
	public List<Tile> FindPath(Tile from, Tile to, boolean allowRiver)
	{
		HashMap<Tile, Tile> cameFrom = new HashMap<>();
		PriorityQueue<Label> openset = new PriorityQueue<>();
		HashMap<Tile, Integer> closedSet = new HashMap<>();
		
		Label f = new Label(from);
		f.heuristicCost = getHeuristicCost(from, to);
		openset.add(f);
		while(!openset.isEmpty())
		{
			Label next = openset.poll();
			closedSet.put(next.tile, next.getTotalCost());

			if(next.tile == to)
			{
				break;
			}
			
			List<Tile> neighboors = srv.getGameState().getGrid().getNeighbors(next.tile, allowRiver);
			for(Tile neighboor : neighboors)
			{
				if(neighboor == next.tile)
				{ 
					System.out.println("nononono");
				}
				int cost = next.pathCost + getCost(neighboor);
				int heuristicCost = getHeuristicCost(next.tile, neighboor);
				int totalCost = cost + heuristicCost;
				if(closedSet.containsKey(neighboor))
				{
					int oldCost = closedSet.get(neighboor);
					// Mise à jour si besoin
					if(totalCost < oldCost)
					{
						closedSet.put(neighboor, totalCost);
						Label label = new Label(neighboor);
						label.heuristicCost = heuristicCost;
						label.pathCost = cost;
						cameFrom.put(label.tile, next.tile);
					}
				}
				else
				{
					Label label = new Label(neighboor);
					label.heuristicCost = heuristicCost;
					label.pathCost = cost;
					openset.add(label);
					cameFrom.put(label.tile, next.tile);
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
