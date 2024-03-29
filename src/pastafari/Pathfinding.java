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
	
	public List<Tile> GetCCL(Unit from)
	{
		return GetCCL(from.getTile(), from.getType(), true, true);
	}
	/**
	 * Obtient la composante connexe accessible par l'unit� donn�e.
	 * @param from
	 * @return
	 */
	public List<Tile> GetCCL(Tile from, UnitType unitType, boolean allowRiver, boolean ignoreEnnemy)
	{
		List<Tile> tiles = new ArrayList<Tile>();
		Stack<Tile> stack = new Stack<Tile>();
		HashSet<Tile> closedSet = new HashSet<>();
		stack.push(from);
		while(!stack.isEmpty())
		{
			Tile t = stack.pop();
			tiles.add(t);
			
			for(Tile neigh : srv.getGameState().getGrid().getFreeNeighbors(t, true, ignoreEnnemy))
			{
				if(closedSet.contains(neigh))
					continue;
				
				if(neigh.getType() == TileType.RIVER)
				{
					if(unitType == UnitType.ENGINEER || neigh.getBuildingType() == BuildingType.BRIDGE)
					{
						stack.push(neigh);
						closedSet.add(neigh);
					}
				}
				else if(neigh.getType() == TileType.MOUNTAIN)
				{
					if(unitType == UnitType.DWARF || unitType == UnitType.PEASANT || unitType == UnitType.BALLISTA)
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
	 * Retourne la position d'attaque optimale de ally vers ennemy.
	 * Si aucune position d'attaque n'est trouv�e, retourne null.
	 * @param ally
	 * @param ennemy
	 * @return
	 */
	public Tile getAttackPosition(Unit ally, Tile ennemy, boolean allowRiver, boolean ignoreEnnemy)
	{
		List<Tile> ccl = GetCCL(ally);
		List<Tile> path;
		Tile tile = null;
		int actionCost = Integer.MAX_VALUE;
		for(Tile t : ccl)
		{
			List<Tile> p = FindPath(ally.getTile(), ennemy, allowRiver, ignoreEnnemy);
			int cost = Grid.getMoveCost(p);
			if(cost < actionCost && Grid.getDistance(p.get(p.size() - 1), ennemy) < ally.getRange())
			{
				actionCost = cost;
				path = p;
				tile = t;
			}
		}
		
		return tile;
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @param allowRiver
	 * @return
	 */
	public List<Tile> FindPath(Tile from, Tile to, boolean allowRiver, boolean ignoreEnnemy)
	{
		HashMap<Tile, Tile> cameFrom = new HashMap<>();
		PriorityQueue<Label> openset = new PriorityQueue<>();
		HashMap<Tile, Integer> closedSet = new HashMap<>();
		boolean found = false;
		Label f = new Label(from);
		f.heuristicCost = getHeuristicCost(from, to);
		openset.add(f);
		
		closedSet.put(from, 0);
		cameFrom.put(from, null);
		while(!openset.isEmpty()) {
			Label currentTile = openset.poll();
			
			if(currentTile.tile.equals(to))
			{
				found = true;
				break;
			}
			
			for(Tile neigh : srv.getGameState().getGrid().getFreeNeighbors(currentTile.tile, allowRiver, ignoreEnnemy)) {

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
		if(found)
		{
			Tile current = to;
			while(!current.equals(from))
			{
				tiles.add(0, current);
				current = cameFrom.get(current);
			}
		}

		
		return tiles;
	}
	
	
	double getEuclidianDst(Tile t1, Tile t2)
	{
		return Math.sqrt(t1.getX()*t2.getX() + t1.getY()*t2.getY());
	}
	
	/**
	 * Co�t heuristique bizarre mais cool il parait.
	 */
	public int getChelouHeuristicCost(Tile t1, Tile src, Tile t2)
	{
		int a = t1.getY() - t2.getY();
		int b = t2.getX() - t1.getX();
		int c = t1.getX()*t2.getY() - t2.getX()*t2.getY();
		double dist = (Math.abs(a*src.getX() + b*src.getY() + c) / Math.sqrt(1+ a*a + b*b));
		double percent = getEuclidianDst(src, t2) / getEuclidianDst(t1, t2);
		dist = 5*Math.max(0, percent*(7 - dist));
		return (int)dist + Grid.getDistance(src, t2) * 1;
	}
	
	/**
	 * Version modifi�e de l'A* avec une heuristique bizarre.
	 * @return
	 */
	public List<Tile> FindPathForPGG(Tile from, Tile to, boolean allowRiver, boolean ignoreEnnemy)
	{
		HashMap<Tile, Tile> cameFrom = new HashMap<>();
		PriorityQueue<Label> openset = new PriorityQueue<>();
		HashMap<Tile, Integer> closedSet = new HashMap<>();
		boolean found = false;
		Label f = new Label(from);
		f.heuristicCost = getHeuristicCost(from, to);
		openset.add(f);
		
		closedSet.put(from, 0);
		cameFrom.put(from, null);
		while(!openset.isEmpty()) {
			Label currentTile = openset.poll();
			
			if(currentTile.tile.equals(to))
			{
				found = true;
				break;
			}
			
			for(Tile neigh : srv.getGameState().getGrid().getFreeNeighbors(currentTile.tile, allowRiver, ignoreEnnemy)) {

				int newCost = closedSet.get(currentTile.tile) + getCost(neigh);
				if(!closedSet.containsKey(neigh) || newCost < closedSet.get(neigh)) {
					closedSet.put(neigh, newCost);
					Label label = new Label(neigh);
					label.heuristicCost = getChelouHeuristicCost(from, to, neigh);
					label.pathCost = newCost;
					openset.add(label);
					cameFrom.put(neigh, currentTile.tile);
				}
			}
		}
		
		
		List<Tile> tiles = new ArrayList<>();
		if(found)
		{
			Tile current = to;
			while(!current.equals(from))
			{
				tiles.add(0, current);
				current = cameFrom.get(current);
			}
		}

		
		return tiles;
	}
	
	/**
	 * Trouve un chemin r�ellement faisable.
	 * @param from
	 * @param to
	 * @param allowRiver
	 * @param ignoreEnnemy
	 * @return
	 */
	public List<Tile> FindRealPath(Tile from, Tile to, boolean allowRiver)
	{
		HashMap<Tile, Tile> cameFrom = new HashMap<>();
		PriorityQueue<Label> openset = new PriorityQueue<>();
		HashMap<Tile, Integer> closedSet = new HashMap<>();
		boolean found = false;
		Label f = new Label(from);
		f.heuristicCost = getHeuristicCost(from, to);
		openset.add(f);
		
		closedSet.put(from, 0);
		cameFrom.put(from, null);
		while(!openset.isEmpty()) {
			Label currentTile = openset.poll();
			
			if(currentTile.tile.equals(to))
			{
				found = true;
				break;
			}
			
			for(Tile neigh : srv.getGameState().getGrid().getFreeNeighbors(currentTile.tile, allowRiver, false)) {

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
		if(found)
		{
			Tile current = to;
			while(!current.equals(from))
			{
				tiles.add(0, current);
				current = cameFrom.get(current);
			}
		}

		
		return tiles;
	}
	/**
	 * Trouve un chemin r�ellement faisable.
	 * @param from
	 * @param to
	 * @param allowRiver
	 * @param ignoreEnnemy
	 * @return
	 */
	public List<Tile> FindBestPath(Tile from, Tile to, boolean allowRiver)
	{
		HashMap<Tile, Tile> cameFrom = new HashMap<>();
		PriorityQueue<Label> openset = new PriorityQueue<>();
		HashMap<Tile, Integer> closedSet = new HashMap<>();
		boolean found = false;
		Label f = new Label(from);
		f.heuristicCost = getHeuristicCost(from, to);
		openset.add(f);
		
		Label candidate = f;
		
		closedSet.put(from, 0);
		cameFrom.put(from, null);
		while(!openset.isEmpty()) {
			Label currentTile = openset.poll();
			
			if(currentTile.tile.equals(to))
			{
				found = true;
				break;
			}
			else
			{
				if(currentTile.heuristicCost < candidate.heuristicCost)
				{
					candidate = currentTile;
				}
			}
			
			for(Tile neigh : srv.getGameState().getGrid().getFreeNeighbors(currentTile.tile, allowRiver, false)) {

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
		if(!found)
		{
			to = candidate.tile;
		}
		
		Tile current = to;
		while(!current.equals(from))
		{
			tiles.add(0, current);
			current = cameFrom.get(current);
		}
		

		
		return tiles;
	}
}
